package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.PayModel;

import java.util.List;

/**
 * Created by gs on 2017/12/26.
 */

public class PayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<PayModel.DataBean> list;

    public PayAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<PayModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_pay, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ViewHolder viewHolder = (ViewHolder) holder;
        PayModel.DataBean dataBean = list.get(position);
        String name = dataBean.getName();
        viewHolder.tvItemPayName.setText(name);
        if ("支付宝".equals(name)){
            viewHolder.ivItemPay.setBackgroundResource(R.mipmap.zfbp);
        }else  if ("转账支付".equals(name)){
            viewHolder.ivItemPay.setBackgroundResource(R.mipmap.xianxiazf);
        }else  if ("微信支付".equals(name)){
            viewHolder.ivItemPay.setBackgroundResource(R.mipmap.wexin);
        }else{
            viewHolder.ivItemPay.setBackgroundResource(R.mipmap.xianxiazf);
        }
        boolean check = dataBean.isCheck();
        if (check){
            viewHolder.tvItemPayCheck.setBackgroundResource(R.mipmap.checkbox_checked);
        }else {
            viewHolder.tvItemPayCheck.setBackgroundResource(R.mipmap.radio_no_select);
        }
        onItemEventClick(viewHolder);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemPayName, tvItemPayCheck;
        ImageView ivItemPay;
        public ViewHolder(View itemView) {
            super(itemView);
            ivItemPay = (ImageView) itemView.findViewById(R.id.iv_ItemPay);
            tvItemPayName = (TextView) itemView.findViewById(R.id.tv_ItemPayName);
            tvItemPayCheck = (TextView) itemView.findViewById(R.id.tv_ItemPayCheck);
        }
    }
    private void onItemEventClick(RecyclerView.ViewHolder holder) {
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
    public OnRcItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

