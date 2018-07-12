package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2018/1/3.
 */

public class InvoiceGridAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Map<String,String>> list;
    private Context context;

    public InvoiceGridAdapter(Context context){
        this.context = context;
        this.mInflater=LayoutInflater.from(context);
    }

    public void setData(List<Map<String,String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list ==null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gridview_invoice, null);
            viewHolder = new ViewHolder();
            viewHolder.tvInvoiceType = (TextView)convertView.findViewById(R.id.tv_InvoiceType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map<String, String> stringMap = list.get(position);
        String name = stringMap.get("name");
        String isCheck = stringMap.get("isCheck");
        viewHolder.tvInvoiceType.setText(name);
        if ("1".equals(isCheck)){
            viewHolder.tvInvoiceType.setBackgroundResource(R.drawable.text_orange_white);
            viewHolder.tvInvoiceType.setTextColor(ContextCompat.getColor(context,R.color.orange));
        }else {
            viewHolder.tvInvoiceType.setBackgroundResource(R.drawable.text_black_white);
            viewHolder.tvInvoiceType.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }
        return convertView;

    }
    class ViewHolder {
        private TextView tvInvoiceType;
    }
}
