package com.yasn.purchase.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.goods.bean.GoodsConfigBean;

import java.util.List;


/**
 * 商品详情里的规格参数数据适配器
 */
public class GoodsConfigAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<GoodsConfigBean> data;

    public GoodsConfigAdapter(Context context, List<GoodsConfigBean> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    public void setData(List<GoodsConfigBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_config_listview, null);
            holder = new ViewHolder(convertView);
            holder.itemKey = (TextView) convertView.findViewById(R.id.item_key);
            holder.itemValue = (TextView) convertView.findViewById(R.id.item_value);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GoodsConfigBean config = data.get(position);
        holder.itemKey.setText(config.keyProp);
        holder.itemValue.setText(config.value);
        return convertView;
    }

    static class ViewHolder {

        TextView itemKey;
        TextView itemValue;

        public ViewHolder(View view) {
            view.setTag(this);
        }
    }

}
