package www.xcd.com.mylibrary.utils;

/**
 * Created by Android on 2017/5/12.
 */

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import www.xcd.com.mylibrary.base.application.XCDApplication;


/**
 * toast显示类，可以在子线程直接调用
 */
public class ToastUtil {
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
        toast = Toast.makeText(XCDApplication.getInstance(), text, duration);
        toast.show();
    }

}

