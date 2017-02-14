package com.tianchuang.ihome_b.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Abyss on 2017/2/13.
 * description:校验工具类
 */

public class VerificationUtil {

	/**
	 * 判断手机号码是否有效
	 */
	public static boolean isValidTelNumber(String telNumber) {
		if (!TextUtils.isEmpty(telNumber)) {
			String regex = "(\\+\\d+)?1[34578]\\d{9}$";
			return Pattern.matches(regex, telNumber);
		}

		return false;
	}
	/**
	 * 判断手机号码是否有效
	 */
	public static boolean isValidPassword(String passwrod) {
		if (!TextUtils.isEmpty(passwrod)) {
			String regex = "[\\da-zA-Z]{6,16}";//6-16位数字或密码的组合
			return Pattern.matches(regex, passwrod);
		}
		return false;
	}
}
