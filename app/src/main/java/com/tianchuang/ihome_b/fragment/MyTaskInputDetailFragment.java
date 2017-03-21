package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskInputDetailListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/3/16.
 * description:任务录入详情
 */

public class MyTaskInputDetailFragment extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_finish_date)
    TextView tvFinishDate;
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
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        layoutManager.setScrollEnabled(false);
        rvList.setLayoutManager(layoutManager);

    }

    @Override
    protected void initData() {
        super.initData();
        MyTaskModel.taskInputDetail(taskRecordId)
                .compose(RxHelper.<TaskInputDetailBean>handleResult())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .compose(this.<TaskInputDetailBean>bindToLifecycle())
                .subscribe(new RxSubscribe<TaskInputDetailBean>() {

                    @Override
                    protected void _onNext(TaskInputDetailBean taskInputDetailBean) {
                        if (taskInputDetailBean != null) {
                            taskInputDetailBean.setTaskRecordId(taskRecordId);
                            MyTaskInputDetailFragment.this.taskInputDetailBean = taskInputDetailBean;
                            tvTitle.setText(getNotNull(item.getTaskName()));
                            tvDate.setText(getNotNull(DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_01)));
                            tvContent.setText(getNotNull(item.getTaskExplains()));
                            tvType.setText(getNotNull(taskInputDetailBean.getEnterTypeMsg()));
                            tvAddress.setText(getNotNull(taskInputDetailBean.getBuildingDetail()));
                            tvFinishDate.setText(getNotNull(DateUtils.formatDate(taskInputDetailBean.getFinishTime(), DateUtils.TYPE_01)));
                            btSure.setVisibility(item.getStatus()==2? View.INVISIBLE:View.VISIBLE);
                            if (taskInputDetailBean.getTaskRoomDataList().size() > 0) {
                                mListData.addAll(taskInputDetailBean.getTaskRoomDataList());
                            }
                            rvList.setAdapter(new TaskInputDetailListAdapter(mListData));
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
