package www.xcd.com.mylibrary.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yonyou.sns.im.entity.album.YYPhotoAlbum;
import com.yonyou.sns.im.entity.album.YYPhotoItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.activity.AlbumPhotoActivity;
import www.xcd.com.mylibrary.activity.CameraEditActivity;
import www.xcd.com.mylibrary.adapter.PhotoAibumAdapter;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.YYStorageUtil;
import www.xcd.com.mylibrary.utils.handler.BitmapCacheManager;


/**
 * 相册碎片
 *
 * @author wudl
 *
 */
public class AlbumPhotoFragment extends PhotoFragment implements OnClickListener {
	/** 拍照 */
	private static final int REQUEST_CAMERA = 0;
	/** 展示相片的网格列表 */
	private GridView gridPhoto;
	/** 相册背景 */
	private View viewBackground;
	/** 相册列表 */
	private ListView listAlbum;
	/** bottom */
	private View viewBottom;

	/** album进入动画 */
	private TranslateAnimation enterAnimation;
	/** album退出动画 */
	private TranslateAnimation exitAnimation;

	/** 相册总列表 */
	private List<YYPhotoAlbum> aibumList;
	/** 当前类别的相册列表 */
	private YYPhotoAlbum aibum;
	/** 相片显示的适配器 */
	private PhotoAdappter photoAapter;
	/** 相册显示的适配器 */
	private PhotoAibumAdapter albumAdapter;
	/** 换相册btn */
	private TextView albumBtn;
	/** 预览btn */
	private TextView preViewBtn;

	/** 缓存 */
	private BitmapCacheManager bitmapCacheManager;

	/** 查询字段 */
	private static final String[] IMAGE_PROJECTION = { ImageColumns._ID, // 图片id
			ImageColumns.DISPLAY_NAME, // 图片显示名称
			ImageColumns.DATA, // 图片path
			ImageColumns.ORIENTATION, // 图片旋转
			ImageColumns.BUCKET_ID, // dir id 目录
			ImageColumns.BUCKET_DISPLAY_NAME, // 目录显示名称
			ImageColumns.MINI_THUMB_MAGIC, // 缩略图id
			ImageColumns.SIZE };

	/** where条件 */
	private static final String IMAGE_SELECTION = ImageColumns.SIZE + ">?";
	/** 图片大小限制 */
	private static final int IMAGE_SIZE_LIMIT = 10240;

	/** 拍照的路径 */
	private String cameraFilePath = "";

	@Override
	protected Object getTopbarTitle() {
		return "图片";
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_album_photo;
	}

	@Override
	protected void initView(LayoutInflater inflater, View view) {
		try {
			view.findViewById(R.id.fragment_phpto).setVisibility(View.GONE);
			gridPhoto = (GridView) view.findViewById(R.id.album_photo_gridview);
			viewBackground = view.findViewById(R.id.album_list_background);
			listAlbum = (ListView) view.findViewById(R.id.album_list_view);
			viewBottom = view.findViewById(R.id.album_photo_bottom_view);
			albumBtn = (TextView) view.findViewById(R.id.album_btn);
			preViewBtn = (TextView) view.findViewById(R.id.album_preview_btn);

			// 按钮点击监听
			viewBackground.setOnClickListener(this);
			albumBtn.setOnClickListener(this);
			preViewBtn.setOnClickListener(this);

			// 先查询所有的图片，并分类
			aibumList = getYYPhotoAlbumList();
			if (aibumList==null){
				return;
			}
			aibum = aibumList.get(0);
			if (aibum==null){
				return;
			}
			aibum.setCurrentChoice(true);
			// 设标题
			((SimpleTopbarActivity) getActivity()).refreshFuncView();
			albumBtn.setText(aibum.getName());
			setPreViewBtnColor();
			// 图片Adapter
			List<YYPhotoItem> photoItems = aibum.getPhotoList();
			if (photoItems==null||photoItems.size()==0){
				return;
			}
			if (photoItems.get(0)==null){
				return;
			}
			if (!isSingleSelection() && !TextUtils.isEmpty(photoItems.get(0).getPhotoPath())) {
                // 添加一个相机
                photoItems.add(0, new YYPhotoItem(0, ""));
            }
			photoAapter = new PhotoAdappter(getActivity(), photoItems);
			gridPhoto.setAdapter(photoAapter);

			// 相册adapter
			albumAdapter = new PhotoAibumAdapter(aibumList, getActivity());
			listAlbum.setAdapter(albumAdapter);
			// 相册选择监听
			listAlbum.setOnItemClickListener(new AlbumOnItemClickListener());

			// 单选
			if (isSingleSelection()) {
                // 不需要bottom
                viewBottom.setVisibility(View.GONE);
            } else {
                viewBottom.setVisibility(View.VISIBLE);
            }

			// 缓存管理器
			bitmapCacheManager = new BitmapCacheManager(getActivity(), false, BitmapCacheManager.ROUND_CORNER_BITMAP,
                    BitmapCacheManager.BITMAP_DPSIZE_80);
			bitmapCacheManager.setDefaultImage(R.mipmap.album_photo_default);
			bitmapCacheManager.generateBitmapCacheWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 设置预览颜色
	 */
	private void setPreViewBtnColor() {
		if (getPhotoSelectListener().getselectItems() != null && getPhotoSelectListener().getselectItems().size() > 0) {
			preViewBtn.setTextColor(getResources().getColor(R.color.red));
		} else {
			preViewBtn.setTextColor(getResources().getColor(R.color.black_33));
		}
	}

	/**
	 * 是否单选
	 *
	 * @return
	 */
	private boolean isSingleSelection() {
		return AlbumPhotoActivity.TYPE_SINGLE.equals(getActivity().getIntent().getStringExtra(AlbumPhotoActivity.EXTRA_TYPE));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (cursor!=null){
			cursor.close();
		}
		if (bitmapCacheManager !=null){
			bitmapCacheManager.clearCache();
		}
	}

	/**
	 * 获取相册列表
	 *
	 * @return
	 */
	private Cursor cursor;
	private List<YYPhotoAlbum> getYYPhotoAlbumList() {
		// 查询sd卡中所有的图片
//		getActivity().getContentResolver().getPersistedUriPermissions();
		try {
			cursor = MediaStore.Images.Media.query(getActivity().getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_SELECTION,
                    new String[] { String.valueOf(IMAGE_SIZE_LIMIT) }, MediaStore.Images.Media.DATE_TAKEN + " DESC");

			// 所有图片
			YYPhotoAlbum total = new YYPhotoAlbum();
			// 名称
			total.setName(getString(R.string.album_all_photo));
			// 封面
			total.setBitmap(R.mipmap.album_photo_default);
			// 所有图片标记
			total.setTotal(true);

			// 所有相册
			Map<String, YYPhotoAlbum> albumMap = new LinkedHashMap<String, YYPhotoAlbum>();
			// 遍历图片
			while (cursor.moveToNext()) {
                // id
                String photoId = cursor.getString(0);
                // 路径
                String photoPath = cursor.getString(2);
                // 目录id
                String dirId = cursor.getString(4);
                // 目录名
                String dirName = cursor.getString(5);

                // 生成YYPhotoItem
                YYPhotoItem item = new YYPhotoItem(Integer.valueOf(photoId), photoPath);
                // 添加到total
                total.addPhotoItem(item);
                // 取相册对象
                YYPhotoAlbum photoAlbum = albumMap.get(dirId);
                if (photoAlbum == null) {
                    photoAlbum = new YYPhotoAlbum(dirName, Integer.parseInt(photoId));
                    albumMap.put(dirId, photoAlbum);
                }
                // 相册里添加照片
                photoAlbum.addPhotoItem(item);
            }
			// 关闭cursor
			if (cursor!=null){
				cursor.close();
			}
			// 相册列表
			aibumList = new ArrayList<YYPhotoAlbum>();
			aibumList.add(total);
			aibumList.addAll(albumMap.values());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return aibumList;
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
	 * 照片Adapter
	 *
	 * @author litfb
	 * @date 2014年10月13日
	 * @version 1.0
	 */
	class PhotoAdappter extends BaseAdapter {

		/** context */
		private Context context;
		/** 照片列表 */
		private List<YYPhotoItem> photoList;

		public PhotoAdappter(Context context, List<YYPhotoItem> photoList) {
			this.context = context;
			this.photoList = photoList;
		}

		@Override
		public int getCount() {
			return photoList.size();
		}

		@Override
		public YYPhotoItem getItem(int position) {
			return photoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// view holder
			final PhotoViewHolder holder;
			int itemWidth;
			boolean isInit = false;
			if (convertView == null) {
				// inflate view
				convertView = new RelativeLayout(context);
				// 计算width
				itemWidth = (int) (getResources().getDisplayMetrics().widthPixels - 4 * getResources().getDimensionPixelSize(
						R.dimen.album_photo_spacing)) / 3;
				// width = height
				AbsListView.LayoutParams param = new AbsListView.LayoutParams(itemWidth, itemWidth);
				convertView.setLayoutParams(param);
				// inflate
				LayoutInflater.from(context).inflate(R.layout.view_album_grid_item, (ViewGroup) convertView);
				// new holder
				holder = new PhotoViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.album_griditem_photo);
				holder.bgView = (View) convertView.findViewById(R.id.album_griditem_background);
				holder.select = convertView.findViewById(R.id.album_griditem_select);
				holder.check = (CheckBox) convertView.findViewById(R.id.album_griditem_checkbox);
				// set holder to tag
				convertView.setTag(R.string.viewholder, holder);
				isInit = true;
			} else {
				// get holder from tag
				holder = (PhotoViewHolder) convertView.getTag(R.string.viewholder);
				itemWidth = convertView.getMeasuredHeight();
			}
			// 存position到holder
			holder.position = position;
			// 单选
			if (isSingleSelection()) {
				initSingleSelectedView(holder, position);
			} else {
				initMultiSelectedView(holder, position, isInit);
			}
			return convertView;
		}

		/**
		 * 单选是初始化View
		 *
		 * @param holder
		 */
		private void initSingleSelectedView(final PhotoViewHolder holder, int position) {
			// load图片
			Glide.with(AlbumPhotoFragment.this)
	        .load(photoList.get(position).getPhotoPath())
	        .centerCrop()
	        .crossFade()
	        .placeholder(R.mipmap.icon_default_image)
	        .into(holder.imageView);
			holder.select.setVisibility(View.GONE);
			holder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = getActivity().getIntent();
					// set return value
					YYPhotoItem item = photoList.get(holder.position);
					intent.putExtra(AlbumPhotoActivity.BUNDLE_RETURN_PHOTO, item);
					// set return code
					getActivity().setResult(Activity.RESULT_OK, intent);
					getActivity().finish();
				}
			});
		}

		/**
		 * 初始化多选视图
		 *
		 * @param holder
		 * @param position
		 */
		private void initMultiSelectedView(final PhotoViewHolder holder, final int position, final boolean isInint) {
			if (holder.position == 0) {
				// 如果是第一个放置拍照icon
				holder.imageView.setImageResource(R.mipmap.photo_camera);
				holder.imageView.setBackgroundResource(R.drawable.chat_camera_bg);
				holder.imageView.setScaleType(ScaleType.CENTER_INSIDE);
				holder.imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View paramView) {
						try {
							// 调用系统相机
							Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							File file = new File(YYStorageUtil.getImagePath(getActivity()), UUID.randomUUID().toString() + ".jpg");
							cameraFilePath = file.getPath();
							Uri mOutPutFileUri = Uri.fromFile(file);
							cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mOutPutFileUri);
							cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
							startActivityForResult(cameraIntent, REQUEST_CAMERA);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				holder.select.setVisibility(View.INVISIBLE);
			} else {
				holder.imageView.setScaleType(ScaleType.CENTER_CROP);
				holder.imageView.setBackgroundColor(getResources().getColor(R.color.transparent));
				// 先记录图片信息
				Glide.with(AlbumPhotoFragment.this)
		        .load(photoList.get(position).getPhotoPath())
		        .centerCrop()
		        .crossFade()
		        .placeholder(R.mipmap.icon_default_image)
		        .into(holder.imageView);
				holder.select.setVisibility(View.VISIBLE);
				holder.imageView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View paramView) {
						// 打开多图预览
						AlbumScrollFragment albumScrollFragment = new AlbumScrollFragment();
						List<YYPhotoItem> list = new ArrayList<YYPhotoItem>();
						list.addAll(aibum.getPhotoList());
						// 删除第一个相机图标
						list.remove(0);
						albumScrollFragment.setPhotoItems(list);
						albumScrollFragment.setCurId(list.get(holder.position - 1).getPhotoId());
						getPhotoSelectListener().changeFragment(albumScrollFragment, albumScrollFragment.getClass().getSimpleName());
					}
				});
				// 监听
				holder.select.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View paramView) {
						// cur position
						int curPosition = holder.position;
						YYPhotoItem photoItem = photoList.get(curPosition);
						getPhotoSelectListener().selectedChanged(photoItem);
						// 重设确认按钮文本
						((SimpleTopbarActivity) getActivity()).refreshFuncView();
						// 通知grid变更
						photoAapter.notifyDataSetChanged();
					}
				});
				// 选中
				if (getPhotoSelectListener().isSelected(photoList.get(position))) {
					holder.bgView.setVisibility(View.VISIBLE);
				} else {
					holder.bgView.setVisibility(View.INVISIBLE);
				}
				holder.check.setChecked(getPhotoSelectListener().isSelected(photoList.get(position)));
			}
		}

		class PhotoViewHolder {

			/** position */
			int position;
			/** 图片 */
			ImageView imageView;
			/** 半透明背景 */
			View bgView;
			/** 复选框 */
			View select;
			/** checkbox */
			CheckBox check;

		}
	}

	/**
	 * 进入动画
	 *
	 * @return
	 */
	private AnimationSet getEnterAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		if (enterAnimation == null) {
			enterAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f);
			enterAnimation.setDuration(400);
		}
		animationSet.addAnimation(enterAnimation);
		return animationSet;
	}

	/**
	 * 退出动画
	 *
	 * @return
	 */
	private AnimationSet getExitAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		if (exitAnimation == null) {
			exitAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
			exitAnimation.setDuration(400);
		}
		animationSet.addAnimation(exitAnimation);
		return animationSet;
	}

	/**
	 * 展示相册
	 */
	private void showAlbumList() {
		gridPhoto.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		listAlbum.startAnimation(getEnterAnimation());
		viewBackground.setVisibility(View.VISIBLE);
		listAlbum.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏相册
	 */
	private void hideAlbumList() {
		gridPhoto.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		listAlbum.startAnimation(getExitAnimation());
		viewBackground.setVisibility(View.GONE);
		listAlbum.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.album_list_background) {
			// 隐藏事件
			hideAlbumList();
		} else if (v.getId() == R.id.album_btn) {
			changeAlbum();
		} else if (v.getId() == R.id.album_preview_btn) {
			// 打开多图预览页面
			AlbumScrollFragment albumScrollFragment = new AlbumScrollFragment();
			albumScrollFragment.setPhotoItems(getPhotoSelectListener().getselectItems());
			albumScrollFragment.setCurId(-1);
			getPhotoSelectListener().changeFragment(albumScrollFragment, albumScrollFragment.getClass().getSimpleName());
		}
	}

	public void changeAlbum() {
		// 选择相册
		if (listAlbum.getVisibility() == View.VISIBLE) {
			hideAlbumList();
		} else {
			showAlbumList();
		}
	}

	/**
	 * 相册点击监听
	 *
	 * @author litfb
	 * @date 2014年10月13日
	 * @version 1.0
	 */
	private class AlbumOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// 隐藏列表
			hideAlbumList();
			// 旧相册取消选中
			aibum.setCurrentChoice(false);
			// 设置新的选中相册
			aibum = aibumList.get(position);
			// 设置选中
			aibum.setCurrentChoice(true);
			// 设标题
			((SimpleTopbarActivity) getActivity()).refreshFuncView();
			albumBtn.setText(aibum.getName());
			setPreViewBtnColor();
			List<YYPhotoItem> photoItems = aibum.getPhotoList();
			if (!isSingleSelection() && !TextUtils.isEmpty(photoItems.get(0).getPhotoPath())) {
				// 添加一个相机
				photoItems.add(0, new YYPhotoItem(0, ""));
			}
			// 重设Adapter
			photoAapter = new PhotoAdappter(getActivity(), photoItems);
			gridPhoto.setAdapter(photoAapter);
			// 通知变更
			albumAdapter.notifyDataSetChanged();
		}

	};

	public YYPhotoAlbum getAlbum() {
		return aibum;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CAMERA:
			if (resultCode == Activity.RESULT_OK) {
				Intent cameraIntent = new Intent(getActivity(), CameraEditActivity.class);
				cameraIntent.putExtra(CameraEditActivity.EXTRA_FILE_PATH, cameraFilePath);
				startActivityForResult(cameraIntent, CameraEditActivity.REQUEST_CAMERA_OPTION);
			}
			break;
		case CameraEditActivity.REQUEST_CAMERA_OPTION:
			if (data != null) {
				boolean isOriginal = data.getBooleanExtra(CameraEditActivity.OPTION_RESULT, false);
				Intent intent = getActivity().getIntent();
				intent.putExtra(AlbumPhotoActivity.CAMERA_FILE_PATH, cameraFilePath);
				intent.putExtra(AlbumPhotoActivity.IS_ORIGINAL, isOriginal);
				// set return code
				getActivity().setResult(CameraEditActivity.REQUEST_CAMERA_OPTION, intent);
				getActivity().finish();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void dataChange(YYPhotoItem photoItem) {
		if (photoAapter != null) {
			photoAapter.notifyDataSetChanged();
		}
	}

}
