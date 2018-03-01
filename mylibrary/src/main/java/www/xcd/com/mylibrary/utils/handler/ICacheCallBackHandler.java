package www.xcd.com.mylibrary.utils.handler;

import android.graphics.Bitmap;

/**
 * Created by Android on 2017/6/6.
 */

public interface ICacheCallBackHandler {
    void onStart(Object var1, Object var2);

    void onSuccess(Object var1, Object var2, byte[] var3);

    void onFailure(Object var1, Object var2);

    void setLoadingImage(Bitmap var1);

    void setLoadingImage(int var1);
}
