package com.yasn.purchase.utils;

import android.util.Log;

public class LogUtil {
	private static boolean flag = false;

	public static void i(String str) {
		if (flag) {
			try {
				Log.i("Purchase", str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void i(String tag, String str) {
		if (flag) {
			try {
				Log.i(tag, str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setFlag(boolean flag) {
		LogUtil.flag = flag;
	}

}
