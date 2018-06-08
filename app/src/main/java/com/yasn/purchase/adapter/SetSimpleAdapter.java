package com.yasn.purchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2018/5/30.
 */

public class SetSimpleAdapter extends SimpleAdapter {

    private List<Map<String,String>> list;
    private LayoutInflater inflater;

    public SetSimpleAdapter(Context context, List<Map<String,String>> list, int resource, String[] from,
                            int[] to) {
        super(context, list, resource, from, to);
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cancelorder, null);
            viewHolder = new ViewHolder();
            viewHolder.ivIsCheck = (ImageView) convertView.findViewById(R.id.iv_isCheck);
            viewHolder.tvReason = (TextView)convertView.findViewById(R.id.tv_Reason);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map<String,String> map =  list.get(position);
        String reason = map.get("reason");
        viewHolder.tvReason.setText(reason);
        String isCheck = map.get("isCheck");
        if ("0".equals(isCheck)){
            viewHolder.ivIsCheck.setBackgroundResource(R.mipmap.checkbox_unchecked);
        }else {
            viewHolder.ivIsCheck.setBackgroundResource(R.mipmap.checkbox_checked);
        }
        return convertView;
    }
    class ViewHolder{
         ImageView ivIsCheck;
         TextView tvReason;
     }
}