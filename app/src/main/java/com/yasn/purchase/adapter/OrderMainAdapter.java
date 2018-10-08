package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.holder.FootViewHolder;
import com.yasn.purchase.holder.OrderContentHolder;
import com.yasn.purchase.holder.OrderHeaderHolder;
import com.yasn.purchase.holder.OrderShopNameHolder;
import com.yasn.purchase.listener.OnRcOrderItemClickListener;
import com.yasn.purchase.model.order.OrderGoodsContentModel;
import com.yasn.purchase.model.order.OrderHeaderModel;
import com.yasn.purchase.model.order.OrderMainPayInfoModel;
import com.yasn.purchase.model.order.OrderShopNameModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_CONTENT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_HEADER;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_PAY;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_SHOPNAME;

/**
 * Created by Administrator on 2016/11/9.
 */

public class OrderMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> list;
    private List<Object> addList;
    private LayoutInflater inflater;
    private LinearLayoutManager linearLayoutManager;
    private Map viewHolderMap = new HashMap<>();

    public OrderMainAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Object> list) {
        this.list = list;
        this.addList = list;
        notifyDataSetChanged();
    }

    public List<Object>  getData() {

       return  list;
    }

    public void addData(List<Object> list) {

        this.addList = list;
        if (this.list != null) {
            if (list !=null && list.size() > 0){
                this.list.addAll(list);
            }
        } else {
            this.list = list;
        }
        notifyDataSetChanged();
    }
    public void cleanData(){
        this.list.clear();
        this.addList.clear();
        notifyDataSetChanged();
    }
    private Map getViewHolderMap() {
        return viewHolderMap;
    }
    public void upFootText(){
        Map viewHolderMap = getViewHolderMap();
        FootViewHolder holder = (FootViewHolder) viewHolderMap.get("holder");
        holder.progressBar.setVisibility(View.GONE);
        holder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case ITEM_HEADER://订单编号布局
                view = inflater.inflate(R.layout.item_order_header, parent, false);
                holder = new OrderHeaderHolder(view);
                break;

            case ITEM_SHOPNAME://店铺名称
                view = inflater.inflate(R.layout.item_order_shopname, parent, false);
                holder = new OrderShopNameHolder(view);
                break;

            case ITEM_CONTENT://商品信息
                view = inflater.inflate(R.layout.item_order_content, parent, false);
                holder = new OrderContentHolder(view);
                break;

            case ITEM_PAY://支付信息
                view = inflater.inflate(R.layout.item_ordermain_pay, parent, false);
                holder = new OrderPayHolder(view);
                break;

            case ITEM_FOOTER:
                view = inflater.inflate(R.layout.item_foot, parent, false);
                holder = new FootViewHolder(view);
                viewHolderMap.put("holder", holder);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_HEADER://订单编号布局
                OrderHeaderHolder headerHolder = (OrderHeaderHolder) holder;
                OrderHeaderModel headerModel = (OrderHeaderModel) list.get(position);
                headerHolder.tvOrderNumber.setText(headerModel.getOrderCode());
                break;

            case ITEM_SHOPNAME://店铺名称
                OrderShopNameHolder shopNameHolder = (OrderShopNameHolder) holder;
                OrderShopNameModel shopNameModel = (OrderShopNameModel) list.get(position);
                shopNameHolder.tvOrderShopName.setText(shopNameModel.getShopName());
                break;

            case ITEM_CONTENT://商品信息
                OrderContentHolder contentHolder = (OrderContentHolder) holder;
                OrderGoodsContentModel contentModel = (OrderGoodsContentModel) list.get(position);
                contentHolder.tvOrderContentName.setText(contentModel.getName());
                String price = contentModel.getPrice();
                int num = contentModel.getNum();
                contentHolder.tvOrderContentNum.setText(price + "\tx\t" + num);
                Glide.with(context.getApplicationContext())
                        .load(contentModel.getImage())
                        .fitCenter()
                        .dontAnimate()
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(contentHolder.ivOrderContent);
                onItemEventClick(contentHolder);
                break;

            case ITEM_PAY://支付信息
                OrderPayHolder payHolder = (OrderPayHolder) holder;
                OrderMainPayInfoModel payInfoModel = (OrderMainPayInfoModel) list.get(position);
                //需要支付金额
                payHolder.tvNeedPayMoney.setText("￥" + payInfoModel.getNeedPayMoney());
                //运费
                String shippingTotal = payInfoModel.getShippingTotal();
//                if (TextUtils.isEmpty(shippingTotal) || Double.valueOf(shippingTotal) <= 0) {
//                    payHolder.tvTotalMoney.setText("0.00");
//                    payHolder.tvTotalMoney.setVisibility(View.GONE);
//                } else {
                    payHolder.tvTotalMoney.setText("\t(包含运费:￥" + shippingTotal + ")");
//                    payHolder.tvTotalMoney.setVisibility(View.VISIBLE);
//                }
                //立即支付
                boolean needPay = payInfoModel.isNeedPay();
                if (needPay){
//                    payHolder.llPay.setVisibility(View.VISIBLE);
                    payHolder.tvPayMoney.setVisibility(View.VISIBLE);
                }else {
//                    payHolder.llPay.setVisibility(View.GONE);
                    payHolder.tvPayMoney.setVisibility(View.GONE);
                }
                break;

            case ITEM_FOOTER:
                FootViewHolder footholder = (FootViewHolder) holder;
                if (list == null || list.size() == 0) {
                    footholder.footView.setVisibility(View.GONE);
                } else {
                    footholder.footView.setVisibility(View.VISIBLE);
                    if (addList == null || addList.size() == 0) {
                        footholder.progressBar.setVisibility(View.GONE);
                        footholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                    } else {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        if (visibleItemCount <= list.size()) {
                            footholder.progressBar.setVisibility(View.GONE);
                            footholder.footText.setText(context.getResources().getString(R.string.pullup_to_load));
                        } else {
                            footholder.progressBar.setVisibility(View.GONE);
                            footholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                        }
                    }
                }

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_FOOTER;
        } else {
            Object o = list.get(position);
            if (o instanceof OrderHeaderModel) {
                return ITEM_HEADER;
            } else if (o instanceof OrderShopNameModel) {
                return ITEM_SHOPNAME;
            } else if (o instanceof OrderGoodsContentModel) {
                return ITEM_CONTENT;
            } else if (o instanceof OrderMainPayInfoModel) {
                return ITEM_PAY;
            } else {
                return ITEM_FOOTER;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = (list == null ? 0 : (list.size() + 1));
        return count;
    }

    class OrderPayHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNeedPayMoney, tvTotalMoney, tvLookOrder, tvPayMoney;
        private LinearLayout llPay;
        public OrderPayHolder(View view) {
            super(view);
            llPay = (LinearLayout) view.findViewById(R.id.ll_Pay);
            tvNeedPayMoney = (TextView) view.findViewById(R.id.tv_NeedPayMoney);
            tvTotalMoney = (TextView) view.findViewById(R.id.tv_totalMoney);
            tvLookOrder = (TextView) view.findViewById(R.id.tv_LookOrder);
            tvLookOrder.setOnClickListener(this);
            tvPayMoney = (TextView) view.findViewById(R.id.tv_PayMoney);
            tvPayMoney.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_LookOrder://查看订单
                    onItemClickListener.OnLookOrderClick(getLayoutPosition());
                    break;
                case R.id.tv_PayMoney://立即支付
                    onItemClickListener.OnPayMoneyClick(getLayoutPosition());
                    break;
            }
        }
    }

    private OnRcOrderItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnRcOrderItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnLookOrderClick(position);
            }
        });
    }
}
