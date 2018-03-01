package www.xcd.com.mylibrary.utils.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yonyou.sns.im.cache.bitmap.BitmapCallBackHanlder;

import www.xcd.com.mylibrary.utils.BitmapUtils;


public class RoundCornerBitmapCallBackHanlder extends BitmapCallBackHanlder {

	@Override
	protected Bitmap createBitmapFromByte(byte[] buffer) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
		// 按宽高比1:1 截图
		bitmap = BitmapUtils.resizeBitmapByCenterCrop(bitmap, 1);
		// 计算半径
		int conerPx = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 10;
		bitmap = BitmapUtils.toRoundCornerBitmap(bitmap, conerPx);
		return bitmap;
	}
}
