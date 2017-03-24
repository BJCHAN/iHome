package com.tianchuang.ihome_b.fragment;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.Constants;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MainActivity;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.activity.ScanCodeActivity;
import com.tianchuang.ihome_b.activity.TaskSelectActivity;
import com.tianchuang.ihome_b.adapter.MyTaskUnderWayAdapter;
import com.tianchuang.ihome_b.bean.ListBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;

/**
 * Created by Abyss on 2017/3/15.
 * description:我的任务（进行中）
 */

public class MyTaskUnderWayFragment extends BaseRefreshAndLoadMoreFragment<MyTaskUnderWayItemBean, MyTaskUnderWayListBean> implements MyTaskActivity.QrResultListener {

    private MyTaskActivity holdingActivity;

    public static MyTaskUnderWayFragment newInstance() {
        return new MyTaskUnderWayFragment();
    }

    @Override
    protected BaseQuickAdapter initAdapter(ArrayList<MyTaskUnderWayItemBean> mData, MyTaskUnderWayListBean listBean) {
        return new MyTaskUnderWayAdapter(mData);
    }

    private static int currentTaskId = -1;//当前的任务ID



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((MyTaskActivity) getHoldingActivity());
    }

    @Override
    protected void handleBundle() {
        super.handleBundle();
        holdingActivity.setQrResultListener(this);
    }

    @Override
    protected void onListitemClick(MyTaskUnderWayItemBean itemBean) {
        int type = itemBean.getTaskKind();
        if (type == 5) {//查看录入任务详情
            addFragment(MyTaskInputDetailFragment.newInstance(itemBean));
        } else {//控制点
            currentTaskId=itemBean.getTaskId();
            EventBus.getDefault().post(new TaskOpenScanEvent());
        }
    }

    @Override
    protected Observable<MyTaskUnderWayListBean> getNetObservable(int maxId) {
        return MyTaskModel.myTaskUnderWayList(maxId).compose(RxHelper.<MyTaskUnderWayListBean>handleResult());
    }

    @Override
    protected String getEmptyString() {
        return getString(R.string.mytask_under_way_empty_string);
    }

    /**
     * 接受到的二维码信息
     */
    @Override
    public void qrResult(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("taskId", String.valueOf(currentTaskId));
        map.put("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId()));
        map.put("code", code);
        HomePageModel.requestQrCode(map)
                .compose(RxHelper.<ArrayList<QrCodeBean>>handleResult())
                .compose(this.<ArrayList<QrCodeBean>>bindToLifecycle())
                .subscribe(new RxSubscribe<ArrayList<QrCodeBean>>() {
                    @Override
                    protected void _onNext(ArrayList<QrCodeBean> qrCodeBeanlist) {
                        if (qrCodeBeanlist != null && qrCodeBeanlist.size() > 0) {
                            Intent intent = new Intent(getContext(), TaskSelectActivity.class);
                            ListBean listBean = new ListBean();
                            listBean.setQrCodeBeanArrayList(qrCodeBeanlist);
                            intent.putExtra("listBean", listBean);
                            startActivityWithAnim(intent);
                        } else {
                            ToastUtil.showToast(getContext(),"任务为空");
                        }

                        ToastUtil.showToast(getContext(), "请求成功！");
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
