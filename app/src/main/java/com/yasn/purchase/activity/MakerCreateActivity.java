package com.yasn.purchase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.common.Config;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;

public class MakerCreateActivity extends SimpleTopbarActivity {

    @Override
    protected Object getTopbarTitle() {
        return "雅森创客";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_create);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int nowWidth = dm.widthPixels; //当前分辨率 宽度
//        int nowHeigth = dm.heightPixels; //当前分辨率高度

        ImageView ivMakerCreateOne = (ImageView) findViewById(R.id.iv_MakerCreateOne);
        Bitmap srcMakerCreateOne = BitmapFactory.decodeResource(getResources(),R.mipmap.iv_makercreateone);
        int picWidthOne= srcMakerCreateOne.getWidth();
        float scaleOne = (float)nowWidth/picWidthOne;
        int picHeightOne = srcMakerCreateOne.getHeight();
        android.view.ViewGroup.LayoutParams params = (LinearLayout.LayoutParams) ivMakerCreateOne.getLayoutParams();
        params.height = (int) (picHeightOne*scaleOne);
        ivMakerCreateOne.setLayoutParams(params);

        ImageView ivMakerCreateTwo = (ImageView) findViewById(R.id.iv_MakerCreateTwo);
        Bitmap srcMakerCreateTwo = BitmapFactory.decodeResource(getResources(),R.mipmap.iv_makercreatetwo);
        int picWidthTwo= srcMakerCreateTwo.getWidth();
        float scaleTwo = (float)nowWidth/picWidthTwo;
        int picHeightTwo = srcMakerCreateTwo.getHeight();
        android.view.ViewGroup.LayoutParams paramsTwo = (LinearLayout.LayoutParams) ivMakerCreateTwo.getLayoutParams();
        paramsTwo.height = (int) (picHeightTwo*scaleTwo);
        ivMakerCreateTwo.setLayoutParams(paramsTwo);
        //加入创客
        TextView tvAddMaker = (TextView) findViewById(R.id.tv_AddMaker);
        tvAddMaker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_AddMaker:
                if (Config.isWebViewPay){
                    Intent intent = new Intent(this, WebViewH5Activity.class);
                    intent.putExtra("webViewUrl", Config.MAKERPAYMENT);
                    startActivity(intent);
                }else {
                    startActivity(new Intent(this,MakerPayActivity.class));
                }
                break;
        }
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
