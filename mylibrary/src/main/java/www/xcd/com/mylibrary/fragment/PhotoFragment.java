package www.xcd.com.mylibrary.fragment;

import com.yonyou.sns.im.entity.album.YYPhotoItem;

import www.xcd.com.mylibrary.base.fragment.BaseFragment;
import www.xcd.com.mylibrary.entity.IPhotoSeclectListener;

/**
 * 文件选择基类
 * @author wudl
 * @date 2014年12月2日
 * @version V1.0
 */
public abstract class PhotoFragment extends BaseFragment {

	/** 是否选择 */
	private boolean isSelect;

	/** 监听 */
	private IPhotoSeclectListener photoSelectListener;

	/**
	 * @return the isSelect
	 */
	public boolean isSelect() {
		return isSelect;
	}

	/**
	 * @param isSelect the isSelect to set
	 */
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	/**
	 * @return the userSelectListener
	 */
	public IPhotoSeclectListener getPhotoSelectListener() {
		return photoSelectListener;
	}

	/**
	 * @param userSelectListener the userSelectListener to set
	 */
	public void setPhotoSelectListener(IPhotoSeclectListener photoSelectListener) {
		this.photoSelectListener = photoSelectListener;
	}
	
	public abstract void dataChange(YYPhotoItem photoItem);
}
