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
import com.tianchuang.ihome_b.bean.BaseHttpBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.LoginModel;
import com.tianchuang.ihome_b.utils.ToastUtil;

import butterknife.BindView;
import rx.Subscriber;

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
	private String phone;
	private String passwrod;

	public static AuthCodeFragment newInstance(String phone, String password) {
		Bundle bundle = new Bundle();
		bundle.putString("phone", phone);
		bundle.putString("password", password);
		AuthCodeFragment authCodeFragment = new AuthCodeFragment();
		authCodeFragment.setArguments(bundle);
		return authCodeFragment;
	}


	@Override
	public void onStart() {
		super.onStart();
		holdingActivity.setToolbarTitle("验证码");

	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		holdingActivity = ((LoginActivity) getHoldingActivity());
		Bundle arguments = getArguments();
		if (arguments != null) {
			phone = arguments.getString("phone");
			passwrod = arguments.getString("password");
		}
	}

	@Override
	protected void initData() {
		LoginModel.requestAuthCode(phone)
				.compose(RxHelper.<String>handleResult())
				.subscribe(new RxSubscribe<String>() {
					@Override
					protected void _onNext(String s) {
						ToastUtil.showToast(getContext(),"成功");
					}

					@Override
					protected void _onError(String message) {
						ToastUtil.showToast(getContext(),message);
					}

					@Override
					public void onCompleted() {

					}
				});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_auth_code;
	}


}
