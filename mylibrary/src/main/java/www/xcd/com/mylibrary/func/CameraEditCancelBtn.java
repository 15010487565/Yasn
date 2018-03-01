package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.activity.CameraEditActivity;


/**
 * 照相编辑页面的取消按钮
 * @author wudl
 *
 */
public class CameraEditCancelBtn extends BaseTopTextViewFunc {

	public CameraEditCancelBtn(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.topbar_camera_edit_cancel;
	}

	@Override
	public View initFuncView(LayoutInflater inflater) {
		// 获得layout
		View funcView = super.initFuncView(inflater);
		// 获取文本
		TextView textView = getTextView();
		// 设置文本margin 为0
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) getActivity().getResources()
				.getDimension(R.dimen.top_bar_textbtn_height));
		params.setMargins(0, 0, 0, 0);
		textView.setLayoutParams(params);
		// 设置文本背景
		textView.setBackgroundResource(R.color.transparent);
		return funcView;
	}

	@Override
	protected int getFuncTextRes() {
		// 设置文本文字
		return R.string.camera_edit_cancel;
	}

	@Override
	public void onclick(View v) {
		// 调用取消事件
		((CameraEditActivity) getActivity()).onCancel();
	}

}
