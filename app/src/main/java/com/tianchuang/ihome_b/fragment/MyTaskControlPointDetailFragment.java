package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.MyTaskActivity;
import com.tianchuang.ihome_b.adapter.ExpandableItemAdapter;
import com.tianchuang.ihome_b.adapter.TaskControlPointDetailListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ControlPointItemBean;
import com.tianchuang.ihome_b.bean.MyTaskUnderWayItemBean;
import com.tianchuang.ihome_b.bean.TaskControlPointDetailBean;
import com.tianchuang.ihome_b.bean.model.MyTaskModel;
import com.tianchuang.ihome_b.bean.recyclerview.CustomLinearLayoutManager;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.DateUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

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
    @BindView(R.id.fl_empty_view)
    FrameLayout flEmptyView;
    private MyTaskUnderWayItemBean item;
    private int taskRecordId;
    private TaskControlPointDetailBean taskControlPointDetailBean;
    private List<ControlPointItemBean> mListData;
    private TaskControlPointDetailListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_control_point_detail;
    }

    public static MyTaskControlPointDetailFragment newInstance(MyTaskUnderWayItemBean item) {
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
        this.item = (MyTaskUnderWayItemBean) getArguments().getSerializable("item");
        taskRecordId = item.getId();
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(getContext());
        layoutManager.setScrollEnabled(false);
        rvList.setLayoutManager(layoutManager);
//        flEmptyView.setVisibility(View.VISIBLE);

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
                            mListData = new ArrayList<>();
                            MyTaskControlPointDetailFragment.this.taskControlPointDetailBean = taskControlPointDetailBean;
                            tvTitle.setText(getNotNull(item.getTaskName()));
                            tvDate.setText(getNotNull(DateUtils.formatDate(item.getCreatedDate(), DateUtils.TYPE_01)));
                            tvContent.setText(getNotNull(item.getTaskExplains()));
                            if (taskControlPointDetailBean.getEquipmentControlVoList().size() > 0) {
                                mListData.addAll(taskControlPointDetailBean.getEquipmentControlVoList());
                            }
//                            ArrayList<MultiItemEntity> multiItemEntities = generateData(mListData);
//                            ExpandableItemAdapter adapter2 = new ExpandableItemAdapter(((MyTaskActivity) getHoldingActivity()),multiItemEntities);
                            adapter = new TaskControlPointDetailListAdapter(getHoldingActivity(), mListData);
                            rvList.setAdapter(adapter);//设置控制点列表
//                            adapter.setLayoutFinishListener(new TaskControlPointDetailListAdapter.LayoutFinishListener() {
//                                @Override
//                                public void layoutFinished() {//在布局完毕后移除空页面
//                                    removeEmptyView();
//                                }
//                            });

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
    private ArrayList<MultiItemEntity> generateData(List<ControlPointItemBean> mListData) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (ControlPointItemBean controlPointItemBean : mListData) {
            controlPointItemBean.addSubItem(controlPointItemBean.getFormTypeVo());
            res.add(controlPointItemBean);
        }

        return res;
    }
    private void removeEmptyView() {
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {//延时,避免界面布局时闪屏
                    @Override
                    public void call(Long aLong) {
                        flEmptyView.setVisibility(View.GONE);
                        dismissProgress();
                    }
                });
    }


}
