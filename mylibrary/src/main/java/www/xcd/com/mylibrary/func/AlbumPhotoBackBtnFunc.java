package www.xcd.com.mylibrary.func;

import android.app.Activity;
import android.view.View;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;


public class AlbumPhotoBackBtnFunc extends BaseTopImageBtnFunc {

	public AlbumPhotoBackBtnFunc(Activity activity) {
		super(activity);
	}

	@Override
	public int getFuncId() {
		return R.id.album_photo_left;
	}

	@Override
	public int getFuncIcon() {
		return R.drawable.selector_back_btn;
	}

	@Override
	public void onclick(View v) {
		SimpleTopbarActivity activity = (SimpleTopbarActivity) getActivity();
		if(activity.getFragmentManager().getBackStackEntryCount()>1){
			activity.getFragmentManager().popBackStack();
		}else{
			activity.finish();
		}
	}
}
