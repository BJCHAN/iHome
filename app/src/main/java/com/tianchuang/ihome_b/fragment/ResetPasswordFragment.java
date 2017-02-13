package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;

/**
 * Created by Abyss on 2017/2/13.
 * description:重置密码界面
 */

public class ResetPasswordFragment extends BaseFragment {
	private LoginActivity activity;

	public static ResetPasswordFragment newInstance() {
		return new ResetPasswordFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		activity = ((LoginActivity) getHoldingActivity());
	}

	@Override
	public void onStart() {
		super.onStart();
		activity.setToolbarTitle("重置密码");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_reset_passwrod;
	}
}
