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
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Abyss on 2017/2/13.
 * description:验证码页面
 */

public class AuthCodeFragment extends BaseFragment {

    private static final int SECOND = 60;
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
    private String name;

    public static AuthCodeFragment newInstance(String phone, String password, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        bundle.putString("password", password);
        bundle.putString("name", name);
        AuthCodeFragment authCodeFragment = new AuthCodeFragment();
        authCodeFragment.setArguments(bundle);
        return authCodeFragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        holdingActivity.setToolbarTitle("验证码");

    }

    @OnClick(R.id.bt_sure)
    public void onClick() {
        String authCode = etAuthCode.getText().toString().trim();
        LoginModel.requestRegisterAccount(phone, passwrod, authCode, name).compose(RxHelper.<String>handleResult())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {//注册成功
                        ToastUtil.showToast(getContext(), "注册成功");
                        holdingActivity.closeAllFragment();//到登录页面
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auth_code;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        holdingActivity = ((LoginActivity) getHoldingActivity());
        Bundle arguments = getArguments();
        if (arguments != null) {
            phone = arguments.getString("phone");
            passwrod = arguments.getString("password");
            name = arguments.getString("name");
        }
    }

    @Override
    protected void initListener() {
        verifyCodeListener();//控制验证码的发送
        RxTextView.textChanges(etAuthCode)//控制确认按钮是否可用
                .compose(this.<CharSequence>bindToLifecycle())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return etAuthCode.length() == 4;
                    }
                })
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        RxView.enabled(btSure).call(aBoolean);
                    }
                });

    }

    private void verifyCodeListener() {

        initTimer();
        RxView.enabled(tvSendAuthCode).call(false);
        tvSendAuthCode.setTextColor(getResources().getColor(R.color.TC_2));
        Observable<Void> verifyCodeObservable = RxView.clicks(tvSendAuthCode)
                .throttleFirst(3, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        RxView.enabled(tvSendAuthCode).call(false);
                    }
                });
        verifyCodeObservable
                .compose(this.<Void>bindToLifecycle())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        requestNetForCode();//请求网络发送验证码
                    }
                });
    }

    /**
     * 请求网络发送验证码
     */
    private void requestNetForCode() {
        LoginModel.requestAuthCode(phone)
                .compose(RxHelper.<String>handleResult())
                .compose(this.<String>bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        initTimer();//初始化计时器
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
     * 初始化计时器
     */
    private void initTimer() {
        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(SECOND)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        RxTextView.text(tvSendAuthCode).call("重新发送");
                        tvSendAuthCode.setTextColor(getResources().getColor(R.color.app_primary_color));
                        RxView.enabled(tvSendAuthCode).call(true);
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
     * 展示提醒
     */
    private void showRedTip(String message) {
        tvRedTip.setVisibility(View.VISIBLE);
        tvRedTip.setText(message);
    }


}
