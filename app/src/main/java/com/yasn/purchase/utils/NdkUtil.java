package com.yasn.purchase.utils;

/**
 * Created by wangsl on 16-3-3.
 */
public class NdkUtil {
    static {
        System.loadLibrary("yasn");
    }
    public native String getPartner();

    public native String getSeller();

    public native String getRsa_private();

    public native String getRsa_public();

    public native String getAlipay_notify();

    public native String getApp_id();

    public native String getMch_id();

    public native String getApi_key();

    public native String getWeixin_notify();
}
