package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
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
    AppCompatCheckBox cbIsagree;
    @BindView(R.id.tv_register_protocol)
    TextView tvRegisterProtocol;
    @BindView(R.id.login_rl)
    RelativeLayout loginRl;
    @BindView(R.id.et_name)
    EditText etName;
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
        setToolbarTitle("注册账号");
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
        final String name = etName.getText().toString().trim();
        Observable.zip(
                Observable.just(phone), Observable.just(password), Observable.just(name),
                (phone1, pwd1, name1) -> whetherCanLogin(phone1, pwd1, name1)
        )
                .filter(b -> b)
                .subscribe(aBoolean -> showDialog(phone, password, name));

    }

    private void showDialog(final String phone, final String password, final String name) {
        //弹窗上确认的点击事件
        Observable.create(eimtter -> initDialog(eimtter, phone))        //防抖
                .throttleFirst(3, TimeUnit.SECONDS)
                .flatMap(o -> LoginModel.requestAuthCode(phone).compose(RxHelper.handleResult())
        )
                .compose(this.bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    public void _onNext(String s) {
                        //跳转添加验证码页面
                        holdingActivity.openFragment(AuthCodeFragment
                                .newInstance(phone
                                        , password, name)
                        );
                    }

                    @Override
                    public  void _onError(String message) {
                        showRedTip(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 检验是否可以登录
     */
    private Boolean whetherCanLogin(String phone, String pwd, String name) {
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
        b = !TextUtils.isEmpty(name);
        if (!b) {
            showRedTip(getString(R.string.register_name_error));
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
        Observable.combineLatest(email, pwd, checkedChanges,  (email1, pwd1, cheaked) -> email1.length() > 0 && pwd1.length() >= 6 && cheaked).compose(this.<Boolean>bindToLifecycle())
                .subscribe( aBoolean -> btRegister.setEnabled(aBoolean));
    }


    private void initDialog(final ObservableEmitter<Object> subscriber, String phone) {
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
                        if (!subscriber.isDisposed()) {
                            subscriber.onNext("");
                        }
                    }
                })
                .show(getHoldingActivity().getFragmentManager(), RegisterDialogFragment.class.getSimpleName());
    }


}
