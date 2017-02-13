package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/13.
 * description:注册账号页面
 */

public class RegisterAccountFragment extends BaseFragment {

	@BindView(R.id.et_phone_num)
	EditText etPhoneNum;
	@BindView(R.id.et_passwrod)
	EditText etPasswrod;
	@BindView(R.id.iv_pwd_isvisible)
	ImageView ivPwdIsvisible;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.bt_register)
	Button btRegister;
	@BindView(R.id.cb_isagree)
	CheckBox cbIsagree;
	@BindView(R.id.tv_register_protocol)
	TextView tvRegisterProtocol;
	@BindView(R.id.login_rl)
	RelativeLayout loginRl;
	private LoginActivity holdingActivity;

	public static RegisterAccountFragment newInstance() {
		return new RegisterAccountFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((LoginActivity) getHoldingActivity());
	}

	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("注册账号");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_register_account;
	}


	@OnClick({R.id.bt_register, R.id.tv_register_protocol})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.bt_register:
				holdingActivity.openFragment(AuthCodeFragment.newInstance());
				break;
			case R.id.tv_register_protocol://注册协议入口
				holdingActivity.openFragment(ProtocolNoteFragment.newInstance());
				break;
		}
	}
}
