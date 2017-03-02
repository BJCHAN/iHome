package com.tianchuang.ihome_b.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.ComplainSuggestListAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.ComplainSuggestProcessedBean;
import com.tianchuang.ihome_b.bean.ComplainSuggestUntratedBean;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.recyclerview.ComplainSuggestListItem;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.ComplainSuggestModel;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * 投诉建议列表
 */
public class ComplainSuggestListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, PullToLoadMoreListener.OnLoadMoreListener {
	@BindView(R.id.rv_list)
	RecyclerView mRvList;
	@BindView(R.id.swipeLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	//未处理
	private int typeUntreated = 0;
	//已处理
	private int typeProcessed = 1;
	private int type;
	private List<ComplainSuggestListItem> mComplainSuggestListItemList = new ArrayList<>();
	private List<ComplainSuggestListItem> mComplainSuggestListItemListTemp;
	private ComplainSuggestListAdapter mComplainSuggestListAdapter;
	private int untratedMaxid = 0;
	private int processedMaxid = 0;
	private ComplainSuggestListItem mComplainSuggestListItem;

	private int untratedSize;
	private int processedSize;

	//是否可以加载更多
	private boolean untratedCanLoadMore = true;
	private boolean processedCanLoadMore = true;
	private LinearLayoutManager mLinearLayoutManager;
	private boolean loadComplete;


	public static ComplainSuggestListFragment getIntance(int type) {
		ComplainSuggestListFragment complainSuggestListFragment = new ComplainSuggestListFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		complainSuggestListFragment.setArguments(bundle);
		return complainSuggestListFragment;
	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_complain_suggest_list;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		if (null != arguments) {
			type = arguments.getInt("type");
		}
		//设置recycleview为垂直方向
		mLinearLayoutManager = new LinearLayoutManager(getHoldingActivity());
		mRvList.setLayoutManager(mLinearLayoutManager);
		//设置recycleview滚动监听
		mRvList.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
		//设置刷新加载进度条颜色
		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
		//设置下拉刷新监听
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mComplainSuggestListItemList.clear();
		mComplainSuggestListAdapter = new ComplainSuggestListAdapter(mComplainSuggestListItemList);
		mComplainSuggestListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		mComplainSuggestListAdapter.setOnLoadMoreListener(new EmptyLoadMore());
		mRvList.setAdapter(mComplainSuggestListAdapter);

	}

	@Override
	protected void initData() {
		super.initData();
		showProgress();
		//未处理
		if (type == typeUntreated) {
			mComplainSuggestListAdapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.complain_suggest_untreated_empty)));
			getComplainSuggestUntratedBean();
		}
		//已处理
		else {
			mComplainSuggestListAdapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.complain_suggest_processed_empty)));
			getComplainSuggestProcessedBean();
		}
		mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
	}

	@Override
	protected void initListener() {
		super.initListener();

		//条目的点击事件
		mRvList.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

			}
		});
	}

	private void getComplainSuggestUntratedBean() {
		getComplainSuggestUntratedBean(untratedMaxid)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						loadComplete = false;
					}
				})
				.compose(this.<ComplainSuggestUntratedBean>bindToLifecycle()) //保证加载过程中，退出本页面造成的一系列空指针
				.subscribe(new RxSubscribe<ComplainSuggestUntratedBean>() {
					@Override
					protected void _onNext(ComplainSuggestUntratedBean complainSuggestUntratedBean) {
						if (null != complainSuggestUntratedBean) {
							untratedSize = complainSuggestUntratedBean.getPageSize();
							List<ComplainSuggestUntratedBean.ListVoBean> listVo = complainSuggestUntratedBean.getListVo();
							int listVoSize = listVo.size();
							mComplainSuggestListItemListTemp = new ArrayList<>();
							for (int i = 0; i < listVoSize; i++) {
								mComplainSuggestListItem = new ComplainSuggestListItem();
								mComplainSuggestListItem.setType(ComplainSuggestListItem.TYPE_UNTRATED);
								mComplainSuggestListItem.setUntratedListVoBean(listVo.get(i));
								mComplainSuggestListItemListTemp.add(mComplainSuggestListItem);
							}
							if (untratedMaxid == 0) {
								mComplainSuggestListItemList.addAll(mComplainSuggestListItemListTemp);
								mComplainSuggestListAdapter.setNewData(mComplainSuggestListItemList);
							} else {
								mComplainSuggestListAdapter.addData(mComplainSuggestListItemListTemp);
							}
							if (listVoSize < untratedSize) {
								untratedCanLoadMore = false;
								mComplainSuggestListAdapter.loadMoreEnd(false);
							}
							if (mComplainSuggestListItemList.size() <= 0) {
								mComplainSuggestListAdapter.getEmptyView().setVisibility(View.VISIBLE);
							} else {
								mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
							}
						}
						loadComplete = true;
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getActivity(), message);
						dismissProgress();
						mSwipeRefreshLayout.setRefreshing(false);//刷新完成
						loadComplete = true;
					}

					@Override
					public void onCompleted() {
						dismissProgress();
						mSwipeRefreshLayout.setRefreshing(false);//刷新完成
					}
				});
	}

	private void getComplainSuggestProcessedBean() {
		getComplainSuggestProcessedBean(processedMaxid)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						loadComplete = false;
						//processedMaxid == 0为第一次加载
						if (processedMaxid == 0) {
							mComplainSuggestListItemList.clear();
						}
					}
				})
				.compose(this.<ComplainSuggestProcessedBean>bindToLifecycle()) //保证加载过程中，退出本页面造成的一系列空指针
				.subscribe(new RxSubscribe<ComplainSuggestProcessedBean>() {
					@Override
					protected void _onNext(ComplainSuggestProcessedBean complainSuggestProcessedBean) {
						if (null != complainSuggestProcessedBean) {
							processedSize = complainSuggestProcessedBean.getPageSize();
							List<ComplainSuggestProcessedBean.ListVoBean> listVo = complainSuggestProcessedBean.getListVo();
							int listVoSize = listVo.size();
							mComplainSuggestListItemListTemp = new ArrayList<>();
							for (int i = 0; i < listVoSize; i++) {
								mComplainSuggestListItem = new ComplainSuggestListItem();
								mComplainSuggestListItem.setType(ComplainSuggestListItem.TYPE_PROCESSED);
								mComplainSuggestListItem.setProcessedListVoBean(listVo.get(i));
								mComplainSuggestListItemListTemp.add(mComplainSuggestListItem);
							}
							if (processedMaxid == 0) {
								mComplainSuggestListItemList.addAll(mComplainSuggestListItemListTemp);
								mComplainSuggestListAdapter.setNewData(mComplainSuggestListItemList);
							} else {
								mComplainSuggestListAdapter.addData(mComplainSuggestListItemListTemp);
							}
							if (listVoSize < processedSize) {
								processedCanLoadMore = false;
								mComplainSuggestListAdapter.loadMoreEnd(false);
							}
							if (mComplainSuggestListItemList.size() <= 0) {
								mComplainSuggestListAdapter.getEmptyView().setVisibility(View.VISIBLE);
							} else {
								mComplainSuggestListAdapter.getEmptyView().setVisibility(View.GONE);
							}
						}
						loadComplete = true;
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getActivity(), message);
						dismissProgress();
						mSwipeRefreshLayout.setRefreshing(false);//刷新完成
						loadComplete = true;
					}

					@Override
					public void onCompleted() {
						dismissProgress();
						mSwipeRefreshLayout.setRefreshing(false);//刷新完成
					}
				});
	}

	@Override
	public void onRefresh() {
		if (!loadComplete) {
			return;
		}
		mComplainSuggestListItemList.clear();
		//未处理
		if (type == typeUntreated) {
			untratedMaxid = 0;
			untratedCanLoadMore = true;
			getComplainSuggestUntratedBean();
		} else {
			processedMaxid = 0;
			processedCanLoadMore = true;
			getComplainSuggestProcessedBean();
		}
	}

	@Override
	public void requestLoadMore() {
		if (!loadComplete) {
			return;
		}
		//未处理
		int size = mComplainSuggestListAdapter.getData().size();
		if (size > 0) {
			if (type == typeUntreated && untratedCanLoadMore) {
				untratedMaxid = mComplainSuggestListAdapter.getData().get(size - 1).getUntratedListVoBean().getId();
				getComplainSuggestUntratedBean();
			} else if (type == typeProcessed && processedCanLoadMore) {
				processedMaxid = mComplainSuggestListAdapter.getData().get(size - 1).getProcessedListVoBean().getId();
				getComplainSuggestProcessedBean();
			}
		}

	}

	//请求数据 未处理投诉建议列表
	@NonNull
	private Observable<ComplainSuggestUntratedBean> getComplainSuggestUntratedBean(int maxid) {
		return ComplainSuggestModel.complainSuggestUntrated(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
				.compose(RxHelper.<ComplainSuggestUntratedBean>handleResult());
	}

	//请求数据 已处理投诉建议列表
	@NonNull
	private Observable<ComplainSuggestProcessedBean> getComplainSuggestProcessedBean(int maxid) {
		return ComplainSuggestModel.complainSuggestProcessed(UserUtil.getLoginBean().getPropertyCompanyId(), maxid)
				.compose(RxHelper.<ComplainSuggestProcessedBean>handleResult());
	}
}
