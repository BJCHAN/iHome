package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskInputDetailListAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.TaskInputDetailBean;
import com.tianchuang.ihome_b.bean.TaskRoomDataListBean;
import com.tianchuang.ihome_b.bean.event.NotifyTaskDetailRefreshEvent;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/3/16.
 * description:任务录入详情
 */

public class MyTaskInputDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.bt_sure)
    Button btSure;
    private int taskRecordId;
    private MyTaskUnderWayItemBean item;
    private List<TaskRoomDataListBean> mListData = new ArrayList<>();
    private TaskInputDetailBean taskInputDetailBean;

    public static MyTaskInputDetailFragment newInstance(MyTaskUnderWayItemBean item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        MyTaskInputDetailFragment myTaskInputDetail = new MyTaskInputDetailFragment();
        myTaskInputDetail.setArguments(bundle);
        return myTaskInputDetail;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("任务详情");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_input_detail;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        this.item = (MyTaskUnderWayItemBean) getArguments().getSerializable("item");
        taskRecordId = item.getId();
//        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
//        layoutManager.setScrollEnabled(false);
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initData() {
        MyTaskModel.INSTANCE.taskInputDetail(taskRecordId)
                .compose(RxHelper.<TaskInputDetailBean>handleResult())
                .compose(this.<TaskInputDetailBean>bindToLifecycle())
                .subscribe(new RxSubscribe<TaskInputDetailBean>() {

                    @Override
                    public void _onNext(TaskInputDetailBean taskInputDetailBean) {
                        taskInputDetailBean.setTaskRecordId(taskRecordId);
                        taskInputDetailBean.setTaskExplains(item.getTaskExplains());
                        MyTaskInputDetailFragment.this.taskInputDetailBean = taskInputDetailBean;
                        TaskInputDetailListAdapter adapter = new TaskInputDetailListAdapter(mListData);
                        View headerView = ViewHelper.getTaskInputHeaderView(taskInputDetailBean);
                        adapter.addHeaderView(headerView);
                            if (taskInputDetailBean.getTaskRoomDataList().size() > 0) {
                                mListData.addAll(taskInputDetailBean.getTaskRoomDataList());
                            }
                        rvList.setAdapter(adapter);
                        btSure.setVisibility(taskInputDetailBean.getStatus()==2? View.INVISIBLE:View.VISIBLE);
                    }

                    @Override
                    public void _onError(String message) {
                        showErrorPage();
                        ToastUtil.showToast(getContext(), message);
                    }

                    @Override
                    public void onComplete() {
                        showSucceedPage();
                    }
                });
    }

    /**
     * 接收数据录入成功界面的刷新通知
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotifyTaskDetailRefreshEvent event) {
        mListData.clear();
        initData();
    }

    @OnClick(R.id.bt_sure)
    public void onClick() {
        if (taskInputDetailBean != null) {
            if (taskInputDetailBean.getTaskRoomDataList().size() > 0) {
                int status = taskInputDetailBean.getStatus();
                if (status == 0 || status == 1) {
                    taskInputDetailBean.setTaskName(item.getTaskName());
                    addFragment(TaskInputBuildingSelectFragment.newInstance(taskInputDetailBean));
                }
            } else {
                ToastUtil.showToast(getContext(), "任务条目为空");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
