package com.tianchuang.ihome_b.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by Abyss on 2017/2/13.
 * description:验证码页面
 */

public class AuthCodeFragment extends BaseFragment {

	@BindView(R.id.et_auth_code)
	EditText etAuthCode;
	@BindView(R.id.tv_send_auth_code)
	TextView tvSendAuthCode;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.bt_sure)
	Button btSure;
	@BindView(R.id.login_rl)
	RelativeLayout loginRl;
	private LoginActivity holdingActivity;

	public static AuthCodeFragment newInstance() {
		return new AuthCodeFragment();
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("验证码");
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((LoginActivity) getHoldingActivity());
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_auth_code;
	}


}
