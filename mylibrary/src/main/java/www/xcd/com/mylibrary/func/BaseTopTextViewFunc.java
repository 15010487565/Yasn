package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import www.xcd.com.mylibrary.R;

/**
 * topbar文本按钮
 *
 * @author litfb
 * @date 2014年10月20日
 * @version 1.0
 */
public abstract class BaseTopTextViewFunc extends BaseTopFunc {

	private View funcView;
	private TextView textView;

	public BaseTopTextViewFunc(Activity activity) {
		super(activity);
	}

	/** 功能文本 */
	protected String getFuncText() {
		return "";
	}

	protected int getFuncTextRes() {
		return 0;
	}
	protected int getFuncBgTextRes() {
		return 0;
	}

	@Override
	public View initFuncView(LayoutInflater inflater) {
		// 获得layout
		funcView = inflater.inflate(R.layout.button_topbar_text, null);
		// 获得func id
		funcView.setId(getFuncId());
		// TextView
		textView = (TextView) funcView.findViewById(R.id.func_text);

		if (getFuncTextRes() > 0) {
			if (getFuncBgTextRes()>0){
				textView.setBackgroundResource(getFuncBgTextRes());
			}
			textView.setText(getFuncTextRes());
		} else {
			textView.setText(getFuncText());
		}
		return funcView;
	}

	protected TextView getTextView() {
		return textView;
	}

	protected View getFuncView() {
		return funcView;
	}

}
