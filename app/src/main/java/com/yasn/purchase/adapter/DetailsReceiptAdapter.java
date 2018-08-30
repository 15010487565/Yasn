package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;

import java.util.List;
import java.util.Map;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class DetailsReceiptAdapter extends RecyclerView.Adapter<DetailsReceiptAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Map<String,String>> list;
    private Context mContext;
    private int receiptStatus;

    public DetailsReceiptAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    public void setData(List<Map<String,String>> list,int receiptStatus){
        this.list = list;
        this.receiptStatus = receiptStatus;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_detailsreceipt_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        Map<String, String> stringStringMap = list.get(position);

        String keyName = stringStringMap.get("keyName");
        Log.e("TAG_keyName","keyName="+keyName);
        //发票类型，2：普通发票，3为专用发票
        if (receiptStatus == 3) {
            if ("专票资质信息".equals(keyName)){
                holder.tvName.setText("");
                holder.tvName.setBackgroundColor(ContextCompat.getColor(mContext,R.color.line_gray));
                String valueName = stringStringMap.get(keyName);
                holder.tvValue.setBackgroundColor(ContextCompat.getColor(mContext,R.color.line_gray));
                holder.tvValue.setText("专票资质信息"+valueName);
            }else {
                holder.tvName.setText(keyName);
                holder.tvName.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                String valueName = stringStringMap.get(keyName);
                holder.tvValue.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
                holder.tvValue.setText(valueName);
            }
        }else {
            holder.tvName.setText(keyName);
            holder.tvName.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            String valueName = stringStringMap.get(keyName);
            holder.tvValue.setBackgroundResource(R.color.white);
            holder.tvValue.setText(valueName);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvValue;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_DetailsReceiptName);
            tvValue = (TextView) itemView.findViewById(R.id.tv_DetailsReceiptValue);
        }
    }
}
