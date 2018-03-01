package www.xcd.com.mylibrary.utils.image;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.yonyou.sns.im.core.YYIMChatManager;
import com.yonyou.sns.im.core.YYIMSessionManager;
import com.yonyou.sns.im.util.CommonConstants;

import org.jump.util.StringUtils;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * IM 帮助类
 *
 * @author wudl
 * @version V1.0
 * @date 2015年5月19日
 */
public class IMHelper {

    /**
     * 是默认租户用户
     *
     * @param accountVal
     * @return
     */
    public static boolean isSimpleAccount(String accountVal) {
        if (TextUtils.isEmpty(accountVal)) {
            return false;
        }
        return accountVal.matches("[\\w]+");
    }

    /**
     * 是指定租户用户
     *
     * @param accountVal
     * @return
     */
    public static boolean isMultiTenantAccount(String accountVal) {
        if (TextUtils.isEmpty(accountVal)) {
            return false;
        }

        return accountVal.matches("[\\w]+@[\\w]+\\.[\\w]+");
    }

    /**
     * 将非中文字符串转成小写
     *
     * @param str
     * @return
     */
    public static String toLowerCaseNotChinese(String str) {
        // 判断是否是中文
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        if (!p.matcher(str).matches()) {
            // 不是中文字的都变成小写
            str = str.toLowerCase(Locale.getDefault());
        }
        return str;
    }

    /**
     * 判断url是否是绝对路径
     *
     * @param url
     * @return
     */
    public static boolean isAbsoluteUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.contains("http://")) {
            return true;
        } else if (url.contains("https://")) {
            return true;
        } else if (url.contains("ftp://")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得文件完整path
     *
     * @param path
     * @return
     */
    public static String getFullFilePath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (isAbsoluteUrl(path)) {
            return path;
        }
        String etpId = YYIMChatManager.getInstance().getYmSettings().getEtpKey();
        String appId = YYIMChatManager.getInstance().getYmSettings().getAppKey();
        String fileServer = YYIMChatManager.getInstance().getYmSettings().getDownloadFileServer() + "/" + etpId + "/" + appId + "/download";
        return fileServer + "?attachId=" + path;
    }

    /***
     * 判断是否是不同设备间的通讯
     *
     * @param id
     * @return
     */
    public static boolean isOwn(String id) {
        if (TextUtils.isEmpty(id)) {
            return false;
        }
        String currentUserJid = YYIMSessionManager.getInstance().getUserId();
        return StringUtils.parseBareName(id).equals(currentUserJid);
    }

    /**
     * 判断是否是系统消息
     *
     * @param jid
     * @return
     */
    public static boolean isSystemChat(String jid) {
        return CommonConstants.ID_SYSTEM_MESSAGE.equalsIgnoreCase(jid)
                || CommonConstants.ID_MULTI_SYSTEM_MESSAGE.equalsIgnoreCase(StringUtils.parseBareName(jid));
    }

    /**
     * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 实现文本复制功能 add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", content.trim());
        cmb.setPrimaryClip(mClipData);
    }

    /**
     * 实现粘贴功能 add by wangqianzhou
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return (String) cmb.getPrimaryClip().getItemAt(cmb.getPrimaryClip().getItemCount() - 1).getText();
    }

}
