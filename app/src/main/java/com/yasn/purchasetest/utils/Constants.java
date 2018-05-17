package com.yasn.purchasetest.utils;

/**
 * 常量配置类
 */
public class Constants {

    public static final String SUCCESS_CODE = "000000";

    public static final String NET_WORK_INVAILABLE = "netInvailable";

    /**
     * errorMessage
     */
    public static final String KEY_ERROR_MESSAGE = "errorMessage";

    /**
     * 取消加载请稍后
     */
    public static final byte HANDLER_DISMISS_PROGRESSDIALOG = 1;

    /**
     * 显示加载请稍后...
     */
    public static final byte HANDLER_LOGO_HTTP_FAILED = HANDLER_DISMISS_PROGRESSDIALOG + 1;

    /**
     * 显示err Message
     */
    public static final byte HANDLER_SHOW_ERRORMESSAGE = HANDLER_LOGO_HTTP_FAILED + 1;

    /**
     * 域名
     */
    public static final String HOST_NAME = "http://bd.shop.10086.cn";

    public static final String POCKET_OPERATE_PATH_URL = HOST_NAME + "/i/gray/aopm/v2";
    public static final String POCKET_OPERATE_PATH_H5_URL = HOST_NAME + "/i/pocketoper/report_app";
//    public static final String POCKET_OPERATE_PATH_H5_URL = "http://172.21.222.162:8890/report_app";

    /**
     * 获取短信验证码
     */
    public static final int GET_SMS_AUTHENTICATION_CODE_INFO = 4;
    /**
     * 获取短信验证码
     */
    public static final String GET_SMS_AUTHENTICATION_CODE_INFO_URL = POCKET_OPERATE_PATH_URL + "/login/downSMS?";

    /**
     * 登录
     */
    public final static int GET_LOGIN_INFO = 5;
    /**
     * 登录
     */
    public static final String GET_LOGIN_INFO_URL = POCKET_OPERATE_PATH_URL + "/login/checkSMS";

    /**
     * 自动更新
     */
    public static final int GET_UPDATE_VERSION_URL_INFO = 6;
    /**
     * 自动更新出错
     */
    public static final int GET_APP_VERSION_ERROR = 7;
    /**
     * 自动更新
     */
    public static final String UPDATE_VERSION_URL = POCKET_OPERATE_PATH_URL + "/report/versionno";

    /**
     * 应用分享
     */
    public static final int GET_SHARE_TO = 8;

    /**
     * 首页
     */
    public static final String GET_H5_INDEX_URL = POCKET_OPERATE_PATH_H5_URL + "/index.html";
    public static final String GET_H5_INDEX_OPEN_FUNC_ID = "110105";
    /**
     * 工作
     */
    public static final String GET_H5_MY_WORK_URL = POCKET_OPERATE_PATH_H5_URL + "/my_work.html";
    public static final String GET_H5_MY_WORK_OPEN_FUNC_ID = "110106";
    /**
     * 数据
     */
    public static final String GET_H5_DATA_ANALYSIS_URL = POCKET_OPERATE_PATH_H5_URL + "/data_analysis.html";
    public static final String GET_H5_DATA_ANALYSIS_OPEN_FUNC_ID = "110107";
    /**
     * 通讯录
     */
    public static final String GET_H5_CONTACTS_URL = POCKET_OPERATE_PATH_H5_URL + "/activities_analysis.html";
    public static final String GET_H5_CONTACTS_OPEN_FUNC_ID = "";

    /**
     * 下发短信的号码
     */
    public static final String SEND_SMS_NUM = "106589995200";
    /**
     * RSA签名--客户端私钥
     */
    public static final String RSA_TERMINAL_PRIVATE_KEY = "";
    /**
     * RSA签名--服务器公钥
     */
    public static final String RSA_SERVER_PUBLIC_KEY = "";

    /**
     * 消息标识--首页
     */
    public static final int MSG_WEB_INDEX = 000;
    /**
     * 消息标识--工作
     */
    public static final int MSG_WEB_WORK = 111;
    /**
     * 消息标识--数据
     */
    public static final int MSG_WEB_DATA = 222;
    /**
     * 消息标识--通讯录
     */
    public static final int MSG_WEB_CONTACTS = 333;
    /**
     * 消息标识--返回上一级H5页面
     */
    public static final int MSG_BACK_TO = 4;
    /**
     * 消息标识--H5入口页面
     */
    public static final int MSG_H5_FIRST_PAGE = 5;
    /**
     * 触屏页面标题Key
     */
    public static final String KEY_WEB_PAGE_TITLE = "webPageTitle";
    /**
     * JS返回的flag
     */
    public static final String KEY_WEB_FLAG = "flag";

}
