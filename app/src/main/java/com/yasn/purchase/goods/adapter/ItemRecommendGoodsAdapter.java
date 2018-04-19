package com.yasn.purchase.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.goods.bean.RecommendGoodsBean;

import java.util.List;


/**
 * item页底部推荐商品适配器
 */
public class ItemRecommendGoodsAdapter extends BaseAdapter {

    private Context context = null;
    private List<RecommendGoodsBean> data = null;

    public ItemRecommendGoodsAdapter(Context context, List<RecommendGoodsBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_goods, null);
            holder = new ViewHolder(convertView);
            holder.itemGoodsImage = (ImageView)convertView.findViewById(R.id.item_goods_image);
            holder.tvGoodsName = (TextView)convertView.findViewById(R.id.tv_goods_name);
            holder.tvGoodsPrice = (TextView)convertView.findViewById(R.id.tv_goods_price);
            holder.tvGoodsOldPrice = (TextView)convertView.findViewById(R.id.tv_goods_old_price);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initItemView(position, holder);
        return convertView;
    }

    private void initItemView(int position, ViewHolder holder) {
        RecommendGoodsBean bean = data.get(position);
        Glide.with(context).load(bean.imag).error(R.mipmap.default_icon).into(holder.itemGoodsImage);
        holder.tvGoodsName.setText(bean.title);
        holder.tvGoodsPrice.setText("￥" + String.format("%.2f", bean.currentPrice));
        holder.tvGoodsOldPrice.setText("￥" + String.format("%.2f", bean.price));
    }

    class ViewHolder {
        ImageView itemGoodsImage;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
        TextView tvGoodsOldPrice;

        public ViewHolder(View view) {
            view.setTag(this);
        }
    }
}
