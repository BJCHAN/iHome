package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.DataBuildingSearchBean;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.DataSearchModel;

import java.util.ArrayList;

/**
 * Created by Abyss on 2017/3/1.
 * description:设备类型查询
 */

public class EquipmentTypeFragment extends BaseFragment {

	private DataSearchActivity holdingActivity;

	public static EquipmentTypeFragment newInstance() {
		return new EquipmentTypeFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("设备查询");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((DataSearchActivity) getHoldingActivity());
		requestNet();
	}

	/**
	 * 请求网络
	 */
	private void requestNet() {
		DataSearchModel.requestEquipmentTypeSearch()
				.compose(RxHelper.<ArrayList<EquipmentTypeSearchBean>>handleResult())
				.subscribe(new RxSubscribe<ArrayList<EquipmentTypeSearchBean>>() {
					@Override
					protected void _onNext(ArrayList<EquipmentTypeSearchBean> equipmentTypeSearchBeen) {

					}

					@Override
					protected void _onError(String message) {

					}

					@Override
					public void onCompleted() {

					}
				});

	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_equipment_type_search;
	}

}
