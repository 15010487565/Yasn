package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2017/12/26.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Map<String, String>> list;

    public OrderDetailsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Map<String, String>> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_orderdetails_maintop, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        Map<String, String> map = list.get(position);
        String keyName = map.get("keyName");
        viewHolder.tvKeyName.setText(keyName+"：");
        String valueName = map.get(keyName);
        if ("订单总额".equals(keyName)){
            viewHolder.tvVlaueName.setText("￥00.00".equals(valueName)?"%":valueName);
            viewHolder.tvVlaueName.setTextColor(ContextCompat.getColor(context,R.color.orange));
        }else {
            viewHolder.tvVlaueName.setText("￥00.00".equals(valueName)?"%":valueName);
            viewHolder.tvVlaueName.setTextColor(ContextCompat.getColor(context,R.color.black_66));
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKeyName, tvVlaueName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvKeyName = (TextView) itemView.findViewById(R.id.tv_orderDetailsKeyName);
            tvVlaueName = (TextView) itemView.findViewById(R.id.tv_orderDetailsVlaueName);
        }
    }
}

