package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.activity.AlbumPhotoActivity;


public class AlbumPhotoConfirmFunc extends BaseTopTextViewFunc {

	public AlbumPhotoConfirmFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.topbar_album_confirm;
	}

	@Override
	public void onclick(View v) {
		((AlbumPhotoActivity) getActivity()).onConfirm();
	}

	@Override
	public View initFuncView(LayoutInflater inflater) {
		View view = super.initFuncView(inflater);
		getTextView().setEnabled(false);
		return view;
	}

	@Override
	public void reView() {
		if (((AlbumPhotoActivity) getActivity()).getselectItems() != null
				&& ((AlbumPhotoActivity) getActivity()).getselectItems().size() > 0) {
			getTextView().setText(getActivity().getResources().getString(R.string.save) + "(" + ((AlbumPhotoActivity) getActivity()).getselectItems().size() + "/9)");
			getFuncView().setEnabled(true);
			getTextView().setEnabled(true);
		} else {
			getTextView().setText(getActivity().getResources().getString(R.string.save));
			getFuncView().setEnabled(false);
			getTextView().setEnabled(false);
		}
	}

}
