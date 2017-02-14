package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

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
	protected void initListener() {
		Observable<CharSequence> email = RxTextView.textChanges(etPhoneNum);
		Observable<CharSequence> pwd = RxTextView.textChanges(etPasswrod);
		registerBtnEnable(email, pwd);//控制登录按钮是否可用
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
				goToRegister(etPhoneNum, etPasswrod);
				break;
			case R.id.tv_register_protocol://注册协议入口
				holdingActivity.openFragment(ProtocolNoteFragment.newInstance());
				break;
		}
	}

	/**
	 * 注册的操作
	 *
	 * @param etPhoneNum
	 * @param etPasswrod
	 */
	private void goToRegister(EditText etPhoneNum, EditText etPasswrod) {
		final String phone = etPhoneNum.getText().toString().trim();
		final String password = etPasswrod.getText().toString().trim();
		Observable.zip(Observable.just(phone), Observable.just(password), new Func2<String, String, Boolean>() {
			@Override
			public Boolean call(String phone, String pwd) {
				return whetherCanLogin(phone, pwd);
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
						holdingActivity.openFragment(AuthCodeFragment
								.newInstance(phone
										, password)
						);
					}
				});

	}

	/**
	 * 检验是否可以登录
	 */
	private Boolean whetherCanLogin(String phone, String pwd) {
		boolean b = VerificationUtil.isValidTelNumber(phone);
		if (!b) {
			showRedTip(getResources().getString(R.string.login_phone_error));
			return b;
		}
		b = VerificationUtil.isValidPassword(pwd);
		if (!b) {
			showRedTip(getResources().getString(R.string.pwd_format_error));
			return b;
		}
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
	private void registerBtnEnable(Observable<CharSequence> email, Observable<CharSequence> pwd) {
		Observable.combineLatest(email, pwd, new Func2<CharSequence, CharSequence, Boolean>() {
			@Override
			public Boolean call(CharSequence email, CharSequence pwd) {//每当发射数据结合最近一个
				return email.length() > 0 && pwd.length() >= 6;
			}
		}).compose(this.<Boolean>bindToLifecycle()).subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean aBoolean) {
				btRegister.setEnabled(aBoolean);
			}
		});
	}


}
