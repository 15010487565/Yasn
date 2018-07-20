package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.StatisticsModel;

import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<StatisticsModel.DataBean.SumInfoEachMonthBean> list;

    public StatisticsAdapter(Context context) {
        super();
        this.context = context;
    }


    public void setData(List<StatisticsModel.DataBean.SumInfoEachMonthBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_statistics, parent, false);
        RecyclerView.ViewHolder holder = new StatisViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        StatisViewHolder holderHolder = (StatisViewHolder) holder;
        StatisticsModel.DataBean.SumInfoEachMonthBean sumInfoEachMonthBean = list.get(position);
        //时间
        String statisticsMonth = sumInfoEachMonthBean.getStatisticsMonth();
        holderHolder.statisticTime.setText(statisticsMonth);
        //采购金额
        double totalCount = sumInfoEachMonthBean.getTotalCount();
        holderHolder.stotalMoney.setText("￥" + String.format("%.2f", totalCount));
        //商品数
        int goodsNum = sumInfoEachMonthBean.getGoodsNum();
        holderHolder.statisGoodsNum.setText(goodsNum + "件");
        //订单数
        int orderNum = sumInfoEachMonthBean.getOrderNum();
        holderHolder.statisOrderNum.setText(orderNum + "笔");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class StatisViewHolder extends RecyclerView.ViewHolder {

        private TextView statisticTime;
        private TextView stotalMoney, statisGoodsNum, statisOrderNum;
        public StatisViewHolder(View view) {
            super(view);
            statisticTime = (TextView) itemView.findViewById(R.id.tv_StatisticTime_item);
            stotalMoney = (TextView) itemView.findViewById(R.id.tv_StatisticStotalMoney_item);
            statisGoodsNum = (TextView) itemView.findViewById(R.id.tv_StatisticGoodsNum_item);
            statisOrderNum = (TextView) itemView.findViewById(R.id.tv_StatisticOrderNum_item);
        }
    }
}
