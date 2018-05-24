package com.yasn.purchasetest.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchasetest.R;
import com.yasn.purchasetest.listener.OnRcOrderItemClickListener;
import com.yasn.purchasetest.model.order.OrderGoodsContentModel;
import com.yasn.purchasetest.model.order.OrderHeaderModel;
import com.yasn.purchasetest.model.order.OrderShopNameModel;
import com.yasn.purchasetest.model.order.OrderSonPayInfoModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */

public class OrderSonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> list;
    private List<Object> addList;
    private static final int ITEM_HEADER = 0,ITEM_SHOPNAME = 1, ITEM_CONTENT = 2, ITEM_PAY = 3, ITEM_FOOTER = 4;
    private LayoutInflater inflater;
    private LinearLayoutManager linearLayoutManager;
    private Map viewHolderMap = new HashMap<>();

    public OrderSonAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
        inflater = LayoutInflater.from(context);
    }
    public void setData(List<Object> list){
        this.list = list;
        this.addList = list;
        notifyDataSetChanged();
    }

    public void addData( List<Object> list) {
        this.addList = list;
        if (this.list != null){
            this.list.addAll(list);
        }else {
            this.list = list;
        }
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

        switch (viewType){
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
                view = inflater.inflate(R.layout.item_orderson_pay, parent, false);
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
        switch (itemViewType){
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
                contentHolder.tvOrderContentNum.setText(price+"\tx\t"+num);
                Glide.with(context.getApplicationContext())
                        .load(contentModel.getImage())
                        .fitCenter()
                        .dontAnimate()
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(contentHolder.ivOrderContent);
                break;

            case ITEM_PAY://支付信息
                OrderPayHolder footerHolder = (OrderPayHolder) holder;
                OrderSonPayInfoModel payInfoModel = (OrderSonPayInfoModel) list.get(position);
                break;

            case ITEM_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;

                if (list == null||list.size()==0){
                    footviewholder.footView.setVisibility(View.GONE);
                }else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    if (addList == null||addList.size()==0){
                        footviewholder.progressBar.setVisibility(View.GONE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                    }else {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        if (visibleItemCount != list.size()){
                            footviewholder.progressBar.setVisibility(View.GONE);
                            footviewholder.footText.setText(context.getResources().getString(R.string.pullup_to_load));
                        }else {
                            footviewholder.progressBar.setVisibility(View.GONE);
                            footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
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
            if ( o instanceof OrderHeaderModel) {
                return ITEM_HEADER;
            }else if (o instanceof OrderShopNameModel) {
                return ITEM_SHOPNAME;
            } else if (o instanceof OrderGoodsContentModel) {
                return ITEM_CONTENT;
            } else if (o instanceof OrderSonPayInfoModel) {
                return ITEM_PAY;
            }else {
                return ITEM_FOOTER;
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = (list == null ? 0 : (list.size()+1));
        return count;
    }

    class OrderHeaderHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderNumber;
        public OrderHeaderHolder(View view) {
            super(view);
            tvOrderNumber = (TextView) view.findViewById(R.id.tv_orderNumber);
        }
    }

    class OrderShopNameHolder extends RecyclerView.ViewHolder {

        private TextView tvOrderShopName;

        public OrderShopNameHolder(View view) {
            super(view);
            tvOrderShopName = (TextView) view.findViewById(R.id.tv_orderShopName);
        }
    }

    class OrderContentHolder extends RecyclerView.ViewHolder {

        private ImageView ivOrderContent;
        private TextView tvOrderContentName;
        private TextView tvOrderContentNum;

        public OrderContentHolder(View view) {
            super(view);
            ivOrderContent = (ImageView) view.findViewById(R.id.iv_orderContent);
            tvOrderContentName = (TextView) view.findViewById(R.id.tv_orderContentName);
            tvOrderContentNum = (TextView) view.findViewById(R.id.tv_orderContentNum);
        }
    }

    class OrderPayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvLookOrder,tvLookMainOrder;

        public OrderPayHolder(View view) {
            super(view);
            tvLookOrder = (TextView) view.findViewById(R.id.tv_LookOrder);
            tvLookOrder.setOnClickListener(this);
            tvLookMainOrder = (TextView) view.findViewById(R.id.tv_LookMainOrder);
            tvLookMainOrder.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_LookOrder://查看订单
                    onItemClickListener.OnLookOrderClick(getLayoutPosition());
                    break;
                case R.id.tv_LookMainOrder://立即支付
                    onItemClickListener.OnLookMainOrderClick(getLayoutPosition());
                    break;
            }
        }
    }
    class FootViewHolder  extends RecyclerView.ViewHolder{
        LinearLayout footView;
        ProgressBar progressBar;
        TextView footText;
        public FootViewHolder(View view) {
            super(view);
            footView = (LinearLayout) itemView.findViewById(R.id.footView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            footText = (TextView) itemView.findViewById(R.id.footText);
        }
    }

    private OnRcOrderItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnRcOrderItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
