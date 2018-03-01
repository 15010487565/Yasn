package www.xcd.com.mylibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Android on 2017/6/6.
 */

public class YYStorageUtil {
    public static final int STORAGE_TYPE_APP = 100001;
    public static final String PATH_LOG = "/log/";
    public static final String PATH_TEMP = "/temp/";
    public static final String PATH_IMAGE = "/image/";
    public static final String PATH_AUDIO = "/audio/";
    public static final String PATH_FILE = "/file/";
    public static final String PATH_LOCATION = "/location/";
    public static final String PATH_APP = "/app/";
    public static final String PATH_HTTP = "/http/";

    public YYStorageUtil() {
    }

    @SuppressWarnings("WrongConstant")
    public static int getMemoryClass(Context context) {
        return ((ActivityManager)context.getSystemService("activity")).getMemoryClass();
    }

    public static boolean hasExternalStorage() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    public static long getUsableSpace(File path) {
        return path.getUsableSpace();
    }

    public static File getExternalDir() {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }

    public static File getPhoneMemeryDir() {
        return new File(Environment.getRootDirectory().getPath());
    }

    public static File getExternalCacheDir(Context context) {
        if(YYVersionUtil.hasFroyo() && context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir();
        } else {
            String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
            return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
        }
    }

    public static File getInternalCacheDir(Context context) {
        return new File(context.getCacheDir() != null?context.getCacheDir().getPath():"/Android/data/" + context.getPackageName() + "/cache/");
    }

    public static File getSystemDiskCacheDir(Context context) {
        return new File(getSystemDisCachePath(context));
    }

    public static String getSystemDisCachePath(Context context) {
        if(!hasExternalStorage() && isExternalStorageRemovable()) {
            return getInternalCacheDir(context).getPath();
        } else {
            File extFile = getExternalCacheDir(context);
            return extFile != null?extFile.getPath():getInternalCacheDir(context).getPath();
        }
    }

    public static File getCacheDir(Context context, String path) {
        File file = new File(getSystemDisCachePath(context), path);
        checkDirPath(file);
        return file;
    }

    public static File getCachePath(Context context, int type) {
        switch(type) {
            case 4:
                return getFilePath(context);
            case 8:
                return getImagePath(context);
            case 64:
                return getAudioPath(context);
            case 128:
                return getLocationPath(context);
            case 100001:
                return getAppPath(context);
            default:
                return getTempPath(context);
        }
    }

    public static File getTempPath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/temp/");
        checkDirPath(file);
        return file;
    }

    public static File getLogPath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/log/");
        checkDirPath(file);
        return file;
    }

    public static File getImagePath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/image/");
        checkDirPath(file);
        return file;
    }

    public static File getAudioPath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/audio/");
        checkDirPath(file);
        return file;
    }

    public static File getFilePath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/file/");
        checkDirPath(file);
        return file;
    }

    public static File getLocationPath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/location/");
        checkDirPath(file);
        return file;
    }

    public static File getAppPath(Context context) {
        File file = new File(getSystemDisCachePath(context), "/app/");
        checkDirPath(file);
        return file;
    }

    public static boolean checkDirPath(String dirpath) {
        return checkDirPath(new File(dirpath));
    }

    public static boolean checkDirPath(File directory) {
        return !directory.exists()?directory.mkdirs():true;
    }
}
