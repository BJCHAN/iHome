package com.tianchuang.ihome_b.bean.model;

import com.tianchuang.ihome_b.bean.LoginBean;
import com.tianchuang.ihome_b.http.retrofit.HttpModle;
import com.tianchuang.ihome_b.http.retrofit.RetrofitService;

import rx.Observable;

/**
 * Created by Abyss on 2017/2/14.
 * description:登录模块请求网络
 */

public class LoginModel {
	/**
	 * 发送验证码
	 */
	public static Observable<HttpModle<String>> requestAuthCode(String phone) {
		return RetrofitService.createShowApi().sendAuthCode(phone);
	}

	/**
	 * 注册账号
	 */
	public static Observable<HttpModle<String>> requestRegisterAccount(String phone, String pwd, String authCode) {
		return RetrofitService.createShowApi().registerAccount(phone, pwd, authCode);
	}

	/**
	 * 登录
	 */
	public static Observable<HttpModle<LoginBean>> requestLogin(String phone, String pwd) {
		return RetrofitService.createShowApi().login(phone, pwd);
	}

	/**
	 * 找回密码发送短信验证
	 */
	public static Observable<HttpModle<String>> retrievePassword(String phone) {
		return RetrofitService.createShowApi().retrievePassword(phone);
	}

	/**
	 * 重置密码
	 */
	public static Observable<HttpModle<String>> resetPassword(String phone, String pwd, String authCode) {
		return RetrofitService.createShowApi().resetPassword(phone, pwd, authCode);
	}

	/**
	 * 修改密码
	 */
	public static Observable<HttpModle<String>> modifyPassword(String oldpwd, String pwd) {
		return RetrofitService.createShowApi().modifyPassword(oldpwd, pwd);
	}

}
