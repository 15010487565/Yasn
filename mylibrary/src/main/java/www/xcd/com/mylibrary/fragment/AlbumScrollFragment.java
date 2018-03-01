package www.xcd.com.mylibrary.fragment;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.yonyou.sns.im.entity.album.YYPhotoItem;
import com.yonyou.sns.im.util.common.FileUtils;
import com.yonyou.sns.im.util.common.LocalBigImageUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.entity.IPhotoSeclectListener;
import www.xcd.com.mylibrary.utils.YYStringUtils;
import www.xcd.com.mylibrary.utils.image.PhotoViewPager;
import www.xcd.com.mylibrary.view.PhotoView;

/**
 * 相册滚动控件
 *
 * @author wudl
 *
 */
public class AlbumScrollFragment extends PhotoFragment {
	/** ViewPager */
	private PhotoViewPager viewPager;
	/** 列表数据源 */
	private List<YYPhotoItem> photoItems;
	/** 首先显示的图片id */
	private int preShowId = 0;
	/** 视图软引用 */
	SparseArray<SoftReference<View>> viewCache;

	public AlbumScrollFragment() {
		viewCache = new SparseArray<>();
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_album_scroll;
	}

	public void setPhotoItems(List<YYPhotoItem> photoItems) {
		this.photoItems=new ArrayList<>();
		this.photoItems.addAll(photoItems);
	}

	public void setCurId(int curId) {
		this.preShowId = curId;
	}

	@Override
	protected void initView(LayoutInflater inflater, View view) {
		viewPager = (PhotoViewPager) view.findViewById(R.id.viewpage);
		init();
	}


	/**
	 * 初始化
	 */
	private void init() {
		/** 当前选中 */
		int curIndex = -1;
		// 遍历图片消息
		for (int i = 0; i < photoItems.size(); i++) {
			// 当前选中
			YYPhotoItem entity = photoItems.get(i);
			if (preShowId == entity.getPhotoId()) {
				curIndex = i;
				break;
			}
		}
		// adapter
		viewPager.setAdapter(new ScrollPageAdapter());
		// 设置选中
		viewPager.setCurrentItem(curIndex < 0 ? 0 : curIndex, false);
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

	/**
	 * 滚动适配器
	 *
	 * @author wudl
	 *
	 */
	private class ScrollPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return photoItems.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// 直接从缓存中获取视图
			View view = viewCache.get(position) == null ? null : viewCache.get(position).get();
			if (view == null) {
				// 初始化视图
				view = initView(position);
				// 添加视图
				container.addView(view);
				// 添加缓存
				viewCache.put(position, new SoftReference<>(view));
			}
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 直接从缓存中获取视图
			View view = viewCache.get(position) == null ? null : viewCache.get(position).get();
			if (view != null) {
				// 移除view
				container.removeView(view);
				// 清除缓存
				viewCache.get(position).clear();
			}
		}

	}

	/**
	 * 初始化视图
	 *
	 * @param position
	 * @return
	 */
	private View initView(final int position) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_album_scroll_item, null);
		PhotoView zoomImage = (PhotoView) view.findViewById(R.id.zoom_image_view);
		final CheckBox isOriginal = (CheckBox) view.findViewById(R.id.original_photo);
		final CheckBox isSelected = (CheckBox) view.findViewById(R.id.select_photo);
		// 显示文件大小
		File file = new File(photoItems.get(position).getPhotoPath());
		String key = FileUtils.bytes2kb(file.exists() ? file.length() : 0l);
		String str = "原图  " + key;
		// 设置原始尺寸大小
		isOriginal.setText(YYStringUtils.initStyle(str, key, getResources().getColor(R.color.red)));
		// 原图点击事件
		isOriginal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((IPhotoSeclectListener) getActivity()).originalChanged();
				// 在勾选原图时表示要发送图片
				if (isOriginal.isChecked() && !isSelected.isChecked()){
					isSelected.setChecked(isOriginal.isChecked());
					((IPhotoSeclectListener) getActivity()).selectedChanged(photoItems.get(position));
					resetCheckBox(position);
					((SimpleTopbarActivity) getActivity()).refreshFuncView();
				}else{
					resetCheckBox(position);
				}
			}
		});
		// 选择点击事件
		isSelected.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((IPhotoSeclectListener) getActivity()).selectedChanged(photoItems.get(position));
				resetCheckBox(position);
				((SimpleTopbarActivity) getActivity()).refreshFuncView();
			}
		});
		isOriginal.setChecked(((IPhotoSeclectListener) getActivity()).isOriginal());
		isSelected.setChecked(((IPhotoSeclectListener) getActivity()).isSelected(photoItems.get(position)));
		// 展示数据
		showOriginImage(photoItems.get(position).getPhotoPath(), zoomImage);
		return view;
	}

	/**
	 * 展示原图
	 */
	private void showOriginImage(String path, PhotoView zoomImage) {
		// DisplayMetrics
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// 取屏幕大小bitmap
		Bitmap imgbitmap = LocalBigImageUtil.getBitmapFromFile(path, displayMetrics.widthPixels, displayMetrics.heightPixels);
		if (imgbitmap == null) {
			imgbitmap = LocalBigImageUtil.getBitmapFromResource(getResources(), R.mipmap.album_photo_default, displayMetrics.widthPixels,
					displayMetrics.heightPixels);
		}
		zoomImage.setImageBitmap(imgbitmap);
	}

	/**
	 * 重置checkBox状态
	 */
	private void resetCheckBox(int position) {
		CheckBox isOriginal;
		CheckBox isSelected;
		// 获取当前视图
		View curView = viewCache.get(position) == null ? null : viewCache.get(position).get();
		if (curView != null) {
			isOriginal = (CheckBox) curView.findViewById(R.id.original_photo);
			isSelected = (CheckBox) curView.findViewById(R.id.select_photo);
			isOriginal.setChecked(((IPhotoSeclectListener) getActivity()).isOriginal());
			isSelected.setChecked(((IPhotoSeclectListener) getActivity()).isSelected(photoItems.get(position)));
		}
		// 获取前一个视图
		View preView = viewCache.get(position - 1) == null ? null : viewCache.get(position - 1).get();
		if (preView != null) {
			isOriginal = (CheckBox) preView.findViewById(R.id.original_photo);
			isSelected = (CheckBox) preView.findViewById(R.id.select_photo);
			isOriginal.setChecked(((IPhotoSeclectListener) getActivity()).isOriginal());
			isSelected.setChecked(((IPhotoSeclectListener) getActivity()).isSelected(photoItems.get(position - 1)));
		}
		// 获取后一个视图
		View nextView = viewCache.get(position + 1) == null ? null : viewCache.get(position + 1).get();
		if (nextView != null) {
			isOriginal = (CheckBox) nextView.findViewById(R.id.original_photo);
			isSelected = (CheckBox) nextView.findViewById(R.id.select_photo);
			isOriginal.setChecked(((IPhotoSeclectListener) getActivity()).isOriginal());
			isSelected.setChecked(((IPhotoSeclectListener) getActivity()).isSelected(photoItems.get(position + 1)));
		}
	}

	@Override
	public void dataChange(YYPhotoItem photoItem) {

	}
}
