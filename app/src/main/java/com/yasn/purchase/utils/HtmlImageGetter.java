package com.yasn.purchase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import www.xcd.com.mylibrary.utils.HelpUtils;

/**
 * Created by gs on 2018/1/30.
 */

public class HtmlImageGetter implements Html.ImageGetter {

    private Context context;
    private TextView textView;

    public HtmlImageGetter(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    /**
     * 获取图片
     */
    @Override
    public Drawable getDrawable(String source) {
        Log.e("TAG_RG", "source---?>>>" + source);
        LevelListDrawable drawable = new LevelListDrawable();
        Drawable empty = context.getResources().getDrawable(
                R.mipmap.failtoload);
        drawable.addLevel(0, 0, empty);
        int[] screenSize = HelpUtils.getScreenSize(context);// 默认屏幕宽
        int newWidth = screenSize[0];
        drawable.setBounds(0, 0, newWidth,
                empty.getIntrinsicHeight());
        new LoadImage().execute(source, drawable);
        return drawable;
    }

    /**
     * 异步下载图片类
     *
     * @author Ruffian
     * @date 2016年1月15日
     *
     */
    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 图片下载完成后执行
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                /**
                 * 适配图片大小 <br/>
                 * 默认大小：bitmap.getWidth(), bitmap.getHeight()<br/>
                 * 适配屏幕：getDrawableAdapter
                 */
                mDrawable = getDrawableAdapter(context, mDrawable,
                        bitmap.getWidth(), bitmap.getHeight());

                // mDrawable.setBounds(0, 0, bitmap.getWidth(),
                // bitmap.getHeight());

                mDrawable.setLevel(1);

                /**
                 * 图片下载完成之后重新赋值textView<br/>
                 * mtvActNewsContent:我项目中使用的textView
                 *
                 */
                textView.invalidate();
                CharSequence t = textView.getText();
                textView.setText(t);
            }
        }

        /**
         * 加载网络图片,适配大小
         *
         * @param context
         * @param drawable
         * @param oldWidth
         * @param oldHeight
         * @return
         * @author Ruffian
         * @date 2016年1月15日
         */
        public LevelListDrawable getDrawableAdapter(Context context,
                                                    LevelListDrawable drawable, int oldWidth, int oldHeight) {
            LevelListDrawable newDrawable = drawable;
            long newHeight = 0;// 未知数
            int[] screenSize = HelpUtils.getScreenSize(context);// 默认屏幕宽
            int newWidth = screenSize[0];
            newHeight = (newWidth * oldHeight) / oldWidth;
            // LogUtils.w("oldWidth:" + oldWidth + "oldHeight:" +
            // oldHeight);
            // LogUtils.w("newHeight:" + newHeight + "newWidth:" +
            // newWidth);
            newDrawable.setBounds(0, 0, newWidth, (int) newHeight);
            return newDrawable;
        }
    }

}

