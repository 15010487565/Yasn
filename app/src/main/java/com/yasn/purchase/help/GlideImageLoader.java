package com.yasn.purchase.help;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.model.HomeModel;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        String url = ((HomeModel.AdvsBean) path).getAtturl();
        Log.e("TAG_轮播图","url="+url);
        Glide.with(context.getApplicationContext())
                .load(url)
                .fitCenter()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.errorimage)
                .error(R.mipmap.errorimage)
                .into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {

        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(R.mipmap.login_y_yasn);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return imageView;
    }
}
