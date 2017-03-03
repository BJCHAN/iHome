package com.tianchuang.ihome_b.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MyOrderUnderWayAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.MyOrderCommonBean;
import com.tianchuang.ihome_b.bean.MyOrderListBean;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.bean.recyclerview.LoadMoreItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.MyOrderModel;
import com.tianchuang.ihome_b.utils.DensityUtil;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/2/22.
 * description:我的订单（进行中和已完成）
 */

public class MyOrderStatusFragment extends BaseFragment implements PullToLoadMoreListener.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	@BindView(R.id.swipeLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private int pageSize;

	protected List<MyOrderCommonBean> mData;
	protected MyOrderUnderWayAdapter adapter;
	private static final int UNDER_WAY = 1;
	private static final int FINISHED = 2;
	private int currentType;

	//type 种类 1 为进行中  2 为 已完成
	@Override
	protected int getLayoutId() {
		return R.layout.base_fragment_refrsh_load;
	}

	public static MyOrderStatusFragment newInstance(int type) {
		Bundle bundle = new Bundle();
		bundle.putInt("type", type);
		MyOrderStatusFragment fragment = new MyOrderStatusFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		currentType = getArguments().getInt("type");
		rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		rvList.addItemDecoration(new LoadMoreItemDecoration(DensityUtil.dip2px(getContext(), 10)));
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
		rvList.addOnScrollListener(new PullToLoadMoreListener(mSwipeRefreshLayout, this));
		getNetObservable(0)
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.subscribe(new RxSubscribe<MyOrderListBean>() {
					@Override
					protected void _onNext(MyOrderListBean bean) {
						pageSize = bean.getPageSize();
						mData = bean.getListVo();
						if (currentType == UNDER_WAY) {
							adapter = new MyOrderUnderWayAdapter(R.layout.myorder_under_way_item_holder
									, mData);
						} else {
							adapter = new MyOrderUnderWayAdapter(R.layout.myorder_finnished_item_holder
									, mData);
						}

						initAdapter(adapter);
						rvList.setAdapter(adapter);
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
	 * 加载更多
	 */
	@Override
	public void requestLoadMore() {
		if (isLoadMoreLoading) {
			return;
		}
		int size = adapter.getData().size();
		if (size == 0) {
			return;
		}
		mSwipeRefreshLayout.setEnabled(false);
		isLoadMoreLoading = true;
		getNetObservable(adapter.getData().get(size - 1).getId())
				.compose(this.<MyOrderListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<MyOrderListBean>() {
					@Override
					protected void _onNext(MyOrderListBean bean) {
						adapter.addData(bean.getListVo());
						if (bean.getListVo().size() < pageSize) {//没有更多数据
							adapter.loadMoreEnd(false);
							isLoadMoreLoading = true;
						} else {
							adapter.loadMoreComplete();//加载完成
							isLoadMoreLoading = false;
						}
						mSwipeRefreshLayout.setEnabled(true);
					}

					@Override
					protected void _onError(String message) {
						isLoadMoreLoading = false;
						adapter.loadMoreFail();
						ToastUtil.showToast(getContext(), message);
					}

					@Override
					public void onCompleted() {

					}
				});
	}

	/**
	 * 刷新
	 */
	@Override
	public void onRefresh() {
		getNetObservable(0)
				.subscribe(new RxSubscribe<MyOrderListBean>() {
					@Override
					protected void _onNext(MyOrderListBean bean) {
						isLoadMoreLoading = false;
						mData.clear();
						if (bean.getListVo().size() < pageSize) {//加载的view Gone掉
							adapter.loadMoreEnd(true);
						}
						mData.addAll(bean.getListVo());
						adapter.setNewData(mData);
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

	/**
	 * 访问网络请求数据
	 */
	private Observable<MyOrderListBean> getNetObservable(int maxId) {
		if (currentType == UNDER_WAY) {
			return MyOrderModel.myOrderUnfinished(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
					.compose(RxHelper.<MyOrderListBean>handleResult())
					.compose(this.<MyOrderListBean>bindToLifecycle());
		} else {
			return MyOrderModel.myOrderfinished(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
					.compose(RxHelper.<MyOrderListBean>handleResult())
					.compose(this.<MyOrderListBean>bindToLifecycle());
		}
	}

	private void initAdapter(final MyOrderUnderWayAdapter adapter) {
		//添加空页面
		if (currentType == UNDER_WAY) {
			adapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.myorder_underway_empty)));
		} else {
			adapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.myorder_finished_empty)));
		}
		adapter.setLoadMoreView(new SimpleLoadMoreView());
		adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		adapter.setOnLoadMoreListener(new EmptyLoadMore());
		if (mData.size() < pageSize) {//加载的view Gone掉
			adapter.loadMoreEnd(true);
		}
		rvList.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//				MenuInnerReportsItemBean menuInnerReportsItemBean = (MenuInnerReportsItemBean) adapter.getData().get(position);
//				addFragment(MenuInnerReportsDetailFragment.newInstance(menuInnerReportsItemBean));
				MyOrderCommonBean myOrderCommonBean = (MyOrderCommonBean) adapter.getData().get(position);
				addFragment(MyOrderDetailFragment.newInstance(myOrderCommonBean.getId()));

			}
		});

	}


}
