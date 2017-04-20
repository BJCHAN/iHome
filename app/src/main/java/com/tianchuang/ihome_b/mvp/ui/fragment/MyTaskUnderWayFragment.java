package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyTaskUnderWayAdapter;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayListBean;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.ControlPointDetailActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.MyTaskActivity;
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
        return new MyTaskUnderWayAdapter(R.layout.mytask_under_way_item_holder,mData);
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
            currentTaskId=itemBean.getId();
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
        map.put("taskRecordId", String.valueOf(currentTaskId));
        map.put("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId()));
        map.put("code", code);
        requestTaskQrCode(map);
    }

    private void requestTaskQrCode(HashMap<String, String> map) {
        HomePageModel.requestTaskQrCode(map)
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<TaskPointDetailBean>() {
                    @Override
                    protected void _onNext(TaskPointDetailBean detailBean) {
                        if (detailBean != null) {
                            Intent intent = new Intent();
                            intent.setClass(getContext(), ControlPointDetailActivity.class);
                            intent.putExtra("detailBean", detailBean);
                            startActivityWithAnim(intent);
                        } else {
                            ToastUtil.showToast(getContext(),"任务为空");
                        }

                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(),message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });

    }
}
