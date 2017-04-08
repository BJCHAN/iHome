package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/22.
 * description:
 */

public class InnerReportsSuccessFragment extends BaseFragment {

	public static InnerReportsSuccessFragment newInstance() {
		return new InnerReportsSuccessFragment();
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
