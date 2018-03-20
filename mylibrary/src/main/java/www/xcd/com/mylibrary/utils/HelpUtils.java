package www.xcd.com.mylibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import www.xcd.com.mylibrary.config.HttpConfig;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * Created by xcd15 on 2017/5/9.
 */

public class HelpUtils {
    //判断手机号是否为手机号
    public static boolean isMobileNO(String mobiles) {

        Pattern p = Pattern.compile("^((17[0-9])|(14[5,7])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
    //保留两位小数
    public static String doubledigit(String reString) {
        Double d = Double.valueOf(reString);
        DecimalFormat f1 = new DecimalFormat("######0.00");
        reString = f1.format(d);
        return reString;
    }
//    public static boolean cleanCatchDisk(Context context) {
//        String cachePath = YYStorageUtil.getSystemDisCachePath(context);
//        boolean deleteFolderFile = deleteFolderFile(cachePath, true);
//        boolean deleteFolderFile1 = deleteFolderFile(XCDApplication.getApp().getCacheDir() + "/" + Config.GLIDE_CARCH_DIR, true);
//        return deleteFolderFile&&deleteFolderFile1;
//    }
    // 按目录删除文件夹文件方法
    private static boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1.getAbsolutePath(), true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {
                    file.delete();
                } else {
                    if (file.listFiles().length == 0) {
                        file.delete();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static String getCacheSize() {
//        try {
//            return getFormatSize(getFolderSize(
//                    new File(XCDApplication.getApp().getCacheDir()
//                            + "/" + Config.GLIDE_CARCH_DIR)));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "获取失败";
//        }
//    }
    // 格式化单位
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
    // 获取指定文件夹内所有文件大小的和
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     *
     * @param context
     * @param mobile  手机号
     * @param isDialInterface 是否跳转本地拨号界面
     */
    public final static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    public static void call(Context context,String mobile,boolean isDialInterface) {
        Log.e("TAG_拨打电话","isDialInterface="+isDialInterface);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
                //上面已经写好的拨号方法
                callDirectly(context,mobile,isDialInterface);
            }
        } else {
            //上面已经写好的拨号方法
            callDirectly(context,mobile,isDialInterface);
        }

    }
    public static void callDirectly(Context context,String mobile,boolean isDialInterface){
        Intent intent = new Intent();
        if (isDialInterface){
            intent.setAction("android.intent.action.DIAL");
        }else {
            intent.setAction("android.intent.action.CALL");
        }
        intent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }
    public static String getDeviceId(Context context){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        if (szImei ==null || "".equals(szImei)){
            String serial = null;

            String m_szDevIDShort = "35" +
                    Build.BOARD.length()%10+ Build.BRAND.length()%10 +

                    Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +

                    Build.DISPLAY.length()%10 + Build.HOST.length()%10 +

                    Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +

                    Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +

                    Build.TAGS.length()%10 + Build.TYPE.length()%10 +

                    Build.USER.length()%10 ; //13 位

            try {
                serial = android.os.Build.class.getField("SERIAL").get(null).toString();
                //API>=9 使用serial号
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                //serial需要一个初始化
                serial = "serial"; // 随便一个初始化
            }
            //使用硬件信息拼凑出来的15位号码
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }else {
            return szImei;
        }
    }
    public static void setHintSize(String hintstring,EditText editText){
        if (hintstring==null||"".equals(hintstring)){
            return;
        }
        SpannableString ss = new SpannableString(hintstring);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(16, true);
        // 附加属性到文本
        ss.setSpan(absoluteSizeSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    private float sp2px(Context context,float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

    /**
     * 半角转换为全角
     *
     * @param str
     * @return
     */
    public static String ToDBC(String str) {

        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
    /**
     * 时间戳转换成字符窜
     * yyyy-MM-dd HH:mm:ss 年于日时分秒
     */
    public static String getDateToString(long time) {
       if ( String.valueOf(time).length()<13){
           time = time*1000;
       }
        Date d = new Date(time);
        SimpleDateFormat sf  = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }
    //获取网络时间
    public static boolean getNetworkTime(String time) {
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection conn = url.openConnection();
            conn.connect();
            long nowTime = conn.getDate();
            Log.e("TAG_js","resetTokenTime="+time+"nowTime="+nowTime);
            if (time == null||"".equals(time)){
                return false;
            }else {
                if (Long.valueOf(time)*1000>nowTime){
                    return true;
                }else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 禁止EditText输入特殊字符
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText){

        InputFilter filter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if(matcher.find())return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }
    public static String formatLongToTimeStr(Long l) {
        int days = 0;
        String daysCov = "00";
        int hour = 0;//时
        String hourCov = "00";
        int minute = 0;//分
        String minuteCov = "00";
        int second = 0;//秒
        String secondCov = "00";
        second = l.intValue();
        if (second >= 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
            if (second < 10) {
                if (second<0){
                    secondCov = "00";
                }else {
                    secondCov = "0" + String.valueOf(second);
                }
            } else {
                secondCov = String.valueOf(second);
            }
        } else {
            if (second < 10) {
                if (second<0){
                    secondCov = "00";
                }else {
                    secondCov = "0" + String.valueOf(second);
                }
            } else {
                secondCov = String.valueOf(second);
            }
        }

        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
            if (minute < 10) {
                minuteCov = "0" + String.valueOf(minute);
            } else {
                minuteCov = String.valueOf(minute);
            }
        } else {
            if (minute < 10) {
                minuteCov = "0" + String.valueOf(minute);
            } else {
                minuteCov = String.valueOf(minute);
            }
        }
        if (hour >= 24) {
            days = hour / 24;
            hour = hour % 24;
            if (hour < 10) {
                hourCov = "0" + String.valueOf(hour);
            } else {
                hourCov = String.valueOf(hour);
            }
        } else {
            if (hour < 10) {
                hourCov = "0" + String.valueOf(hour);
            } else {
                hourCov = String.valueOf(hour);
            }
        }
        if (days < 10) {
            daysCov = "0" + String.valueOf(days);
        } else {
            daysCov = String.valueOf(days);
        }
        String strtime = daysCov + "天" + hourCov + "时" + minuteCov + "分" + secondCov + "秒";
        return strtime;

    }
    public static String formatLongToTime(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        second = l.intValue() / 1000;

        if (second >= 60) {
            minute = second / 60;
            second = second % 60;
        }
        if (minute >= 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        return (getTwoLength(hour) + ":" + getTwoLength(minute)  + ":"  + getTwoLength(second));
    }

    private static String getTwoLength(final int data) {
        if(data < 10) {
            return "0" + data;
        } else {
            return "" + data;
        }
    }
    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context,String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取屏幕宽高
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
    public static void getGoodsNum(final String path, final Handler handler) {
        Runnable runnablePost = new Runnable() {
            @Override
            public void run() {
                try {
                    //创建okHttpClient对象
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(path)
                            .build();
                    Response response = client.newCall(request).execute();
                    String body = null;
                    if (response.isSuccessful()) {
                        body = response.body().string();
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("returnData", body);
                    message.setData(bundle);
                    message.what = HttpConfig.SUCCESSCODE;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnablePost);
        thread.start();
    }
    public static void loge(String tag,String result){
        if (result.length() > 3000) {
            for (int i = 0; i < result.length(); i += 3000) {
                if (i + 3000 < result.length())
                    Log.e(tag, "result第一段log===" + result.substring(i, i + 3000));
                else {
                    Log.e(tag, "result第二段log===" + result.substring(i, result.length()));
                }
            }
        } else {
            Log.e(tag, "result=" + result);
        }
    }
}
