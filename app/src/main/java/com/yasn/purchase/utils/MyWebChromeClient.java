package com.yasn.purchase.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.io.File;
/**
 * Created by fanjl on 2016-12-15.
 */
public class MyWebChromeClient extends WebChromeClient
{
	public static final int FILECHOOSER_RESULTCODE = 100;

	Activity
		at;

	ValueCallback<Uri> mUploadMessage4;
	ValueCallback<Uri[]> mUploadMessage5;

	public MyWebChromeClient(Activity at)
	{
		this.at = at;
	}

	// For Android 4.1
	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
	{
		if(mUploadMessage4 != null)
		{
			mUploadMessage4.onReceiveValue(null);
		}
		mUploadMessage4 = uploadMsg;

		if(at != null)
		{
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			String type = TextUtils.isEmpty(acceptType) ? "*/*" : acceptType;
			intent.setType(type);
			at.startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
		}
	}
	//Android 5.0+
	@Override
	@SuppressLint("NewApi")
	public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams)
	{
		if(mUploadMessage5 != null)
		{
			mUploadMessage5.onReceiveValue(null);
		}
		mUploadMessage5 = filePathCallback;

		if(at != null)
		{
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.addCategory(Intent.CATEGORY_OPENABLE);
			if(fileChooserParams != null && fileChooserParams.getAcceptTypes() != null && fileChooserParams.getAcceptTypes().length > 0)
			{
				intent.setType(fileChooserParams.getAcceptTypes()[0]);
			}else
			{
				intent.setType("*/*");
			}
			at.startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILECHOOSER_RESULTCODE);
		}

		return true;
	}
	public void onResult(Uri inUri)
	{
		if (inUri == null)
		{
			resetUploadMsg();
		}
		else
		{
			String path =  FileUtils.getPath(at, inUri);
			if (TextUtils.isEmpty(path))
			{
				resetUploadMsg();
			}
			else
			{
				Uri uri = Uri.fromFile(new File(path));
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
				{
					if(mUploadMessage5 != null)
					{
						mUploadMessage5.onReceiveValue(new Uri[]{uri});
						mUploadMessage5 = null;
					}
				}
				else
				{
					if(mUploadMessage4 != null)
					{
						mUploadMessage4.onReceiveValue(uri);
						mUploadMessage4 = null;
					}
				}
			}
		}
	}
	private void resetUploadMsg()
	{
		if(mUploadMessage4 != null)
		{
			mUploadMessage4.onReceiveValue(null);
			mUploadMessage4 = null;
		}
		else if(mUploadMessage5 != null)
		{
			mUploadMessage5.onReceiveValue(null);
			mUploadMessage5 = null;
		}
	}
}
