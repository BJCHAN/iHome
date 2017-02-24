package com.tianchuang.ihome_b.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Abyss on 2017/2/23.
 * description:
 */

public class DateUtils {
	public static final String TYPE_01 = "yyyy/MM/dd HH:mm";

	public static String formatDate(long time, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return new SimpleDateFormat(format).format(cal.getTime());
	}
}
