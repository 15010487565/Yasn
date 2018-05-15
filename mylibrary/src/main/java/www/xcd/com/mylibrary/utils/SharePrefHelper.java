package www.xcd.com.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Android on 2017/5/19.
 */

public class SharePrefHelper {
    private static final String FILE_NAME = "yasnsp";
    public static Context context;
    /**
     * 单例模式
     */
    private static SharePrefHelper instance = null;
    private static SharedPreferences.Editor editor;
    private static SharedPreferences preferences;

    public static SharePrefHelper getInstance(Context context){
        if (instance == null)
            syncInit(context);

        return instance;
    }
    private static synchronized void syncInit(Context context) {
        if (instance == null)
            instance = new SharePrefHelper(context);
    }
    private SharePrefHelper(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.context = context;
    }

    /**
     * 保     *
     */
    public static void putSpString(String key, String value) {
        //用putString的方法保存数据
        editor.putString(key, value);
        //提交当前数据
        editor.commit();
    }

    /**
     *  获取各项配置参数
     */
    public static String getSpString(String key) {
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String value =preferences.getString(key, "");
        return value;
    }
    /**
     * 保     *
     */
    public static void putSpInt(String key, int value) {
        //用putString的方法保存数据
        editor.putInt(key, value);
        //提交当前数据
        editor.commit();
    }

    /**
     *  获取各项配置参数
     */
    public static int getSpInt(String key) {
        // 使用getString方法获得value，注意第2个参数是value的默认值
        int value =preferences.getInt(key, -1);
        return value;
    }
    public boolean putSpBoolean(String key, boolean value) {
        return editor.putBoolean(key, value).commit();
    }

    public boolean getSpBoolean(String key, boolean def) {
        return preferences.getBoolean(key, def);
    }

}
