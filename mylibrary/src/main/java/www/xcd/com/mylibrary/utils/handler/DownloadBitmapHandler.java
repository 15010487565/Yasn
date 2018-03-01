package www.xcd.com.mylibrary.utils.handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yonyou.sns.im.config.YYIMPreferenceConfig;
import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMConfigConstants;
import com.yonyou.sns.im.core.YYIMSessionManager;
import com.yonyou.sns.im.http.Response;
import com.yonyou.sns.im.http.YYHttpClient;
import com.yonyou.sns.im.http.utils.builder.BaseBuilder;
import com.yonyou.sns.im.log.YYIMLogger;
import com.yonyou.sns.im.util.message.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public class DownloadBitmapHandler extends ResizerBitmapHandler {

    private static final String TAG = "DownloadBitmapHandler";

    /**
     * 初始化一个目标提供图像的宽度和高度的来处理图像
     *
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    public DownloadBitmapHandler(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        init(context);
    }

    /**
     * Initialize providing a single target image size (used for both width and
     * height);
     *
     * @param context
     * @param imageSize
     */
    public DownloadBitmapHandler(Context context, int imageSize) {
        super(context, imageSize);
        init(context);
    }

    private void init(Context context) {
        initDiskCacheInternal();
    }

    private Bitmap processBitmap(String urlString) {
        try {
            if (urlString != null && (urlString.startsWith(YYIMChatManager.getInstance().getYmSettings().getDownloadFileServer()) ||
                    urlString.startsWith(YYIMChatManager.getInstance().getYmSettings().getFileServer()))) {
                // 追加明文的参数
                Map<String, String> urlMap = new HashMap<String, String>();
                urlMap.put("downloader", YYIMSessionManager.getInstance().getFullAccount());
                urlMap.put("token", YYIMPreferenceConfig.getInstance().getString(YYIMConfigConstants.TOKEN, ""));
                urlString = UrlUtils.plusExtendUrlParam(urlString, urlMap);
            }
            BaseBuilder builder = YYHttpClient.get().url(urlString);
            Response response = builder.build().execute();
//            Log.e("TAG_头像","下载完成");
            return BitmapFactory.decodeStream(response.body().byteStream());
        } catch (Exception e) {
//            Log.e("TAG_头像","下载出错");
            YYIMLogger.d(TAG, "Error in downloadBitmap", e);
        }
        return null;
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        if (data == null) {
            return null;
        } else {
            return processBitmap(String.valueOf(data));
        }
    }

}
