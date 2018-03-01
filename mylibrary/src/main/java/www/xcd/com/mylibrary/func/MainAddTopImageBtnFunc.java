package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.View;

import www.xcd.com.mylibrary.R;


public class MainAddTopImageBtnFunc extends BaseTopImageBtnFunc {

	public MainAddTopImageBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.main_topbar_add;
	}

	@Override
	public int getFuncIcon() {
		return R.mipmap.home_top_arrows;
	}

	@Override
	public void onclick(View v) {
//		ToastUtil.showToast(getActivity().getResources().getString(R.string.ing));
	}
}
