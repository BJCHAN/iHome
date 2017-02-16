package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/16.
 * description:修改密码界面
 */

public class ChangePwdSuccessFragment extends BaseFragment {


	@Override
	protected void initView(View view, Bundle savedInstanceState) {

	}

	public static ChangePwdSuccessFragment newInstance() {
		return new ChangePwdSuccessFragment();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_change_password_success;
	}


	@OnClick(R.id.bt_back)
	public void onClick() {
		removeFragment();
	}
}
