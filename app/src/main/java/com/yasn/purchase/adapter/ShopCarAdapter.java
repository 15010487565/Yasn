package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import static com.yasn.purchase.R.id.iv_orderListSelect;

/**
 * Created by gs on 2017/12/29.
 */

public class ShopCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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

    public void setData( List<ShopCarAdapterModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {

            ShopCarAdapterModel adapterModel = list.get(position);
            int itmeType = adapterModel.getItmeType();
            if (itmeType == 1){
                return TYPE_ITEMTITLE;
            }else {
                return TYPE_ITEMLIST;
            }

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        ShopCarAdapterModel shopCarAdapterModel = list.get(position);
        Log.e("TAG_ShopCarAdapter","shopCarAdapterModel="+shopCarAdapterModel.toString());
        //是否选中
        int isCheck = shopCarAdapterModel.getIsCheck();
        switch (itemViewType){
            case TYPE_ITEMTITLE:
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                String storeName = shopCarAdapterModel.getStoreName();
                titleViewHolder.tvStoreName.setText(storeName==null?"":storeName);
                int store_id = shopCarAdapterModel.getStore_id();
                Log.e("TAG_进货单标签adapter","store_id="+store_id);
                if (store_id == 1){

                    //包邮价格
                    String freeShipMoney = shopCarAdapterModel.getFreeShipMoney();
                    if (Double.valueOf(freeShipMoney)>0){
                        titleViewHolder.llMail.setVisibility(View.VISIBLE);
                        String tvMmailHintString = titleViewHolder.tvMmailHint.getText().toString();
                        //选中价格
                        double storeCheckPrice = shopCarAdapterModel.getStoreCheckPrice();
                        double residueDouble = Double.valueOf(freeShipMoney)-storeCheckPrice;
                        tvMmailHintString = String.format(tvMmailHintString, freeShipMoney,String.valueOf(residueDouble<0?0:residueDouble)+"元");
                        SpannableStringBuilder span = new SpannableStringBuilder(tvMmailHintString);
                        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.orange)), tvMmailHintString.length()-3,tvMmailHintString.length() ,
                                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                        titleViewHolder.tvMmailHint.setText(span);
                    }else {
                        titleViewHolder.llMail.setVisibility(View.GONE);
                    }

                }else {
                    titleViewHolder.llMail.setVisibility(View.GONE);
                }
                if (isCheck==1){
                    titleViewHolder.ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                }else {
                    titleViewHolder.ivStoreNameSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                }
                break;
            case TYPE_ITEMLIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                //是否选中
                if (isCheck==1){
                    listViewHolder.ivOrderListSelect.setBackgroundResource(R.mipmap.checkbox_checked);
                }else {
                    listViewHolder.ivOrderListSelect.setBackgroundResource(R.mipmap.checkbox_unchecked);
                }

                String name = shopCarAdapterModel.getName();
                listViewHolder.tvOrderListName.setText(name==null?"":name);
                //规格
                List<String> specList = shopCarAdapterModel.getSpecList();
                int childCount = listViewHolder.shopcarLabel.getChildCount();
                if (childCount==0){
                    if (specList !=null&&specList.size()>0){
                        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        for (int i = 0,j = specList.size(); i < j; i++) {
                            String sprcTextString = specList.get(i);
                            Log.e("TAG_进货单标签adapter","sprcTextString="+sprcTextString);
                            final TextView tvSprc = new TextView(context);
                            tvSprc.setText(sprcTextString);
                            tvSprc.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                            tvSprc.setBackgroundResource(R.drawable.text_n_f5);
                            tvSprc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                            tvSprc.setGravity(Gravity.CENTER);
                            tvSprc.setTag(list.get(i));
                            lp.setMargins(10, 10, 10, 0);
                            listViewHolder.shopcarLabel.addView(tvSprc, lp);

                        }
                    }
                }
//                GridViewTextAdapter gridViewAdapter=new GridViewTextAdapter(context, specList);
//                listViewHolder.shopcarGridview.setAdapter(gridViewAdapter);
                double price = shopCarAdapterModel.getPrice();
                listViewHolder.tvOrderListPrice.setText("￥"+String.valueOf(price));
                int num = shopCarAdapterModel.getNum();
                listViewHolder.etGoodsNum.setText(String.valueOf(num));
                String imageDefault = shopCarAdapterModel.getImageDefault();
                Glide.with(context.getApplicationContext())
                        .load(imageDefault)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(listViewHolder.ivOrderListImage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    class TitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout llStoreName, llAddgoods,llMail;
        TextView tvStoreName,tvMmailHint;
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
            ivStoreNameSelect = (ImageView) itemView.findViewById(R.id.iv_StoreNameSelect);
            ivStoreNameSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_addgoods://凑单
                    onItemClickListener.OnClickMore(getLayoutPosition());
                    break;
                case R.id.iv_StoreNameSelect://选中图片
                    onItemClickListener.OnClickSelected(getLayoutPosition());
                    break;
            }
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , TextWatcher{
        TextView tvOrderListName,tvOrderListPrice;
        ImageView ivOrderListSelect,ivOrderListImage,ivOrderListClean;
        private TagsLayout shopcarLabel;
        private LinearLayout ivSubtractNum,ivAddNum;
        private EditText etGoodsNum;

        public ListViewHolder(View itemView) {
            super(itemView);
            //列表商品名称
            tvOrderListName = (TextView) itemView.findViewById(R.id.tv_orderListName);
            ivOrderListImage = (ImageView) itemView.findViewById(R.id.iv_orderListImage);
            ivOrderListSelect = (ImageView) itemView.findViewById(iv_orderListSelect);
            ivOrderListSelect.setOnClickListener(this);
            //删除
            ivOrderListClean = (ImageView) itemView.findViewById(R.id.iv_orderListClean);
            ivOrderListClean.setOnClickListener(this);
            //规格布局
            shopcarLabel = (TagsLayout) itemView.findViewById(R.id.shopcarLabel);
            //价格
            tvOrderListPrice = (TextView) itemView.findViewById(R.id.tv_orderListPrice);
            //加数量
            ivAddNum = (LinearLayout) itemView.findViewById(R.id.iv_addNum);
            ivAddNum.setOnClickListener(this);
            //减数量
            ivSubtractNum = (LinearLayout) itemView.findViewById(R.id.iv_subtractNum);
            ivSubtractNum.setOnClickListener(this);
            //数量
            etGoodsNum = (EditText) itemView.findViewById(R.id.et_goodsNum);
            etGoodsNum.addTextChangedListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.iv_orderListClean://删除
                    onItemClickListener.OnClickClean(getLayoutPosition());
                    break;

                case R.id.iv_orderListSelect://选中图片
                    onItemClickListener.OnClickSelected(getLayoutPosition());
                    break;

                case R.id.iv_addNum: //加数量
                    onItemClickListener.OnClickAdd(getLayoutPosition());
                    break;

                case R.id.iv_subtractNum://减数量
                    onItemClickListener.OnClickSubtract(getLayoutPosition());
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String textChanged = charSequence.toString();
            if (textChanged !=null&&!"".equals(textChanged)){

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

//    protected void onItemEventClick(RecyclerView.ViewHolder holder) {
//        final int position = holder.getLayoutPosition();
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.OnItemClick(v, position);
//            }
//        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                onItemClickListener.OnItemLongClick(v, position);
//                return true;
//            }
//        });
//    }
}
