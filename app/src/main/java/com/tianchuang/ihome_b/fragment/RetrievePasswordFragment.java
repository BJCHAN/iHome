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
import butterknife.OnClick;

/**
 * Created by Abyss on 2017/2/13.
 * description:找回密码页面
 */

public class RetrievePasswordFragment extends BaseFragment {

	@BindView(R.id.et_phone_num)
	EditText etPhoneNum;
	@BindView(R.id.et_auth_code)
	EditText etAuthCode;
	@BindView(R.id.tv_send_auth_code)
	TextView tvSendAuthCode;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.bt_next)
	Button btNext;
	@BindView(R.id.login_rl)
	RelativeLayout loginRl;
	private LoginActivity mActivity;

	public static RetrievePasswordFragment newInstance() {
		return new RetrievePasswordFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		this.mActivity = ((LoginActivity) getHoldingActivity());
	}

	@Override
	public void onStart() {
		super.onStart();
		mActivity.setToolbarTitle("找回密码");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_retrieve_password;
	}


	@OnClick({R.id.tv_send_auth_code, R.id.bt_next})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_send_auth_code://发送验证码
				break;
			case R.id.bt_next://下一步
				mActivity.openFragment(ResetPasswordFragment.newInstance());
				break;
		}
	}
}
