package www.xcd.com.mylibrary.entity;


import com.yonyou.sns.im.entity.album.YYPhotoItem;

import java.util.List;

import www.xcd.com.mylibrary.fragment.PhotoFragment;


/**
 * 相片选择监听
 * @author wudl
 *
 */
public interface IPhotoSeclectListener {
	/**
	 * 是否是原图改变
	 */
	public void originalChanged();
	/**
	 * 是否选中原图 
	 */
	public boolean isOriginal();
	/**
	 * 是否已被选中
	 */
	public boolean isSelected(YYPhotoItem item);
	/**
	 * 选择是否已改变
	 * @param item
	 */
	public void selectedChanged(YYPhotoItem item);
	/**
	 * 获取
	 * @return
	 */
	public List<YYPhotoItem> getselectItems();
	/**
	 * 更改碎片
	 * @param fragment
	 * @param tag
	 */
	public void changeFragment(PhotoFragment fragment, String tag);
}
