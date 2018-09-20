package com.yasn.purchase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.help.ShopCarUtils;
import com.yasn.purchase.utils.ActivityHelper;
import com.yasn.purchase.utils.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import www.xcd.com.mylibrary.help.HelpUtils;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.activity.GuideActivity.GUIDEACTIVITYCODE;

/**
 * Created by chen on 2017/2/14.
 */

/**
 * 引导页或者启动页过后的广告页面  点击跳过或者自动3秒后跳到首页 不缓存图片
 */
public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView adIv;
    private Runnable runnable;
    private boolean flag = false;
    private Button skip;
    private Context context = LaunchActivity.this;
    //引导页是否已经显示
    private boolean isShowState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
    }

    private void initView() {
        //广告容器
        adIv = (ImageView) findViewById(R.id.ad_iv);
        //跳过按钮
        skip = (Button) findViewById(R.id.skip_btn);
        skip.setOnClickListener(this);
        int navigationBarHeight = DensityUtil.getBottomStatusHeight(this);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) adIv.getLayoutParams();
        layoutParams.height = DensityUtil.dip2px(this, 486) - navigationBarHeight;
        adIv.setLayoutParams(layoutParams);
        //获取广告图
        getAsyncHttp(Config.LAUNCHIMAGE, 100);
//        int i = 1/0;
    }
    private Thread thread;
    public void getAsyncHttp(final String url, int requestCode) {
        thread = new Thread(){
            @Override
            public void run() {
                super.run();
                String shopCar = ShopCarUtils.getShopCar(url,null);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = shopCar;
                handler.sendMessage(msg);
            }
        };
        thread.start();
    }

    private Handler  handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String returnData = (String) msg.obj;
                    HelpUtils.loge("TAG_欢迎页", "returnData=" + returnData);
                    try {
                        if (returnData != null && !"".equals(returnData)) {
                            JSONObject jsonObject = new JSONObject(returnData);
                            String imagrurl = jsonObject.optString("image");
    //                    imagrurl = "http://img02.sogoucdn.com/app/a/100520024/dc36a9a8bf56661ab778bcdafc6b7d09";
                            Glide.with(LaunchActivity.this.getApplication()).load(imagrurl)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .crossFade()
                                    .into(adIv);
                            isFirstOpen();
                        }else {
                            isFirstOpen();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        isFirstOpen();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        isShowState = true;
        flag = false;
        Log.e("TAG_isShowState","onRestart=");
        isFirstOpen();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowState = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowState = false;
        thread.interrupt();
        ShopCarUtils.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        handler.removeCallbacksAndMessages(null);
    }

    private void isFirstOpen() {
        isFirstOpen(3000);
    }
    private void isFirstOpen(int skipTime) {
        runnable = new Runnable() {
            @Override
            public void run() {
                flag = true;
                boolean userGuideShow = SharePrefHelper.getInstance(context).getSpBoolean("is_user_guide_show", false);
                Log.e("TAG_isShowState","isShowState="+isShowState);
                if (isShowState){
                    if (!userGuideShow) {
                        startActivityForResult(new Intent(LaunchActivity.this, GuideActivity.class), GUIDEACTIVITYCODE);
                    } else {
                        startActivity();
                    }
                }else {
                    startActivity();
                }
            }
        };
        handler.postDelayed(runnable, skipTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_btn:
                Log.e("TAG_flag","flag="+flag);
                if (flag) {
                    return;
                }
                handler.removeCallbacks(runnable);
                startActivity();
                break;
        }
    }
    private void startActivity(){
        Log.e("TAG_欢迎页","startActivity=");
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiIx" +
//                "MjM0NTYiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiLCJ0cnVzdCJdLCJleHAiOjE1" +
//                "MzM5NzY5MjgsImF1dGhvcml0aWVzIjpbIllBU05fU0hPUF9DTElFTlQiLCJZQVNOX1" +
//                "NIT1BfVFJVU1RFRF9DTElFTlQiXSwianRpIjoiMWU4OTIzNTQtOTAzNi00Y2UxLWFhY2ItN2Q1YTMxNzAwMDY1IiwiY2xpZ" +
//                "W50X2lkIjoieWFzbi1zaG9wIiwibWVtYmVySWQiOjI4fQ.-Js19_xMHrvkNKEatVbEz9EPibNBG-XHCthLR7QIoCM";
//        SharePrefHelper.getInstance(this).putSpString("token",token);
        ActivityHelper.init(LaunchActivity.this).startActivity(MainActivity.class);
//        Intent intent = new Intent(LaunchActivity.this, WebViewH5Activity.class);
//        intent.putExtra("webViewUrl", Config.HOMEVIEW);
//        startActivity(intent);
        LaunchActivity.this.finish();
    }

}
