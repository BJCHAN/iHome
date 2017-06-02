package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/2/13.
 * description:重置密码界面
 */

public class ResetPasswordFragment extends BaseFragment {
	@BindView(R.id.et_new_passwrod)
	EditText etNewPasswrod;
	@BindView(R.id.iv_pwd_isvisible)
	ImageView ivPwdIsvisible;
	@BindView(R.id.tv_red_tip)
	TextView tvRedTip;
	@BindView(R.id.bt_sure)
	Button btSure;
	private LoginActivity activity;
	private String phone;
	private String code;
	private Boolean isPwdVisible = false;

	public static ResetPasswordFragment newInstance(String phone, String code) {
		Bundle bundle = new Bundle();
		bundle.putString("phone", phone);
		bundle.putString("code", code);
		ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
		resetPasswordFragment.setArguments(bundle);
		return resetPasswordFragment;
	}

	@Override
	protected void initView(View view, Bundle savedInstanceState) {
		activity = ((LoginActivity) getHoldingActivity());
		Bundle arguments = getArguments();
		phone = arguments.getString("phone");
		code = arguments.getString("code");

	}

	@Override
	protected void initData() {
		super.initData();
	}

	@Override
	public void onStart() {
		super.onStart();
		setToolbarTitle("重置密码");
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_reset_passwrod;
	}

	@Override
	protected void initListener() {
		RxTextView.textChanges(etNewPasswrod)
				.compose(this.<CharSequence>bindToLifecycle())
				.map(text -> text.length() >= 6)
				.subscribe(aBoolean -> RxView.enabled(btSure).accept(aBoolean));
	}

	@OnClick({R.id.iv_pwd_isvisible, R.id.bt_sure})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.iv_pwd_isvisible:
				if (isPwdVisible) {
					ivPwdIsvisible.setImageResource(R.mipmap.pwd_invisible_icon);
					etNewPasswrod.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				} else {
					ivPwdIsvisible.setImageResource(R.mipmap.pwd_visible_icon);
					etNewPasswrod.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
				isPwdVisible = !isPwdVisible;
				etNewPasswrod.requestFocus();
				etNewPasswrod.setSelection(etNewPasswrod.length());
				break;

			case R.id.bt_sure:
				 String pwd = etNewPasswrod.getText().toString().trim();
				Observable.just(pwd)
						.filter(this::whetherCanLogin)
						.flatMap(str-> LoginModel.INSTANCE.resetPassword(phone, str, code).compose(RxHelper.handleResult()))
						.doOnSubscribe(o ->showProgress())
						.compose(bindToLifecycle())
						.subscribe(new RxSubscribe<String>() {
							@Override
							public void _onNext(String s) {
								activity.closeAllFragment();
								dismissProgress();
							}

							@Override
							public void _onError(String message) {
								showRedTip(message);
								dismissProgress();
							}

							@Override
							public void onComplete() {

							}
						});
				break;
		}
	}

	/**
	 * 检验是否可以进行下一步
	 */
	private boolean whetherCanLogin(String pwd) {
		boolean b = VerificationUtil.isValidPassword(pwd);
		if (!b) showRedTip(getResources().getString(R.string.pwd_format_error));
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
