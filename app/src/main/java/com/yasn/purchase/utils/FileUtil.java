package com.yasn.purchase.utils;

import android.os.Environment;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangshuang
 * Time: 16/8/27
 * Email:xiaoshuang990@sina.com
 */
public class FileUtil {
    // 删除单个文件
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists())
            return false;
        return file.delete();
    }

    // 删除单个文件
    public static boolean deleteFile(File file) {
        if (!file.exists())
            return false;
        return file.delete();
    }

    // 删除文件夹
    public static void deleteFiles(File file) {
        if (file.exists()) {// 判断文件是否存在
            if (file.isFile()) {// 判断是否是文件
                file.delete();// 删除文件
            } else if (file.isDirectory()) {// 否则如果它是一个目录
                File[] files = file.listFiles();// 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
                    deleteFiles(files[i]);// 把每个文件用这个方法进行迭代
                }
                file.delete();// 删除文件夹
            }
        }
    }

    // 根据文件修改时间删除文件
    public static void deleteByTime(String path) {
        File file = new File(path);
        if (!file.exists())
            return;
        File[] listFiles = file.listFiles();
        File temp;
        // 根据最后修改时间排序
        for (int i = 0; i < listFiles.length; i++) {
            for (int j = i; j < listFiles.length; j++) {
                if (listFiles[i].lastModified() > listFiles[j].lastModified()) {
                    temp = listFiles[i];
                    listFiles[i] = listFiles[j];
                    listFiles[j] = temp;
                }
            }
        }
        // 删除文件
        for (int i = 0; i < listFiles.length / 2; i++) {
            listFiles[i].delete();
        }
    }

    // 获取SD卡根目录
    public static String getFilePath() {
        if (hasSDCard())
            return Environment.getExternalStorageDirectory().getAbsolutePath();

        List<String> devMountList = getDevMountList();
        for (String devMount : devMountList) {
            LogUtil.i("--->" + devMount);
            File file = new File(devMount);
            if (file.isDirectory() && file.canWrite()) {
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     */
    private static List<String> getDevMountList() {
        String[] toSearch = readFile("/etc/vold.fstab").split(" ");
        List<String> out = new ArrayList<>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    private static String readFile(String filePath) {
        String fileContent = "";
        File file = new File(filePath);
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(is);
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                fileContent += line + " ";
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

    // 判断是否有SD卡
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            return false;
        }
        return true;
    }

}
