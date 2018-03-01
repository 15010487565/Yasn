package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.OrderModel;

import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<OrderModel> list;
    private OnRcItemClickListener onItemClickListener;

    public ShopCarAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData( List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.recycleritem_order, parent, false);
        holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder orderholder = (ViewHolder) holder;
        OrderModel orderModel = list.get(position);

        onItemEventClick(orderholder);

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout storename_linear,addgoods;
        TextView storename;
        ImageView storename_select;

        public ViewHolder(View itemView) {
            super(itemView);
            storename_linear = (LinearLayout) itemView.findViewById(R.id.storename_linear);
            //去凑单
            addgoods = (LinearLayout) itemView.findViewById(R.id.addgoods);
            addgoods.setOnClickListener(this);

            storename = (TextView) itemView.findViewById(R.id.storename);
            storename_select = (ImageView) itemView.findViewById(R.id.storename_select);
            storename_select.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addgoods://凑单
                    onItemClickListener.OnClickRecyButton(1, getLayoutPosition());
                    break;
                case R.id.storename_select://选中图片
                    onItemClickListener.OnClickRecyButton(2, getLayoutPosition());
                    break;
            }
        }
    }

    protected void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.OnItemLongClick(v, position);
                return true;
            }
        });
    }
}
