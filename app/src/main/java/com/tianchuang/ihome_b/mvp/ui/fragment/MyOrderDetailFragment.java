package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.DetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.EvaluateBean;
import com.tianchuang.ihome_b.bean.MyOrderDetailBean;
import com.tianchuang.ihome_b.bean.RepairsFeeBean;
import com.tianchuang.ihome_b.bean.event.FeeSubmitSuccessEvent;
import com.tianchuang.ihome_b.bean.model.MyOrderModel;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:我的订单详情
 */

public class MyOrderDetailFragment extends BaseLoadingFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    private int id;
    private DetailMultiAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myorder_detail;
    }

    public static MyOrderDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        MyOrderDetailFragment detailFragment = new MyOrderDetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        setToolbarTitle("订单详情");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        id = getArguments().getInt("id");
        rvList.addItemDecoration(new CommonItemDecoration(20));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DetailMultiAdapter(new ArrayList<>());
        rvList.setAdapter(adapter);
    }


    @Override
    protected void initData() {
        MyOrderModel.myOrderDetail(id)
                .compose(this.<HttpModle<MyOrderDetailBean>>bindToLifecycle())
                .compose(RxHelper.<MyOrderDetailBean>handleResult())
                .subscribe(new RxSubscribe<MyOrderDetailBean>() {
                    @Override
                    public void _onNext(MyOrderDetailBean bean) {
                        adapter.getData().clear();
                        adapter.removeAllHeaderView();
                        adapter.removeAllFooterView();
                        adapter.getData().addAll(bean.getRepairsDataVos());
                        int status = bean.getStatus();
                        //添加头部
                        adapter.addHeaderView(ViewHelper.getDetailHeaderView(bean.getTypeName(), bean.getCreatedDate()));
                        //添加底部
                        MyOrderDetailBean.OwnersInfoVoBean ownersInfoVo = bean.getOwnersInfoVo();
                        List<MyOrderDetailBean.RepairsFeeVosBean> repairsFeeVos = bean.getRepairsFeeVos();
                        RepairsFeeBean repairsFeeBean = new RepairsFeeBean().setRepairsFeeVos(repairsFeeVos).setTotalFee(bean.getTotalFee());
                        EvaluateBean evaluateBean = new EvaluateBean().setStars(bean.getEvaluateStar()).setEvaluateContent(bean.getEvaluate());
                        List<MyOrderDetailBean.RepairsOrderLogVo> repairsOrderLogVos = bean.getRepairsOrderLogVos();
                        if (status >= 1) {
                            //添加报修人员信息的底部
                            adapter.addFooterView(ViewHelper.getMyOrderOwnerInfoFooterView(ownersInfoVo));
                        }
                        if (status > 3) {
                            //添加费用列表信息
                            adapter.addFooterView(ViewHelper.getMyOrderFeeInfoFooterView(repairsFeeBean));
                        }
                        if (status > 4) {
                            //评价信息
                            adapter.addFooterView(ViewHelper.getMyOrderEvaluateInfoFooterView(evaluateBean));
                        }

                        //添加事件轴信息
                        adapter.addFooterView(ViewHelper.getTimeLineFooterView(repairsOrderLogVos));
                        switch (status) {
                            case 0://待接单
                                break;
                            case 1://待服务,去添加详情照片
                                tvStatus.setText(R.string.myorder_fee_confirm);
                                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_3));
                                tvStatus.setClickable(true);
                                tvStatus.setOnClickListener(v -> addFragment(ConfirmFixedFragment.newInstance(MyOrderDetailFragment.this.id)));
                                break;
                            case 2://费用确认，去添加费用明细
                                tvStatus.setText(R.string.myorder_fee_confirm);
                                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_3));
                                tvStatus.setClickable(true);
                                tvStatus.setOnClickListener(v -> addFragment(MyOrderFeeDetailFragment.newInstance(MyOrderDetailFragment.this.id)));
                                break;
                            case 3://待付款
                                tvStatus.setText(R.string.myorder_wait_pay);
                                tvStatus.setClickable(false);
                                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_2));
                                break;
                            case 4://待评价
                                tvStatus.setText(R.string.myorder_wait_evaluate);
                                tvStatus.setClickable(false);
                                tvStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_2));
                                break;
                            case 5://已完成
                                tvStatus.setVisibility(View.GONE);
                                break;
                        }
                        checkData(bean);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void _onError(String message) {
                        ToastUtil.showToast(getContext(), message);
                        showErrorPage();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * 费用明细提交成功刷新页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FeeSubmitSuccessEvent feeSubmitSuccess) {
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
