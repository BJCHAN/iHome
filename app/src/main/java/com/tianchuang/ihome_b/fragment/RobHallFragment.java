package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.EmptyViewHolder;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.FaultDetailActivity;
import com.tianchuang.ihome_b.activity.RobHallActivity;
import com.tianchuang.ihome_b.adapter.RobHallAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallListItem;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.RobHallModel;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/2/20.
 * description:抢单大厅fragment
 */

public class RobHallFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

	@BindView(R.id.rv_list)
	RecyclerView mRecyclerView;
	@BindView(R.id.swipeLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private RobHallActivity holdingActivity;
	private RobHallAdapter robHallAdapter;
	private ArrayList<RobHallListItem> mData;

	private static final int TOTAL_COUNTER = 18;

	private static final int PAGE_SIZE = 6;

	private int mCurrentCounter = 0;

	private boolean mLoadMoreEndGone = false;

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
				.subscribe(new RxSubscribe<ArrayList<RobHallListItem>>() {

					@Override
					protected void _onNext(ArrayList<RobHallListItem> robHallListItems) {
						mData = robHallListItems;
						robHallAdapter = new RobHallAdapter(R.layout.rob_hall_item_holder, mData);
						//设置空页面
						EmptyViewHolder emptyViewHolder = new EmptyViewHolder();
						emptyViewHolder.bindData(getString(R.string.rob_hall_empty_tip));
						robHallAdapter.setEmptyView(emptyViewHolder.getholderView());
						robHallAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
						robHallAdapter.setOnLoadMoreListener(RobHallFragment.this);
						mRecyclerView.setAdapter(robHallAdapter);
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


	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		robHallAdapter.setEnableLoadMore(false);
		getHallListObservable(0).subscribe(new RxSubscribe<ArrayList<RobHallListItem>>() {
			@Override
			protected void _onNext(ArrayList<RobHallListItem> robHallListItems) {
				mData.clear();
				mData.addAll(robHallListItems);
				robHallAdapter.setNewData(mData);
				mSwipeRefreshLayout.setRefreshing(false);
				robHallAdapter.setEnableLoadMore(true);
			}

			@Override
			protected void _onError(String message) {
				ToastUtil.showToast(getContext(), message);
			}

			@Override
			public void onCompleted() {

			}
		});
	}

	/**
	 * 上拉加载
	 */
	@Override
	public void onLoadMoreRequested() {
//		mSwipeRefreshLayout.setEnabled(false);
//		getHallListObservable(mCurrentCounter)
//				.subscribe(new RxSubscribe<ArrayList<RobHallListItem>>() {
//					@Override
//					protected void _onNext(ArrayList<RobHallListItem> robHallListItems) {
//						if (robHallAdapter.getData().size() < PAGE_SIZE) {
//							robHallAdapter.loadMoreEnd(true);
//						} else {
//							if (mCurrentCounter >= TOTAL_COUNTER) {
////                    pullToRefreshAdapter.loadMoreEnd();//default visible
//								robHallAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
//							} else {
//								if (isErr) {
//									robHallAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//									mCurrentCounter = robHallAdapter.getData().size();
//									robHallAdapter.loadMoreComplete();
//								} else {
//									isErr = true;
//									Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
//									robHallAdapter.loadMoreFail();
//
//								}
//							}
//							mSwipeRefreshLayout.setEnabled(true);
//						}
//
//					}
//
//					@Override
//					protected void _onError(String message) {
//
//					}
//
//					@Override
//					public void onCompleted() {
//
//					}
//				});

	}

	@NonNull
	private Observable<ArrayList<RobHallListItem>> getHallListObservable(int maxid) {
		return RobHallModel.requestRobHallList(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
				.compose(RxHelper.<ArrayList<RobHallListItem>>handleResult());
	}
}
