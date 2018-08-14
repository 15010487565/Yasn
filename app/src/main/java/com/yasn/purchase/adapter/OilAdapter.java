package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.OilParamsModel;

import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class OilAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OilParamsModel> list;
    private OnOilClickListener onItemClickListener;
    private int type;

    public OilAdapter(int type, Context context) {
        this.type = type;
        this.context = context;
    }

    public void setOnItemClickListener(OnOilClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<OilParamsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycleritem_oil, parent, false);
        RecyclerView.ViewHolder holder = new OilViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        OilViewHolder oilViewHolder = (OilViewHolder) holder;
        OilParamsModel oilParamsModel = list.get(position);
        String name = oilParamsModel.getName();
        oilViewHolder.tvOilItem.setText(name == null ? "" : name);
        onItemEventClick(oilViewHolder);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class OilViewHolder extends RecyclerView.ViewHolder {
        TextView tvOilItem;

        public OilViewHolder(View itemView) {
            super(itemView);
            tvOilItem = (TextView) itemView.findViewById(R.id.tv_OilItem);
        }
    }

    public interface OnOilClickListener {
        //选择
        void OnItemClick(int type , int position, List<OilParamsModel> list);
    }

    private void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(type, position, list);
            }
        });

    }
}
