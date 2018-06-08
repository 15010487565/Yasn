package com.yasn.purchase.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

    /**
     * 用户的信息
     */
    public static final String userMode = "userMode";


    public static final String COOKIE = "cookie";


    /**
     * 将用户对象序列化在本地
     *
     * @param obj
     * @param filesDir
     * @param saveName
     */
    public static void saveObject(Object obj, File filesDir, String saveName) {
        // File filesDir = getFilesDir();
        try {
            if (!filesDir.exists()){
                filesDir.mkdirs();
            }
            File files = new File(filesDir, saveName);
            if (!files.exists()){
                files.createNewFile();
            }
            FileOutputStream os = new FileOutputStream(files);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            oos.close();
            os.close();
        } catch (Exception e) {
//            SerializableUtil.deleObj(filesDir);
            e.printStackTrace();
        }

    }

    // 获取序列化对象
    public static Object readObject(File filesDir, String saveName) {
        // File filesDir = getFilesDir();
        try {
            filesDir = new File(filesDir, saveName);
            if (filesDir.exists()) {
                FileInputStream is = new FileInputStream(filesDir);
                ObjectInputStream ois = new ObjectInputStream(is);
                return ois.readObject();
            } else {
                return null;
            }
        } catch (Exception e) {
//            SerializableUtil.deleObj(filesDir);
            e.printStackTrace();
        }
        return null;
    }

    public static void deleObj(File filesDir) {
        filesDir.delete();
    }
}
