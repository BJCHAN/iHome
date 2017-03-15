package com.tianchuang.ihome_b.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Abyss on 2017/2/23.
 * description:
 */

public class DateUtils {
	public static final String TYPE_01 = "yyyy/MM/dd HH:mm";
	public static final String TYPE_02 = "MM月dd日";
	public static final String TYPE_03 = "yyyy年MM月dd日";
	public static final String TYPE_04 = "yyyy/MM/dd";
	public static final String TYPE_05 = "yyyy年MM月dd HH:mm";

	public static String formatDate(int time, String format) {
		long time1 = time * 1000l;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time1);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
}
