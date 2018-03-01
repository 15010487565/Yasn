package www.xcd.com.mylibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import www.xcd.com.mylibrary.R;


public class DialogUtil {

//	public static Dialog getMenuDialog(Activity context, View view) {
//		final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle);
//		dialog.setContentView(view);
//		Window window = dialog.getWindow();
//		WindowManager.LayoutParams lp = window.getAttributes();
//
//		int screenW = DensityUtils.getScreenWidth(context);
//
//		lp.width = screenW;
//		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
//		window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
//		return dialog;
//	}

	public static Dialog getProgressDialog(Activity context, int string) {

		final Dialog dialog = new Dialog(context, R.style.FullScreenDialog);
		dialog.setCancelable(false);//返回键是否能关闭
		dialog.setContentView(R.layout.custom_progress_dialog);
		return dialog;
	}
	public static Dialog getProgressDialog(Context context) {

		final Dialog dialog = new Dialog(context, R.style.FullScreenDialog);
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.custom_progress_dialog);
		return dialog;
	}
}
