package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * topbar按钮
 *
 * @author litfb
 * @date 2014年10月8日
 * @version 1.0
 */
public abstract class BaseTopFunc {

	/** 功能id */
	public abstract int getFuncId();

	/** 弱引用 */
	private WeakReference<Activity> refActivity;

	public BaseTopFunc(Activity activity) {
		this.refActivity = new WeakReference<Activity>(activity);
	}

	/**
	 * 功能点击事件
	 * 
	 * @param
	 */
	public abstract void onclick(View v);

	/**
	 * 取Activity
	 * 
	 * @return
	 */
	protected Activity getActivity() {
		return refActivity.get();
	}

	/**
	 * 实例化一个BaseTopBtnFunc
	 * 
	 * @param clazz
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BaseTopFunc newInstance(Class<?> clazz, Activity activity) {
		if (!BaseTopFunc.class.isAssignableFrom(clazz)) {
			return null;
		}

		BaseTopFunc func = null;

		// 通过构造函数实例化BaseTopBtnFunc的子类

		try {
			Constructor<BaseTopFunc> constructor = (Constructor<BaseTopFunc>) clazz.getConstructor(Activity.class);
			func = (BaseTopFunc) constructor.newInstance(activity);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return func;
	}

	/**
	 * 获得功能View
	 *
	 * @return
	 */
	public abstract View initFuncView(LayoutInflater inflater);

	/**
	 * 
	 * 更新View
	 * 
	 */
	public void reView() {
	}

}
