package com.yasn.purchase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

/**
 * 日期工具类
 */
public class DateUtil {
	private static String format = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 得到当前时间
	 * 
	 * @param dateFormat
	 *            时间格式
	 * @return 转换后的时间格式
	 */
	public static String getCurrentTime(String dateFormat) {
		if (!TextUtils.isEmpty(dateFormat)) {
			format = dateFormat;
		}
		Date currentTime = new Date();
		return dateToString(currentTime, format);
	}
	/**
	 * 得到当前日期
	 *
	 * @return 转换后的时间格式
	 */
	public static String getCurrentDate() {
		 String format = "yyyy-MM-dd";
		Date currentTime = new Date();
		return dateToString(currentTime, format);
	}

	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
	}

	/**
	 * 将字符串型日期转换成日期
	 * 
	 * @param dateStr
	 *            字符串型日期
	 * @param dateFormat
	 *            日期格式
	 * @return
	 */
	public static Date stringToDate(String dateStr, String dateFormat) {
		if (!TextUtils.isEmpty(dateFormat)) {
			format = dateFormat;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期转字符串
	 * 
	 * @param str
	 * @param dateFormat
	 * @return
	 */
	public static String dateToString(String str, String dateFormat) {
		Date date = new Date(Long.parseLong(str) * 1000);
		return dateToString(date, dateFormat);
	}

	public static String dateToString(Date date, String dateFormat) {
		if (!TextUtils.isEmpty(dateFormat)) {
			format = dateFormat;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		return formatter.format(date);
	}

	/**
	 * 两个时间点的间隔时长（分钟）
	 * 
	 * @param before
	 *            开始时间
	 * @param after
	 *            结束时间
	 * @return 两个时间点的间隔时长（分钟）
	 */
	public static long compareMin(Date before, Date after) {
		if (before == null || after == null) {
			return 0l;
		}
		long dif = 0;
		if (after.getTime() >= before.getTime()) {
			dif = after.getTime() - before.getTime();
		} else if (after.getTime() < before.getTime()) {
			dif = after.getTime() + 86400000 - before.getTime();
		}
		dif = Math.abs(dif);
		return dif / 60000;
	}

	/**
	 * 获取指定时间间隔分钟后的时间
	 * 
	 * @param date
	 *            指定的时间
	 * @param min
	 *            间隔分钟数
	 * @return 间隔分钟数后的时间
	 */
	public static Date addMinutes(Date date, int min) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, min);
		return calendar.getTime();
	}

	/**
	 * 根据时间返回指定术语，自娱自乐，可自行调整
	 * 
	 * @param hourday
	 *            小时
	 * @return
	 */
	public static String showTimeView(int hourday) {
		if (hourday >= 22 && hourday <= 24) {
			return "晚上";
		} else if (hourday >= 0 && hourday <= 6) {
			return "凌晨";
		} else if (hourday > 6 && hourday <= 12) {
			return "上午";
		} else if (hourday > 12 && hourday < 22) {
			return "下午";
		}
		return null;
	}

}
