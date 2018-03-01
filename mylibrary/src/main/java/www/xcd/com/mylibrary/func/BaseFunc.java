package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;

import www.xcd.com.mylibrary.R;


public abstract class BaseFunc  implements IFuncRequestCode{

	/** funcView */
	protected View funcView;

	/** 功能id */
	public abstract int getFuncId();

	/** 功能图标 */
	public abstract int getFuncIcon();

	/** 功能名称 */
	public abstract int getFuncName();
	/** 重设功能名称 */
	public void resetFuncName(String name){
		nameView.setText(name);
	}
	/** 弱引用 */
	private WeakReference<Activity> refActivity;

	public BaseFunc(Activity activity) {
		this.refActivity = new WeakReference<Activity>(activity);
	}


	/**
	 * 实例化一个BaseFunc
	 * 
	 * @param clazz
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BaseFunc newInstance(Class<?> clazz, Activity activity) {
		if (!BaseFunc.class.isAssignableFrom(clazz)) {
			return null;
		}
		BaseFunc func = null;
		try {
			Constructor<BaseFunc> constructor = (Constructor<BaseFunc>) clazz.getConstructor(Activity.class);
			func = (BaseFunc) constructor.newInstance(activity);
		} catch (Exception e) {

		}
		return func;
	}

	/**
	 * 取Activity
	 * 
	 * @return
	 */
	protected Activity getActivity() {
		return refActivity.get();
	}

	/**
	 * 功能点击事件
	 * 
	 *
	 */
	public abstract void onclick();

	/**
	 * 是否处理该request
	 * 
	 * @param requestCode
	 * @return
	 */
	public boolean acceptRequest(int requestCode) {
		return false;
	}

	/**
	 * onActivityResult
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	/**
	 * 是否有右箭头
	 * 
	 * @return
	 */
	public boolean hasArrowRight() {
		return true;
	}

	/**
	 * 获得功能View
	 * 
	 * @param isSeparator
	 * @param params
	 * @return
	 */
	TextView nameView;
	public View initFuncView(boolean isSeparator, Object... params) {
		// view
		// view
		funcView = getActivity().getLayoutInflater().inflate(R.layout.view_func, null);
		// func id
		funcView.setId(getFuncId());
		// func icon
		ImageView iconView = (ImageView) funcView.findViewById(R.id.func_icon);
		//		funcView.findViewById(R.id.func_icon_redpoint).setVisibility(View.GONE);
		// func name
		nameView = (TextView) funcView.findViewById(R.id.func_name);
		nameView.setText(getFuncName());
		if (getFuncIcon() > 0) {
			iconView.setImageResource(getFuncIcon());
		} else {
			iconView.setVisibility(View.GONE);
			LinearLayout.LayoutParams textparams =
					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
							, LinearLayout.LayoutParams.WRAP_CONTENT);
			textparams.leftMargin = (int) getActivity().getResources().getDimension(R.dimen.margin_15);
			nameView.setLayoutParams(textparams);//设置布局参数
		}
		// custom view
		LinearLayout customView = (LinearLayout) funcView.findViewById(R.id.func_custom);
		initCustomView(customView);
		// arrow right
		if (!hasArrowRight()) {
			ImageView rightView = (ImageView) funcView.findViewById(R.id.func_right);
			rightView.setVisibility(View.INVISIBLE);
		}
		// separator
		View separator = funcView.findViewById(R.id.func_separator);
		if (!isSeparator) {
			separator.setVisibility(View.GONE);
		}else {
			separator.setVisibility(View.VISIBLE);
		}
		// bindView
		bindView();
		return funcView;
	}

	public void reFlashFunc(){
		
	}
	/**
	 * bindView
	 *
	 */
	public void bindView() {

	}

	/**
	 * 自定义部分
	 *
	 */
	protected void initCustomView(LinearLayout customView) {

	}

}
