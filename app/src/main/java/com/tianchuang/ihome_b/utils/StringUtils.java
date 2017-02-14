//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tianchuang.ihome_b.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public StringUtils() {
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}


	public static String[] chargeIntToString(int[] arr) {
		if (arr == null) {
			return null;
		}
		String[] result = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = String.valueOf(arr[i]);
		}
		return result;
	}

	public static String[] chargeLongToString(Long[] arr) {
		if (arr == null) {
			return null;
		}
		String[] result = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = String.valueOf(arr[i]);
		}
		return result;
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
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
