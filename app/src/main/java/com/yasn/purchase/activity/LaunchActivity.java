package com.yasn.purchase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ActivityHelper;
import com.yasn.purchase.utils.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.activity.GuideActivity.GUIDEACTIVITYCODE;

/**
 * Created by chen on 2017/2/14.
 */

/**
 * 引导页或者启动页过后的广告页面  点击跳过或者自动3秒后跳到首页 不缓存图片
 */
public class LaunchActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private ImageView adIv;
    private Handler handler;
    private Runnable runnable;
    private boolean flag;
    private Button skip;
    private Context context = LaunchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        initView();
    }

    private void initView() {
        handler = new Handler();
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
//    @Overridegradlew compileDebugSources --stacktrace -info
//    protected void onRestart() {
//        super.onRestart();
//        if (!isNeedCheck) {
//
//        }
//    }
//    @Override
//    public void haveAllPermisson() {
//        super.haveAllPermisson();
//
//    }

    private void isFirstOpen() {
        isFirstOpen(3000);
    }
    private void isFirstOpen(int skipTime) {
        runnable = new Runnable() {
            @Override
            public void run() {
                flag = true;

                boolean userGuideShow = SharePrefHelper.getInstance(context).getSpBoolean("is_user_guide_show", false);
                if (!userGuideShow) {
                    startActivityForResult(new Intent(LaunchActivity.this, GuideActivity.class), GUIDEACTIVITYCODE);

                } else {
                    startActivity();
                }

            }
        };
        handler.postDelayed(runnable, skipTime);
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                try {
                    JSONObject jsonObject = new JSONObject(returnData);
                    String imagrurl = jsonObject.optString("image");
//                    imagrurl = "http://img02.sogoucdn.com/app/a/100520024/dc36a9a8bf56661ab778bcdafc6b7d09";
                    Glide.with(LaunchActivity.this.getApplication()).load(imagrurl)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .crossFade()
                            .into(adIv);
                    checkForUpdates();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onCancelResult() {
        isFirstOpen();
    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {
        isFirstOpen();
    }

    @Override
    public void onParseErrorResult(int errorCode) {
        isFirstOpen();
    }

    @Override
    public void onFinishResult() {
        isFirstOpen();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_btn:
                if (flag) {
                    return;
                }
                handler.removeCallbacks(runnable);
                startActivity();
                break;
        }
    }
    private void startActivity(){
        ActivityHelper.init(LaunchActivity.this).startActivity(MainActivityNew.class);
//        ActivityHelper.init(LaunchActivity.this).startActivity(MainActivity.class);
        LaunchActivity.this.finish();
    }
    private void checkForUpdates() {
        try {
            UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
            if (upgradeInfo != null) {
                try {
                    int versionCode = getVersionCode();
                    if (upgradeInfo.versionCode > versionCode) {
                        return;
                    }else if (upgradeInfo.versionCode==0){
                        isFirstOpen();
                        return;
                    }
                    StringBuilder info = new StringBuilder();
                    info.append("id: ").append(upgradeInfo.id).append("\n");
                    info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
                    info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            //是否首次打开app
            isFirstOpen();
        } catch (Exception e) {
            e.printStackTrace();
            //是否首次打开app
            isFirstOpen();
        }
    }

    private int getVersionCode() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        int code = packInfo.versionCode;
        return code;
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // 根据上面发送过去的请求吗来区别
            switch (requestCode) {
                case 10000:
                    SharePrefHelper.getInstance(context).putSpBoolean("is_user_guide_show", true);
                    isFirstOpen(2000);
                    break;
                default:
                    break;
            }
        }
    }
}
