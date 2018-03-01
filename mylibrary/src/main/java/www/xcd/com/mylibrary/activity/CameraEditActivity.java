package www.xcd.com.mylibrary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.yonyou.sns.im.log.YYIMLogger;
import com.yonyou.sns.im.util.common.FileUtils;
import com.yonyou.sns.im.util.common.LocalBigImageUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import www.xcd.com.mylibrary.R;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.func.CameraEditCancelBtn;
import www.xcd.com.mylibrary.func.CameraEditConfirmBtn;
import www.xcd.com.mylibrary.utils.YYStringUtils;


public class CameraEditActivity extends SimpleTopbarActivity {

	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";

	public static final int REQUEST_CAMERA_OPTION = 99;

	public static final String OPTION_RESULT = "OPTION_RESULT";

	/** 照片展示 */
	private ImageView showImage;
	/** checkbox */
	private CheckBox originalCheckBox;

	private String filePath;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cameraedit);

		showImage = (ImageView) findViewById(R.id.surface_camera);
		originalCheckBox = (CheckBox) findViewById(R.id.original_image);
		Intent intent = getIntent();
		filePath = intent.getStringExtra(EXTRA_FILE_PATH);
		if (filePath==null){
			return;
		}
		File file = new File(filePath);
		FileInputStream fis = null;
		long length = 0;

		try {
			fis = new FileInputStream(file);
			length = fis.available();

			YYIMLogger.d("文件长度：" + length);
		} catch (FileNotFoundException e) {
			YYIMLogger.e(e.getMessage());
		} catch (IOException e) {
			YYIMLogger.e(e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					YYIMLogger.e(e.getMessage());
				}
			}
		}

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int mScreenW = dm.widthPixels; // 得到宽度
		int mScreenH = dm.heightPixels; // 得到高度
		// 获取图片旋转角度
		int degree = LocalBigImageUtil.readPictureDegree(filePath);
		// 获取图片
		Bitmap curImageBitmap = LocalBigImageUtil.getBitmapFromFile(filePath, mScreenW, mScreenH);
		// 将图片旋转
		curImageBitmap = LocalBigImageUtil.rotateBitmap(curImageBitmap, degree);
		showImage.setImageBitmap(curImageBitmap);
		String key = FileUtils.bytes2kb(length);
		String str = "原始尺寸  " + key;
			originalCheckBox.setText(YYStringUtils.initStyle(str, key,
				getResources().getColor(R.color.red)));
	}

	/**
	 * 点击确认按钮
	 */
	public void onConfirm() {
		boolean isOriginal = originalCheckBox.isChecked();
		Intent intent = getIntent();
		intent.putExtra(OPTION_RESULT, isOriginal);
		setResult(REQUEST_CAMERA_OPTION, intent);
		finish();
	}

	/**
	 * 点击取消按钮
	 */
	public void onCancel() {
		// 如果取消了使用了图片，应该立即清除
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		finish();
	}

	@Override
	protected Object getTopbarTitle() {
		return R.string.camera_edit_title;
	}

	@Override
	protected Class<?>[] getTopbarRightFuncArray() {
		Class<?>[] btn = { CameraEditConfirmBtn.class };
		return btn;
	}


	@Override
	protected Class<?> getTopbarLeftFunc() {
		return CameraEditCancelBtn.class;
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
