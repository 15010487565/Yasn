package com.yasn.purchase.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.yasn.purchase.application.YasnApplication;


/**
 * Toast统一管理类
 *
 * @author way
 */
public class ToastUtil {

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param object
     */
    public static void show(Context context, Object object) {
        if (object instanceof String) {
            show(context, object.toString());
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, int message) {
        if (context != null && message > 0) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    private static Runnable r = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    public static void showToast(Context mContext, String text, int duration) {

        mHandler.removeCallbacks(r);
        if (toast != null)
            toast.setText(text);
        else
            toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mHandler.postDelayed(r, duration);

        toast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

    private static Toast toast;

    public static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(final String text, final int duration) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            show(text, duration);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, duration);
                }
            });
        }
    }

    private static void show(String text, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(YasnApplication.getInstance(), text, duration);
        toast.show();
    }
}
