package com.yasn.purchase.utils;


import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.yasn.purchase.R;


public class ActivityHelper {
    private static ActivityHelper helper;
    private static Intent intent;
    private static Activity activity;

    public ActivityHelper() {
        super();
    }

    public static ActivityHelper init(Activity activity) {
        return init(activity, Intent.FLAG_ACTIVITY_SINGLE_TOP);
    }

    public static ActivityHelper init(Activity activity, int flags) {
        ActivityHelper.activity = activity;
        if (helper == null) {
            helper = new ActivityHelper();
        }
        intent = new Intent();
        intent.addFlags(flags);
        return helper;
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        this.startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        intent.setClass(activity, cls);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.activity_in_anim,
                R.anim.activity_out_anim);
        activity=null;
    }

    public void startActivity(Class<?> cls) {
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_anim,
                R.anim.activity_out_anim);
        activity=null;
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        intent.setClass(activity, cls);
        intent.putExtra("bundle", bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.activity_in_anim,
                R.anim.activity_out_anim);
        activity=null;
    }


    public void startActivity(Class<?> cls, View v) {
        // 让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeScaleUpAnimation(v,
                        // animating from
                        (int) v.getWidth() / 2, (int) v.getHeight() / 2, // 拉伸开始的坐标
                        0, 0);// 拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        intent.setClass(activity, cls);
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
        activity.overridePendingTransition(R.anim.activity_in_anim,
                R.anim.activity_out_anim);
        activity=null;
    }

    public void startActivity(Class<?> cls, View v, Bundle bundle) {
        // 让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeScaleUpAnimation(v,
                        // animating from
                        (int) v.getWidth() / 2, (int) v.getHeight() / 2, // 拉伸开始的坐标
                        0, 0);// 拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        intent.setClass(activity, cls);
        intent.putExtra("bundle", bundle);
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
        activity.overridePendingTransition(R.anim.activity_in_anim,
                R.anim.activity_out_anim);
        activity=null;
    }

}
