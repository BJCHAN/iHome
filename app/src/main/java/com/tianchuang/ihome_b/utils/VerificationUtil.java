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
}
