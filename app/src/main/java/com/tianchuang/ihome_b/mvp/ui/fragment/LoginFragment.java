package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.LoginActivity;
import com.tianchuang.ihome_b.mvp.ui.activity.MainActivity;
import com.tianchuang.ihome_b.utils.UserUtil;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


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

    private LoginActivity mActivity;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        registerbt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        registerbt.getPaint().setAntiAlias(true);//抗锯齿
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
                clearData();
                break;
            default:
        }
    }

    private void clearData() {
        etPhoneNum.setText("");
        etPasswrod.setText("");
        tvRedTip.setVisibility(View.INVISIBLE);
        tvRedTip.setText("");
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
        Observable.zip(Observable.just(phone), Observable.just(pwd),

                (phone1, pwd1) -> whetherCanLogin(phone)
        )
                .filter(b -> b)
                .flatMap(b -> LoginModel.INSTANCE.requestLogin(phone, pwd).compose(RxHelper.handleResult())

                )
                .compose(this.bindToLifecycle())
                .doOnSubscribe(o -> showProgress())
                .subscribe(new RxSubscribe<LoginBean>() {
                    @Override
                    public void _onNext(LoginBean s) {
                        UserUtil.login(s);
                        mActivity.dismissProgress();
                        startActivityWithAnim(new Intent(mActivity, MainActivity.class));
                        mActivity.finish();
                    }

                    @Override
                    public void _onError(String message) {
                        mActivity.getMaterialDialogsUtil().dismiss();
                        showRedTip(message);
                        dismissProgress();
                    }

                    @Override
                    public void onComplete() {

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
     * @param phone
     * @param pwd
     */
    private void loginBtnEnable(Observable<CharSequence> phone, Observable<CharSequence> pwd) {
        Observable.combineLatest(phone, pwd,
                (email1, pwd1) -> email1.length() > 0 && pwd1.length() >= 6
        ).compose(this.bindToLifecycle()).subscribe(b -> mLoginBt.setEnabled(b));
    }
}
