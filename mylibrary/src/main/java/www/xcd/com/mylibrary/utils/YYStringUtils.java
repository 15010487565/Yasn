package www.xcd.com.mylibrary.utils;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import org.jump.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Android on 2017/6/6.
 */

public class YYStringUtils {
    public YYStringUtils() {
    }

    public static SpannableStringBuilder initStyle(String str, String key, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        int start = str.indexOf(key);
        int end = start + key.length();
        if(start >= 0 && !StringUtils.isEmpty(key)) {
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
            style.setSpan(colorSpan, start, end, 33);
        }

        return style;
    }

    public static boolean notNumberOrLetter(String s) {
        return s.matches("[^a-zA-Z0-9]");
    }

    public static String toString(Object obj) {
        return obj == null?"":(obj instanceof String?(String)obj:(obj.getClass().isArray()?toString((Object[])((Object[])obj)):(obj instanceof Map ?toString((Map)obj):(obj instanceof Collection ?toString((Collection)obj):(obj instanceof Throwable?toString((Throwable)obj):(obj instanceof Thread?toString((Thread)obj):obj.toString()))))));
    }

    public static String toString(Object[] objs) {
        if(objs != null && objs.length > 0) {
            StringBuilder sb = new StringBuilder();
            Object[] arr$ = objs;
            int len$ = objs.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object obj = arr$[i$];
                sb.append(toString(obj)).append(",");
            }

            return sb.substring(0, sb.length() - 2);
        } else {
            return "";
        }
    }

    public static String toString(Map<?, ?> map) {
        if(map == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            Iterator i$ = map.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry entry = (Map.Entry)i$.next();
                sb.append(toString(entry.getKey())).append(" = ").append(toString(entry.getValue())).append(";\r\n");
            }

            return sb.toString();
        }
    }

    public static String toString(Collection<?> list) {
        if(list != null && list.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                Object obj = i$.next();
                sb.append(toString(obj)).append(";");
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String toString(Throwable e) {
        if(e == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int count = 0; e != null && count < 10; e = e.getCause()) {
                ++count;
                sb.append(e.toString());
                StackTraceElement[] trace = e.getStackTrace();

                for(int i = 0; i < trace.length; ++i) {
                    sb.append("\r\n\tat " + trace[i]);
                }
            }

            return sb.toString();
        }
    }

    public static String toString(Thread t) {
        if(t == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            if(t != null) {
                sb.append(t.toString());
                StackTraceElement[] trace = t.getStackTrace();

                for(int i = 0; i < trace.length; ++i) {
                    sb.append("\r\n\tat " + trace[i]);
                }
            }

            return sb.toString();
        }
    }
}
