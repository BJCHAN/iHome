package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.ui.activity.LoginActivity;
import com.tianchuang.ihome_b.utils.VerificationUtil;
import com.tianchuang.ihome_b.view.RegisterDialogFragment;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        Observable.combineLatest(authcode, phone, (authcode1, phone1) -> authcode1.length() == 4 && phone1.length() == 11)
                .compose(this.bindToLifecycle())
                .subscribe(aBoolean -> RxView.enabled(btNext).accept(aBoolean));


    }

    private void verifyCodeListener() {
        RxView.clicks(tvSendAuthCode)//发送验证码
                .throttleFirst(3, TimeUnit.SECONDS) //防止20秒内连续点击,或者只使用doOnNext部分
                .filter(aVoid -> {//验证是否是电话号码
                            return whetherCanLogin(etPhoneNum.getText().toString().trim());
                        }
                )
                .compose(this.bindToLifecycle())
                .subscribe(b -> {
                            showDialog(etPhoneNum.getText().toString().trim());
                        }

                );


    }

    private void showDialog(final String phone) {
        Observable.create(emitter -> initDialog(emitter, phone))
                .flatMap(o ->{showProgress();
                return LoginModel.INSTANCE.retrievePassword(etPhoneNum.getText().toString().trim()).compose(RxHelper.<String>handleResult());
                }
        )
                .compose(this.bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        showRedTip("");
                        initTimer();//访问成功开倒计时
                    }

                    @Override
                    public void _onError(String message) {
                        showRedTip(message);
                        dismissProgress();
                        tvSendAuthCode.setClickable(true);
                    }

                    @Override
                    public void onComplete() {

                        dismissProgress();
                    }
                });

    }

    private void initDialog(final ObservableEmitter<Object> subscriber, String phone) {
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
                        if (!subscriber.isDisposed()) {
                            subscriber.onNext("");
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
                .compose(this.bindToLifecycle())
                .doOnSubscribe(disposable -> tvSendAuthCode.setClickable(false))
                .subscribe(aLong -> {
                            if (aLong == 0)
                                tvSendAuthCode.setTextColor(getResources().getColor(R.color.TC_2));
                            RxTextView.text(tvSendAuthCode).accept((SECOND - aLong) + "s");
                        }, throwable -> {

                        }, () -> {
                            RxTextView.text(tvSendAuthCode).accept("重新发送");
                            tvSendAuthCode.setTextColor(getResources().getColor(R.color.app_primary_color));
                            tvSendAuthCode.setClickable(true);
                        }
                );
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
