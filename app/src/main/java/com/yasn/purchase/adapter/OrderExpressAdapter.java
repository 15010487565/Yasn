package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.order.OrderQueryExpressModel;

import java.util.List;

/**
 * Created by gs on 2018/1/3.
 */

public class OrderExpressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private  List<OrderQueryExpressModel.DeliverysBean.ExpressDetailBean.DataBean> list;
    private OnRcItemClickListener onItemClickListener;
    private Context context;
    public final static int TYPE_TOP = 1;
    public final static int TYPE_SURPLUS = 2;
    public final static int TYPE_FOOT = 3;

    public OrderExpressAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData( List<OrderQueryExpressModel.DeliverysBean.ExpressDetailBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_TOP:
                view = inflater.inflate(R.layout.recycleritem_orderexpresstop, parent, false);
                break;
            case TYPE_SURPLUS:
                view = inflater.inflate(R.layout.recycleritem_orderexpress, parent, false);
                break;
            case TYPE_FOOT:
                view = inflater.inflate(R.layout.recycleritem_orderexpressfoot, parent, false);
                break;
        }
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("TAG_OrderExpressAdapter", "onBindViewHolder");
        ViewHolder viewHolder = (ViewHolder) holder;
        OrderQueryExpressModel.DeliverysBean.ExpressDetailBean.DataBean dataBean = list.get(position);
        String time = dataBean.getTime();
        if (time.indexOf(" ")!=-1){
            String[] split = time.split(" ");
            viewHolder.tvTopTime.setText(split[0]);
            viewHolder.tvBelowTime.setText(split[1]);
        }
        String contextString = dataBean.getContext();
        if (contextString.indexOf("【")!=-1){
            contextString = contextString.replace("【", "[");
            Log.e("TAG_替换","【="+contextString);

        }
        if (contextString.indexOf("】")!=-1){
            contextString = contextString.replace("】", "]");
            Log.e("TAG_替换","】="+contextString);
        }
        viewHolder.tvContent.setText(contextString);
        if (position== 0){
            viewHolder.tvContent.setTextColor(ContextCompat.getColor(context,R.color.orange));
        }else {
            viewHolder.tvContent.setTextColor(ContextCompat.getColor(context,R.color.black_33));
        }
        if (position%2 == 0){
            viewHolder.llOrderExpressItem.setBackgroundColor(ContextCompat.getColor(context,R.color.white));

        }else {
            viewHolder.llOrderExpressItem.setBackgroundColor(ContextCompat.getColor(context,R.color.black_f7));
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_TOP;
        }else if (position == list.size()-1){
            return TYPE_FOOT;
        }else {
            return TYPE_SURPLUS;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTopTime,tvBelowTime,tvContent;
        ImageView ivConter;
        LinearLayout llOrderExpressItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTopTime = (TextView) itemView.findViewById(R.id.tv_OrderExpressTopTime);
            tvBelowTime = (TextView) itemView.findViewById(R.id.tv_OrderExpressBelowTime);
            tvContent = (TextView) itemView.findViewById(R.id.tv_OrderExpressContent);
            ivConter = (ImageView) itemView.findViewById(R.id.iv_OrderExpressConter);
            llOrderExpressItem = (LinearLayout) itemView.findViewById(R.id.ll_OrderExpressItem);
        }
    }
}
