package com.tianchuang.ihome_b.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.http.retrofit.model.LoginModel;
import com.tianchuang.ihome_b.utils.VerificationUtil;
import com.tianchuang.ihome_b.view.RegisterDialogFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

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
	private static final int SECOND = 60;

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


	@OnClick(R.id.bt_next)
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.bt_next://下一步
				if (whetherCanLogin(etPhoneNum.getText().toString().trim())) {
					mActivity.openFragment(ResetPasswordFragment.newInstance(etPhoneNum.getText().toString().trim(), etAuthCode.getText().toString().trim()));
				}
				break;
		}
	}

	@Override
	protected void initListener() {
		verifyCodeListener();//控制验证码的发送
		Observable<CharSequence> authcode = RxTextView.textChanges(etAuthCode);
		Observable<CharSequence> phone = RxTextView.textChanges(etPhoneNum);
		//控制下一步按钮是否可用
		Observable.combineLatest(authcode, phone, new Func2<CharSequence, CharSequence, Boolean>() {
			@Override
			public Boolean call(CharSequence authcode, CharSequence phone) {
				return authcode.length() == 4 && phone.length() == 11;
			}
		})
				.compose(this.<Boolean>bindToLifecycle())
				.subscribe(new Action1<Boolean>() {
					@Override
					public void call(Boolean aBoolean) {
						RxView.enabled(btNext).call(aBoolean);
					}
				});


	}

	private void verifyCodeListener() {
		RxView.clicks(tvSendAuthCode)//发送验证码
				.throttleFirst(3, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
				.filter(new Func1<Void, Boolean>() {//验证是否是电话号码
					@Override
					public Boolean call(Void aVoid) {
						return whetherCanLogin(etPhoneNum.getText().toString().trim());
					}
				})
				.compose(this.<Void>bindToLifecycle())
				.subscribe(new Action1<Void>() {
					@Override
					public void call(Void aVoid) {
						showDialog(etPhoneNum.getText().toString().trim());
					}
				});


	}

	private void showDialog(final String phone) {
		Observable.create(new Observable.OnSubscribe<Object>() {
			@Override
			public void call(final Subscriber<? super Object> subscriber) {
				initDialog(subscriber, phone);
			}
		}).flatMap(new Func1<Object, Observable<String>>() {
			@Override
			public Observable<String> call(Object o) {//访问网络
				showProgress();
				return LoginModel.retrievePassword(etPhoneNum.getText().toString().trim()).compose(RxHelper.<String>handleResult());
			}
		})
				.compose(this.<String>bindToLifecycle())
				.subscribe(new RxSubscribe<String>() {
					@Override
					protected void _onNext(String s) {
						showRedTip("");
						initTimer();//访问成功开倒计时
						dismissProgress();
					}

					@Override
					protected void _onError(String message) {
						showRedTip(message);
						dismissProgress();
						tvSendAuthCode.setClickable(true);
					}

					@Override
					public void onCompleted() {
					}
				});

	}

	private void initDialog(final Subscriber<? super Object> subscriber, String phone) {
		RegisterDialogFragment
				.newInstance(
						String.format(getString(R.string.retrieve_pwd_custom_dialog_tip)
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

	/**
	 * 初始化计时器
	 */
	private void initTimer() {
		Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
				.take(SECOND)
				.compose(this.<Long>bindToLifecycle())
				.doOnSubscribe(new Action0() {
					@Override
					public void call() {
						tvSendAuthCode.setClickable(false);
					}
				})
				.subscribe(new Observer<Long>() {
					@Override
					public void onCompleted() {
						RxTextView.text(tvSendAuthCode).call("重新发送");
						tvSendAuthCode.setTextColor(getResources().getColor(R.color.app_primary_color));
						tvSendAuthCode.setClickable(true);
					}

					@Override
					public void onError(Throwable e) {
					}

					@Override
					public void onNext(Long aLong) {
						if (aLong == 0)
							tvSendAuthCode.setTextColor(getResources().getColor(R.color.TC_2));
						RxTextView.text(tvSendAuthCode).call((SECOND - aLong) + "s");
					}
				});
	}

	/**
	 * 检验是否可以进行下一步
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

}
