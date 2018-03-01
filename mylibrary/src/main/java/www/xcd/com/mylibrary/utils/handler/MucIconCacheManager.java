package www.xcd.com.mylibrary.utils.handler;

import android.content.Context;

import com.yonyou.sns.im.cache.ICacheCallBackHandler;
import com.yonyou.sns.im.cache.ICacheDataProcessHandler;
import com.yonyou.sns.im.cache.bitmap.BitmapCacheWork;
import com.yonyou.sns.im.cache.bitmap.ProcessBitmapHandler;
import com.yonyou.sns.im.core.YYIMChat;

import www.xcd.com.mylibrary.R;


/***
 * 群组头像管理
 * 
 * @author wudl
 * 
 */
public class MucIconCacheManager extends BitmapCacheManager {

	/** BitmapCacheWork */
	private BitmapCacheWork bitmapCacheWork;
	/** 上下文 */
	private Context context;
	/** 图像的形状 */
	private int shape;
	/** 默认图片 */
	private int defaultBitmapResId = R.mipmap.icon_default_user;
	/** ProcessBitmapHandler */
	private ProcessBitmapHandler processHandler;

	/**
	 * 初始化生成器
	 * 
	 * @param context
	 */
	public MucIconCacheManager(Context context) {
		super(context, false, BitmapCacheManager.NORMAL_BITMAP);
		this.context = context;
		this.shape = BitmapCacheManager.NORMAL_BITMAP;
		// 生成位图缓存工作
		generateBitmapCacheWork();
	}

	/**
	 * 初始化生成器
	 * 
	 * @param context
	 */
	public MucIconCacheManager(Context context, int shape) {
		super(context, false, shape);
		this.context = context;
		this.shape = shape;
		// 生成位图缓存工作
		generateBitmapCacheWork();
	}

	/**
	 * 设置ProcessBitmapHandler
	 * @param processHandler
	 */
	public void setProcessHandler(ProcessBitmapHandler processHandler) {
		this.processHandler = processHandler;
	}

	/**
	 * 
	 * 获取图片缓存
	 * 
	 * @param data
	 * @param responseObject
	 */
	public void loadFormCache(Object data, Object responseObject) {
		bitmapCacheWork.loadFormCache(data, responseObject, defaultBitmapResId);
	}

	/**
	 * 
	 * 生成需求的图片缓存机制处理
	 * 
	 * @return
	 */
	public BitmapCacheWork generateBitmapCacheWork() {
		// 数据处理器
		ICacheDataProcessHandler cacheDataProcessHandler = new MucIconProcessHandler(context, getImageWidth(),
				getImageHeight());
		// 设置自定义ProcessBitmapHandler
		if (processHandler != null) {
			cacheDataProcessHandler = processHandler;
		}
		// 回调处理器
		ICacheCallBackHandler cacheCallBackHandler;
		switch (shape) {
		case NORMAL_BITMAP:
			// 生成正方形图片
			cacheCallBackHandler = new NormalBitmapCallBackHanlder();
			break;
		case CIRCLE_BITMAP:
			// 生成圆形图片
			cacheCallBackHandler = new CircleBitmapCallBackHanlder();
			break;
		case ROUND_CORNER_BITMAP:
			// 生成圆角正方形图片
			cacheCallBackHandler = new RoundCornerBitmapCallBackHanlder();
			break;
		default:
			// 默认生成正方形图片
			cacheCallBackHandler = new NormalBitmapCallBackHanlder();
			break;
		}
		// 默认图片设置
		cacheCallBackHandler.setLoadingImage(this.defaultBitmapResId);
		// BitmapCacheWork
		bitmapCacheWork = new BitmapCacheWork(context);
		bitmapCacheWork.setProcessDataHandler(cacheDataProcessHandler);
		bitmapCacheWork.setCallBackHandler(cacheCallBackHandler);
		// 使用全局缓存
		bitmapCacheWork.setFileCache(YYIMChat.getInstance().getFileCache());
		// 设置缓存前缀
		bitmapCacheWork.setPrefix(genCachePrefix());
		return bitmapCacheWork;
	}

	/**
	 * 获取缓存前缀
	 * 
	 * @return
	 */
	private String genCachePrefix() {
		return "muc_icon|";
	}

	/**
	 * 清理缓存
	 */
	public void clearCache() {
		bitmapCacheWork.clearCache();
	}

	/**
	 * 清理缓存
	 */
	public void removeCache(String key) {
		bitmapCacheWork.removeCache(key,null);
	}
}
