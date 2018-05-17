package com.yasn.purchasetest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yasn.purchasetest.R;

public class ImgTextDetTXTActivity extends AppCompatActivity {

    private TextView textcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgtextdettxt);
        textcontext = (TextView) findViewById(R.id.textcontext);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        parseJson(url);
    }
    private void parseJson(String pathurl) {
        // 用xutils从服务器获取json数据
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, pathurl, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException error, String msg) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // TODO Auto-generated method stub
                String result = responseInfo.result;
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = result;
                handler.sendMessage(message);

            }
        });
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String text = (String) msg.obj;
                    textcontext.setText(text==null?"暂无文本内容！":text);
                    break;
            }
        }
    };
}
