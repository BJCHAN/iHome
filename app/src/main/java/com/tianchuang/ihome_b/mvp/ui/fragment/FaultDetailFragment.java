package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.DetailMultiAdapter;
import com.tianchuang.ihome_b.base.BaseLoadingFragment;
import com.tianchuang.ihome_b.bean.RobHallListItem;
import com.tianchuang.ihome_b.bean.RobHallRepairDetailListBean;
import com.tianchuang.ihome_b.bean.event.RobOrderSuccessEvent;
import com.tianchuang.ihome_b.bean.model.RobHallModel;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;
import com.tianchuang.ihome_b.view.OneButtonDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Abyss on 2017/2/20.
 * description:故障详情fragment
 */

public class FaultDetailFragment extends BaseLoadingFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_rob_order)
    TextView tvRobOrder;
    private DetailMultiAdapter mAdapter;
    private int repairsId;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fault_detail;
    }

    public static FaultDetailFragment newInstance(RobHallListItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("item", item);
        FaultDetailFragment faultDetailFragment = new FaultDetailFragment();
        faultDetailFragment.setArguments(bundle);
        return faultDetailFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initData() {
        RobHallListItem item = (RobHallListItem) getArguments().getSerializable("item");
        if (item != null) {
            repairsId = item.getId();
            RobHallModel.requestRobHallRepairDetail(repairsId)
                    .compose(RxHelper.handleResult())
                    .compose(this.bindToLifecycle())
                    .subscribe(new RxSubscribe<RobHallRepairDetailListBean>() {
                        @Override
                        public void _onNext(RobHallRepairDetailListBean bean) {
                            mAdapter = new DetailMultiAdapter(bean.getRepairsDataVos());
                            //recyclerView添加头部
                            mAdapter.addHeaderView(ViewHelper.getDetailHeaderView(bean.getTypeName(), bean.getCreatedDate()));
                            rvList.setAdapter(mAdapter);
                            showSucceedPage();
                        }

                        @Override
                        public void _onError(String message) {
                            showErrorPage();
                            showToast(message);

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

            RxView.clicks(tvRobOrder)
                    .throttleFirst(3, TimeUnit.SECONDS)
                    .flatMap(o -> {
                                showProgress();
                                return RobHallModel.requestRobRepair(repairsId);
                            }
                    )
                    .compose(this.bindToLifecycle())
                    .subscribe(new Observer<HttpModle<String>>() {
                        @Override
                        public void onComplete() {
                            dismissProgress();
                        }

                        @Override
                        public void onError(Throwable e) {
                            dismissProgress();
                        }

                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(HttpModle<String> modle) {
                            if (modle.success()) {
                                EventBus.getDefault().post(new RobOrderSuccessEvent());
                                ToastUtil.showToast(getContext(), "抢单成功");
                                getHoldingActivity().finishWithAnim();
                            } else {
                                showDialog(modle.msg);
                            }

                        }
                    });
        }
    }

    private void showDialog(String tip) {
        OneButtonDialogFragment.newInstance(tip)
                .show(getHoldingActivity().getFragmentManager(), OneButtonDialogFragment.class.getSimpleName());
    }


}
