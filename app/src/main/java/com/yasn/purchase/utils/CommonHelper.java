package com.yasn.purchase.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CommonHelper {
    private static CommonHelper helper;
    private ExecutorService executorService;
    private final static Object syncLock = new Object();

    private CommonHelper() {

    }

    public static CommonHelper with() {
        if (helper == null) {
            synchronized (syncLock) {
                if (helper == null)
                    helper = new CommonHelper();
            }

        }
        return helper;
    }

    public ExecutorService getExecutorService() {
        if (executorService == null) {
            int maxExecutor = Runtime.getRuntime().availableProcessors() * 2 + 1;
            executorService = Executors
                    .newFixedThreadPool(maxExecutor > 3 ? maxExecutor : 3);
        }
        return executorService;
    }

    public String getPrice(Activity activity, String price) {
       // if (TextUtils.isEmpty(UserHelper.init(activity).getUserInfoBean()
       //         .getShop_id())) {
       //    return "登录显示价格";
       //}
        return "￥ " + price;
    }

//    public String changeUrl(String url, String size) {
//        String path = "";
//        if (TextUtils.isEmpty(url)) {
//            return Config.IMAGE;
//        }
//        if (!TextUtils.isEmpty(size)) {
//            path = url.replace("/org/", "/" + size + "/");
//        }
//        return Config.IMAGE + path;
//    }

    /**
     * 數字格式化数据
     *
     * @param object 需要格式化的数据(默认格式0.00)
     * @return
     */
    public String formatDigital(Object object) {
        if (object == null || object.toString().length() < 1) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(Double.parseDouble(object.toString()));
    }

    /**
     * 判断是否登陆,startActivity
     *
     * @param activity
     * @return
//     */
//    public boolean isLogin(Activity activity) {
//        if (TextUtils.isEmpty(UserHelper.init(activity).getUserInfoBean()
//                .getShop_id())) {
//            Bundle bundle = new Bundle();
//            bundle.putInt("type", 0);
//            ActivityHelper.init(activity).startActivityForResult(
//                    LoginActivity.class, bundle, Messages.LOGINRESULT);
//            return true;
//        }
//        return false;
//    }



    /**
     * 邮箱格式校验
     *
     * @param string
     * @return
     */
    public boolean checkEmail(String string) {
        String math = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        return string.matches(math);
    }

    /**
     * 手机号格式校验
     *
     * @param trim
     * @return
     */
    public boolean checkPhone(String trim) {
        if (TextUtils.isEmpty(trim)) {
            return false;
        }
        String math = "1[3|4|5|6|7|8|9][0-9]{9}";
        return trim.trim().matches(math);
    }

    /**
     * 检查网络是否连通
     *
     * @param context
     * @return
     */
    public boolean checkNetWork(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info == null) {
            return false;
        }
        for (int i = 0; i < info.length; i++) {
            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查Wifi是否连通
     *
     * @param context
     * @return
     */

    public boolean checkWiFi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI
                && activeNetInfo.isConnected()) {
            return true;
        }
        return false;
    }

//    public String getLocalIpAddress() {
//        String ipaddress = "127.0.0.1";
//        try {
//            Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces();
//            // 遍历所用的网络接口
//            while (en.hasMoreElements()) {
//                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//                Enumeration<InetAddress> inet = nif.getInetAddresses();
//                // 遍历每一个接口绑定的所有ip
//                while (inet.hasMoreElements()) {
//                    InetAddress ip = inet.nextElement();
//                    if (!ip.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(ip
//                            .getHostAddress())) {
//                        ipaddress = ip.getHostAddress();
//                        return ipaddress;
//                    }
//                }
//
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        return ipaddress;
//    }
}
