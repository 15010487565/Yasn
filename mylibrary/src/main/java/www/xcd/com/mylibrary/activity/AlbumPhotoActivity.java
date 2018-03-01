package www.xcd.com.mylibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View.OnClickListener;

import com.yonyou.sns.im.entity.album.YYPhotoItem;
import com.yonyou.sns.im.util.common.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.entity.IPhotoSeclectListener;
import www.xcd.com.mylibrary.fragment.AlbumPhotoFragment;
import www.xcd.com.mylibrary.fragment.PhotoFragment;
import www.xcd.com.mylibrary.func.AlbumPhotoBackBtnFunc;
import www.xcd.com.mylibrary.func.AlbumPhotoConfirmFunc;


/**
 * 相册Activity
 * 
 * @author yh
 * @modified by litfb
 * @modified by wudl 2015/07/28
 */
public class AlbumPhotoActivity extends SimpleTopbarActivity implements OnClickListener, IPhotoSeclectListener {

	/** 多选图片时的确定按钮的Topbar功能列表 */
	private static Class<?> TopBarRightFuncArray[] = { AlbumPhotoConfirmFunc.class };
	/** 返回值key-多选 */
	public static final String BUNDLE_RETURN_PHOTOS = "ALBUM_PHOTOS";
	/** 返回值key-是否是原图 */
	/** 返回值key-单选 */
	public static final String IS_ORIGANL = "IS_ORIGANL";
	public static final String BUNDLE_RETURN_PHOTO = "ALBUM_PHOTO";
	/** camera file path mark */
	public static final String CAMERA_FILE_PATH = "CAMERA_FILE_PATH";
	/** isOriginal mark */
	public static final String IS_ORIGINAL = "IS_ORIGINAL";
	/** 参数 */
	public static final String EXTRA_TYPE = "EXTRA_TYPE";
	/** single selection */
	public static final String TYPE_SINGLE = "TYPE_SINGLE";
	/** 可选图片max num */
	public static final int MAX_IMAGE_SELECT_NUM = 9;
	/** 是否是原图 */
	private volatile boolean isOriginal = false;

	/** 已选图片 */
	private ArrayList<YYPhotoItem> selPhotoList = new ArrayList<YYPhotoItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albumphoto);
		// 展示相册页面
		showAlbumFragment();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 返回按钮监听
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
				getSupportFragmentManager().popBackStack();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 展示相册碎片
	 */
	private void showAlbumFragment(){
		AlbumPhotoFragment fragment= new AlbumPhotoFragment();
		changeFragment(fragment, fragment.getClass().getSimpleName());
	}

	/**
	 * 发送
	 */
	public void onConfirm() {
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		// set return value
		bundle.putSerializable(BUNDLE_RETURN_PHOTOS, selPhotoList);
		Log.e("TAG_","selPhotoList="+selPhotoList.toString());
		bundle.putBoolean(IS_ORIGANL, isOriginal);
		intent.putExtras(bundle);
		// set return code
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	protected Class<?> getTopbarLeftFunc() {
		return AlbumPhotoBackBtnFunc.class;
	}
	/**
	 * 是否单选
	 * 
	 * @return
	 */
	private boolean isSingleSelection() {
		return TYPE_SINGLE.equals(getIntent().getStringExtra(EXTRA_TYPE));
	}

	@Override
	protected Class<?>[] getTopbarRightFuncArray() {
		// 如果是单选，右上角不需要功能按钮，如果是多选需要发送按钮
		return isSingleSelection() ? null : TopBarRightFuncArray;
	}


	@Override
	protected Object getTopbarTitle() {
		return getString(R.string.album_title);
	}

	@Override
	public boolean isOriginal() {
		return isOriginal;
	}

	@Override
	public boolean isSelected(YYPhotoItem item) {
		for (YYPhotoItem photoItem : selPhotoList) {
			if (item.getPhotoId() == photoItem.getPhotoId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void selectedChanged(YYPhotoItem item) {
		if (isSelected(item)) {
			// 已经在已选列表，删除item
			for (int i = 0; i < selPhotoList.size(); i++) {
				YYPhotoItem photoItem = selPhotoList.get(i);
				if (item.getPhotoId() == photoItem.getPhotoId()) {
					selPhotoList.remove(i);
					break;
				}
			}
		} else {
			if (selPhotoList.size() >= MAX_IMAGE_SELECT_NUM) {
				// 超过最大已选数量，提示

				ToastUtil.showShort(this, getString(R.string.album_max_toast, MAX_IMAGE_SELECT_NUM));
			} else {
				// 否则添加图片
				selPhotoList.add(item);
			}
		}
		for (Fragment fragment : getSupportFragmentManager().getFragments()) {
			if(fragment instanceof PhotoFragment){
				PhotoFragment target = (PhotoFragment) fragment;
				if(target!=null){
					target.dataChange(item);
				}
			}
		}
	}

	@Override
	public void changeFragment(PhotoFragment fragment, String tag) {
		// 设fragment属性
		fragment.setSelect(true);
		fragment.setPhotoSelectListener(this);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, fragment, tag);
		ft.addToBackStack(null);
		ft.commit();
	}
	@Override
	public void originalChanged() {
		isOriginal = !isOriginal;
	}
	@Override
	public List<YYPhotoItem> getselectItems() {
		return selPhotoList;
	}



	@Override
	public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

	}

	@Override
	public void onCancelResult() {

	}

	@Override
	public void onErrorResult(int errorCode, IOException errorExcep) {

	}

	@Override
	public void onParseErrorResult(int errorCode) {

	}

	@Override
	public void onFinishResult() {

	}
}
