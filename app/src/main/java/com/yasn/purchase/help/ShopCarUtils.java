package com.yasn.purchase.help;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gs on 2018/4/9.
 */

public class ShopCarUtils {
    static Call call;
    public static String getShopCar(String path,String token){
        try {
        String body = null;
            Log.e("TAG_PATH","path="+path);
            Log.e("TAG_PATH","token="+token);
            Request.Builder builder = new Request.Builder();
            builder.url(path);
            if (token != null && !"".equals(token)) {
                builder.addHeader("Authorization", "Bearer" +
                        "" + token);
            }
            Request request = builder.build();
            //创建okHttpClient对象
            OkHttpClient client = new OkHttpClient();
            call = client.newCall(request);
            Response response = call.execute();
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
    public static void cancel(){
        if (call!=null)
        call.cancel();
    }
}
