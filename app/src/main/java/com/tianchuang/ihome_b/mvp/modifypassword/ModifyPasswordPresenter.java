package com.tianchuang.ihome_b.mvp.modifypassword;

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

public class ModifyPasswordPresenter extends BasePresenterImpl<ModifyPasswordContract.View> implements ModifyPasswordContract.Presenter{

    //提交密码修改
    public void submitPwdChanged(final String oldPwd, final String newPwd, String surePwd) {
        Subscription subscribe = Observable.zip(Observable.just(oldPwd), Observable.just(newPwd), Observable.just(surePwd)
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
        getCompositeSubscription().add(subscribe);
    }

    /**
     * 检验是否可以修改
     */
    private Boolean whetherValidPwd(String newPwd, String pwd) {
        boolean b1 = VerificationUtil.isValidPassword(pwd);
        boolean b2 = VerificationUtil.isValidPassword(newPwd);
        if (!(b1 && b2)) {
            mView.showRedTip(mView.getContext().getResources().getString(R.string.pwd_format_error));
            return false;
        }
        if (!newPwd.equals(pwd)) {
            mView.showRedTip(mView.getContext().getResources().getString(R.string.pwd_modify_error));
            return false;
        }
        return true;
    }
}
