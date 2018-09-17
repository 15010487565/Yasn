package com.yasn.purchase.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.holder.OrderContentHolder;
import com.yasn.purchase.holder.OrderHeaderHolder;
import com.yasn.purchase.holder.OrderShopNameHolder;
import com.yasn.purchase.model.order.OrderDetailsGiftModel;
import com.yasn.purchase.model.order.OrderDetailsPayModel;
import com.yasn.purchase.model.order.OrderGoodsContentModel;
import com.yasn.purchase.model.order.OrderHeaderModel;
import com.yasn.purchase.model.order.OrderShopNameModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_CONTENT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_GIFT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_HEADER;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_PAY;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_SHOPNAME;

/**
 * Created by gs on 2017/12/26.
 */

public class OrderDetailsGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Object> list;
    private int isMainOrder;

    public OrderDetailsGoodsAdapter(Context context,int isMainOrder) {
        this.context = context;
        this.isMainOrder = isMainOrder;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
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

            case ITEM_GIFT://赠品
                view = inflater.inflate(R.layout.item_order_contentgift, parent, false);
                holder = new GiftViewHolder(view);
                break;

            case ITEM_PAY://支付信息
                view = inflater.inflate(R.layout.item_orderdetails_pay, parent, false);
                holder = new OrderDetailsPayHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_HEADER://订单编号布局
                OrderHeaderHolder headerHolder = (OrderHeaderHolder) holder;
                OrderHeaderModel headerModel = (OrderHeaderModel) list.get(position);
                headerHolder.tvOrderNumber.setText(headerModel.getOrderCode());
                headerHolder.tvOrderPayType.setVisibility(View.VISIBLE);
                int status = headerModel.getStatus();
                Log.e("TAG_订单状态adapter","status="+status);
                /**
                 * (0为新建订单，
                 * 货到付款需确认,
                 * 1为已确认,2为已支付,3为已发货,4为已收货,5为已完成,
                 * 6为订单取消（货到付款审核未通过、新建订单取消、订单发货前取消）,
                 * 7为交易成功已申请售后申请,8为待人工推单,9为风控审核,100为已支付待放款,200为放款失败)
                 */
                switch (status) {
                    case 0:
                        headerHolder.tvOrderPayType.setText("");
                        break;
                    case 1:
                        headerHolder.tvOrderPayType.setText("待付款");
                        break;
                    case 2:
                        headerHolder.tvOrderPayType.setText("已付款");
                        break;
                    case 3:
                        headerHolder.tvOrderPayType.setText("已发货");
                        break;
                    case 4:
                        headerHolder.tvOrderPayType.setText("已收货");
                        break;
                    case 5:
                        headerHolder.tvOrderPayType.setText("已完成");
                        break;
                    case 6:
                        headerHolder.tvOrderPayType.setText("已取消");
                        break;
                    case 7:
                        headerHolder.tvOrderPayType.setText("交易完成申请售后");
                        break;
                    case 8:
                        headerHolder.tvOrderPayType.setText("待人工退单");
                        break;
                    case 9:
                        headerHolder.tvOrderPayType.setText("风控审核中");
                        break;
                    case 100:
                        headerHolder.tvOrderPayType.setText("已确认");
                        break;
                    case 200:
                        headerHolder.tvOrderPayType.setText("已确认");
                        break;
                }
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
                //规格
                contentHolder.tlOrderDetails.removeAllViews();
                List<OrderGoodsContentModel.OrderGoodsValueBean> list = contentModel.getList();
                if (list != null && list.size() > 0) {
                    Log.e("TAG_sblist", list.toString());
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    for (int i = 0, j = list.size(); i < j; i++) {
                        OrderGoodsContentModel.OrderGoodsValueBean orderGoodsValueBean = list.get(i);
                        String sprcTextString = orderGoodsValueBean.getValue();
                        Log.e("TAG_sprcTextString", sprcTextString);
                        if (!TextUtils.isEmpty(sprcTextString)) {
                            final TextView tvSprc = new TextView(context.getApplicationContext());
                            tvSprc.setText(sprcTextString);

                            tvSprc.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                            tvSprc.setBackgroundResource(R.drawable.text_n_f5);
                            tvSprc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            tvSprc.setGravity(Gravity.CENTER);
                            lp.setMargins(10, 5, 5, 0);
                            contentHolder.tlOrderDetails.addView(tvSprc, lp);
                            contentHolder.tlOrderDetails.setVisibility(View.VISIBLE);
                        } else {
                            contentHolder.tlOrderDetails.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    contentHolder.tlOrderDetails.setVisibility(View.INVISIBLE);
                }
                onItemEventClick(contentHolder);
                break;
            case ITEM_GIFT://贈品信息
                final GiftViewHolder giftHolder = (GiftViewHolder) holder;
                OrderDetailsGiftModel giftModel = (OrderDetailsGiftModel) this.list.get(position);
                String name = giftModel.getName();
                if (TextUtils.isEmpty(name)){
                    giftHolder.llActivityGift.setVisibility(View.GONE);
                }else {
                    giftHolder.llActivityGift.setVisibility(View.VISIBLE);
                    giftHolder.tvOrderGiftName.setText(name);
                    String giftMoney = giftModel.getMoney();
//                String giftNum = giftModel.getNum();
                    giftHolder.tvOrderGiftNum.setText(giftMoney + "\tx\t" + "1");
                    Glide.with(context.getApplicationContext())
                            .load(giftModel.getImage())
                            .fitCenter()
                            .dontAnimate()
                            .placeholder(R.mipmap.errorimage)
                            .error(R.mipmap.errorimage)
                            .into(giftHolder.ivOrderGift);
                }
                String moreBuyToSend = giftModel.getMoreBuyToSend();
                if (TextUtils.isEmpty(moreBuyToSend)||"[]".equals(moreBuyToSend)){
                    giftHolder.llMoreBuyToSend.setVisibility(View.GONE);
                }else {
                    giftHolder.llMoreBuyToSend.setVisibility(View.VISIBLE);
                    String[] splitArr = moreBuyToSend.split(",");
                    String goods_gift_num = "0";
                    for (int i = 0; i < splitArr.length ; i++) {
                        String splitObj = splitArr[i];
                        int indexOfNum = splitObj.indexOf("goods_gift_num");
                        String s = splitObj.replaceAll("[\\[\\]]", "").replaceAll("[\"\\{\\}]", "");
                        String[] splitNum = s.split(":");
                        if (indexOfNum !=-1){
                            goods_gift_num = splitNum[1];
                            Log.e("TAG_赠品多买多增","goods_gift_num="+goods_gift_num);
                        }
                        int indexOfId = splitObj.indexOf("goods_gift_id");
                        if (indexOfId !=-1){
                            String[] splitId = s.split(":");
                            String goods_gift_id = splitId[1];
                            Log.e("TAG_赠品多买多增","goods_gift_id="+goods_gift_id);
                            DownGift downGift =new DownGift(giftHolder,goods_gift_num);
                            downGift.execute(Config.ORDERDETAILSGIFT + goods_gift_id);
                        }
                    }
                }
                break;

            case ITEM_PAY://支付信息
                OrderDetailsPayHolder payHolder = (OrderDetailsPayHolder) holder;
                OrderDetailsPayModel payInfoModel = (OrderDetailsPayModel) this.list.get(position);
                //商品总额
                String goodsAmount = payInfoModel.getGoodsAmount();
                payHolder.tvGoodsMoney.setText(goodsAmount);
                //积分抵现
                String deductMoney = payInfoModel.getDeductMoney();
                payHolder.tvDeductMoney.setText(deductMoney);
                //运费总额
                String shippingTotal1 = payInfoModel.getShippingTotal();
                payHolder.tvShippingTotal.setText(shippingTotal1);
                //小计
                payHolder.tvNeedPayMoney.setText(payInfoModel.getNeedPayMoney());

                int payStatus = payInfoModel.getStatus();
                if (isMainOrder == 1){
                    payHolder.llAlert.setVisibility(View.VISIBLE);
                }else if (isMainOrder == 2){
                    payHolder.llAlert.setVisibility(View.GONE);
                }
                switch (payStatus) {
                    case 0:
                        break;
                    case 1://待付款
                        payHolder.tvDetailsApply.setVisibility(View.GONE);//申请售后
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);//提醒发货
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);//查看物流
                        payHolder.tvDetailsOk.setVisibility(View.GONE);//确认收货
                        break;
                    case 2://已付款
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.VISIBLE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 3://已发货
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.VISIBLE);
                        payHolder.tvDetailsOk.setVisibility(View.VISIBLE);
                        break;
                    case 4://已收货
                        payHolder.tvDetailsApply.setVisibility(View.VISIBLE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 5://已完成
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 6://已取消
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 7://交易完成申请售后
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 8://待人工退单
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 9://风控审核中
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 100://已确认
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                    case 200://已确认
                        payHolder.tvDetailsApply.setVisibility(View.GONE);
                        payHolder.tvDetailsRemind.setVisibility(View.GONE);
                        payHolder.tvDetailsLookOver.setVisibility(View.GONE);
                        payHolder.tvDetailsOk.setVisibility(View.GONE);
                        break;
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object o = list.get(position);
        if (o instanceof OrderHeaderModel) {//订单编号
            return ITEM_HEADER;
        } else if (o instanceof OrderShopNameModel) {//店铺名称
            return ITEM_SHOPNAME;
        } else if (o instanceof OrderGoodsContentModel) {//商品详情
            return ITEM_CONTENT;
        } else if (o instanceof OrderDetailsGiftModel) {//赠品
            return ITEM_GIFT;
        } else if (o instanceof OrderDetailsPayModel) {//支付信息
            return ITEM_PAY;
        } else {
            return ITEM_HEADER;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKeyName, tvVlaueName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvKeyName = (TextView) itemView.findViewById(R.id.tv_orderDetailsKeyName);
            tvVlaueName = (TextView) itemView.findViewById(R.id.tv_orderDetailsVlaueName);
        }
    }

    class GiftViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llActivityGift, llMoreBuyToSend;
        public ImageView ivOrderGift;
        public TextView tvOrderGiftName;
        public TextView tvOrderGiftNum;
        public TextView tvorderMoreBuyToSend;
        public GiftViewHolder(View view) {
            super(view);
            llActivityGift = (LinearLayout) view.findViewById(R.id.ll_ActivityGift);
            llMoreBuyToSend = (LinearLayout) view.findViewById(R.id.ll_MoreBuyToSend);
            ivOrderGift = (ImageView) view.findViewById(R.id.iv_orderGift);
            tvOrderGiftName = (TextView) view.findViewById(R.id.tv_orderGiftName);
            tvOrderGiftNum = (TextView) view.findViewById(R.id.tv_orderGiftNum);
            tvorderMoreBuyToSend = (TextView) view.findViewById(R.id.tv_orderMoreBuyToSend);
        }
    }

    class OrderDetailsPayHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvGoodsMoney, tvDeductMoney, tvShippingTotal, tvNeedPayMoney;
        TextView tvDetailsApply,tvDetailsRemind, tvDetailsLookOver, tvDetailsOk;
        private LinearLayout llAlert;
        public OrderDetailsPayHolder(View itemView) {
            super(itemView);
            tvGoodsMoney = (TextView) itemView.findViewById(R.id.tv_orderDetailsGoodsMoney);
            tvDeductMoney = (TextView) itemView.findViewById(R.id.tv_orderDetailsDeductMoney);
            tvShippingTotal = (TextView) itemView.findViewById(R.id.tv_orderDetailsShippingTotal);
            tvNeedPayMoney = (TextView) itemView.findViewById(R.id.tv_NeedPayMoney);
            //申请售后
            tvDetailsApply = (TextView) itemView.findViewById(R.id.tv_DetailsApply);
            tvDetailsApply.setOnClickListener(this);
            //提醒发货
            tvDetailsRemind = (TextView) itemView.findViewById(R.id.tv_DetailsRemind);
            tvDetailsRemind.setOnClickListener(this);
            //查看物流
            tvDetailsLookOver = (TextView) itemView.findViewById(R.id.tv_DetailsLookOver);
            tvDetailsLookOver.setOnClickListener(this);
            //确认收货
            tvDetailsOk = (TextView) itemView.findViewById(R.id.tv_DetailsOk);
            tvDetailsOk.setOnClickListener(this);
            //
            llAlert = (LinearLayout) itemView.findViewById(R.id.ll_alert);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_DetailsApply://申请售后
                    onItemClickListener.OnDetailsApplyClick(view,getLayoutPosition());
                    break;
                case R.id.tv_DetailsRemind://提醒发货
                    String s = tvDetailsRemind.getText().toString();
                    if ("提醒物流".equals(s)){
                        onItemClickListener.OnDetailsRemindClick(view,getLayoutPosition(),1);
                    }else if ("提醒发货".equals(s)){
                        onItemClickListener.OnDetailsRemindClick(view,getLayoutPosition(),2);
                    }
                    break;
                case R.id.tv_DetailsLookOver://查看物流
                    onItemClickListener.OnDetailsLookOverClick(view,getLayoutPosition());
                    break;
                case R.id.tv_DetailsOk://确认收货
                    onItemClickListener.OnDetailsOkClick(view,getLayoutPosition());
                    break;
            }
        }
    }
    public OnRcOrderDetailsClickListener onItemClickListener;
    public void setOnItemClickListener(OnRcOrderDetailsClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnRcOrderDetailsClickListener {
        void OnDetailsApplyClick(View view, int position);
        void OnDetailsRemindClick(View view, int position ,int type);
        void OnDetailsLookOverClick(View view, int position);
        void OnDetailsOkClick(View view, int position);
        void OnItemClick(View view, int position);
    }
    private void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v,position);
            }
        });
    }
    class DownGift extends AsyncTask<String, Integer, String> {

        private GiftViewHolder giftHolder;
        private String num;
        public DownGift(GiftViewHolder giftHolder,String num) {
            this.giftHolder = giftHolder;
            this.num = num;
            Log.e("TAG_赠品","num="+num);
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            String giftName = null;
            try {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(path).build();
                Response response=client.newCall(request).execute();
                String result = response.body().string();
                JSONObject object = new JSONObject(result);
                giftName = object.optString("giftName");
                Log.e("TAG_获取赠品多买多得","giftName="+giftName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return giftName;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("TAG_赠品","num="+num);
            giftHolder.tvorderMoreBuyToSend.setText(result+"\t\t*\t\t"+num);
        }

    }

}

