package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.TaskFormListAdapter;
import com.tianchuang.ihome_b.adapter.TaskPointListAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.base.QrResultListener;
import com.tianchuang.ihome_b.base.ToolBarActivity;
import com.tianchuang.ihome_b.bean.TaskPointDetailBean;
import com.tianchuang.ihome_b.bean.event.TaskFormSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.event.TaskOpenScanEvent;
import com.tianchuang.ihome_b.bean.model.HomePageModel;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/3/20.
 * description:我的任务控制点详情页面
 */

public class MyTaskControlPointDetailFragment extends BaseLoadingFragment implements QrResultListener {

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
    @BindView(R.id.sure_bt)
    Button sureBt;
    private int taskRecordId;
    private List<TaskPointDetailBean.EquipmentControlVoListBean> mListControls;
    private List<TaskPointDetailBean.FormTypeVoListBean> mListForms;
    private TaskPointListAdapter pointAdapter;
    private TaskFormListAdapter formAdapter;
    private TaskPointDetailBean taskPointDetailBean;
    private ToolBarActivity holdingActivity;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((ToolBarActivity) getHoldingActivity());
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        holdingActivity.setQrResultListener(this);
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
    }

    /**
     * 执行任务按钮
     */
    @OnClick(R.id.sure_bt)
    public void onViewClicked() {
        if (mListControls.size() > 0) {//有控制点,去扫码才能进入表单列表
            EventBus.getDefault().post(new TaskOpenScanEvent());
        } else {
            goToFormTypeList(taskPointDetailBean);//直接进入表单列表
        }
    }

    @Override
    protected void initData() {
        if (taskPointDetailBean == null) {
            requestDetailData();
        } else {
            showSucceedPage();
            updateUI(taskPointDetailBean);
        }

    }

    /**
     * 请求详情数据
     */
    private void requestDetailData() {
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
        if (detailBean.getStatus() == 2||(equipmentControlVoList.size()==0&&formTypeVoList.size()==0)) {//任务已完成
            sureBt.setVisibility(View.INVISIBLE);
        }
        if (equipmentControlVoList != null && equipmentControlVoList.size() > 0) {
            initPointList(detailBean);
        } else {//没有控制点直接去表单页面
            goToFormTypeList(detailBean);
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
        rvFormlist.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                TaskPointDetailBean.FormTypeVoListBean formTypeVoListBean = mListForms.get(position);
                if (formTypeVoListBean.isDone()) {//去结果页面
                    addFragment(TaskControlPointResultFragment.newInstance(formTypeVoListBean));
                }
            }
        });
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
        setToolbarTitle("任务详情");
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接受到的二维码信息
     */
    @Override
    public void qrResult(String code) {
        HashMap<String, String> map = new HashMap<>();
        map.put("taskRecordId", String.valueOf(taskRecordId));
        map.put("propertyCompanyId", String.valueOf(UserUtil.getLoginBean().getPropertyCompanyId()));
        map.put("code", code);
        requestTaskQrCode(map);
    }

    private void requestTaskQrCode(HashMap<String, String> map) {
        HomePageModel.requestTaskQrCode(map)
                .compose(RxHelper.handleResult())
                .doOnSubscribe(this::showProgress)
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<TaskPointDetailBean>() {
                    @Override
                    protected void _onNext(TaskPointDetailBean detailBean) {
                        if (detailBean != null) {
                            addFragment(ControlPointSucceedFragment.newInstance(detailBean));
//                            goToFormTypeList(detailBean);
                        } else {
                            ToastUtil.showToast(getContext(), "任务为空");
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
     * 去表单页面
     */
    private void goToFormTypeList(TaskPointDetailBean detailBean) {
        if (detailBean.getFormTypeVoList() != null && detailBean.getFormTypeVoList().size() > 0) {
            addFragment(TaskFormTypeListFragment.newInstance(detailBean));
        } else {
            ToastUtil.showToast(getContext(), "表单为空");
        }
    }
}
