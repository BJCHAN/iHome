package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/22.
 * description:
 */

public class InnerReportsSuccess extends BaseFragment {

	public static InnerReportsSuccess newInstance() {
		return new InnerReportsSuccess();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_inner_reports_success;
	}


	@OnClick(R.id.bt_back)
	public void onClick() {
		removeFragment();
	}
}
