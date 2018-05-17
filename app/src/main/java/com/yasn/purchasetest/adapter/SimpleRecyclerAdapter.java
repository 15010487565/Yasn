package com.yasn.purchasetest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchasetest.R;

import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2017/12/26.
 */

public class SimpleRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;
    private Context mContext;
    private List<Map<String, String>> list;

    public SimpleRecyclerAdapter(Context context) {
        super();
        this.mContext = context;
    }


    public void setData(List<Map<String, String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.goods_attributes, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final ViewHolder simpleholder = (ViewHolder) holder;
        Map<String, String> stringStringMap = list.get(position);
        String attrName = stringStringMap.get("attrName");
        simpleholder.attrName.setText(attrName==null?"":attrName);
        String attrValue = stringStringMap.get("attrValue");
        simpleholder.attrValue.setText(attrValue==null?"":attrValue);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView attrName, attrValue;

        public ViewHolder(View itemView) {
            super(itemView);
            attrName = (TextView) itemView.findViewById(R.id.attrName);
            attrValue = (TextView) itemView.findViewById(R.id.attrValue);
        }
    }
}

