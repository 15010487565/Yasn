package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.model.order.GoodsOrderInfo;
import com.yasn.purchase.model.order.OrderGoodsItem;
import com.yasn.purchase.model.order.OrderPayInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> data;
    private View headerView;
    private int ITEM_HEADER = 1,ITEM_CONTENT = 2,ITEM_FOOTER = 3;

    public OrderAdapter(Context context, List<Object> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == ITEM_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_header, parent, false);
            return new MyViewHolderHeader(view);
        }else if(viewType == ITEM_CONTENT){
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_content, parent, false);
            return new MyViewHolderContent(view);
        }else if(viewType == ITEM_FOOTER){
            view = LayoutInflater.from(context).inflate(R.layout.item_allorder_footer, parent, false);
            return new MyViewHolderFooter(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolderHeader){
            GoodsOrderInfo datas = (GoodsOrderInfo)data.get(position);
            ((MyViewHolderHeader) holder).tvAllOrderItemid.setText("订单编号：" + datas.getOrderCode());
            ((MyViewHolderHeader) holder).tvAllOrderItemShopName.setText(datas.getShopName());
            if(datas.getStatus().equals("0")){
                ((MyViewHolderHeader) holder).tvAllOrderItemState.setText("待付款");
            }else if(datas.getStatus().equals("1")){
                ((MyViewHolderHeader) holder).tvAllOrderItemState.setText("待发货");
            }else if(datas.getStatus().equals("2")){
                ((MyViewHolderHeader) holder).tvAllOrderItemState.setText("待收货");
            }else if(datas.getStatus().equals("3")){
                ((MyViewHolderHeader) holder).tvAllOrderItemState.setText("交易完成");
            }
        }else if(holder instanceof MyViewHolderContent) {
            OrderGoodsItem datas = (OrderGoodsItem)data.get(position);
            Glide.with(context).load(datas.getProductPic())
                    .into(((MyViewHolderContent) holder).ivAllOrderItemPic);
            ((MyViewHolderContent) holder).tvAllOrderItemTitle.setText(datas.getProductName());
            ((MyViewHolderContent) holder).tvAllOrderItemNum.setText("共" + datas.getCount() + "件");
            ((MyViewHolderContent) holder).tvAllOrderItemPrice.setText("¥" + datas.getTotalPrice());

            final int pos = datas.getOrderid();

            ((MyViewHolderContent) holder).llAllOrderItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    //类型是确认订单
                    bundle.putString("mType", "orderdetail");
                    bundle.putInt("mOrderId",pos);
                    intent.putExtras(bundle);
                    context.startActivity(intent);*/
                }
            });

        }else if(holder instanceof MyViewHolderFooter) {
            OrderPayInfo datas = (OrderPayInfo)data.get(position);
            ((MyViewHolderFooter) holder).tvAllOrderTotal.setText(datas.getTotalAmount() + "");
            final int pos = datas.getId();
            if(datas.getStatus().equals("0")){
                ((MyViewHolderFooter) holder).tvAllOrderSubmit.setText("付款");
            }else {
                ((MyViewHolderFooter) holder).tvAllOrderSubmit.setText("商品详情");
            }

            ((MyViewHolderFooter) holder).tvAllOrderSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(context, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    //类型是确认订单
                    bundle.putString("mType", "orderdetail");
                    bundle.putInt("mOrderId",pos);
                    intent.putExtras(bundle);
                    context.startActivity(intent);*/
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position) instanceof GoodsOrderInfo) {
            return ITEM_HEADER;
        }else if(data.get(position) instanceof OrderGoodsItem){
            return ITEM_CONTENT;
        }else if(data.get(position) instanceof OrderPayInfo){
            return ITEM_FOOTER;
        }
        return ITEM_CONTENT;
    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if(headerView != null){
            count++;
        }
        return count;
    }


    class MyViewHolderContent extends RecyclerView.ViewHolder
    {
        private ImageView ivAllOrderItemPic;
        private TextView tvAllOrderItemTitle;
        private TextView tvAllOrderItemPrice;
        private LinearLayout llAllOrderItem;
        private TextView tvAllOrderItemNum;

        public MyViewHolderContent(View view)
        {
            super(view);
            llAllOrderItem = (LinearLayout) view.findViewById(R.id.ll_item_allorder);
            ivAllOrderItemPic = (ImageView) view.findViewById(R.id.iv_item_allorder_pic);
            tvAllOrderItemTitle = (TextView) view.findViewById(R.id.tv_item_allorder_title);
            tvAllOrderItemPrice = (TextView) view.findViewById(R.id.tv_item_allorder_item_price);
            tvAllOrderItemNum = (TextView) view.findViewById(R.id.tv_item_allorder_item_num);
        }
    }

    class MyViewHolderHeader extends RecyclerView.ViewHolder
    {
        private LinearLayout llAllOrderItem;
        private TextView tvAllOrderItemState,tvAllOrderItemid,tvAllOrderItemShopName;

        public MyViewHolderHeader(View view)
        {
            super(view);
            llAllOrderItem = (LinearLayout) view.findViewById(R.id.ll_item_allorder_header);
            tvAllOrderItemid = (TextView) view.findViewById(R.id.tv_item_allorder_orderid);
            tvAllOrderItemShopName = (TextView) view.findViewById(R.id.tv_item_allorder_shopname);
            tvAllOrderItemState = (TextView) view.findViewById(R.id.tv_item_allorder_state);
        }
    }

    class MyViewHolderFooter extends RecyclerView.ViewHolder
    {
        private TextView tvAllOrderTotal,tvAllOrderSubmit;

        public MyViewHolderFooter(View view)
        {
            super(view);
            tvAllOrderTotal = (TextView) view.findViewById(R.id.tv_item_allorder_total);
            tvAllOrderSubmit = (TextView) view.findViewById(R.id.tv_item_allorder_submit);
        }
    }


    public View getHeaderView(){
        return headerView;
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
