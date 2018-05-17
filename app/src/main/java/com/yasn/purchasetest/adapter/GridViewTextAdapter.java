package com.yasn.purchasetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yasn.purchasetest.R;

import java.util.List;

/**
 * Created by gs on 2018/1/8.
 */

public class GridViewTextAdapter extends BaseAdapter {
    private Context mContext;
    private   List<String> listTextSpec;

    public GridViewTextAdapter(Context mContext, List<String> listTextSpec) {
        super();
        this.mContext = mContext;
        this.listTextSpec = listTextSpec;
    }
    @Override
    public int getCount() {
        if (listTextSpec == null) {
            return 0;
        } else {
            return this.listTextSpec.size();
        }
    }
    @Override
    public Object getItem(int position) {
        if (listTextSpec == null) {
            return null;
        } else {
            return this.listTextSpec.get(position);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (this.mContext).inflate(R.layout.listview_gridview_text, null, false);
            holder.tv_spec = (TextView) convertView.findViewById(R.id.tv_shopcarSpec);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.listTextSpec != null) {
            String textSpec = listTextSpec.get(position);
            if (holder.tv_spec != null) {
                holder.tv_spec.setText(textSpec);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_spec;
    }
}
