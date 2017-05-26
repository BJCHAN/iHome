package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hitomi.tilibrary.TransferImage;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.FaultDetailAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.event.NotifyHomePageRefreshEvent;
import com.tianchuang.ihome_b.bean.event.TransferLayoutEvent;
import com.tianchuang.ihome_b.bean.model.InnerReportsModel;
import com.tianchuang.ihome_b.bean.recyclerview.ImagesSelectorItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.StringUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事fragment详情(菜单)
 */

public class MenuInnerReportsDetailFragment extends BaseFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_department_name)
    TextView tvDepartmentName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.statusBt)
    Button statusBt;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private ArrayList<String> urls;
    protected TransferImage transferLayout;
    private MenuInnerReportsItemBean info;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inner_reports_detail;
    }

    public static MenuInnerReportsDetailFragment newInstance(MenuInnerReportsItemBean menuInnerReportsItemBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", menuInnerReportsItemBean);
        MenuInnerReportsDetailFragment fragment = new MenuInnerReportsDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        info = ((MenuInnerReportsItemBean) getArguments().getSerializable("info"));
        tvName.setText(StringUtils.getNotNull(info.getPropertyEmployeeRoleVo().getEmployeeName()));
        tvDepartmentName.setText(StringUtils.getNotNull(info.getPropertyEmployeeRoleVo().getDepartmentName()));
        tvContent.setText(StringUtils.getNotNull(info.getContent()));
        statusBt.setText(StringUtils.getNotNull(info.getStatusMsg()));
        urls = new ArrayList<>();
        String photo1Url = info.getPhoto1Url();
        String photo2Url = info.getPhoto2Url();
        String photo3Url = info.getPhoto3Url();
        addUrl(photo1Url);
        addUrl(photo2Url);
        addUrl(photo3Url);
        rvList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvList.addItemDecoration(new ImagesSelectorItemDecoration(10));
        FaultDetailAdapter adapter = new FaultDetailAdapter(R.layout.fault_image_item_holder, urls);
        rvList.setAdapter(adapter);
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                transferLayout = new TransferImage.Builder(getContext())
                        .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black))
                        .setOriginImageList(wrapOriginImageViewList(urls, rvList))
                        .setImageUrlList(urls)
                        .setOriginIndex(position)
                        .create();
                transferLayout.show();
                EventBus.getDefault().post(new TransferLayoutEvent(transferLayout));
            }
        });
    }

    private void addUrl(String photo1Url) {
        if (!TextUtils.isEmpty(photo1Url)) {
            urls.add(photo1Url);
        }
    }

    /**
     * 包装缩略图 ImageView 集合
     *
     * @param imageStrList
     * @param rvList
     * @return
     */
    @NonNull
    protected List<ImageView> wrapOriginImageViewList(List<String> imageStrList, RecyclerView rvList) {
        List<ImageView> originImgList = new ArrayList<>();
        for (int i = 0; i < imageStrList.size(); i++) {
            ImageView thumImg = (ImageView) rvList.getChildAt(i);
            originImgList.add(thumImg);
        }
        return originImgList;
    }

    @OnClick(R.id.statusBt)
    public void onViewClicked() {
        int status = info.getStatus();
        if (status == 0) {
            requestProcessing();//请求处理中
        } else if (status == 1 && info.getProcessEmployeeId() == UserUtil.getUserid()) {
            requestFinished();//请求已完成
        }
    }

    private void requestFinished() {
        InnerReportsModel.INSTANCE.reportsFinished(info.getId())
                .compose(RxHelper.<String>handleResult())
                .compose(this.<String>bindToLifecycle())
                .doOnSubscribe(o ->
                        showProgress()

                )
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        dismissProgress();
                        statusBt.setText("已完成");
                        info.setStatus(info.getStatus() + 1);
                        if (statusChangeListener != null) {
                            statusChangeListener.onStatushanged();
                        }
                        EventBus.getDefault().post(new NotifyHomePageRefreshEvent());
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void requestProcessing() {
        InnerReportsModel.INSTANCE.reportsProcessing(info.getId())
                .compose(RxHelper.<String>handleResult())
                .compose(this.<String>bindToLifecycle())
                .doOnSubscribe(o -> {
                        showProgress();
                    }
                )
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        if (statusChangeListener != null) {
                            statusChangeListener.onStatushanged();
                        }
                        statusBt.setText("处理中");
                        info.setStatus(info.getStatus() + 1);
                        info.setProcessEmployeeId(UserUtil.getUserid());
                        EventBus.getDefault().post(new NotifyHomePageRefreshEvent());
                    }

                    @Override
                    public void _onError(String message) {
                        dismissProgress();
                        ToastUtil.showToast(getContext(), message);

                    }

                    @Override
                    public void onComplete() {

                        dismissProgress();
                    }
                });
    }

    //状态发生改变的接口
    private StatusChangeListener statusChangeListener;

    public void setStatusChangeListener(StatusChangeListener statusChangeListener) {
        this.statusChangeListener = statusChangeListener;
    }

    public interface StatusChangeListener {
        void onStatushanged();
    }
}
