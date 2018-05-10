package com.yasn.purchase.help;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gs on 2018/4/9.
 */

public class ShopCarUtils {
    public static String getShopCar(String path,String token){
        try {
        String body = null;
            Log.e("TAG_PATH","path="+path);
            Log.e("TAG_PATH","token="+token);
            //创建okHttpClient对象
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(path);
            if (token != null && !"".equals(token)) {
                builder.addHeader("Authorization", "Bearer" +
                        "" + token);
            }
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                body = response.body().string();
            }
            Log.e("TAG_进货单","code="+response.code());
            Log.e("TAG_进货单错误信息","response="+response);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG_进货单错误信息","IOException="+ e.toString());
            return "";
        }
    }
}
