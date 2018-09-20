package com.yasn.purchase.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.model.order.OrderDetailsGiftModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_GIFT;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ITEMTOTAL;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ONE;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_TWO;

/**
 * Created by gs on 2017/12/29.
 */

public class ShopCarPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> list;
    private OnShopCarClickListener onItemClickListener;
    private String place = " ";
    private int placeNum = 3;

    public ShopCarPayAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setOnItemClickListener(OnShopCarClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        Object o = list.get(position);
        if (o instanceof ShopCarAdapterModel) {//订单编号
            ShopCarAdapterModel adapterModel = (ShopCarAdapterModel) o;
            int itmeType = adapterModel.getItmeType();
            if (itmeType == 1) {
                return TYPE_ONE;
            } else if (itmeType == 2){
                return TYPE_TWO;
            }else {
                return TYPE_ITEMTOTAL;
            }
        } else  if (o instanceof OrderDetailsGiftModel){
            return ITEM_GIFT;
        }else {
            return TYPE_ITEMTOTAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_shopcarpaytitle, parent, false);
                holder = new TitleViewHolder(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_shopcarpaylist, parent, false);
                holder = new ListViewHolder(view);
                break;
            case ITEM_GIFT://赠品
                view =  LayoutInflater.from(context).inflate(R.layout.item_pay_contentgift, parent, false);
                holder = new GiftViewHolder(view);
                break;

            case TYPE_ITEMTOTAL:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_shopcarpaytotal, parent, false);
                holder = new TotalViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_ONE:
                ShopCarAdapterModel titleModel = (ShopCarAdapterModel) list.get(position);
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                String storeName = titleModel.getStoreName();
                titleViewHolder.tvStoreName.setText(storeName == null ? "" : storeName);
                break;
            case TYPE_TWO:
                ShopCarAdapterModel listModel = (ShopCarAdapterModel) list.get(position);
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                String name = listModel.getName();
                listViewHolder.tvShopCarPayName.setText(name);
                //价格
                double price = listModel.getPrice();
                //数量
                int num = listModel.getNum();
                listViewHolder.tvShopCarPayNum.setText("￥" + String.format("%.2f", price <= 0 ? 0.00 : price) +"x"+num);
                //需要金额
                double needPayMoney = listModel.getNeedPayMoney();
                listViewHolder.tvShopCarPayNeedMoney.setText("￥" + String.format("%.2f", needPayMoney <= 0 ? 0.00 : needPayMoney));
                //規格
                String specs = listModel.getSpecs();
                if (TextUtils.isEmpty(specs)){
                    listViewHolder.tvShopCarPaySpecs.setText("");
                }else {
                    listViewHolder.tvShopCarPaySpecs.setText(specs);
                }
                String imageDefault = listModel.getImageDefault();
                Glide.with(context.getApplicationContext())
                        .load(imageDefault)
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(listViewHolder.ivShopCarPayImage);

                break;
            case ITEM_GIFT://贈品信息
                OrderDetailsGiftModel giftModel = (OrderDetailsGiftModel) list.get(position);
                final GiftViewHolder giftHolder = (GiftViewHolder) holder;
                String nameGift = giftModel.getName();
                if (TextUtils.isEmpty(nameGift)){
                    giftHolder.llActivityGift.setVisibility(View.GONE);
                }else {
                    giftHolder.llActivityGift.setVisibility(View.VISIBLE);
                    giftHolder.tvOrderGiftName.setText(nameGift);
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
            case TYPE_ITEMTOTAL:
                ShopCarAdapterModel totalModel = (ShopCarAdapterModel) list.get(position);

                TotalViewHolder totalViewHolder = (TotalViewHolder) holder;
                double shippingTotal = totalModel.getShippingTotal();
                totalViewHolder.tvShopCarPayToal.setText("￥" + String.format("%.2f", shippingTotal));
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class TitleViewHolder extends RecyclerView.ViewHolder{
        TextView tvStoreName;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tvStoreName = (TextView) itemView.findViewById(R.id.tv_ShopCarPayStoreName);
        }
    }
    class TotalViewHolder extends RecyclerView.ViewHolder{
        TextView tvShopCarPayToal;

        public TotalViewHolder(View itemView) {
            super(itemView);
            tvShopCarPayToal = (TextView) itemView.findViewById(R.id.tv_ShopCarPayToal);
        }
    }
    class ListViewHolder extends RecyclerView.ViewHolder{

        ImageView ivShopCarPayImage;
        private TextView tvShopCarPayName,tvShopCarPayNum,tvShopCarPayNeedMoney, tvShopCarPaySpecs;

        public ListViewHolder(View itemView) {
            super(itemView);
            ivShopCarPayImage = (ImageView) itemView.findViewById(R.id.iv_ShopCarPayImage);
            tvShopCarPayName = (TextView) itemView.findViewById(R.id.tv_ShopCarPayName);
            tvShopCarPayNum = (TextView) itemView.findViewById(R.id.tv_ShopCarPayNum);
            tvShopCarPayNeedMoney = (TextView) itemView.findViewById(R.id.tv_ShopCarPayNeedMoney);
            //規格
            tvShopCarPaySpecs = itemView.findViewById(R.id.tv_ShopCarPaySpecs);
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
