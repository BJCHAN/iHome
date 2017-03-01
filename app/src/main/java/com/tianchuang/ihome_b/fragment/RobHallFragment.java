package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.EmptyViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.FaultDetailActivity;
import com.tianchuang.ihome_b.activity.RobHallActivity;
import com.tianchuang.ihome_b.adapter.RobHallAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallListBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallListItem;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.RobHallModel;
import com.tianchuang.ihome_b.utils.LogUtils;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/2/20.
 * description:抢单大厅fragment
 */

public class RobHallFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PullToLoadMoreListener.OnLoadMoreListener {

	@BindView(R.id.rv_list)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private RobHallActivity holdingActivity;
	private RobHallAdapter robHallAdapter;
	private ArrayList<RobHallListItem> mData;
	private int pageSize;


	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("抢单大厅");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_rob_hall;
	}

	public static RobHallFragment newInstance() {
		return new RobHallFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((RobHallActivity) getHoldingActivity());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		mRecyclerView.addItemDecoration(new RobHallItemDecoration(20));
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
		mRecyclerView.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
		initAdapter();
		//条目的点击事件
		mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				Intent intent = new Intent(getHoldingActivity(), FaultDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", robHallAdapter.getItem(position));
				intent.putExtras(bundle);
				getHoldingActivity().startActivityWithAnim(intent);
			}
		});

	}

	@NonNull
	private void initAdapter() {
		getHallListObservable(0)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.compose(this.<RobHallListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<RobHallListBean>() {

					@Override
					protected void _onNext(RobHallListBean bean) {
						pageSize = bean.getPageSize();
						mData = bean.getListVo();
						robHallAdapter = new RobHallAdapter(R.layout.rob_hall_item_holder, mData);
						//设置空页面
						robHallAdapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.rob_hall_empty_tip)));
						robHallAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
						robHallAdapter.setOnLoadMoreListener(new EmptyLoadMore());
						mRecyclerView.setAdapter(robHallAdapter);
						if (mData.size() < pageSize) {//加载的view Gone掉
							robHallAdapter.loadMoreEnd(true);
						}
						dismissProgress();
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getContext(), message);
						dismissProgress();
					}

					@Override
					public void onCompleted() {

					}
				});

	}

	private boolean isLoadMoreLoading = false;//是否正在加载更多

	/**
	 * 上拉加载
	 */
	@Override
	public void requestLoadMore() {
		LogUtils.d(RobHallFragment.class.getName(), "aaa");
		if (isLoadMoreLoading) {
			return;
		}
		mSwipeRefreshLayout.setEnabled(false);
		isLoadMoreLoading = true;
		getHallListObservable(robHallAdapter.getData().get(robHallAdapter.getData().size() - 1).getId())
				.compose(this.<RobHallListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<RobHallListBean>() {
					@Override
					protected void _onNext(RobHallListBean bean) {
						robHallAdapter.addData(bean.getListVo());
						if (bean.getListVo().size() < pageSize) {//没有更多数据
							robHallAdapter.loadMoreEnd(false);
							isLoadMoreLoading = true;
						} else {
							robHallAdapter.loadMoreComplete();//加载完成
							isLoadMoreLoading = false;
						}
						mSwipeRefreshLayout.setEnabled(true);
					}

					@Override
					protected void _onError(String message) {
						isLoadMoreLoading = false;
						robHallAdapter.loadMoreFail();
						ToastUtil.showToast(getContext(), message);
					}

					@Override
					public void onCompleted() {

					}
				});
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		getHallListObservable(0)
				.compose(this.<RobHallListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<RobHallListBean>() {
					@Override
					protected void _onNext(RobHallListBean bean) {
						isLoadMoreLoading = false;
						mData.clear();
						if (bean.getListVo().size() < pageSize) {//加载的view Gone掉
							robHallAdapter.loadMoreEnd(true);
						}
						mData.addAll(bean.getListVo());
						robHallAdapter.setNewData(mData);
						mSwipeRefreshLayout.setRefreshing(false);//刷新完成
					}

					@Override
					protected void _onError(String message) {
						mSwipeRefreshLayout.setRefreshing(false);
						ToastUtil.showToast(getContext(), message);
					}

					@Override
					public void onCompleted() {

					}
				});
	}

	//请求数据
	@NonNull
	private Observable<RobHallListBean> getHallListObservable(int maxid) {
		return RobHallModel.requestRobHallList(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
				.compose(RxHelper.<RobHallListBean>handleResult());
	}

}
