package com.tianchuang.ihome_b.utils;

import android.text.TextUtils;

/**
 * Created by Abyss on 2017/2/15.
 * description:记载用户信息的工具类
 */

public class UserUtil {
	private static final String TOKEN = "token";
	private static final String ISLOGIN_INFO = "islogin_info";
	private static Boolean isLogin = false;
	private static String token = null;


	public static String getToken() {
		if (TextUtils.isEmpty(token)) {//先在内存读取
			token = Utils.getSpUtils().getString(TOKEN);
		}
		return token;
	}

	//退出登录
	public static void logout() {
		setToken(null);
		setIsLogined(false);
	}

	public static void setToken(String token) {
		Utils.getSpUtils().put(TOKEN, token);
		UserUtil.token = null;
	}

	public static void setIsLogined(Boolean isLogined) {
		Utils.getSpUtils().put(ISLOGIN_INFO, isLogined);
		UserUtil.isLogin = false;
	}

	//用户是否已经登录
	public static Boolean isLogin() {
		if (!isLogin) {
			isLogin = Utils.getSpUtils().getBoolean(ISLOGIN_INFO);
		}
		return isLogin;
	}
}
