package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.GoodsDetailsModel;

import java.util.List;

/**
 * Created by gs on 2018/1/3.
 */

public class TradePriceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean> ladderPrices;
    private OnRcItemClickListener onItemClickListener;
    private Context context;

    public TradePriceAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean> ladderPrices) {
        this.ladderPrices = ladderPrices;
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
        View view = mInflater.inflate(R.layout.recycleritem_tradeprice, parent, false);
        //view.setBackgroundColor(Color.RED);
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
        ViewHolder tradePriceHolder = (ViewHolder) holder;

        GoodsDetailsModel.GoodsDetailsBean.ProductsBean.LadderPricesBean ladderPricesBean = ladderPrices.get(position);
        int minNum = ladderPricesBean.getMinNum();
        int maxNum = ladderPricesBean.getMaxNum();
//        if (maxNum ==2000000000){
//            tradePriceHolder.topNumber.setText(">="+minNum);
//        }else {
            tradePriceHolder.topNumber.setText(minNum + "~" + maxNum);
//        }
        double wholesalePrice = ladderPricesBean.getWholesalePrice();
        tradePriceHolder.tradePrice.setText(String.valueOf(wholesalePrice));
//        onItemEventClick(tradePriceHolder);
    }

    @Override
    public int getItemCount() {
        return ladderPrices == null ? 0 : ladderPrices.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topNumber;
        public TextView tradePrice;

        public ViewHolder(View view) {
            super(view);
            topNumber = (TextView) view.findViewById(R.id.topNumber);
            tradePrice = (TextView) view.findViewById(R.id.tradePrice);
        }

    }

    protected void onItemEventClick(final RecyclerView.ViewHolder holder) {
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
