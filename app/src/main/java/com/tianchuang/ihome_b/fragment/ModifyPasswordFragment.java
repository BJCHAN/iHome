package com.tianchuang.ihome_b.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.activity.ModifyPasswordActivity;
import com.tianchuang.ihome_b.base.BaseFragment;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.utils.FragmentUtils;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by Abyss on 2017/2/16.
 * description:修改密码界面
 */

public class ModifyPasswordFragment extends BaseFragment {
    @BindView(R.id.et_old_passwrod)
    EditText etOldPasswrod;
    @BindView(R.id.et_new_passwrod)
    EditText etNewPasswrod;
    @BindView(R.id.et_sure_passwrod)
    EditText etSurePasswrod;
    @BindView(R.id.tv_red_tip)
    TextView tvRedTip;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    private ModifyPasswordActivity holdingActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        holdingActivity = ((ModifyPasswordActivity) getHoldingActivity());
    }

    public static ModifyPasswordFragment newInstance() {
        return new ModifyPasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }


    @OnClick(R.id.bt_submit)
    public void onClick() {
        final String oldPwd = etOldPasswrod.getText().toString().trim();
        final String newPwd = etNewPasswrod.getText().toString().trim();
        String surePwd = etSurePasswrod.getText().toString().trim();

        Observable.zip(Observable.just(oldPwd), Observable.just(newPwd), Observable.just(surePwd)
                , new Func3<String, String, String, Boolean>() {
                    @Override
                    public Boolean call(String oldPwd, String newPwd, String surePwd) {
                        return whetherValidPwd(newPwd, surePwd);
                    }
                })
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean aBoolean) {
                        return aBoolean;
                    }
                })
                .flatMap(new Func1<Boolean, Observable<String>>() {
                    @Override
                    public Observable<String> call(Boolean aBoolean) {
                        return LoginModel.modifyPassword(oldPwd, newPwd).compose(RxHelper.<String>handleResult());
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showProgress();
                    }
                })
                .compose(this.<String>bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        dismissProgress();
                        FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getContainer(), ModifyPwdSuccessFragment.newInstance(), false);
                    }

                    @Override
                    protected void _onError(String message) {
                        dismissProgress();
                        showRedTip(message);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Observable<CharSequence> oldPwd = RxTextView.textChanges(etOldPasswrod);
        Observable<CharSequence> newPwd = RxTextView.textChanges(etNewPasswrod);
        Observable<CharSequence> surePwd = RxTextView.textChanges(etSurePasswrod);
        submitBtnEnable(oldPwd, newPwd, surePwd);
    }

    /**
     * 原密码和新密码控制登录按钮
     *
     * @param oldPwd
     * @param newPwd
     * @param surePwd
     */
    private void submitBtnEnable(Observable<CharSequence> oldPwd, Observable<CharSequence> newPwd, Observable<CharSequence> surePwd) {
        Observable.combineLatest(oldPwd, newPwd, surePwd, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence oldPwd, CharSequence newPwd, CharSequence surePwd) {
                return oldPwd.length() >= 6 && newPwd.length() > 0 && surePwd.length() > 0;
            }
        }).compose(this.<Boolean>bindToLifecycle()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                btSubmit.setEnabled(aBoolean);
            }
        });
    }

    /**
     * 检验是否可以修改
     */
    private Boolean whetherValidPwd(String newPwd, String pwd) {
        boolean b1 = VerificationUtil.isValidPassword(pwd);
        boolean b2 = VerificationUtil.isValidPassword(newPwd);
        if (!(b1 && b2)) {
            showRedTip(getResources().getString(R.string.pwd_format_error));
            return false;
        }
        if (!newPwd.equals(pwd)) {
            showRedTip(getResources().getString(R.string.pwd_modify_error));
            return false;
        }
        return true;
    }

    /**
     * 展示提醒
     */
    private void showRedTip(String message) {
        tvRedTip.setVisibility(View.VISIBLE);
        tvRedTip.setText(message);
    }
}
