package www.xcd.com.mylibrary.utils.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yonyou.sns.im.cache.bitmap.BitmapCallBackHanlder;


public class NormalBitmapCallBackHanlder extends BitmapCallBackHanlder {

	@Override
	protected Bitmap createBitmapFromByte(byte[] buffer) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
		return bitmap;
	}
}
