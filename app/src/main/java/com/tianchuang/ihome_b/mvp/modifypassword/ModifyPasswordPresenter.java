package com.tianchuang.ihome_b.mvp.modifypassword;

import android.support.v4.content.ContextCompat;

import com.tianchuang.ihome_b.R;
import com.tianchuang.ihome_b.bean.model.LoginModel;
import com.tianchuang.ihome_b.http.retrofit.RxHelper;
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe;
import com.tianchuang.ihome_b.mvp.mvpbase.BasePresenterImpl;
import com.tianchuang.ihome_b.utils.VerificationUtil;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func3;

/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

public class ModifyPasswordPresenter extends BasePresenterImpl<ModifyPasswordContract.View> implements ModifyPasswordContract.Presenter {

    //提交密码修改
    public void submitPwdChanged(final String oldPwd, final String newPwd, String surePwd) {
        Observable.zip(Observable.just(oldPwd), Observable.just(newPwd), Observable.just(surePwd)
                , (oldPwd1, newPwd1, surePwd1) -> whetherValidPwd(newPwd1, surePwd1))
                .filter(bln -> bln)
                .flatMap(bln -> LoginModel.modifyPassword(oldPwd, newPwd).compose(RxHelper.handleResult()))
                .compose(mView.bindToLifecycle())
                .subscribe(new RxSubscribe<String>() {
                    @Override
                    protected void _onNext(String s) {
                        mView.onRequestCompleted();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.onRequestError(message);
                    }

                    @Override
                    public void onCompleted() {

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
            mView.showRedTip(getContext().getResources().getString(R.string.pwd_format_error));
            return false;
        }
        if (!newPwd.equals(pwd)) {
            mView.showRedTip(getContext().getResources().getString(R.string.pwd_modify_error));
            return false;
        }
        return true;
    }
}
