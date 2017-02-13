package com.tianchuang.ihome_b.utils;

import android.content.Context;
import android.widget.Toast;

import com.tianchuang.ihome_b.R;


/**
 * Created by Abyss on 2017/2/13.
 * description:单例toast
 */
public class ToastUtil {
	/**
	 * 之前显示的内容
	 */
	private static String oldMsg;
	/**
	 * 第一次时间
	 */
	private static long oneTime = 0;
	/**
	 * 第二次时间
	 */
	private static long twoTime = 0;
	/**
	 * Toast对象
	 */
	private static Toast toast = null;

	public static void showToast(Context context, String message) {
		showToast(context, message, false);
	}

	public static void showToast(Context context, int resId) {
		showToast(context, resId, false);
	}

	public static void showErrorToast(Context context, String message) {
		showToast(context, message, true);
	}

	public static void showErrorToast(Context context, int message) {
		showToast(context, message, true);
	}

	public static void showToast(Context context, int resId, boolean errorToast) {
		showToast(context, context.getResources().getString(resId), errorToast);
	}

	public static void showToast(Context context, String message, boolean errorToast) {
		//避免吐司重复显示
		if (toast == null) {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (message.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = message;
				toast.setText(message);
				toast.show();
			}
		}
		oneTime = twoTime;

	}

	public static void showNetworkError(Context context) {
		if (null == context)
			return;
		showToast(context, context.getResources().getString(R.string.network_error_message), false);
	}

	public static void showConnectError(Context context) {
		if (null == context)
			return;
		showToast(context, context.getResources().getString(R.string.connect_server_error), false);
	}

}