package www.xcd.com.mylibrary.config;

/**
 * @auther Leo--薛传东
 * Create at 2017/5/11 17:09
 * 网络配置类
 */
public class HttpConfig {

    /**
     * 请求失败
     */
    public static final int REQUESTERROR = 0x008;

    /**
     * 解析错误
     */
    public static final int PARSEERROR = REQUESTERROR + 1;

    /**
     * 网络错误
     */
    public static final int NETERROR = REQUESTERROR + 2;

    /**
     * 请求成功
     */
    public static final int SUCCESSCODE = 10001;
}
