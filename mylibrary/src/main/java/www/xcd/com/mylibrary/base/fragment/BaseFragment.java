package www.xcd.com.mylibrary.base.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.config.HttpConfig;
import www.xcd.com.mylibrary.func.BaseTopFunc;
import www.xcd.com.mylibrary.help.OkHttpHelper;
import www.xcd.com.mylibrary.http.HttpInterface;
import www.xcd.com.mylibrary.utils.DialogUtil;
import www.xcd.com.mylibrary.utils.NetUtil;
import www.xcd.com.mylibrary.utils.SharePrefHelper;
import www.xcd.com.mylibrary.utils.ToastUtil;

import static www.xcd.com.mylibrary.utils.ToastUtil.showToast;

/**
 * fragment基类
 *
 * @author litfb
 * @date 2014年10月8日
 * @version 1.0
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener,View.OnFocusChangeListener,HttpInterface {

	/**
	 * 本地文件读写权限
	 */
	public static final String[] WRITEREADPERMISSIONS = {
			Manifest.permission.WRITE_EXTERNAL_STORAGE
			,Manifest.permission.READ_EXTERNAL_STORAGE
	};
	/** 右侧功能对象的MAP，可以通过id获得指定的功能对象 */
	protected Hashtable<Integer, BaseTopFunc> funcMap = new Hashtable<Integer, BaseTopFunc>();
	private TextView viewTitle;
	protected LinearLayout leftFuncZone;
	protected LinearLayout rightFuncZone;

	/**
	 * 在这里实现Fragment数据的缓加载.
	 * @param isVisibleToUser
	 */
	protected boolean isVisible;
	protected void onVisible(){
		lazyLoad();
	}

	protected void lazyLoad() {

	}

	protected void onInvisible(){}

	/**
	 * get layout resid
	 *
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化页面
	 *
	 * @param view
	 */

	protected abstract void initView(LayoutInflater inflater, View view);

	/**
	 * 顶部标题
	 * @return
     */
	protected Object getTopbarTitle() {
		return "";
	}

	/**
	 * 是否显示顶部标题图片
	 * @return
     */
	protected boolean getTitleIsVisibilityImage() {
		return false;
	}

	private boolean isActive = true;
	/** 自行持有activity */
	private WeakReference<FragmentActivity> refActivity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (getLayoutId() == 0) {
			return null;
		}
		View view = inflater.inflate(getLayoutId(), container, false);
		viewTitle = (TextView) view.findViewById(R.id.topbar_title);
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
		ImageView topbar_titlearrowsimage = (ImageView) view.findViewById(R.id.topbar_titlearrowsimage);
		ImageView topbar_titleimage = (ImageView) view.findViewById(R.id.topbar_titleimage);
		LinearLayout topbar_middle = (LinearLayout) view.findViewById(R.id.topbar_middle);
		if (getTitleIsVisibilityImage()){
			topbar_titlearrowsimage.setVisibility(View.VISIBLE);
			topbar_titleimage.setVisibility(View.VISIBLE);
			topbar_middle.setOnClickListener(this);
		}else {
			topbar_titlearrowsimage.setVisibility(View.GONE);
			topbar_titleimage.setVisibility(View.GONE);
		}

		leftFuncZone = (LinearLayout) view.findViewById(R.id.left_func);
		rightFuncZone = (LinearLayout) view.findViewById(R.id.right_func);
		// 添加左侧控件（默认是返回按钮，但是支持自定义替换此按钮）
		addViewToLeftFunctionZone();
		// 添加右侧控件
		addViewToRightFunctionZone();
		initView(inflater, view);

		return view;
	}
	/**
	 * 获得左侧的功能控件
	 * 只能是imagebutton，并且只有一个
	 *
	 * @return
	 */
	protected Class<?> getTopbarLeftFunc() {
		// 默认使用back
//		return CommonBackTopBtnFunc.class;
		return null;
	}	/**
	 *
	 * 将功能控件添加到左侧功能区域
	 *
	 */
	protected void addViewToLeftFunctionZone() {
		Class<?> customFunc = (Class<?>) getTopbarLeftFunc();
		if (customFunc == null) {
			return;
		}

		View funcView = getFuncView(getActivity().getLayoutInflater(), customFunc);

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
	protected Class<?>[] getTopbarRightFuncArray() {
		return null;
	}
	/**
	 *
	 * 将功能控件添加到右侧功能区域
	 *
	 * @param
	 */
	private void addViewToRightFunctionZone() {
		Class<?>[] customFuncs = getTopbarRightFuncArray();

		// 功能列表为空,隐藏区域
		if (customFuncs == null || customFuncs.length == 0) {
			rightFuncZone.setVisibility(View.GONE);
			return;
		}
		// 初始化功能
		for (Class<?> customFunc : customFuncs) {
			// view
			View funcView = getFuncView(getActivity().getLayoutInflater(), customFunc);
			if (funcView != null) {
				// 点击事件
				funcView.setOnClickListener(this);
				// 加入页面
				rightFuncZone.addView(funcView);
			}
		}
		// 设置列表显示
		rightFuncZone.setVisibility(View.VISIBLE);
	}
	/**
	 * 获得功能View
	 *
	 * @param inflater
	 * @param
	 */
	protected View getFuncView(LayoutInflater inflater, Class<?> funcClazz) {
		BaseTopFunc func = BaseTopFunc.newInstance(funcClazz, getActivity());

		if (func == null) {
			return null;
		}

		funcMap.put(func.getFuncId(), func);

		// view
		View funcView = func.initFuncView(inflater);
		return funcView;
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof FragmentActivity) {
			setActivity((FragmentActivity) activity);
		}
	}

	/**
	 * setActivity
	 *
	 * @param activity
	 */
	public void setActivity(FragmentActivity activity) {
		this.refActivity = new WeakReference<FragmentActivity>(activity);
	}
	/**
	 * getActivity
	 *
	 * @return
	 */
	public FragmentActivity getFragmentActivity() {
		return refActivity.get();
	}
	/**
	 * 重设Title
	 *
	 * @param text
	 */
	public void setActivityTitle(String text) {
		FragmentActivity fragmentActivity = getFragmentActivity();
		if (fragmentActivity instanceof SimpleTopbarActivity) {
			((SimpleTopbarActivity) fragmentActivity).resetTopbarTitle(text);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		isActive = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		isActive = false;
	}

	public boolean activityIsActivity() {
		return isActive;
	}

	/**
	 * 数据请求中的dialog
	 */
	private Dialog requestDialog;

	/**
	 * 关闭请求中dialog
	 */
	protected void dialogDissmiss() {
		if (requestDialog != null && requestDialog.isShowing()) {
			requestDialog.dismiss();
		}
	}

	/**
	 * 显示请求中dialog
	 */
	protected void dialogshow() {
		if (requestDialog != null && !requestDialog.isShowing()&&!getActivity().isFinishing()) {
			requestDialog.show();
		}
	}

	protected void createDialog(){
		// 请求中dialog
		requestDialog = DialogUtil.getProgressDialog(getActivity());
	};

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.topbar_middle) {
			topTitleOnclick();
		} else {
			BaseTopFunc topFunc = funcMap.get(v.getId());
			if (topFunc != null) {
				topFunc.onclick(v);
			}
		}
	}

	/**
	 * 顶部title点击事件
	 */
	protected void topTitleOnclick(){

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

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		EditText textView = (EditText) view;
		String hint;
		hint = textView.getHint().toString();
		if (hasFocus) {
			textView.setTag(hint);
			textView.setHint("");
		} else {
			if (textView.getTag()!=null){
				hint = textView.getTag().toString();
				textView.setHint(hint);
			}
		}
		if (!hasFocus) {
			InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}else {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			view.requestFocus();
			imm.showSoftInput(view, 0);
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
	public void okHttpPost(final int requestCode, String url, final Map<String, Object> paramsMaps) {
		if (NetUtil.getNetWorking(getActivity()) == false) {
			showToast("请检查网络。。。");
			return;
		}
		OkHttpHelper.getInstance().postAsyncHttp(requestCode, url, paramsMaps,new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					//请求错误
					case HttpConfig.REQUESTERROR:
						IOException error = (IOException) msg.obj;
						onErrorResult(HttpConfig.REQUESTERROR, error);
						break;
					//解析错误
					case HttpConfig.PARSEERROR:
						onParseErrorResult(HttpConfig.PARSEERROR);
						break;
					//网络错误
					case HttpConfig.NETERROR:
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

		if (NetUtil.getNetWorking(getActivity()) == false) {
			showToast("请检查网络。。。");
			return;
		}
		dialogshow();
		OkHttpHelper.getInstance().getAsyncHttp(requestCode, url, paramsMaps,new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
					//请求错误
					case HttpConfig.REQUESTERROR:
						IOException error = (IOException) msg.obj;
						onErrorResult(HttpConfig.REQUESTERROR, error);
						ToastUtil.showToast("网络异常,请求错误...");
						dialogDissmiss();

						break;
					//解析错误
					case HttpConfig.PARSEERROR:
						onParseErrorResult(HttpConfig.PARSEERROR);
						ToastUtil.showToast("网络异常,数据解析错误...");
						dialogDissmiss();

						break;
					//网络错误
					case HttpConfig.NETERROR:
						ToastUtil.showToast("网络异常...");
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
		if (NetUtil.getNetWorking(getActivity()) == false) {
			showToast("请检查网络。。。");
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
						break;
					//解析错误
					case HttpConfig.PARSEERROR:
						onParseErrorResult(HttpConfig.PARSEERROR);
						break;
					//网络错误
					case HttpConfig.NETERROR:
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
						break;
				}
			}
		});
	}

	public void cleanToken(){
		String token = SharePrefHelper.getInstance(getActivity()).getSpString("token");
		String resetToken = SharePrefHelper.getInstance(getActivity()).getSpString("resetToken");
		if (token !=null&&!"".equals(token)){
			SharePrefHelper.getInstance(getActivity()).putSpString("token","");
		}else if (resetToken !=null&&!"".equals(resetToken)){
			SharePrefHelper.getInstance(getActivity()).putSpString("token","");
			SharePrefHelper.getInstance(getActivity()).putSpString("resetToken","");
			SharePrefHelper.getInstance(getActivity()).putSpString("resetTokenTime","");
			SharePrefHelper.getInstance(getActivity()).putSpString("priceDisplayMsg","");
			SharePrefHelper.getInstance(getActivity()).putSpString("regionId", "");
			ToastUtil.showToast("登录已过期,请重新登录！");
		}
	}
}