package com.tianchuang.ihome_b.mvp.presenter

import com.tianchuang.ihome_b.R
import com.tianchuang.ihome_b.bean.model.LoginModel
import com.tianchuang.ihome_b.http.retrofit.RxHelper
import com.tianchuang.ihome_b.http.retrofit.RxSubscribe
import com.tianchuang.ihome_b.mvp.BasePresenterImpl
import com.tianchuang.ihome_b.mvp.contract.ModifyPasswordContract
import com.tianchuang.ihome_b.utils.VerificationUtil

import io.reactivex.Observable
import io.reactivex.functions.Function3


/**
 * Created by Abyss on 2017/4/1.
 * description:
 */

class ModifyPasswordPresenter : BasePresenterImpl<ModifyPasswordContract.View>(), ModifyPasswordContract.Presenter {

    //提交密码修改
    override fun submitPwdChanged(oldPwd: String, newPwd: String, surePwd: String) {
        Observable.zip(Observable.just(oldPwd), Observable.just(newPwd), Observable.just(surePwd), Function3<String, String, String, Boolean> { s, newPwd1, surePwd1 -> whetherValidPwd(newPwd1, surePwd1) })
                .doOnSubscribe { mView!!.showProgress() }
                .filter { aBoolean -> aBoolean!! }
                .flatMap { LoginModel.modifyPassword(oldPwd, newPwd).compose(RxHelper.handleResult<String>()) }
                .compose(mView!!.bindToLifecycle<String>())
                .subscribe(object : RxSubscribe<String>() {
                    override fun _onNext(s: String?) {
                        mView!!.dismissProgress()
                        mView!!.startFragment()//跳转第二个页面
                    }

                    override fun _onError(message: String) {
                        mView!!.showToast(message)
                        mView!!.dismissProgress()
                    }

                    override fun onComplete() {

                    }
                })
    }

    /**
     * 检验是否可以修改
     */
    private fun whetherValidPwd(newPwd: String, pwd: String): Boolean {
        val b1 = VerificationUtil.isValidPassword(pwd)
        val b2 = VerificationUtil.isValidPassword(newPwd)
        if (!(b1 && b2)) {
            mView!!.showRedTip(context.resources.getString(R.string.pwd_format_error))
            return false
        }
        if (newPwd != pwd) {
            mView!!.showRedTip(context.resources.getString(R.string.pwd_modify_error))
            return false
        }
        return true
    }
}
