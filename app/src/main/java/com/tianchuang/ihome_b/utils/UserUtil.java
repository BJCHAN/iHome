package com.tianchuang.ihome_b.utils;

/**
 * Created by Abyss on 2017/2/15.
 * description:
 */

public class UserUtil {
	private static Boolean isLogined = false;
	private static String token ="";

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		UserUtil.token = token;
	}

	public static void setIsLogined(Boolean isLogined) {
		UserUtil.isLogined = isLogined;
	}

	public static Boolean isLogin() {
		return isLogined;
	}
}
