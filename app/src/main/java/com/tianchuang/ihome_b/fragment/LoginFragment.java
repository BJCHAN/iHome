package com.tianchuang.ihome_b.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.activity.Main2Activity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.utils.MaterialDialogsUtil;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Abyss on 2017/2/13.
 * description:登录页面
 */

public class LoginFragment extends BaseFragment {

	@BindView(R.id.et_phone_num)
	EditText etPhoneNum;
	@BindView(R.id.et_passwrod)
	EditText etPasswrod;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.tv_forget_pwd)
	TextView tvForgetPwd;
	@BindView(R.id.loginBt)
	Button mLoginBt;
	@BindView(R.id.registerbt)
	TextView registerbt;

	private MaterialDialogsUtil materialDialogsUtil;
	private LoginActivity mActivity;

	public static LoginFragment newInstance() {
		return new LoginFragment();
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		registerbt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
		registerbt.getPaint().setAntiAlias(true);//抗锯齿
		materialDialogsUtil = new MaterialDialogsUtil(getHoldingActivity());
		mActivity = (LoginActivity) getHoldingActivity();
		mLoginBt.setEnabled(false);

	}

	@Override
	protected void initListener() {
		Observable<CharSequence> email = RxTextView.textChanges(etPhoneNum);
		Observable<CharSequence> pwd = RxTextView.textChanges(etPasswrod);
		loginBtnEnable(email, pwd);//控制登录按钮是否可用
	}

	@Override
	protected void initData() {

	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_login;
	}


	@OnClick({R.id.tv_forget_pwd, R.id.loginBt, R.id.registerbt})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_forget_pwd://忘记密码
				mActivity.openFragment(RetrievePasswordFragment.newInstance());
				break;
			case R.id.loginBt://登录
				goToLogin(etPhoneNum, etPasswrod);
				break;
			case R.id.registerbt://注册账户
				mActivity.openFragment(RegisterAccountFragment.newInstance());
				break;
		}
	}

	/**
	 * 登录的操作
	 *
	 * @param etPhoneNum
	 * @param etPasswrod
	 */
	private void goToLogin(EditText etPhoneNum, EditText etPasswrod) {
		final String phone = etPhoneNum.getText().toString().trim();
		final String pwd = etPasswrod.getText().toString().trim();
		Observable.zip(Observable.just(phone), Observable.just(pwd), new Func2<String, String, Boolean>() {
			@Override
			public Boolean call(String phone, String pwd) {
				return whetherCanLogin(phone);
			}
		})
				.filter(new Func1<Boolean, Boolean>() {
					@Override
					public Boolean call(Boolean aBoolean) {
						return aBoolean;
					}
				})
				.subscribe(new Action1<Boolean>() {
					@Override
					public void call(Boolean aBoolean) {
						mActivity.startActivity(new Intent(mActivity, Main2Activity.class));
						mActivity.finish();
					}
				});

	}

	/**
	 * 检验是否可以登录
	 */
	private Boolean whetherCanLogin(String phone) {
		boolean b = VerificationUtil.isValidTelNumber(phone);
		if (!b) showRedTip(getResources().getString(R.string.login_phone_error));
		return b;
	}

	/**
	 * 展示提醒
	 */
	private void showRedTip(String message) {
		tvRedTip.setVisibility(View.VISIBLE);
		tvRedTip.setText(message);
	}

	/**
	 * 账号和密码控制登录按钮
	 *
	 * @param email
	 * @param pwd
	 */
	private void loginBtnEnable(Observable<CharSequence> email, Observable<CharSequence> pwd) {
		Observable.combineLatest(email, pwd, new Func2<CharSequence, CharSequence, Boolean>() {
			@Override
			public Boolean call(CharSequence email, CharSequence pwd) {//每当发射数据结合最近一个
				return email.length() > 0 && pwd.length() >= 6;
			}
		}).compose(this.<Boolean>bindToLifecycle()).subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean aBoolean) {
				mLoginBt.setEnabled(aBoolean);
			}
		});
	}
}
