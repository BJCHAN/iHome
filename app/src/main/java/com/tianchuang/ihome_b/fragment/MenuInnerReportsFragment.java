package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.adapter.MenuInnerReportsAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.recyclerview.MenuInnerReportsItemBean;
import com.tianchuang.ihome_b.bean.recyclerview.RobHallItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.InnerReportsModel;
import com.tianchuang.ihome_b.utils.ToastUtil;
import com.tianchuang.ihome_b.utils.UserUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.functions.Action0;

/**
 * Created by Abyss on 2017/2/22.
 * description:内部报事（菜单）
 */

public class MenuInnerReportsFragment extends BaseFragment {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private MenuInnerReportsAdapter adapter;

	public static MenuInnerReportsFragment newInstance() {
		return new MenuInnerReportsFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		rvList.setLayoutManager(new LinearLayoutManager(getHoldingActivity()));
		rvList.addItemDecoration(new RobHallItemDecoration(10));
		InnerReportsModel.requestReportsList(UserUtil.getLoginBean().getPropertyCompanyId())
				.compose(RxHelper.<ArrayList<MenuInnerReportsItemBean>>handleResult())
				.compose(this.<ArrayList<MenuInnerReportsItemBean>>bindToLifecycle())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						showProgress();
					}
				})
				.subscribe(new RxSubscribe<ArrayList<MenuInnerReportsItemBean>>() {

					@Override
					protected void _onNext(ArrayList<MenuInnerReportsItemBean> arrayList) {
						adapter = new MenuInnerReportsAdapter(R.layout.inner_reports_item_holder
								, arrayList);
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

	private void initAdapter(final MenuInnerReportsAdapter adapter) {
		rvList.addOnItemTouchListener(new OnItemClickListener() {
			@Override
			public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
				MenuInnerReportsItemBean menuInnerReportsItemBean = (MenuInnerReportsItemBean) adapter.getData().get(position);
				addFragment(MenuInnerReportsDetailFragment.newInstance(menuInnerReportsItemBean));
			}
		});

	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_menu_inner_reports;
	}


}
