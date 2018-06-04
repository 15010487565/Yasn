package com.yasn.purchase.goods.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.model.GoodsDetailsModel;

import java.util.List;


/**
 * 图片轮播适配器
 */
public class BannerHolderView implements Holder<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> {

    ImageView bannerImg;
    private TextView textView;
    private List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> list;

    public BannerHolderView(TextView textView, List<GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean> list) {
        this.textView = textView;
        this.list = list;
    }

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_img, null);
        bannerImg = (ImageView)view.findViewById(R.id.banner_img);
        bannerImg.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, GoodsDetailsModel.GoodsDetailsBean.GoodsGallerysBean data) {
        textView.setText(list == null ?"0/0":((position+1)+"/"+list.size()));
        String imageUrl = data.getBig();
        if (imageUrl.indexOf("http://")==-1){
            imageUrl = "http://"+imageUrl;
        }
        Log.e("TAG_详情页轮播图","imageUrl="+imageUrl);
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .error(R.mipmap.default_icon)
                .into(bannerImg);
    }
}
