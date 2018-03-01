package www.xcd.com.mylibrary.base.application;

import android.app.Application;
import android.content.Context;


/**
 * application基类
 *
 * @author litfb
 * @version 1.0
 * @date 2014年4月10日
 */
public class XCDApplication extends Application {

    private static XCDApplication instance;

    public static XCDApplication getInstance() {
        if (instance == null) {
            instance = new XCDApplication();
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_RUNNING_CRITICAL://内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
            case TRIM_MEMORY_RUNNING_LOW://内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
            case TRIM_MEMORY_RUNNING_MODERATE://内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
                System.gc();
                break;
            default:
                break;
        }
    }
}
