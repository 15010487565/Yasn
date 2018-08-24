package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.ShopCarAdapterModel;

import java.util.List;

import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ITEMTOTAL;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ONE;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_TWO;

/**
 * Created by gs on 2017/12/29.
 */

public class ShopCarPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ShopCarAdapterModel> list;
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

    public void setData(List<ShopCarAdapterModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        ShopCarAdapterModel adapterModel = list.get(position);
        int itmeType = adapterModel.getItmeType();
        if (itmeType == 1) {
            return TYPE_ONE;
        } else if (itmeType == 2){
            return TYPE_TWO;
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
        ShopCarAdapterModel shopCarAdapterModel = list.get(position);
        switch (itemViewType) {
            case TYPE_ONE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                String storeName = shopCarAdapterModel.getStoreName();
                titleViewHolder.tvStoreName.setText(storeName == null ? "" : storeName);
                break;
            case TYPE_TWO:
                ListViewHolder listViewHolder = (ListViewHolder) holder;

                String name = shopCarAdapterModel.getName();
                listViewHolder.tvShopCarPayName.setText(name);
                //价格
                double price = shopCarAdapterModel.getPrice();
                //数量
                int num = shopCarAdapterModel.getNum();
                listViewHolder.tvShopCarPayNum.setText("￥" + String.format("%.2f", price) +"x"+num);
                //需要金额
                double needPayMoney = shopCarAdapterModel.getNeedPayMoney();
                listViewHolder.tvShopCarPayNeedMoney.setText("￥" + String.format("%.2f", needPayMoney));

                String imageDefault = shopCarAdapterModel.getImageDefault();
                Glide.with(context.getApplicationContext())
                        .load(imageDefault)
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(listViewHolder.ivShopCarPayImage);

                break;
            case TYPE_ITEMTOTAL:
                TotalViewHolder totalViewHolder = (TotalViewHolder) holder;
                double shippingTotal = shopCarAdapterModel.getShippingTotal();
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
        private TextView tvShopCarPayName,tvShopCarPayNum,tvShopCarPayNeedMoney;

        public ListViewHolder(View itemView) {
            super(itemView);
            ivShopCarPayImage = (ImageView) itemView.findViewById(R.id.iv_ShopCarPayImage);
            tvShopCarPayName = (TextView) itemView.findViewById(R.id.tv_ShopCarPayName);
            tvShopCarPayNum = (TextView) itemView.findViewById(R.id.tv_ShopCarPayNum);
            tvShopCarPayNeedMoney = (TextView) itemView.findViewById(R.id.tv_ShopCarPayNeedMoney);
        }
    }
}
