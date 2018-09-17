package www.xcd.com.mylibrary.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.utils.CustomDialog;


/**
 * 权限弹出框
 *
 * @author wudl
 * @date Created at 2016/4/6.
 */
public class PermissionsActivity extends FragmentActivity {
    /** 权限授权 */
    public static final int PERMISSIONS_GRANTED = 0;
    /** 权限拒绝 */
    public static final int PERMISSIONS_DENIED = 1;
    /** 系统权限管理页面的参数 */
    private static final int PERMISSION_REQUEST_CODE = 1;
    /** 权限参数 */
    private static final String EXTRA_PERMISSIONS = "com.yasn.purchase.Activity.permissions.extra_permission";
    /** 方案 */
    private static final String PACKAGE_URL_SCHEME = "package:";
    /** 权限检测器 */
    private PermissionsChecker mChecker;
    /** 是否需要系统权限检测 */
    private boolean isRequireCheck;

    /**
     * 启动当前权限页面的公开接口
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void startActivityForResult(Activity activity, int requestCode, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, requestCode, null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("PermissionsActivity需要使用静态startActivityForResult方法启动!");
        }
        setContentView(R.layout.activity_permissions);

        mChecker = new PermissionsChecker(this);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (mChecker.lacksPermissions(permissions)) {
                // 请求权限
                requestPermissions(permissions);
            } else {
                // 全部权限都已获取
                allPermissionsGranted();
            }
        } else {
            isRequireCheck = true;
        }
    }

    /**
     * 返回传递的权限参数
     *
     * @return
     */
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    /**
     * 请求权限兼容低版本
     *
     * @param permissions
     */
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 全部权限均已获取
     */
    private void allPermissionsGranted() {
        setResult(PERMISSIONS_GRANTED);
        finish();
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode 请求码
     * @param permissions 权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e("TAG_权限", "requestCode=" + requestCode );
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
            finish();
        }
    }

    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle(R.string.permission_title);
        builder.setMessage(R.string.permission_message);
        builder.setPositiveButton(R.string.permission_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });
        builder.setNegativeButton(R.string.permisssion_setting, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.create().show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
