package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.utils.VerificationUtil;
import com.tianchuang.ihome_b.view.RegisterDialogFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

import static com.tianchuang.ihome_b.R.id.iv_pwd_isvisible;

/**
 * Created by Abyss on 2017/2/13.
 * description:注册账号页面
 */

public class RegisterAccountFragment extends BaseFragment {

	@BindView(R.id.et_phone_num)
	EditText etPhoneNum;
	@BindView(R.id.et_passwrod)
	EditText etPasswrod;
	@BindView(iv_pwd_isvisible)
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
	private Boolean isPwdVisible = false;

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
		Observable<Boolean> checkedChanges = RxCompoundButton.checkedChanges(cbIsagree);
		registerBtnEnable(email, pwd, checkedChanges);//控制登录按钮是否可用
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


	@OnClick({R.id.bt_register, R.id.tv_register_protocol, iv_pwd_isvisible})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.bt_register:
				goToRegister(etPhoneNum, etPasswrod);
				break;
			case R.id.tv_register_protocol://注册协议入口
				holdingActivity.openFragment(ProtocolNoteFragment.newInstance());
				break;
			case iv_pwd_isvisible://密码是否可见
				if (isPwdVisible) {
					ivPwdIsvisible.setImageResource(R.mipmap.pwd_invisible_icon);
					etPasswrod.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				} else {
					ivPwdIsvisible.setImageResource(R.mipmap.pwd_visible_icon);
					etPasswrod.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				isPwdVisible = !isPwdVisible;
				etPasswrod.requestFocus();
				etPasswrod.setSelection(etPasswrod.length());
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
						showDialog(phone, password);
					}
				});

	}

	private void showDialog(final String phone, final String password) {
		//弹窗上确认的点击事件
		Observable.create(new Observable.OnSubscribe<Object>() {
			@Override
			public void call(final Subscriber<? super Object> subscriber) {
				initDialog(subscriber, phone);
			}
		})        //防抖
				.throttleFirst(3, TimeUnit.SECONDS)
				.flatMap(new Func1<Object, Observable<String>>() {
					@Override
					public Observable<String> call(Object o) {//请求网络
						return LoginModel.requestAuthCode(phone).compose(RxHelper.<String>handleResult());
					}
				})
				.compose(this.<String>bindToLifecycle())
				.subscribe(new RxSubscribe<String>() {
					@Override
					protected void _onNext(String s) {
						//跳转添加验证码页面
						holdingActivity.openFragment(AuthCodeFragment
								.newInstance(phone
										, password)
						);
					}

					@Override
					protected void _onError(String message) {
						showRedTip(message);
					}

					@Override
					public void onCompleted() {

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
	 * @param checkedChanges
	 */
	private void registerBtnEnable(Observable<CharSequence> email, Observable<CharSequence> pwd, Observable<Boolean> checkedChanges) {
		Observable.combineLatest(email, pwd, checkedChanges, new Func3<CharSequence, CharSequence, Boolean, Boolean>() {
			@Override
			public Boolean call(CharSequence email, CharSequence pwd, Boolean cheaked) {
				return email.length() > 0 && pwd.length() >= 6 && cheaked;
			}
		}).compose(this.<Boolean>bindToLifecycle()).subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean aBoolean) {
				btRegister.setEnabled(aBoolean);
			}
		});
	}


	private void initDialog(final Subscriber<? super Object> subscriber, String phone) {
		RegisterDialogFragment
				.newInstance(
						String.format(getString(R.string.register_custom_dialog_tip)
								, phone
						))
				.setOnClickButtonListener(new RegisterDialogFragment.OnClickButtonListener() {

					@Override
					public void onClickCancel() {

					}

					@Override
					public void onClickSure() {
						if (!subscriber.isUnsubscribed()) {
							subscriber.onNext(null);
						}
					}
				})
				.show(getHoldingActivity().getFragmentManager(), RegisterDialogFragment.class.getSimpleName());
	}


}
