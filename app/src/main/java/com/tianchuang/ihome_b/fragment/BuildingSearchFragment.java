package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.adapter.BuildingSearchAdapter;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.recyclerview.CommonItemDecoration;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.DataSearchModel;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:楼宇查询
 */

public class BuildingSearchFragment extends BaseFragment {
	@BindView(R.id.rv_list)
	RecyclerView rvList;
	private DataSearchActivity holdingActivity;
	@Override
	protected int getLayoutId() {
		return R.layout.fragment_building_search;
	}
	public static BuildingSearchFragment newInstance() {
		return new BuildingSearchFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("楼宇查询");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((DataSearchActivity) getHoldingActivity());
		rvList.addItemDecoration(new CommonItemDecoration(20));
		rvList.setLayoutManager(new LinearLayoutManager(getContext()));
		requestNet();
	}

	/**
	 * 请求网络
	 */
	private void requestNet() {
		DataSearchModel.requestBuildingSearch()
				.compose(RxHelper.<ArrayList<DataBuildingSearchBean>>handleResult())
				.subscribe(new RxSubscribe<ArrayList<DataBuildingSearchBean>>() {
					@Override
					protected void _onNext(ArrayList<DataBuildingSearchBean> arrayList) {
						BuildingSearchAdapter searchAdapter = new BuildingSearchAdapter(R.layout.building_search_item_holder, arrayList);
						rvList.setAdapter(searchAdapter);
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




}
