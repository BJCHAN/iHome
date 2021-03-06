package com.tianchuang.ihome_b.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.mvp.ui.activity.ModifyPasswordActivity;
import com.tianchuang.ihome_b.mvp.MVPBaseFragment;
import com.tianchuang.ihome_b.mvp.contract.ModifyPasswordContract;
import com.tianchuang.ihome_b.mvp.presenter.ModifyPasswordPresenter;
import com.tianchuang.ihome_b.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * Created by Abyss on 2017/2/16.
 * description:修改密码界面
 *
 */

public class ModifyPasswordFragment extends MVPBaseFragment<ModifyPasswordContract.View, ModifyPasswordPresenter> implements ModifyPasswordContract.View {
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

    @Override
    protected void initData() {
        showSucceedPage();
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
     */
    private void submitBtnEnable(Observable<CharSequence> oldPwd, Observable<CharSequence> newPwd, Observable<CharSequence> surePwd) {
        Observable.combineLatest(oldPwd, newPwd, surePwd
                , (oldPwd1, newPwd1, surePwd1) -> oldPwd1.length() >= 6 && newPwd1.length() > 0 && surePwd1.length() > 0)
                .compose(bindToLifecycle())
                .subscribe(aBoolean -> btSubmit.setEnabled(aBoolean));
    }

    @OnClick(R.id.bt_submit)
    public void onClick() {
        final String oldPwd = etOldPasswrod.getText().toString().trim();
        final String newPwd = etNewPasswrod.getText().toString().trim();
        String surePwd = etSurePasswrod.getText().toString().trim();
        getMPresenter().submitPwdChanged(oldPwd, newPwd, surePwd);
    }

    /**
     * 展示提醒
     */
    public void showRedTip(String message) {
        tvRedTip.setVisibility(View.VISIBLE);
        tvRedTip.setText(message);
    }

    public void startFragment() {
        FragmentUtils.popAddFragment(getFragmentManager(), holdingActivity.getContainer(), ModifyPwdSuccessFragment.newInstance(), false);
    }


}
