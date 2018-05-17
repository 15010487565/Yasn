package com.yasn.purchasetest.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import java.io.File;

public class VersionUtil {

	/**
	 * 判断是否需要更新
	 * 
	 * @param context
	 * @param versionName
	 */
	public static boolean checkVersion(Context context, String versionName) {
		String current_Version = getVersionName(context);
		if (current_Version == null) {
			return false;
		}
		return !versionName.equals(current_Version);
	}

	/*
	 * 获取当前程序的版本号
	 */
	public static String getVersionName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo != null ? packInfo.versionName : null;
	}
	public static int getVersionCode(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packInfo != null ? packInfo.versionCode : 0;
	}

	// 安装apk
	public static void installApk(Context context, File file) {
		try {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.show(context, "安装失败");
		}
	}
}