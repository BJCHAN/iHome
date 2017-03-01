package com.tianchuang.ihome_b.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MenuInnerReportsAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.PullToLoadMoreListener;
import com.tianchuang.ihome_b.bean.recyclerview.EmptyLoadMore;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerListBean;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.InnerReportsModel;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.ViewHelper;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事（菜单）
 */

public class MenuInnerReportsFragment extends BaseFragment implements PullToLoadMoreListener.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	@BindView(R.id.swipeLayout)
	SwipeRefreshLayout mSwipeRefreshLayout;
	private int pageSize;
	private ArrayList<MenuInnerReportsItemBean> mData;
	private MenuInnerReportsAdapter adapter;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_menu_inner_reports;
	}

	public static MenuInnerReportsFragment newInstance() {
		return new MenuInnerReportsFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		rvList.addItemDecoration(new RobHallItemDecoration(10));
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
				.subscribe(new RxSubscribe<MenuInnerListBean>() {

					@Override
					protected void _onNext(MenuInnerListBean bean) {
						pageSize = bean.getPageSize();
						mData = bean.getListVo();
						adapter = new MenuInnerReportsAdapter(R.layout.inner_reports_item_holder
								, mData);
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
		mSwipeRefreshLayout.setEnabled(false);
		isLoadMoreLoading = true;
		getNetObservable(adapter.getData().get(adapter.getData().size() - 1).getId())
				.compose(this.<MenuInnerListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<MenuInnerListBean>() {
					@Override
					protected void _onNext(MenuInnerListBean bean) {
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
				.compose(this.<MenuInnerListBean>bindToLifecycle())
				.subscribe(new RxSubscribe<MenuInnerListBean>() {
					@Override
					protected void _onNext(MenuInnerListBean bean) {
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
	private Observable<MenuInnerListBean> getNetObservable(int maxId) {
		return InnerReportsModel.requestReportsList(UserUtil.getLoginBean().getPropertyCompanyId(), maxId)
				.compose(RxHelper.<MenuInnerListBean>handleResult())
				.compose(this.<MenuInnerListBean>bindToLifecycle());
	}

	private void initAdapter(final MenuInnerReportsAdapter adapter) {
		//添加空页面
		adapter.setEmptyView(ViewHelper.getEmptyView(getString(R.string.menu_inner_list_empty)));
		adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
		adapter.setOnLoadMoreListener(new EmptyLoadMore());
		if (mData.size() < pageSize) {//加载的view Gone掉
			adapter.loadMoreEnd(true);
		}
		rvList.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				MenuInnerReportsItemBean menuInnerReportsItemBean = (MenuInnerReportsItemBean) adapter.getData().get(position);
				addFragment(MenuInnerReportsDetailFragment.newInstance(menuInnerReportsItemBean));
			}
		});

	}


}
