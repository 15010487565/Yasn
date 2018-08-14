package com.yasn.purchase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewH5Activity;

import www.xcd.com.mylibrary.utils.AppManager;

public class WeChatCaptureActivity extends BaseCaptureActivity {

    private SurfaceView surfaceView;
    private AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_capture);
        AppManager.getInstance().addActivity(this);
        surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        autoScannerView = (AutoScannerView) findViewById(R.id.autoscanner_view);
    }
    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }



    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        Log.e("TAG_扫码", "dealDecode =" + rawResult.getText());
        playBeepSoundAndVibrate(true, false);
//        Toast.makeText(this, rawResult.getText(), Toast.LENGTH_LONG).show();
//        对此次扫描结果不满意可以调用
//        reScan();

        String rawResultText = rawResult.getText();
        getInviteCode(rawResultText);
    }

    private void getInviteCode(String url){
//        Intent intent = new Intent(this,MakerInviteRegisterAActivity.class);
//        if (TextUtils.isEmpty(url)){//邀请码无效
//            intent.putExtra("isValid","0");
//        }else {
//            int i = url.indexOf("&");
//            String substring = url.substring(i + 1);
//            Log.e("TAG_扫一扫","substring="+substring);
//            String[] split = substring.split("=");
//            String key = split[0];
//            Log.e("TAG_扫一扫","key="+key);
//            String value = split[1];
//            Log.e("TAG_扫一扫","value="+value);
//            intent.putExtra("key",key);
//            intent.putExtra("value",value);
//            intent.putExtra("isValid","1");
//        }
//        startActivity(intent);
        Intent intent = new Intent(this, WebViewH5Activity.class);
        intent.putExtra("webViewUrl", url == null ? "" : url);
        startActivity(intent);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        AppManager.getInstance().removeActivity(this);
    }
}
