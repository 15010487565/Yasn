package com.yasn.purchase.help;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.sobot.chat.SobotApi;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.yasn.purchase.R;
import com.yasn.purchase.model.SobotModel;
import com.yasn.purchase.utils.ToastUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by gs on 2018/2/28.
 */

public class SobotUtil {
    /**
     *
     * @param context
     * @param sobotModel 商品信息
     */
    public static void startSobot(Context context,SobotModel sobotModel) {
        Information info = new Information();
        if (sobotModel != null){
            setGoodsInfo(sobotModel, info);
        }
        setSobotClickListener(context, info);
    }

    private static void setSobotClickListener(Context context, Information info) {
        String uname = SharePrefHelper.getInstance(context).getSpString("uname");
        if (uname == null||"".equals(uname)){
            uname ="游客";
        }
        //用户编号
        //注意：uid为用户唯一标识，不能传入一样的值
        info.setUid((uname==null||"".equals(uname))?getUniqueId(context):uname);
        //用户昵称，选填
        info.setUname(uname);
//        //用户姓名，选填
//        info.setRealname("");
//        //用户电话，选填
//        info.setTel("");
//        //用户邮箱，选填
//        info.setEmail("");
//        //自定义头像，选填
//        info.setFace("");
//        //用户QQ，选填
//        info.setQq("");
//        //用户备注，选填
//        info.setRemark("");
        //对话页标题，选填
        info.setVisitTitle("客服");
        //对话页路径，选填
//        info.setVisitUrl("");
        //1仅机器人 2仅人工 3机器人优先 4人工优先
        info.setInitModeType(2);
        //是否使用语音功能 true使用 false不使用   默认为true
        info.setUseVoice(false);
        //返回时是否弹出满意度评价
        info.setShowSatisfaction(false);
//        Map<String, String> customInfo = new HashMap<String, String>();
//        customInfo.put("appkey", appkey);
//        //自定义用户资料
//        info.setCustomInfo(customInfo);
        //设置标题栏的背景颜色，如果背景颜色和背景图片都设置，则以背景图片为准，选填
        info.setColor("#3C3C3C");
        //启动参数设置结束
        String appkey = context.getResources().getString(R.string.zhichi_key);
        if (!TextUtils.isEmpty(appkey)) {
            info.setAppkey(appkey);

            //设置标题显示模式
            SobotApi.setChatTitleDisplayMode(context.getApplicationContext(),
//           int enumType = 0;//0 默认,  1  自定义,  2  公司name;
                    SobotChatTitleDisplayMode.values()[0], "会话");//如果头部显示自定义文案，文案内容
            SobotApi.hideHistoryMsg(context.getApplicationContext(), 0);//显示多少分钟内的历史记录
            SobotApi.setEvaluationCompletedExit(context.getApplicationContext(), false);//评价完是否结束会话

            SobotApi.startSobotChat(context, info);
        } else {
            ToastUtil.showToast("AppKey 不能为空 ！！！");
        }
    }

    private static void setGoodsInfo(SobotModel sobotModel, Information info) {
        //咨询内容
        ConsultingContent consultingContent = new ConsultingContent();
        //咨询内容标题，必填
        consultingContent.setSobotGoodsTitle(sobotModel.getSobotGoodsTitle());
        //咨询内容图片，选填 但必须是图片地址
        String sobotGoodsImgUrl = sobotModel.getSobotGoodsImgUrl();
        if (sobotGoodsImgUrl !=null&&!"".equals(sobotGoodsImgUrl)){
            consultingContent.setSobotGoodsImgUrl(sobotGoodsImgUrl);
        }
        //咨询来源页，必填
        consultingContent.setSobotGoodsFromUrl("www.sobot.com");
        //描述，选填
        String sobotGoodsDescribe = sobotModel.getSobotGoodsDescribe();
        if (sobotGoodsDescribe !=null&&!"".equals(sobotGoodsDescribe)){
            consultingContent.setSobotGoodsDescribe(sobotGoodsDescribe);
        }
        //金额
        String sobotGoodsLable = sobotModel.getSobotGoodsLable();
        if (Double.valueOf(sobotGoodsLable) > 0 ){
            consultingContent.setSobotGoodsLable("￥"+sobotGoodsLable);
        }
        //可以设置为null
        info.setConsultingContent(consultingContent);
    }

    public static String getUniqueId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = digest[i] & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }
}
