package www.xcd.com.mylibrary.http;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Leo on 2017/2/20.
 * 请求回调接口
 */
public interface HttpInterface {
    /**
     * 成功
     * status 状态
     * msg 提示
     * result 返回数据
     */
    void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps);

    /**
     * 取消
     */
    void onCancelResult();

    /**
     * 错误
     */
    void onErrorResult(int errorCode, IOException errorExcep);

    /**
     * 解析错误
     * @param errorCode
     */
    void onParseErrorResult(int errorCode);

    /**
     * 失败
     */
    void onFinishResult();
}
