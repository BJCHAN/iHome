package com.tianchuang.ihome_b.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Abyss on 2017/2/14.
 * description:App相关工具类
 */

public class AppUtils {
	/**
	 * 获取App版本号
	 *
	 * @param context 上下文
	 * @return App版本号
	 */
	public static String getAppVersionName(Context context) {
		return getAppVersionName(context, context.getPackageName());
	}

	/**
	 * 获取App版本号
	 *
	 * @param context     上下文
	 * @param packageName 包名
	 * @return App版本号
	 */
	public static String getAppVersionName(Context context, String packageName) {
		if (StringUtils.isSpace(packageName)) return null;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			return pi == null ? null : pi.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
