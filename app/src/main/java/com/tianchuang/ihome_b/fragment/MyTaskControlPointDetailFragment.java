package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskControlPointDetailListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.QrCodeBean;
import com.tianchuang.ihome_b.bean.TaskControlPointDetailBean;
import com.tianchuang.ihome_b.bean.event.TaskFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/20.
 * description:我的任务控制点详情页面
 */

public class MyTaskControlPointDetailFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    private QrCodeBean item;
    private int taskRecordId;
    private List<ControlPointItemBean> mListData;
    private TaskControlPointDetailListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_control_point_detail;
    }

    public static MyTaskControlPointDetailFragment newInstance(QrCodeBean item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        MyTaskControlPointDetailFragment fragment = new MyTaskControlPointDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("任务详情");
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        this.item = (QrCodeBean) getArguments().getSerializable("item");
        taskRecordId = item.getTaskRecordId();
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        layoutManager.setScrollEnabled(false);
        mListData = new ArrayList<>();
        rvList.setLayoutManager(layoutManager);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initListener() {

        //去往提交编辑任务
        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ControlPointItemBean controlPointItemBean = (ControlPointItemBean) adapter.getData().get(position);
                addFragment(TaskControlPointEditFragment.newInstance(taskRecordId, controlPointItemBean));
            }
        });
    }

    @Override
    protected void initData() {
        MyTaskModel.taskControlPointDetail(taskRecordId)//请求控制点数据
                .compose(RxHelper.<TaskControlPointDetailBean>handleResult())
                .compose(this.<TaskControlPointDetailBean>bindToLifecycle())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .subscribe(new RxSubscribe<TaskControlPointDetailBean>() {
                    @Override
                    protected void _onNext(TaskControlPointDetailBean taskControlPointDetailBean) {
                        if (taskControlPointDetailBean != null) {
//                            tvTitle.setText(getNotNull(item.getTaskName()));
//                            tvDate.setText(getNotNull(DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_01)));
//                            tvContent.setText(getNotNull(item.getTaskExplains()));
                            if (taskControlPointDetailBean.getEquipmentControlVoList().size() > 0) {
                                mListData.clear();
                                mListData.addAll(taskControlPointDetailBean.getEquipmentControlVoList());
                            }
                            adapter = new TaskControlPointDetailListAdapter(mListData);
                            rvList.setAdapter(adapter);//设置控制点列表
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        dismissProgress();

                    }

                    @Override
                    public void onCompleted() {
                        dismissProgress();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TaskFormSubmitSuccessEvent event) {//任务提交成功的事件
        //进行数据的刷新
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}