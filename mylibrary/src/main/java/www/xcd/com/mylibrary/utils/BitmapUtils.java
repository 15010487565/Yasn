package www.xcd.com.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;

import com.yonyou.sns.im.util.common.PinYinUtil;

import java.util.HashMap;

public class BitmapUtils {

	public static final HashMap<String, String> colorMap = new HashMap<>();

	static {
//		colorMap.put("a", "#bb7f352c");
//		colorMap.put("b", "#bb41b6e1");
//		colorMap.put("c", "#bbef6599");
//		colorMap.put("d", "#bbbc4e73");
//		colorMap.put("e", "#bb8c5ed9");
//		colorMap.put("f", "#bb4b7dd6");
//		colorMap.put("g", "#bbd9a954");
//		colorMap.put("h", "#bbbbb215");
//		colorMap.put("i", "#bbd5564f");
//		colorMap.put("j", "#bbd9ad44");
//		colorMap.put("k", "#bb0e7f73");
//		colorMap.put("l", "#bbcd4046");
//		colorMap.put("m", "#bb74345a");
//		colorMap.put("n", "#bb318ab0");
//		colorMap.put("o", "#bb81076c");
//		colorMap.put("p", "#bb0caaa5");
//		colorMap.put("q", "#bb116cba");
//		colorMap.put("r", "#bb562493");
//		colorMap.put("s", "#bb2f54af");
//		colorMap.put("t", "#bbb3610d");
//		colorMap.put("u", "#bb4945b3");
//		colorMap.put("v", "#bb8baf41");
//		colorMap.put("w", "#bb8144ae");
//		colorMap.put("x", "#bb549c2c");
//		colorMap.put("y", "#bb299a60");
//		colorMap.put("z", "#bba9326a");
//		colorMap.put("1", "#bbd9ad44");
//		colorMap.put("2", "#bb549c2c");
//		colorMap.put("3", "#bbb3610d");
//		colorMap.put("4", "#bbf5a623");
//		colorMap.put("5", "#bbef6599");
//		colorMap.put("6", "#bbcd4046");
//		colorMap.put("7", "#bbbc4e73");
//		colorMap.put("8", "#bb0caaa5");
//		colorMap.put("9", "#bb8baf41");
//		colorMap.put("0", "#bb4b7dd6");
//		colorMap.put("def", "#bbd5564f");
		colorMap.put("a", "#F95450");
		colorMap.put("b", "#3AAFEC");
		colorMap.put("c", "#7DB82A");
		colorMap.put("d", "#4AAA44");
		colorMap.put("e", "#3A6DEC");
		colorMap.put("f", "#C6B91F");
		colorMap.put("g", "#F66C26");
		colorMap.put("h", "#29AE9B");
		colorMap.put("i", "#4C6DE0");
		colorMap.put("j", "#E04F4A");
		colorMap.put("k", "#A16FE8");
		colorMap.put("l", "#F66C26");
		colorMap.put("m", "#ECAC19");
		colorMap.put("n", "#F05C38");
		colorMap.put("o", "#2BC6B0");
		colorMap.put("p", "#817BFF");
		colorMap.put("q", "#EA4B7C");
		colorMap.put("r", "#AB64E8");
		colorMap.put("s", "#43C678");
		colorMap.put("t", "#9064F7");
		colorMap.put("u", "#DE56C4");
		colorMap.put("v", "#4CA8D9");
		colorMap.put("w", "#DC7E39");
		colorMap.put("x", "#D76155");
		colorMap.put("y", "#D79944");
		colorMap.put("z", "#499CFA");
		colorMap.put("1", "#F95450");
		colorMap.put("2", "#3AAFEC");
		colorMap.put("3", "#7DB82A");
		colorMap.put("4", "#4AAA44");
		colorMap.put("5", "#3A6DEC");
		colorMap.put("6", "#C6B91F");
		colorMap.put("7", "#F66C26");
		colorMap.put("8", "#29AE9B");
		colorMap.put("9", "#4C6DE0");
		colorMap.put("0", "#E04F4A");
		colorMap.put("def", "#A16FE8");
	}

	/**
	 * 将原始图片转化成圆形图片
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap,int padding) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;

		if (width <= height) {
			float clip = (height - width) / 2;
			left = 0;
			top = clip;
			right = width;
			bottom = height - clip;

			height = width;

			dst_left = padding;
			dst_top = padding;
			dst_right = width+padding;
			dst_bottom = width+padding;
		} else {
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;

			dst_left = padding;
			dst_top = padding;
			dst_right = height+padding;
			dst_bottom = height+padding;
		}
		Bitmap newBitmap = Bitmap.createBitmap(width+padding*2, height+padding*2, Config.ARGB_8888);
		try {
			// 指定n
			Canvas canvas = new Canvas(newBitmap);
			// 填充整个Canvas,相当于设置透明的背景
			canvas.drawARGB(0, 0, 0, 0);
			// 设置画笔无锯齿
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			// 绘制外层白圈
			paint.setColor(Color.WHITE);
			float roundPx = newBitmap.getWidth()/2;
			// 圆角
			canvas.drawCircle(roundPx, roundPx, width/2, paint);
			// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			// 图形的绘制区域
			Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
			Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
			// 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle
			canvas.drawBitmap(bitmap, src, dst, paint);
			// 绘制外层白圈
			Paint mBorderPaint = new Paint();
			mBorderPaint.setAntiAlias(true);
			mBorderPaint.setColor(Color.WHITE);
			mBorderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
			canvas.drawCircle(roundPx, roundPx,roundPx,mBorderPaint);
		}finally {
			bitmap.recycle();
		}
		return newBitmap;
	}

	/**
	 * 画圆矩形
	 * @param bitmap
	 * @param roundPixels
	 * @return
	 */
	public static Bitmap toRoundCornerBitmap(Bitmap bitmap, int roundPixels) {
		// 创建一个和原始图片一样大小位图
		Bitmap roundConcerImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		// 创建带有位图roundConcerImage的画布
		Canvas canvas = new Canvas(roundConcerImage);
		// 创建画笔
		Paint paint = new Paint();
		// 创建一个和原始图片一样大小的矩形
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectF = new RectF(rect);
		// 去锯齿
		paint.setAntiAlias(true);
		// 填充整个Canvas,相当于设置透明的背景
		canvas.drawARGB(0, 0, 0, 0);
		// 画一个和原始图片一样大小的圆角矩形
		canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);
		// 设置相交模式
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// 把图片画到矩形去
		canvas.drawBitmap(bitmap, rect, rect, paint);
		bitmap.recycle();
		return roundConcerImage;
	}

	/**
	 * 创建用户自定义的用户默认头像
	 * @return
	 */
	public static Bitmap createDefaultUserBitmap(String name) {
		String first;
		String sec;
		if (!TextUtils.isEmpty(name)&&name.length() >= 2) {
			first = name.substring(name.length() - 2, name.length() - 1);
			sec = name.substring(name.length() - 1);
		} else {
			first = name;
			sec = "";
		}
		String color = null;
		try {
			color = colorMap.get(PinYinUtil.converterFromPinYinFirstLetter(first));
			if (TextUtils.isEmpty(color)) {
                color = colorMap.get("def");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		int width = 200;
		RectF rectF = new RectF(0, 0, width, width);
		// 创建一个和原始图片一样大小位图
		Bitmap bitmap = Bitmap.createBitmap(width, width, Config.ARGB_8888);
		// 创建带有位图bitmap的画布
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.parseColor(color));
		paint.setTextSize(60);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		int baseline = (int) (rectF.top + (rectF.bottom - rectF.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);
		paint.setTextAlign(Paint.Align.CENTER);
		if (name.matches("^[a-zA-Z0-9_ ]+$")){
			// 表示是英文名字，取前俩位字母
			canvas.drawText(name.substring(0, name.length() >= 2 ? 2 : name.length()), width / 2, baseline, paint);
		}else{
			// 包含中文字，取后俩位字母
			canvas.drawText(first + sec, width / 2, baseline, paint);
		}
		return bitmap;
	}

	/**
	 * 创建用户自定义的用户默认头像
	 * @return
	 */
	public static Bitmap createDefaultUserBitmap(String name , Bitmap icon) {
		String first;
		if (name.length() >= 2) {
			first = name.substring(name.length() - 2, name.length() - 1);
		} else {
			first = name;
		}
		String color = colorMap.get(PinYinUtil.converterFromPinYinFirstLetter(String.valueOf(first)));
		if (TextUtils.isEmpty(color)) {
			color = colorMap.get("def");
		}
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		int padding = 45;
		Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
		Rect rect = new Rect(padding, padding, icon.getWidth()+padding, icon.getHeight()+padding);
		// 创建一个和原始图片一样大小位图
		Bitmap bitmap = Bitmap.createBitmap(padding*2+icon.getWidth(), padding*2+icon.getHeight(), Config.ARGB_8888);
		// 创建带有位图bitmap的画布
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.parseColor(color));
		canvas.drawBitmap(icon, src, rect, paint);
		return bitmap;
	}

	/**
	 * 以CenterCrop方式resize图片
	 * @param src  原始图片
	 * @param scale 目标的宽高比例(width/height)
	 * @return
	 */
	public static Bitmap resizeBitmapByCenterCrop(Bitmap src, int scale) {
		if (src == null || scale <= 0) {
			return null;
		}
		// 图片宽度
		int w = src.getWidth();
		// 图片高度
		int h = src.getHeight();
		// 高宽比之差
		int temp = (1 / scale) - (h / w);
		/**
		 * 判断高宽比例，如果目标高宽比例大于原图，则原图高度不变，宽度为(w1 = (h * x) / y)拉伸
		 * 画布宽高(w1,h),在原图的((w - w1) / 2, 0)位置进行切割
		 */

		if (temp > 0) {
			// 计算画布宽度
			int w1 = h * scale;
			// 创建一个指定高宽的图片
			Bitmap newb = Bitmap.createBitmap(src, (w - w1) / 2, 0, w1, h);
			// 原图回收
			src.recycle();
			return newb;
		} else {
			/**
			 * 如果目标高宽比小于原图，则原图宽度不变，高度为(h1 = (y * w) / x),
			 * 画布宽高(w, h1), 原图切割点(0, (h - h1) / 2)
			 */

			// 计算画布高度
			int h1 = w / scale;
			// 创建一个指定高宽的图片
			Bitmap newb = Bitmap.createBitmap(src, 0, (h - h1) / 2, w, h1);
			// 原图回收
			src.recycle();
			return newb;
		}
	}

}
