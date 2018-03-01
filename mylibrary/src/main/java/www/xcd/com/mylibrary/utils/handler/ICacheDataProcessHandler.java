package www.xcd.com.mylibrary.utils.handler;

/**
 * Created by Android on 2017/6/6.
 */

public interface ICacheDataProcessHandler {
    byte[] processData(Object var1);

    byte[] processError(Object var1);

    void initDiskCacheInternal();

    void clearCacheInternal();

    void flushCacheInternal();

    void closeCacheInternal();

    void setDefaultIcon(Object var1);
}