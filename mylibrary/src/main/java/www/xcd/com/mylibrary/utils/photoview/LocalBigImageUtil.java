package www.xcd.com.mylibrary.utils.photoview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Created by Android on 2017/6/6.
 */

public class LocalBigImageUtil {
    private static final int DEFAULT_MAX_WIDTH = 200;
    private static final int DEFAULT_MAX_HEIGHT = 200;
    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT;
    private static final int DEFAULT_COMPRESS_QUALITY = 100;

    public LocalBigImageUtil() {
    }

    public static byte[] bmpToByteArray(Bitmap bitmap) {
        ByteArrayInputStream is = null;

        try {
            if(bitmap != null) {
                ByteArrayOutputStream ignore = new ByteArrayOutputStream();
                bitmap.compress(DEFAULT_COMPRESS_FORMAT, 100, ignore);
                is = new ByteArrayInputStream(ignore.toByteArray());
                byte[] var3 = readStream(is);
                return var3;
            }
        } catch (Exception var14) {
//            YYIMLogger.d(var14);
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch (IOException var13) {
                ;
            }

        }

        return null;
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        boolean len = false;

        int len1;
        while((len1 = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len1);
        }

        outStream.close();
        return outStream.toByteArray();
    }

    public static Bitmap getBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = computeSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap getBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, (Rect)null, options);
        options.inSampleSize = computeSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, (Rect)null, options);
    }

    public static Bitmap reduce(String path, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        } else {
            float ratio = 1.0F;
            float sx = (new BigDecimal(width)).divide(new BigDecimal(bitmap.getWidth()), 4, 1).floatValue();
            float sy = (new BigDecimal(height)).divide(new BigDecimal(bitmap.getHeight()), 4, 1).floatValue();
            if(width < bitmap.getWidth() && height > bitmap.getHeight()) {
                ratio = sx;
            } else if(width < bitmap.getWidth() && height < bitmap.getHeight()) {
                ratio = Math.min(sx, sy);
            } else if(width > bitmap.getWidth() && height < bitmap.getHeight()) {
                ratio = sy;
            }

            Matrix matrix = new Matrix();
            matrix.postScale(ratio, ratio);
            int degree = readPictureDegree(path);
            if(degree > 0) {
                matrix.setRotate((float)degree, (float)(bitmap.getWidth() / 2), (float)(bitmap.getHeight() / 2));
            }

            Bitmap target = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if(bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }

            return target;
        }
    }

    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if(dst != null && dst.exists()) {
            BitmapFactory.Options opts = null;
            if(width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                opts.inSampleSize = computeSampleSize(opts, width, height);
                opts.inJustDecodeBounds = false;
            }

            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError var5) {
//                YYIMLogger.d(var5);
            }
        }

        return null;
    }

    public static Bitmap getBitmapFromFile(String path, int width, int height) {
        File file = new File(path);
        if(null != file && file.exists()) {
            BitmapFactory.Options opts = null;
            if(width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, opts);
                opts.inSampleSize = computeInitialSampleSize(opts, Math.min(width, height), height * width);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }

            try {
                return BitmapFactory.decodeFile(path, opts);
            } catch (OutOfMemoryError var6) {
//                YYIMLogger.d(var6);
            }
        }

        return null;
    }

    public static int[] getcomputeSampleSizeByPath(String path) {
        File file = new File(path);
        if(null != file && file.exists()) {
            BitmapFactory.Options opts = null;
            opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            opts.inSampleSize = computeSampleSize(opts, 200, 200);
            int w = opts.outWidth / opts.inSampleSize;
            int h = opts.outHeight / opts.inSampleSize;
            return new int[]{w, h};
        } else {
            return new int[]{200, 200};
        }
    }

    public static int readPictureDegree(String path) {
        short degree = 0;

        try {
            ExifInterface e = new ExifInterface(path);
            int orientation = e.getAttributeInt("Orientation", 1);
            switch(orientation) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
            }

            return degree;
        } catch (IOException var4) {
//            YYIMLogger.d(var4);
            return degree;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if(degrees != 0 && null != bitmap) {
            Matrix matrix = new Matrix();
            matrix.setRotate((float)degrees, (float)(bitmap.getWidth() / 2), (float)(bitmap.getHeight() / 2));
            Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if(null != bitmap) {
                bitmap.recycle();
                bitmap = null;
            }

            return bmp;
        } else {
            return bitmap;
        }
    }

    private static int computeSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float)height / (float)reqHeight);
            int widthRatio = Math.round((float)width / (float)reqWidth);
            inSampleSize = heightRatio < widthRatio?heightRatio:widthRatio;
        }

        return inSampleSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = (double)options.outWidth;
        double h = (double)options.outHeight;
        int lowerBound = maxNumOfPixels == -1?1:(int)Math.ceil(Math.sqrt(w * h / (double)maxNumOfPixels));
        int upperBound = minSideLength == -1?128:(int)Math.min(Math.floor(w / (double)minSideLength), Math.floor(h / (double)minSideLength));
        return upperBound < lowerBound?lowerBound:(maxNumOfPixels == -1 && minSideLength == -1?1:(minSideLength == -1?lowerBound:upperBound));
    }

    static {
        DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    }
}
