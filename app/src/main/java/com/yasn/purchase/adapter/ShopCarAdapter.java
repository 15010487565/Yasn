package com.yasn.purchase.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnShopCarClickListener;
import com.yasn.purchase.model.ShopCarAdapterModel;
import com.yasn.purchase.view.TagsLayout;

import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ShopCarAdapterModel> list;
    private OnShopCarClickListener onItemClickListener;
    private static final int TYPE_ITEMTITLE = 1;
    private static final int TYPE_ITEMLIST = 2;

    public ShopCarAdapter(Context context) {
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
            return TYPE_ITEMTITLE;
        } else {
            return TYPE_ITEMLIST;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType) {
            case TYPE_ITEMTITLE:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_order, parent, false);
                holder = new TitleViewHolder(view);
                break;
            case TYPE_ITEMLIST:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_orderlist, parent, false);
                holder = new ListViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        ShopCarAdapterModel shopCarAdapterModel = list.get(position);
        //是否选中
        int isCheck = shopCarAdapterModel.getIsCheck();
        switch (itemViewType) {
            case TYPE_ITEMTITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                String storeName = shopCarAdapterModel.getStoreName();
                titleViewHolder.tvStoreName.setText(storeName == null ? "" : storeName);
                int store_id = shopCarAdapterModel.getStore_id();
                if (store_id == 1) {

                    //包邮价格
                    String freeShipMoney = shopCarAdapterModel.getFreeShipMoney();
                    if (Double.valueOf(freeShipMoney) > 0) {
                        titleViewHolder.llMail.setVisibility(View.VISIBLE);
                        String tvMmailHintString = titleViewHolder.tvMmailHint.getText().toString();
                        //选中价格
                        double storeCheckPrice = shopCarAdapterModel.getStoreCheckPrice();
                        double residueDouble = Double.valueOf(freeShipMoney) - storeCheckPrice;
                        tvMmailHintString = String.format(tvMmailHintString, freeShipMoney, String.valueOf(residueDouble < 0 ? 0 : residueDouble) + "元");
                        SpannableStringBuilder span = new SpannableStringBuilder(tvMmailHintString);
                        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.orange)), tvMmailHintString.length() - 3, tvMmailHintString.length(),
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        titleViewHolder.tvMmailHint.setText(span);
                    } else {
                        titleViewHolder.llMail.setVisibility(View.GONE);
                    }

                } else {
                    titleViewHolder.llMail.setVisibility(View.GONE);
                }
                if (isCheck == 1) {
                    titleViewHolder.ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                } else {
                    titleViewHolder.ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                }
                break;
            case TYPE_ITEMLIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                //是否选中
                if (isCheck == 1) {
                    listViewHolder.ivOrderListSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                } else {
                    listViewHolder.ivOrderListSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                }

                String name = shopCarAdapterModel.getName();
                final int enableStore = shopCarAdapterModel.getEnableStore();
                if (enableStore < 10) {
                    listViewHolder.tvOrderListHintNum.setText("仅剩" + enableStore + "件");
                    listViewHolder.tvOrderListHintNum.setVisibility(View.VISIBLE);
                } else {
                    listViewHolder.tvOrderListHintNum.setVisibility(View.GONE);
                }
                int goodsOff = shopCarAdapterModel.getGoodsOff();
                if (goodsOff == 0) {//0上架
                    listViewHolder.ivShroud.setVisibility(View.GONE);
                    listViewHolder.ivOrderListSelect.setVisibility(View.VISIBLE);
                    listViewHolder.tvOrderListName.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                } else {//1下架
                    listViewHolder.ivShroud.setVisibility(View.VISIBLE);
                    listViewHolder.ivOrderListSelect.setVisibility(View.INVISIBLE);
                    listViewHolder.tvOrderListName.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                }

                //规格
                listViewHolder.shopcarLabel.removeAllViews();
                List<String> specList = shopCarAdapterModel.getSpecList();
                if (specList != null && specList.size() > 0) {
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    for (int i = 0, j = specList.size(); i < j; i++) {
                        String sprcTextString = specList.get(i);
                        final TextView tvSprc = new TextView(context);
                        Log.e("TAG_规格","sprcTextString="+sprcTextString+";position="+position);
                        tvSprc.setText(sprcTextString);
                        if (goodsOff == 0) {
                            tvSprc.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                            tvSprc.setBackgroundResource(R.drawable.text_n_f5);
                        } else {
                            tvSprc.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                            tvSprc.setBackgroundResource(R.drawable.text_n_f5);
                        }
                        tvSprc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        tvSprc.setGravity(Gravity.CENTER);
                        tvSprc.setTag(list.get(i));
                        lp.setMargins(10, 10, 10,0);
                        listViewHolder.shopcarLabel.addView(tvSprc, lp);

                    }
                }

                double price = shopCarAdapterModel.getPrice();
                if (goodsOff == 0) {
                    listViewHolder.tvOrderListPrice.setTextColor(ContextCompat.getColor(context, R.color.orange));
                    listViewHolder.llNum.setBackgroundResource(R.drawable.text_black_white);
                    listViewHolder.ivAddNum.setBackgroundResource(R.mipmap.addblack);
                    listViewHolder.llAddNum.setEnabled(true);
                    listViewHolder.ivSubtractNum.setBackgroundResource(R.mipmap.subtractimage);
                    listViewHolder.llSubtractNum.setEnabled(true);
                    listViewHolder.ivOrderListClean.setBackgroundResource(R.mipmap.cleanhistory);
                    listViewHolder.ivOrderListClean.setEnabled(true);
                } else {
                    listViewHolder.tvOrderListPrice.setTextColor(ContextCompat.getColor(context, R.color.orange_un));
                    listViewHolder.llNum.setBackgroundResource(R.drawable.text_black99_white);
                    listViewHolder.ivAddNum.setBackgroundResource(R.mipmap.addblack1);
                    listViewHolder.llAddNum.setEnabled(false);
                    listViewHolder.ivSubtractNum.setBackgroundResource(R.mipmap.subtractimage1);
                    listViewHolder.llSubtractNum.setEnabled(false);
                    listViewHolder.ivOrderListClean.setBackgroundResource(R.mipmap.cleanhistory1);
                    listViewHolder.ivOrderListClean.setEnabled(false);
                }
                listViewHolder.tvOrderListPrice.setText("￥" + String.valueOf(price));

                int num = shopCarAdapterModel.getNum();
                if (goodsOff == 0) {
                    listViewHolder.etGoodsNum.setEnabled(true);
                    listViewHolder.etGoodsNum.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                    listViewHolder.etGoodsNum.setText(String.valueOf(num));
                    listViewHolder.etGoodsNum.setTag(position);
                } else {
                    listViewHolder.etGoodsNum.setEnabled(false);
                    listViewHolder.etGoodsNum.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                    listViewHolder.etGoodsNum.setText(String.valueOf(num));
                }
                StringBuffer sb = new StringBuffer();
                int goneNum = 0;
                String beforeSale = shopCarAdapterModel.getBeforeSale();
                if (beforeSale==null||"".equals(beforeSale)){
                    listViewHolder.tvPresellHint.setVisibility(View.GONE);
                    listViewHolder.tvPresellHint.setText("");
                    listViewHolder.tvPresell.setVisibility(View.GONE);
                }else {
                    listViewHolder.tvPresellHint.setVisibility(View.VISIBLE);
                    listViewHolder.tvPresellHint.setText("预售商品请单独下单");
                    goneNum += 3;
                    sb.append("预售 ");
                    listViewHolder.tvPresell.setVisibility(View.VISIBLE);
                }
                int hasDiscount = shopCarAdapterModel.getHasDiscount();
                if (hasDiscount == 0){//是否有抢购   1 有  0没有
                    listViewHolder.tvPurchase.setVisibility(View.GONE);
                    listViewHolder.tvPurchase.setText("");
                }else {
                    goneNum += 3;
                    sb.append("抢购 ");
                    listViewHolder.tvPurchase.setVisibility(View.VISIBLE);
                    listViewHolder.tvPurchase.setText("抢购");
                }
                //限购
                int limitnum = shopCarAdapterModel.getLimitnum();
                if (limitnum <=0){
                    listViewHolder.tvPurchaseHint.setVisibility(View.GONE);
                    listViewHolder.tvPurchaseHint.setText("");
                }else {
                    listViewHolder.tvPurchaseHint.setVisibility(View.VISIBLE);
                    listViewHolder.tvPurchaseHint.setText("限购"+limitnum+"件");
                }
                SpannableStringBuilder span = new SpannableStringBuilder(sb + (name == null ? "" : name));
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)), 0, goneNum,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                listViewHolder.tvOrderListName.setText(span);

                String imageDefault = shopCarAdapterModel.getImageDefault();
                Glide.with(context.getApplicationContext())
                        .load(imageDefault)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(listViewHolder.ivOrderListImage);
                onItemEventClick(listViewHolder);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout llStoreName, llAddgoods, llMail, llStoreNameSelect;
        TextView tvStoreName, tvMmailHint;
        ImageView ivStoreNameSelect;

        public TitleViewHolder(View itemView) {
            super(itemView);
            llStoreName = (LinearLayout) itemView.findViewById(R.id.ll_StoreName);
            //包邮
            llMail = (LinearLayout) itemView.findViewById(R.id.ll_mail);
            tvMmailHint = (TextView) itemView.findViewById(R.id.tv_mailHint);
            //去凑单
            llAddgoods = (LinearLayout) itemView.findViewById(R.id.ll_addgoods);
            llAddgoods.setOnClickListener(this);

            tvStoreName = (TextView) itemView.findViewById(R.id.tv_StoreName);
            llStoreNameSelect = (LinearLayout) itemView.findViewById(R.id.ll_StoreNameSelect);
            llStoreNameSelect.setOnClickListener(this);
            ivStoreNameSelect = (ImageView) itemView.findViewById(R.id.iv_StoreNameSelect);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_addgoods://凑单
                    onItemClickListener.OnClickMore(getLayoutPosition());
                    break;
                case R.id.ll_StoreNameSelect://选中图片
                    onItemClickListener.OnClickSelected(getLayoutPosition());
                    break;
            }
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvOrderListName, tvOrderListPrice, tvOrderListHintNum;
        ImageView ivOrderListSelect, ivOrderListImage, ivOrderListClean, ivShroud;
        private TagsLayout shopcarLabel;
        private LinearLayout llSubtractNum, llAddNum, llOorderListSelect;
        private ImageView ivSubtractNum, ivAddNum;
        private TextView etGoodsNum,tvPresellHint,tvPurchaseHint,tvPurchase,tvPresell;
        private LinearLayout llNum;
        public ListViewHolder(View itemView) {
            super(itemView);
            llNum = (LinearLayout) itemView.findViewById(R.id.ll_Num);
            tvPresellHint = (TextView) itemView.findViewById(R.id.tv_PresellHint);
            tvPurchaseHint = (TextView) itemView.findViewById(R.id.tv_PurchaseHint);
            tvPurchase = (TextView) itemView.findViewById(R.id.tv_purchase);
            tvPresell = (TextView) itemView.findViewById(R.id.tv_presell);
            //列表商品名称
            tvOrderListName = (TextView) itemView.findViewById(R.id.tv_orderListName);
            ivOrderListImage = (ImageView) itemView.findViewById(R.id.iv_orderListImage);
            ivOrderListSelect = (ImageView) itemView.findViewById(R.id.iv_orderListSelect);
            llOorderListSelect = (LinearLayout) itemView.findViewById(R.id.ll_orderListSelect);
            llOorderListSelect.setOnClickListener(this);
            //删除
            ivOrderListClean = (ImageView) itemView.findViewById(R.id.iv_orderListClean);
            ivOrderListClean.setOnClickListener(this);
            //规格布局
            shopcarLabel = (TagsLayout) itemView.findViewById(R.id.shopcarLabel);
            //价格
            tvOrderListPrice = (TextView) itemView.findViewById(R.id.tv_orderListPrice);
            //加数量
            llAddNum = (LinearLayout) itemView.findViewById(R.id.ll_addNum);
            llAddNum.setOnClickListener(this);
            ivAddNum = (ImageView) itemView.findViewById(R.id.iv_addNum);
            //减数量
            llSubtractNum = (LinearLayout) itemView.findViewById(R.id.ll_subtractNum);
            llSubtractNum.setOnClickListener(this);
            ivSubtractNum = (ImageView) itemView.findViewById(R.id.iv_subtractNum);
            //数量
            etGoodsNum = (TextView) itemView.findViewById(R.id.et_goodsNum);
            etGoodsNum.setOnClickListener(this);
            tvOrderListHintNum = (TextView) itemView.findViewById(R.id.tv_orderListHintNum);
            Drawable tvOrderListHintNumBg = tvOrderListHintNum.getBackground();
            tvOrderListHintNumBg.setAlpha(100);
            ivShroud = (ImageView) itemView.findViewById(R.id.iv_shroud);
            Drawable ivShroudBg = tvOrderListHintNum.getBackground();
            ivShroudBg.setAlpha(255);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.iv_orderListClean://删除
                    onItemClickListener.OnClickClean(getLayoutPosition());
                    break;

                case R.id.ll_orderListSelect://选中图片
                    onItemClickListener.OnClickSelected(getLayoutPosition());
                    break;

                case R.id.ll_addNum: //加数量
                    onItemClickListener.OnClickAdd(getLayoutPosition());
                    break;

                case R.id.ll_subtractNum://减数量
                    onItemClickListener.OnClickSubtract(getLayoutPosition());
                    break;
                case R.id.et_goodsNum:
                    onItemClickListener.setOnTouchListener(getLayoutPosition());
                    break;
            }
        }
    }

    protected void onItemEventClick(RecyclerView.ViewHolder holder) {
        final int position = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v, position);
            }
        });
    }
}
