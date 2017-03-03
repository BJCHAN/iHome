package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.DataSearchActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.EquipmentTypeSearchBean;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/3/1.
 * description:设备类型表单选择
 */

public class EquipmentTypeFormFragment extends BaseFragment {


	@BindView(R.id.tv_equipment_name)
	TextView tvEquipmentName;
	private DataSearchActivity holdingActivity;

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_equipment_type_form;
	}

	public static EquipmentTypeFormFragment newInstance(EquipmentTypeSearchBean bean) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", bean);
		EquipmentTypeFormFragment formFragment = new EquipmentTypeFormFragment();
		formFragment.setArguments(bundle);
		return formFragment;
	}

	@Override
	public void onStart() {
		super.onStart();
//		holdingActivity.setToolbarTitle("设备查询");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((DataSearchActivity) getHoldingActivity());
		EquipmentTypeSearchBean item = ((EquipmentTypeSearchBean) getArguments().getSerializable("item"));
		if (item != null) {
			tvEquipmentName.setText(item.getName());
		}
		requestNet();
	}

	/**
	 * 请求网络
	 */
	private void requestNet() {


	}


}
