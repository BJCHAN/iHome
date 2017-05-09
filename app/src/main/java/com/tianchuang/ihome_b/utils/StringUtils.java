//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tianchuang.ihome_b.utils;


import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

public class StringUtils {
	public StringUtils() {
	}

	/**
	 * 判断字符串是否为null或全为空格
	 *
	 * @param s 待校验字符串
	 * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
	 */
	public static boolean isSpace(String s) {
		return (s == null || s.trim().length() == 0);
	}

	/**
	 * 判断字符串是否是数字
	 */
	public static boolean isNumber(String str) {
		String reg = "^[0-9]+(.[0-9]+)?$";
		return str.matches(reg);
	}

	/**
	 * 字符串始终不为空
	 */
	public static String getNotNull(String text) {
		return TextUtils.isEmpty(text) ? "" : text;
	}

	/**
	 * 保留两位的数
	 */
	public static String formatNum(int num) {
		return new DecimalFormat("0.00").format(num);
	}

	/**
	 * 保留两位的数
	 */
	public static String formatNum(float num) {
		if (num == 0) {
			return "0.0";
		}
		return new DecimalFormat("0.00").format(num);
	}
	/**
	 *
	 * 保留四位的小数
	 */
	public static String formatNumWithFour(String num) {
		if (Double.valueOf(num)==0) {
			return "0";
		}
		return new DecimalFormat("0.0000").format(Double.valueOf(num));
	}
	/**
	 * 将实体类转换成json字符串对象            注意此方法需要第三方gson  jar包
	 *
	 * @param obj 对象
	 * @return map
	 */
	public static String toJson(Object obj, int method) {
		if (method == 1) {
			Gson gson = new Gson();
			return gson.toJson(obj);
		} else if (method == 2) {
			Gson gson2 = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
			return gson2.toJson(obj);
		}
		return "";
	}
}
