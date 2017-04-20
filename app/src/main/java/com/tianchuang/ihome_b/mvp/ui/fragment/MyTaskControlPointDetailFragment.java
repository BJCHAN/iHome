package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskFormListAdapter;
import com.tianchuang.ihome_b.adapter.TaskPointListAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.bean.event.TaskFormSubmitSuccessEvent;
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

/**
 * Created by Abyss on 2017/3/20.
 * description:我的任务控制点详情页面
 */

public class MyTaskControlPointDetailFragment extends BaseLoadingFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rv_list)
    RecyclerView rvPointList;
    @BindView(R.id.rv_formlist)
    RecyclerView rvFormlist;
    private int taskRecordId;
    private List<TaskPointDetailBean.EquipmentControlVoListBean> mListControls;
    private List<TaskPointDetailBean.FormTypeVoListBean> mListForms;
    private TaskPointListAdapter pointAdapter;
    private TaskFormListAdapter formAdapter;
    private TaskPointDetailBean taskPointDetailBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_control_point_detail;
    }

    public static MyTaskControlPointDetailFragment newInstance(int taskRecordId) {
        Bundle bundle = new Bundle();
        bundle.putInt("taskRecordId", taskRecordId);
        MyTaskControlPointDetailFragment fragment = new MyTaskControlPointDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MyTaskControlPointDetailFragment newInstance(TaskPointDetailBean detailBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailBean", detailBean);
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
        if (getArguments().getSerializable("detailBean") != null) {
            taskPointDetailBean = (TaskPointDetailBean) getArguments().getSerializable("detailBean");
            this.taskRecordId = taskPointDetailBean.getId();
        } else {
            this.taskRecordId = getArguments().getInt("taskRecordId");
        }
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        layoutManager.setScrollEnabled(false);
        mListControls = new ArrayList<>();
        mListForms = new ArrayList<>();
        rvPointList.setLayoutManager(layoutManager);
        rvFormlist.setLayoutManager(new LinearLayoutManager(getContext()));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initListener() {
        //去往提交编辑任务
//        rvPointList.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ControlPointItemBean controlPointItemBean = (ControlPointItemBean) adapter.getData().get(position);
//                if (!controlPointItemBean.isDone()) {
//                    addFragment(TaskControlPointEditFragment.newInstance(taskRecordId, controlPointItemBean));
//                } else {
//                    addFragment(TaskControlPointResultFragment.newInstance(controlPointItemBean));
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        if (taskPointDetailBean == null) {
            MyTaskModel.taskControlPointDetail(taskRecordId)//请求控制点数据
                    .compose(RxHelper.handleResult())
                    .compose(this.bindToLifecycle())
                    .subscribe(new RxSubscribe<TaskPointDetailBean>() {

                        @Override
                        protected void _onNext(TaskPointDetailBean detailBean) {
                            updateUI(detailBean);
                        }

                        @Override
                        protected void _onError(String message) {
                            showErrorPage();
                            ToastUtil.showToast(getContext(), message);

                        }


                        @Override
                        public void onCompleted() {
                            showSucceedPage();

                        }
                    });
        } else {
            showSucceedPage();
            updateUI(taskPointDetailBean);
        }

    }

    /**
     * 根据数据更新UI
     */
    private void updateUI(TaskPointDetailBean detailBean) {
        MyTaskControlPointDetailFragment.this.taskPointDetailBean = detailBean;
        tvTitle.setText(getNotNull(detailBean.getTaskName()));
        tvDate.setText(getNotNull(DateUtils.formatDate(detailBean.getCreatedDate(), DateUtils.TYPE_01)));
        tvContent.setText(getNotNull(detailBean.getTaskExplains()));
        List<TaskPointDetailBean.EquipmentControlVoListBean> equipmentControlVoList = detailBean.getEquipmentControlVoList();
        List<TaskPointDetailBean.FormTypeVoListBean> formTypeVoList = detailBean.getFormTypeVoList();
        if (equipmentControlVoList != null && equipmentControlVoList.size() > 0) {
            initPointList(detailBean);
        } else {//没有控制点直接去表单页面

            return;
        }
        if (formTypeVoList != null && formTypeVoList.size() > 0) {
            initFormList(detailBean);
        }
    }

    /**
     * 初始化表单列表
     */
    private void initFormList(TaskPointDetailBean detailBean) {
        mListForms.clear();
        mListForms.addAll(detailBean.getFormTypeVoList());
        formAdapter = new TaskFormListAdapter(mListForms);
        formAdapter.addHeaderView(ViewHelper.getTaskHeaderView("表单"));
        rvFormlist.setAdapter(formAdapter);
    }

    /**
     * 初始化控制点列表
     */
    private void initPointList(TaskPointDetailBean detailBean) {
        mListControls.clear();
        mListControls.addAll(detailBean.getEquipmentControlVoList());
        pointAdapter = new TaskPointListAdapter(mListControls);
        pointAdapter.addHeaderView(ViewHelper.getTaskHeaderView("控制/设备点"));
        rvPointList.setAdapter(pointAdapter);//设置控制点列表
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TaskFormSubmitSuccessEvent event) {//任务提交成功的事件
        taskPointDetailBean = null;
        //进行数据的刷新
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
