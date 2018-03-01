package www.xcd.com.mylibrary.base.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.utils.AppManager;
import www.xcd.com.mylibrary.utils.CustomDialog;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.SystemBarTintManager;


/**
 * 薛传东
 * Created by xcd15 on 2017/5/3.
 */

public abstract class BaseActivity  extends FragmentActivity implements View.OnFocusChangeListener{

    private CustomDialog dialog;
    private boolean isActive = true;

    /**
     * 本地文件读写权限
     */
    public static final String[] WRITEREADPERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private SystemBarTintManager tintManager;
    public String token;
    public String resetToken;
    public String resetTokenTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(R.color.top_bar_background);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
        }
        token = SharePrefHelper.getInstance(this).getSpString("token");
        resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
        resetTokenTime = SharePrefHelper.getInstance(this).getSpString("resetTokenTime");
        super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
        initDialog();
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    /**
     * 获取当前页面需要的权限
     *
     * @return
     */
    protected String[] getPermissions() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isActive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    public boolean dialogIsActivity() {
        return isActive;
    }

    @Override
    public void finish() {
        super.finish();
        AppManager.getInstance().removeActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView();
    }



    protected void afterSetContentView() {

    }

    /**
     * 根据resId获得一个Bitmap
     *
     * @param resId
     * @return
     */
    public Bitmap getDrawableBitmap(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        } else {
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyDeatchView();
    }
    protected void onDestroyDeatchView() {
    }
    /**
     * 初始化踢下线弹出框
     */
    public void initDialog() {
        // 冲突踢下线
        CustomDialog.Builder builder = new CustomDialog.Builder(BaseActivity.this);
        builder.setTitle("帐号下线");
        builder.setMessage("您的帐号已在其他移动端登录，已断开连接。");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in = new Intent("android.intent.action.LOGIN");
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                AppManager.getInstance().finishAllActivity();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
    }
    /**
     * 展示踢人dialog
     */
    protected void showKickDialog(){
        Log.e("TAG_踢下线","activityIsActivity="+dialogIsActivity()+";isActive="+isActive);
        if (dialog != null && !dialog.isShowing()&&dialogIsActivity()) {
            dialog.show();
        }
    }
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText textView = (EditText) view;
        if (textView==null){
            return;
        }
        if (textView.getHint()==null){
            return;
        }
        String hint = textView.getHint().toString();

        if (hasFocus) {
            textView.setTag(hint);
            textView.setHint("");
        } else {
            hint = textView.getTag().toString();
            textView.setHint(hint);
        }
        if (!hasFocus) {
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }else {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    showKickDialog();
                    break;
            }
        }
    };
}
