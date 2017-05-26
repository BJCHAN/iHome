package com.tianchuang.ihome_b.mvp.ui.fragment;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.LoginActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


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
        setToolbarTitle("验证码");

    }

    @OnClick(R.id.bt_sure)
    public void onClick() {
        String authCode = etAuthCode.getText().toString().trim();
        LoginModel.INSTANCE.requestRegisterAccount(phone, passwrod, authCode, name).compose(RxHelper.<String>handleResult())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {//注册成功
                        ToastUtil.showToast(getContext(), "注册成功");
                        holdingActivity.closeAllFragment();//到登录页面
                    }

                    @Override
                    public void _onError(String message) {
                        showRedTip(message);
                    }

                    @Override
                    public void onComplete() {

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
        try {
            verifyCodeListener();//控制验证码的发送
        } catch (Exception e) {
            e.printStackTrace();
        }
        RxTextView.textChanges(etAuthCode)//控制确认按钮是否可用
                .compose(this.<CharSequence>bindToLifecycle())
                .map(o -> etAuthCode.length() == 4)
                .subscribe(b ->
                        RxView.enabled(btSure).accept(b));

    }

    private void verifyCodeListener() throws Exception {
        initTimer();
        RxView.enabled(tvSendAuthCode).accept(false);
        tvSendAuthCode.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_2));
        Observable<Object> verifyCodeObservable = RxView.clicks(tvSendAuthCode)
                .throttleFirst(3, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(o ->
                        RxView.enabled(tvSendAuthCode).accept(false));

        verifyCodeObservable
                .compose(bindToLifecycle())
                .subscribe(o -> this.requestNetForCode());
    }

    /**
     * 请求网络发送验证码
     */
    public void requestNetForCode() {
        LoginModel.INSTANCE.requestAuthCode(phone)
                .compose(RxHelper.handleResult())
                .compose(bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                    }

                    @Override
                    public void _onError(String message) {
                        showRedTip(message);
                    }

                    @Override
                    public void onComplete() {
                        initTimer();//初始化计时器
                    }
                });
    }

    /**
     * 初始化计时器
     */
    private void initTimer() {
        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(SECOND)
                .compose(this.bindToLifecycle())
                .subscribe(aLong -> {
                            if (aLong == 0)
                                tvSendAuthCode.setTextColor(ContextCompat.getColor(getContext(), R.color.TC_2));
                            RxTextView.text(tvSendAuthCode).accept((SECOND - aLong) + "s");
                        }
                        , e -> {
                        }
                        , () -> {
                            RxTextView.text(tvSendAuthCode).accept("重新发送");
                            tvSendAuthCode.setTextColor(getResources().getColor(R.color.app_primary_color));
                            RxView.enabled(tvSendAuthCode).accept(true);
                        }
                );
    }


    /**
     * 展示提醒
     */
    private void showRedTip(String message) {
        tvRedTip.setVisibility(View.VISIBLE);
        tvRedTip.setText(message);
    }


}
