package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.view.BadgeView;


/**
 * topbar图标按钮
 *
 * @author litfb
 * @date 2014年10月20日
 * @version 1.0
 */
public abstract class BaseTopImageRedPointFunc extends BaseTopFunc {

	private View funcView;

	private ImageView imageView;

	private BadgeView redPoint;

	public BaseTopImageRedPointFunc(Activity activity) {
		super(activity);
	}

	/** 功能图标 */
	public int getFuncIcon() {
		return 0;
	}

	@Override
	public View initFuncView(LayoutInflater inflater) {
		funcView = inflater.inflate(R.layout.button_topbar_image_redpoint, null);
		// 获得func id
		funcView.setId(getFuncId());
		// 获得func icon
		imageView = (ImageView) funcView.findViewById(R.id.topbar_func_icon);
		redPoint = (BadgeView) funcView.findViewById(R.id.topbar_func_icon_redpoint);
		if (getFuncIcon() > 0) {
			imageView.setImageResource(getFuncIcon());
//			resetRedPoint("");
		} else {
			imageView.setVisibility(View.GONE);
		}
		redPoint.setVisibility(View.GONE);
		return funcView;
	}

	protected ImageView getImageView() {
		return imageView;
	}

	protected View getFuncView() {
		return funcView;
	}

//	protected void resetRedPoint(String redPointNum) {
//		redPoint.setText(redPointNum);
//	}
}
