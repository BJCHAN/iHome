package com.tianchuang.ihome_b.bean.model

import com.tianchuang.ihome_b.bean.LoginBean
import com.tianchuang.ihome_b.http.retrofit.HttpModle
import com.tianchuang.ihome_b.http.retrofit.RetrofitService

import io.reactivex.Observable


/**
 * Created by Abyss on 2017/2/14.
 * description:登录模块请求网络
 */

object LoginModel {
    /**
     * 发送验证码
     */
    fun requestAuthCode(phone: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().sendAuthCode(phone)
    }

    /**
     * 注册账号
     */
    fun requestRegisterAccount(phone: String, pwd: String, authCode: String, name: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().registerAccount(phone, pwd, authCode, name)
    }

    /**
     * 登录
     */
    fun requestLogin(phone: String, pwd: String): Observable<HttpModle<LoginBean>> {
        return RetrofitService.createShowApi().login(phone, pwd)
    }

    /**
     * 找回密码发送短信验证
     */
    fun retrievePassword(phone: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().retrievePassword(phone)
    }

    /**
     * 重置密码
     */
    fun resetPassword(phone: String, pwd: String, authCode: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().resetPassword(phone, pwd, authCode)
    }

    /**
     * 修改密码
     */
    fun modifyPassword(oldpwd: String, pwd: String): Observable<HttpModle<String>> {
        return RetrofitService.createShowApi().modifyPassword(oldpwd, pwd)
    }

}
