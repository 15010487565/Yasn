package com.yasn.purchase.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yasn.purchase.R;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class AuthorAreaAdapter extends BaseAdapter implements SpinnerAdapter{
    private Context context ;
    private String[] list;

    public AuthorAreaAdapter(Context context){
        this.context=context;
    }

    public void setData(String[] list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 :list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView=LayoutInflater.from(context).inflate(R.layout.spinnerauthor_list_item_getview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvgetView = (TextView) convertView.findViewById(R.id.tv_tinted_spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvgetView.setText(getItem(position).toString());
        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView=LayoutInflater.from(context).inflate(R.layout.spinnerauthor_list_item_getdropdownview, null);
            viewHolder = new ViewHolder();
            viewHolder.tvdropdowview = (TextView) convertView.findViewById(R.id.tv_tinted_spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.e("TAG_getDropDownView"+position,getItem(position).toString());
        viewHolder.tvdropdowview.setText(getItem(position).toString());
        return convertView;
    }
    private class ViewHolder {
        public TextView tvgetView,tvdropdowview;
    }

}
