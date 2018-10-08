package www.xcd.com.mylibrary.base.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.func.BaseTopFunc;
import www.xcd.com.mylibrary.func.CommonBackTopBtnFunc;
import www.xcd.com.mylibrary.help.OkHttpHelper;
import www.xcd.com.mylibrary.http.HttpInterface;
import www.xcd.com.mylibrary.utils.DialogUtil;
import www.xcd.com.mylibrary.utils.NetUtil;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.ToastUtil;
import www.xcd.com.mylibrary.view.BadgeView;

import static www.xcd.com.mylibrary.utils.ToastUtil.showToast;

/**
 * topbar
 * 目标：
 * 1、可以自定义的增加按钮或者文本按钮（支持右侧最多添加两个图片按钮或者一个文本按钮，左侧只能是一个按钮，返回或者目录按钮）
 * 2、自定义按钮自己初始化自己的view并响应事件
 * 3、支持title名称的改变
 * 4、按钮支持文本按钮和imagebutton
 *
 * @author litfb
 * @version 1.0
 * @date 2014年10月10日
 */
public abstract class SimpleTopbarActivity extends BaseActivity implements OnClickListener, HttpInterface {

    public static final String IMAGE_UNSPECIFIED = "image/*";
    protected Dialog loginDialog;
    /**
     * 右侧功能对象的MAP，可以通过id获得指定的功能对象
     */
    protected Hashtable<Integer, BaseTopFunc> funcMap = new Hashtable<Integer, BaseTopFunc>();

    /**
     * title
     */
    private TextView viewTitle;


    /**
     * 左侧功能区，用来放置功能按钮
     */
    protected LinearLayout leftFuncZone;
    /**
     * 右侧功能区，用来放置功能按钮
     */
    protected LinearLayout rightFuncZone;
    /**
     * 中间功能区，用来放置功能按钮或者默认的文本域
     */
    protected RelativeLayout middlerFuncZone;
    //右侧红点
    BadgeView redPoint;

    /**
     * 获得title文本
     *
     * @return
     */
    protected Object getTopbarTitle() {
        return "";
    }

    /**
     * 获得左侧的功能控件
     * 只能是imagebutton，并且只有一个
     *
     * @return
     */
    protected Class<?> getTopbarLeftFunc() {
        // 默认使用back
        return CommonBackTopBtnFunc.class;
    }

    /**
     * 获得右侧的功能控件集合
     * 如果有文本，那么就不能有imagebutton
     * 如果是imagebutton，那么可以是一个或者两个
     *
     * @return
     */
    protected Class<?>[] getTopbarRightFuncArray() {
        return null;
    }

    protected Class<?> getTopbarMiddleFunc() {
        return null;
    }


    /**
     * 重新设置title
     *
     * @param resId
     */
    public void resetTopbarTitle(int resId) {
        // 设置title
        viewTitle.setText(resId);
    }

    /**
     * 重新设置title
     *
     * @param text
     */
    public void resetTopbarTitle(String text) {
        // 设置title
        viewTitle.setText(text);
    }

    /**
     * 是否显顶部标题
     */
    public boolean isTopbarVisibility() {
        return true;
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        RelativeLayout topbat_parent = (RelativeLayout) findViewById(R.id.topbat_parent);
        if (isTopbarVisibility()) {
            topbat_parent.setVisibility(View.VISIBLE);
        } else {
            topbat_parent.setVisibility(View.GONE);
        }
        viewTitle = (TextView) findViewById(R.id.topbar_title);
        leftFuncZone = (LinearLayout) findViewById(R.id.left_func);
        rightFuncZone = (LinearLayout) findViewById(R.id.right_func);
        // 判断使用默认的文字title还是使用自定义的title
        if (getTopbarMiddleFunc() != null) {
            viewTitle.setVisibility(View.GONE);

            addCustomViewToMiddleFunctionZone();
        } else {
            viewTitle.setVisibility(View.VISIBLE);

            // 设置title
            if (getTopbarTitle() instanceof Integer) {
                int title = (Integer) getTopbarTitle();

                if (title != 0) {
                    viewTitle.setText(title);
                }
            } else if (getTopbarTitle() instanceof String) {
                String title = (String) getTopbarTitle();
                viewTitle.setText(title);
            }
        }
        ImageView topbar_titlearrowsimage = (ImageView) findViewById(R.id.topbar_titlearrowsimage);
        ImageView topbar_titleimage = (ImageView) findViewById(R.id.topbar_titleimage);
        LinearLayout topbar_middle = (LinearLayout) findViewById(R.id.topbar_middle);
        if (getTitleIsVisibilityImage()) {
            topbar_titlearrowsimage.setVisibility(View.VISIBLE);
            topbar_titleimage.setVisibility(View.VISIBLE);
            topbar_middle.setOnClickListener(this);
        } else {
            topbar_titlearrowsimage.setVisibility(View.GONE);
            topbar_titleimage.setVisibility(View.GONE);
        }
        // 添加左侧控件（默认是返回按钮，但是支持自定义替换此按钮）
        addViewToLeftFunctionZone();
        // 添加右侧控件
        addViewToRightFunctionZone();

    }

    /**
     * 是否显示顶部标题图片
     *
     * @return
     */
    protected boolean getTitleIsVisibilityImage() {
        return false;
    }

    @Override
    public void onClick(View v) {
        BaseTopFunc topFunc = funcMap.get(v.getId());
        if (topFunc != null) {
            topFunc.onclick(v);
        }
    }

    /**
     * 在中间放置自定义的控件
     */
    private void addCustomViewToMiddleFunctionZone() {
        Class<?> customFunc = getTopbarMiddleFunc();
        View funcView = getFuncView(getLayoutInflater(), customFunc);

        if (funcView != null) {
            // 点击事件
            funcView.setOnClickListener(this);
            // 加入页面
            middlerFuncZone.addView(funcView);

            // 设置列表显示
            middlerFuncZone.setVisibility(View.VISIBLE);
        } else {
            middlerFuncZone.setVisibility(View.GONE);
        }
    }

    /**
     * 将功能控件添加到左侧功能区域
     */
    protected void addViewToLeftFunctionZone() {
        Class<?> customFunc = (Class<?>) getTopbarLeftFunc();
        if (customFunc == null) {
            return;
        }

        View funcView = getFuncView(getLayoutInflater(), customFunc);

        if (funcView != null) {
            // 点击事件
            funcView.setOnClickListener(this);
            // 加入页面
            leftFuncZone.addView(funcView);

            // 设置列表显示
            leftFuncZone.setVisibility(View.VISIBLE);
        } else {
            leftFuncZone.setVisibility(View.GONE);
        }
    }

    /**
     * 将功能控件添加到右侧功能区域
     *
     * @param
     */
    public void addViewToRightFunctionZone() {
        Class<?>[] customFuncs = getTopbarRightFuncArray();

        // 功能列表为空,隐藏区域
        if (customFuncs == null || customFuncs.length == 0) {
            rightFuncZone.setVisibility(View.GONE);
            return;
        }
        // 初始化功能
        for (Class<?> customFunc : customFuncs) {
            // view
            View funcView = getFuncView(getLayoutInflater(), customFunc);
            if (funcView != null) {
                // 点击事件
                funcView.setOnClickListener(this);
                //红点
                redPoint = (BadgeView) funcView.findViewById(R.id.topbar_func_icon_redpoint);
                // 加入页面
                rightFuncZone.addView(funcView);
            }
        }
        // 设置列表显示
        rightFuncZone.setVisibility(View.VISIBLE);
    }

    /**
     * 重置顶部右侧红点
     */
    public void setRedPoint(int num) {
        if (redPoint != null){
            if (num >0){
                redPoint.setVisibility(View.VISIBLE);
            }else {
                redPoint.setVisibility(View.GONE);
            }
            redPoint.setText(String.valueOf(num));
        }
    }

    public void setRedPoint(String num) {
        if (redPoint != null){
            if (!TextUtils.isEmpty(num)){
                redPoint.setVisibility(View.VISIBLE);
            }else {
                redPoint.setVisibility(View.GONE);
            }
            redPoint.setText(num);
        }
    }

    /**
     * 重设右侧控件
     *
     * @param visibility
     */
    public void refreshRightFunctionZone(boolean visibility) {
        Log.e("TAG_重设右侧控件", "visibility=" + visibility);
        if (visibility) {
            rightFuncZone.setVisibility(View.VISIBLE);
        } else {
            rightFuncZone.setVisibility(View.GONE);
        }
    }

    /**
     * 获得功能View
     *
     * @param inflater
     * @param
     */
    protected View getFuncView(LayoutInflater inflater, Class<?> funcClazz) {
        BaseTopFunc func = BaseTopFunc.newInstance(funcClazz, this);

        if (func == null) {
            return null;
        }

        funcMap.put(func.getFuncId(), func);

        // view
        View funcView = func.initFuncView(inflater);
        return funcView;
    }

    /**
     * 更新topbar控件，暂时全部更新，以后考虑指定更新
     */
    public void refreshFuncView() {
        for (Map.Entry<Integer, BaseTopFunc> entity : funcMap.entrySet()) {
            BaseTopFunc topFunc = entity.getValue();
            topFunc.reView();
        }
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.e("TAG_", "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public void runUiThread(Runnable action) {
        if (dialogIsActivity() == false) {
            Log.e("TAG_", "runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }

    public void createDialog() {
        // 登录中dialog
        loginDialog = DialogUtil.getProgressDialog(this);
    }

    /**
     * 关闭登录中dialog
     */
    public void dialogDissmiss() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
    }

    /**
     * 显示登录中dialog
     */
    public void dialogshow() {
        if (loginDialog != null && !loginDialog.isShowing()) {
            loginDialog.show();
            Log.e("TAG_显示显dialog", (loginDialog != null) + "===" + !loginDialog.isShowing());
        } else if (loginDialog == null) {
            createDialog();
            dialogshow();
        }
    }

    /* 动态设置ListView的高度
            * @param listView
            */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0, count = listAdapter.getCount(); i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            ////计算子项View 的宽高
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() + listView.getDividerHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

    /**
     * POST网络请求
     *
     * @param url        地址
     * @param paramsMaps 参数
     */
    public void okHttpPost(final int requestCode, String url, final Map<String, String> paramsMaps) {
        if (NetUtil.getNetWorking(SimpleTopbarActivity.this) == false) {
            showToast("请检查网络。。。");
            dialogDissmiss();
            return;
        }
        OkHttpHelper.getInstance().postAsyncHttp(requestCode, url, paramsMaps, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        ToastUtil.showToast("请求错误");
                        dialogDissmiss();
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);
                        ToastUtil.showToast("解析错误");
                        dialogDissmiss();
                        break;
                    //网络错误
                    case HttpConfig.NETERROR:
                        ToastUtil.showToast("网络错误");
                        dialogDissmiss();
                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        dialogDissmiss();
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        break;
                    default:
                        dialogDissmiss();
                        break;
                }
            }
        });
    }

    /**
     * POST网络请求
     * 请求头
     *
     * @param url        地址
     * @param paramsMaps 参数
     */
    public void okHttpPostHeader(final int requestCode, String url, final Map<String, String> paramsMaps) {
        if (NetUtil.getNetWorking(SimpleTopbarActivity.this) == false) {
            showToast("请检查网络。。。");
            dialogDissmiss();
            return;
        }
        OkHttpHelper.getInstance().postAsyncHttpHeader(requestCode, url, paramsMaps, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        ToastUtil.showToast("请求错误");
                        dialogDissmiss();
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);
                        ToastUtil.showToast("解析错误");
                        dialogDissmiss();
                        break;
                    //网络错误
                    case HttpConfig.NETERROR:
                        ToastUtil.showToast("网络错误");
                        dialogDissmiss();
                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        dialogDissmiss();
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        break;
                    default:
                        dialogDissmiss();
                        break;
                }
            }
        });
    }

    /**
     * GET网络请求
     *
     * @param url        地址
     * @param paramsMaps 参数
     */
    public void okHttpGet(final int requestCode, String url, final Map<String, Object> paramsMaps) {
        if (NetUtil.getNetWorking(SimpleTopbarActivity.this) == false) {
            showToast("请检查网络。。。");
            return;
        }
//		dialogshow();
        OkHttpHelper.getInstance().getAsyncHttp(requestCode, url, paramsMaps, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        dialogDissmiss();
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);
                        dialogDissmiss();
                        break;
                    //网络错误
                    case HttpConfig.NETERROR:
                        dialogDissmiss();
                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        dialogDissmiss();
                        break;
                }
            }
        });
    }

    /**
     * POST网络请求上传图片
     *
     * @param url        地址
     * @param paramsMaps 参数
     */
    public void okHttpImgPost(final int requestCode, String url, final Map<String, Object> paramsMaps) {
        if (NetUtil.getNetWorking(SimpleTopbarActivity.this) == false) {
            showToast("请检查网络。。。");
            dialogDissmiss();
            return;
        }
        OkHttpHelper.getInstance().postAsyncPicHttp(requestCode, url, paramsMaps, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    //请求错误
                    case HttpConfig.REQUESTERROR:
                        IOException error = (IOException) msg.obj;
                        onErrorResult(HttpConfig.REQUESTERROR, error);
                        dialogDissmiss();
                        ToastUtil.showToast("请求错误");
                        break;
                    //解析错误
                    case HttpConfig.PARSEERROR:
                        onParseErrorResult(HttpConfig.PARSEERROR);
                        dialogDissmiss();
                        ToastUtil.showToast("解析错误");
                        break;
                    //网络错误
                    case HttpConfig.NETERROR:
                        ToastUtil.showToast("网络错误");
                        dialogDissmiss();
                        break;
                    //请求成功
                    case HttpConfig.SUCCESSCODE:
                        Bundle bundle = msg.getData();
                        int requestCode = bundle.getInt("requestCode");
                        int returnCode = bundle.getInt("returnCode");
                        String returnMsg = bundle.getString("returnMsg");
                        String returnData = bundle.getString("returnData");
                        Map<String, Object> paramsMaps = (Map) msg.obj;
                        onSuccessResult(requestCode, returnCode, returnMsg, returnData, paramsMaps);
                        dialogDissmiss();
                        break;
                }
            }
        });
    }

    public void cleanToken() {
        String token = SharePrefHelper.getInstance(this).getSpString("token");
        String resetToken = SharePrefHelper.getInstance(this).getSpString("resetToken");
        if (token != null && !"".equals(token)) {
            SharePrefHelper.getInstance(this).putSpString("token", "");
        } else if (resetToken != null && !"".equals(resetToken)) {
            SharePrefHelper.getInstance(this).putSpString("token", "");
            SharePrefHelper.getInstance(this).putSpString("resetToken", "");
            SharePrefHelper.getInstance(this).putSpString("resetTokenTime", "");
            SharePrefHelper.getInstance(this).putSpString("priceDisplayMsg", "");
            SharePrefHelper.getInstance(this).putSpString("regionId", "");
            ToastUtil.showToast("登录已过期,请重新登录！");
        }
    }

    /**
     * 认证状态
     */
    public void showStartAuthorDialog(Class<?> clzz){
        int lv_id = SharePrefHelper.getInstance(this).getSpInt("lv_id");
        if (lv_id == 2) {//认证审核中
            showAuthDialog("");
        } else if (lv_id == 3) {
            String memssage = SharePrefHelper.getInstance(this).getSpString("memssage");
            showAuthDialog(memssage);
        } else {//去认证
            startActivity(new Intent(this,clzz));
        }
    }
    //认证弹窗
    protected AlertDialog mAuthNotifyDialog;

    private void showAuthDialog(String reasonString) {
        LayoutInflater factor = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_authorhint, null);
        TextView tvAuthorMessageHint = (TextView) serviceView.findViewById(R.id.tv_AuthorMessageHint);
        if (TextUtils.isEmpty(reasonString)){
            tvAuthorMessageHint.setText("认证审核中，请耐心等待！");
        }else {
            tvAuthorMessageHint.setText("由于" + reasonString + ",认证失败,请重新认证!");
        }
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mAuthNotifyDialog = builder.create();
        mAuthNotifyDialog.show();
        mAuthNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }

}
