package www.xcd.com.mylibrary.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 6.0 危险权限检查
 * @author wudl
 * @date Created at 2016/4/6.
 */
public class PermissionsChecker {
    private final Context mContext;

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        if (permissions!=null){
            for (String permission : permissions) {
                if (lacksPermission(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {

        return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED;
//        return false;
    }
}
