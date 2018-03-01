package com.yasn.purchase.activity.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.help.OkHttpHelper;
import www.xcd.com.mylibrary.http.HttpInterface;

public class YasnBaseActivity extends AppCompatActivity implements HttpInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void getAsyncHttp(String url, int requestCode) {
        OkHttpHelper.getInstance().getAsyncHttp(requestCode, url, null, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        Log.e("TAG_", "请求错误");
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);
                        Log.e("TAG_", "解析错误");
                        break;
                    //网络错误
                    case HttpConfig.NETERROR:
                        Log.e("TAG_", "网络错误");
                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        Log.e("TAG_", "请求成功");
                        break;
                }
            }
        });
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}
