package com.yasn.purchase.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

/** 
*
* 短信的帮助类
* @author Medivh
* @date 2014-7-24 下午2:23:22 
*  
*/
public class SmsContent extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String smsContent = "";
	private EditText verifyText = null;

	public SmsContent(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;// 光标
		// 读取收件箱中指定号码的短信
		try {
			cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] { "_id", "address", "body", "read" }, "read=?", new String[] { "0" }, "date desc");
			if (cursor != null) {// 如果短信为未读模式
				cursor.moveToFirst();
				if (cursor.moveToFirst()) {
					String smsbody = cursor.getString(cursor.getColumnIndex("body"));
					if (smsbody.contains("车")) {
						String regEx = "[^0-9]";
						Pattern p = Pattern.compile(regEx);
						Matcher m = p.matcher(smsbody.toString());
						smsContent = m.replaceAll("").trim().toString();
						verifyText.setText(smsContent);
						verifyText.setSelection(verifyText.getText().toString().length());
					}
				}
			}
		} catch (Exception e) {
			//如果读取短息过程中出现异常，就不自动填写验证码
			e.printStackTrace();
		}
	}
}