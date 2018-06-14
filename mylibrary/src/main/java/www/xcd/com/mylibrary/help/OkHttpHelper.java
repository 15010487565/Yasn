package www.xcd.com.mylibrary.help;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.BuildConfig;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import www.xcd.com.mylibrary.config.HttpConfig;

/**
 * @auther Leo--薛传东
 * Create at 2017/5/11 16:56
 * okHttp网络请求协助类
 */
public class OkHttpHelper {

    private static OkHttpHelper instance = null;

//    private static HttpInterface okHttpFaceHelper = null;

    private static OkHttpClient client = getOkhttpClient();

    public static OkHttpHelper getInstance() {
        if (instance != null) {
            instance = null;
        }
        if (instance == null) {
            synchronized (OkHttpHelper.class) {
                if (instance == null) {
                    instance = new OkHttpHelper();
                }
//                okHttpFaceHelper = okHttpFace;
            }
        }
        return instance;
    }
    /**
     * 获取okhttp对象设置联网请求超时信息
     *
     * @return
     */
    public static OkHttpClient getOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.sslSocketFactory(createSSLSocketFactory());
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient client = builder.readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        /**添加header这几步一定要分开写 不然header会无效 别问我为什么
                         *我看了build源码 看返回了一个新的对象 猜想是要一个新的对象来接收
                         * 我就只定义了一个新的对象来接受新的Request
                         * 后面应该就可以，但是我没确定是否成功 ，然后我就全部都拆开了吧buider对象
                         * request的新的对象都分开之后 就能看到成功了。。。。巨大的bug 真是让人头疼
                         */
                        Request request = chain.request();
                        Request.Builder requestBuilder = request.newBuilder();
                        requestBuilder.addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("x-versionname", BuildConfig.VERSION_NAME)
                                .addHeader("x-versioncode", BuildConfig.VERSION_CODE + "");
                        Request newRequest = requestBuilder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .connectTimeout(5, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)//设置超时
                .build();
        return client;
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
    /**
     * 异步GET请求
     *
     * @param url        请求网址
     * @param paramsMaps 请求body
     */
    public void getAsyncHttp(final int requestCode, final String url, final Map<String, Object> paramsMaps, final Handler mHandler) {
        Runnable runnableGet = new Runnable() {
            @Override
            public void run() {
                //处理参数
                StringBuilder tempParams;
                String requestUrl = "";
                String token = "";
                String android_client = null;
                int pos = 0;
                if (paramsMaps == null) {
                    //补全请求地址
                    requestUrl = url;
                } else {
                    tempParams = new StringBuilder();
                    for (String key : paramsMaps.keySet()) {
                        if ("User-Agent".equals(key)){
                            android_client = (String) paramsMaps.get(key);
                        }else if ("access_token".equals(key)) {
                            token = (String) paramsMaps.get(key);
                        } else {
                            if (pos > 0) {
                                tempParams.append("&");
                            }
                            try {
                                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(((String) paramsMaps.get(key)), "utf-8")));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            pos++;
                        }

                    }
                    //补全请求地址
//                    requestUrl = url+"?"+tempParams.toString();
                    requestUrl = url + tempParams.toString();
                }
                try {
                    Log.e("TAG_url", "Authorization=" + requestUrl);
                    if (!"".equals(token)) {
                        Log.e("TAG_urltoken", "Bearer=" + token);
                    }
                    if (!"".equals(android_client)) {
                        Log.e("TAG_android_client", "android_client=" + android_client);
                    }
                    Request.Builder builder = new Request.Builder();
                    builder.url(requestUrl);
                    if (token != null && !"".equals(token)) {
                        builder.addHeader("Authorization", "Bearer" +
                                "" + token);
                    } 
                    if (android_client != null && !"".equals(android_client)) {
                        builder.addHeader("User-Agent", android_client);
                    }
                    Request request = builder.build();
                    Call callRequest = client.newCall(request);

                    final Response response = callRequest.execute();
                    getJsonData(response, requestCode, paramsMaps, mHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
                }
            }
        };
        Thread thread = new Thread(runnableGet);
        thread.start();
    }

//    public static final MediaType JSON
//            = MediaType.parse("application/json; charset=utf-8");

    /**
     * post请求
     *
     * @param url        请求路径
     * @param paramsMaps 请求参数
     */
    public void postAsyncHttp(final int requestCode, final String url, final Map<String, String> paramsMaps, final Handler mHandler) {
        Runnable runnablePost = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                if (paramsMaps == null || paramsMaps.size() == 0) {
//                    builder.post(null);
                } else {
                    okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
                    for (String key : paramsMaps.keySet()) {
                        String value = "";
                        if (paramsMaps.get(key) != null) {
                            value = paramsMaps.get(key).toString();
                        }
                        formEncodingBuilder.add(key, value);
                        Log.e("TAG_", "KEY=" + key + ";value=" + value);
                    }
                    builder.post(formEncodingBuilder.build());
                }
                Request request = builder.build();
                Call postCall = client.newCall(request);

                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException error) {
                        Message message = new Message();
                        message.what = HttpConfig.REQUESTERROR;
                        message.obj = error;
                        mHandler.sendMessage(message);
                        Log.e("TAG_url", "onFailure=" + error.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getJsonData(response, requestCode, paramsMaps, mHandler);
                    }
                });
            }
        };
        Thread thread = new Thread(runnablePost);
        thread.start();
    }

    //参数类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    /**
     * post请求 上传图片
     *
     * @param url        请求路径
     * @param paramsMaps 请求参数
     */
    public void postAsyncPicHttp(final int requestCode, final String url, final Map<String, Object> paramsMaps, final Handler mHandler) {
        Runnable runnablePost = new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                if (paramsMaps == null || paramsMaps.size() == 0) {
                    builder.post(null);
                } else {
                    MultipartBody.Builder multiBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    for (String key : paramsMaps.keySet()) {
                        String value = paramsMaps.get(key).toString();
                        if (key.equals("headimg")) {
                            multiBuilder.addFormDataPart(key, "head.png", RequestBody.create(MEDIA_TYPE_PNG, new File(value)));
                        } else {
                            multiBuilder.addFormDataPart(key, value);
                        }
                        Log.e("TAG_", "KEY=" + key + ";value=" + value);
                    }
                    builder.post(multiBuilder.build());
                }
                Request request = builder.build();
                Call postCall = client.newCall(request);
                postCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException error) {
                        Log.e("TAG_", "error=" + error);
                        Message message = new Message();
                        message.what = HttpConfig.REQUESTERROR;
                        message.obj = error;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        getJsonData(response, requestCode, paramsMaps, mHandler);
                    }
                });
            }
        };
        Thread thread = new Thread(runnablePost);
        thread.start();
    }

    public void getJsonData(Response response, int requestCode, Map paramsMaps, Handler mHandler) {
        try {
            int returnCode = response.code();
            Log.e("TAG_Code", "returnCode=" + returnCode);
            String result = null;
            if (null != response.cacheResponse()) {
                result = response.cacheResponse().toString();
            } else {
                result = response.body().string();
            }
            HelpUtils.loge("TAG_result",result);
            int i = result.indexOf("{");
            Log.e("TAG_Code", "i=" + i);
            if (i == 0){
                JSONObject jsonObject = new JSONObject(result);
                String returnMsg = jsonObject.optString("message");
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("returnCode", returnCode);

                bundle.putInt("requestCode", requestCode);
                bundle.putString("returnMsg", returnMsg);
                bundle.putString("returnData", result);
                message.setData(bundle);
                message.what = HttpConfig.SUCCESSCODE;
                message.obj = paramsMaps;
                mHandler.sendMessage(message);
            }else {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("returnCode", returnCode);
                bundle.putInt("requestCode", requestCode);
                bundle.putString("returnData", result);
                message.setData(bundle);
                message.what = HttpConfig.SUCCESSCODE;
                message.obj = paramsMaps;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            mHandler.sendEmptyMessage(HttpConfig.PARSEERROR);
        }
    }
}
