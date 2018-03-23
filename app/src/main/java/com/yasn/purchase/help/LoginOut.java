package com.yasn.purchase.help;

import android.content.Context;

import com.yasn.purchase.model.EventBusMsg;

import org.greenrobot.eventbus.EventBus;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by gs on 2018/3/21.
 */

public class LoginOut {
    public static void loginOut(Context activity){
        SharePrefHelper.getInstance(activity).putSpString("token", "");
        SharePrefHelper.getInstance(activity).putSpString("resetToken", "");
        SharePrefHelper.getInstance(activity).putSpString("resetTokenTime", "");
        SharePrefHelper.getInstance(activity).putSpString("regionId", "");
        SharePrefHelper.getInstance(activity).putSpString("priceDisplayMsg", "");
        SharePrefHelper.getInstance(activity).putSpString("priceDisplayMsg", "");
        SharePrefHelper.getInstance(activity).putSpString("memberid","");
        EventBus.getDefault().post(new EventBusMsg("loginout"));
    }
}
