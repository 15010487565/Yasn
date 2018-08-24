package com.yasn.purchase.help;

import android.content.Context;
import android.content.Intent;

import com.yasn.purchase.activity.MainActivity;
import com.yasn.purchase.model.EventBusMsg;

import org.greenrobot.eventbus.EventBus;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by gs on 2018/3/21.
 */

public class LoginOut {
    public static void startLoginOut(Context activity){

        activity.startActivity(new Intent(activity, MainActivity.class));
        loginOut(activity);

    }
    public static void loginOut(Context activity){
        loginOutClean(activity);
        EventBus.getDefault().post(new EventBusMsg("loginout"));
    }
    public static void loginOutClean(Context activity){
        SharePrefHelper.getInstance(activity).putSpString("token", "");
        SharePrefHelper.getInstance(activity).putSpString("resetToken", "");
        SharePrefHelper.getInstance(activity).putSpString("resetTokenTime", "");
        SharePrefHelper.getInstance(activity).putSpString("regionId", "");
        SharePrefHelper.getInstance(activity).putSpString("priceDisplayMsg", "");
        SharePrefHelper.getInstance(activity).putSpString("priceDisplayMsg", "");
        SharePrefHelper.getInstance(activity).putSpString("memberid","");
        SharePrefHelper.getInstance(activity).putSpString("regionName","");
        SharePrefHelper.getInstance(activity).putSpInt("lv_id",-1);
        SharePrefHelper.getInstance(activity).putSpString("JavaShopUser","");
        SharePrefHelper.getInstance(activity).putSpString("provinceId", "");
        SharePrefHelper.getInstance(activity).putSpString("employeeAuth","");
    }
}
