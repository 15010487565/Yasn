package com.yasn.purchase.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.AuthorActivity;
import com.yasn.purchase.activity.LoginActivity;
import com.yasn.purchase.activity.OftenShopActivity;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.holder.FootViewHolder;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.OftenModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.common.ItemTypeConfig.TYPE_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ITEM;

/**
 * /**
 * 常购清单
 * Created by gs on 2017/12/29.
 */

public class OftenShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<OftenModel.DataBean.RegularPurcaseBean> list;
    private List<OftenModel.DataBean.RegularPurcaseBean> addList;
    private OnRcItemClickListener onItemClickListener;
    private OnOftenAddShopCarListener onAddListener;
    private LinearLayoutManager linearLayoutManager;
    private String loginState;
    private String regionName;
    private String place = " ";
    private int placeNum = 3;
    private Map viewHolderMap = new HashMap<>();

    public OftenShopAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
        loginState = SharePrefHelper.getInstance(context).getSpString("loginState");
        regionName = SharePrefHelper.getInstance(context).getSpString("regionName");
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener,OnOftenAddShopCarListener onAddListener) {
        this.onItemClickListener = onItemClickListener;
        this.onAddListener = onAddListener;
    }

    public void setData(List<OftenModel.DataBean.RegularPurcaseBean> list) {
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<OftenModel.DataBean.RegularPurcaseBean> list) {
        this.addList = list;
        if (this.list != null) {
            this.list.addAll(list);
        } else {
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

    public List<OftenModel.DataBean.RegularPurcaseBean> getData(){
        return this.list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_oftenshop, parent, false);
                holder = new ViewHolderItem(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
                holder = new FootViewHolder(view);
                viewHolderMap.put("holder", holder);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                ViewHolderItem holderItem = (ViewHolderItem) holder;
                OftenModel.DataBean.RegularPurcaseBean regularPurcaseBean = list.get(position);
                StringBuffer sb = new StringBuffer();
                int goneNum = 0;
                int store_id = regularPurcaseBean.getStore_id();
                if (store_id == 1) {
                    sb.append("自营" + place);
                    goneNum += placeNum;
                    holderItem.autotrophy.setVisibility(View.VISIBLE);
                    holderItem.autotrophy.setText("自营");
                }
                else {
//                    if (store_id!=99){
//                        if (regionName==null||"".equals(regionName)){
//                            holderOneItem.autotrophy.setVisibility(View.GONE);
//                            holderOneItem.autotrophy.setText("");
//                        }else {
//                            holderOneItem.autotrophy.setVisibility(View.VISIBLE);
//                            holderOneItem.autotrophy.setText(regionName+"直供");
//                            sb.append(regionName+"直供"+place);
//                            goneNum =goneNum+ regionName.length()+placeNum;
//                        }
//                    }else {
                    holderItem.autotrophy.setText("");
                    holderItem.autotrophy.setVisibility(View.GONE);
//                }
        }
                int is_limit_buy = regularPurcaseBean.getIs_limit_buy();
                if (is_limit_buy > 0) {
                    holderItem.purchase.setVisibility(View.VISIBLE);
                    sb.append("限购" + place);
                    goneNum += placeNum;
                } else {
                    holderItem.purchase.setVisibility(View.GONE);
                }
                int is_before_sale = regularPurcaseBean.getIs_before_sale();
                if (is_before_sale == 1) {
                    holderItem.presell.setVisibility(View.VISIBLE);
                    goneNum += placeNum;
                    sb.append("预售" + place);
                } else {
                    holderItem.presell.setVisibility(View.GONE);
                }
                SpannableStringBuilder span = new SpannableStringBuilder(sb + regularPurcaseBean.getName());
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)), 0, goneNum,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderItem.oftenshopTitle.setText(span);

                String minNumberString = String.format("已售%s笔", String.valueOf(regularPurcaseBean.getSold_num()));
                SpannableStringBuilder countRecySpan = new SpannableStringBuilder(minNumberString);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), 0, 2,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), minNumberString.length() - 1, minNumberString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderItem.tvOftenCount.setText(countRecySpan);

                int has_discount = regularPurcaseBean.getHas_discount();
                //抢购价
//                String discount_price = regularPurcaseBean.getDiscount_price();
                //正常价格
                double price = regularPurcaseBean.getPrice();
//                if (has_discount == 1) {
//                    holderItem.llBuy.setVisibility(View.VISIBLE);
//                    holderItem.tvbuyMoney.setText("￥" + String.format("%.2f", Double.valueOf(discount_price)));
                    if ("0".equals(loginState)) {
                        holderItem.oftenshopMoney.setText("￥" + String.format("%.2f", price));
//                        holderItem.llBuy.setVisibility(View.VISIBLE);
                    } else {
                        holderItem.oftenshopMoney.setText(loginState == null ? "登录看价格" : loginState);
//                        holderItem.llBuy.setVisibility(View.GONE);
                    }
//                } else {
//                    if ("0".equals(loginState)) {
//                        holderItem.oftenshopMoney.setText("￥" + String.format("%.2f", price));
//                    } else {
//                        holderItem.oftenshopMoney.setText(loginState == null ? "登录看价格" : loginState);
//                    }
//                    holderItem.tvbuyMoney.setText("");
//                    holderItem.llBuy.setVisibility(View.GONE);
//                }
                int have_voice = regularPurcaseBean.getHave_voice();//是否有音频 1：有
                if (have_voice == 1) {
                    holderItem.button3.setVisibility(View.VISIBLE);
                } else {
                    holderItem.button3.setVisibility(View.GONE);
                }
                int is_success_case = regularPurcaseBean.getIs_success_case(); //是否成功案例 1：有
                if (is_success_case == 1) {
                    holderItem.button2.setVisibility(View.VISIBLE);
                } else {
                    holderItem.button2.setVisibility(View.GONE);
                }
                int market_enable = regularPurcaseBean.getMarket_enable();
                int beforeSale = regularPurcaseBean.getIs_before_sale();//是否有预售(0否1是)
                int enableStore = regularPurcaseBean.getEnable_store();
                if (market_enable == 1) {//上架1, 下架0
                    if ( beforeSale == 0){//没有预售
                        if (enableStore <= 0 ){

                            holderItem.oftenshopTitle.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                            holderItem.ivOftenShroud.setVisibility(View.VISIBLE);
                            holderItem.ivOftenShroud.setBackgroundResource(R.mipmap.image_ungoods);
                            holderItem.oftenshopMoney.setTextColor(ContextCompat.getColor(context, R.color.orange_un));

                            holderItem.ivAddshopcar.setBackgroundResource(R.mipmap.oftershop_black);
                        }else {
                            holderItem.oftenshopTitle.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                            holderItem.ivOftenShroud.setVisibility(View.GONE);
                            holderItem.oftenshopMoney.setTextColor(ContextCompat.getColor(context, R.color.orange));

                            holderItem.ivAddshopcar.setBackgroundResource(R.mipmap.oftershop_red);
                        }
                    }else {//预售
                        holderItem.oftenshopTitle.setTextColor(ContextCompat.getColor(context, R.color.black_66));
                        holderItem.ivOftenShroud.setVisibility(View.GONE);
                        holderItem.oftenshopMoney.setTextColor(ContextCompat.getColor(context, R.color.orange));

                        holderItem.ivAddshopcar.setBackgroundResource(R.mipmap.oftershop_red);
                    }
                } else {

                    holderItem.oftenshopTitle.setTextColor(ContextCompat.getColor(context, R.color.black_99));
                    holderItem.ivOftenShroud.setVisibility(View.VISIBLE);
                    holderItem.ivOftenShroud.setBackgroundResource(R.mipmap.soldout);
                    holderItem.oftenshopMoney.setTextColor(ContextCompat.getColor(context, R.color.orange_un));

                    holderItem.ivAddshopcar.setBackgroundResource(R.mipmap.oftershop_black);
                }
                //规格
                String spec_value = regularPurcaseBean.getSpec_value();
                holderItem.specOftenShop.setText(spec_value);

                Glide.with(context)
                        .load(regularPurcaseBean.getSmall())
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(holderItem.ivLeft);
                onItemEventClick(holderItem);
                break;
            case TYPE_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;
                if (list == null || list.size() == 0) {
                    footviewholder.footView.setVisibility(View.GONE);
                } else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    Log.e("TAG_底部","addList="+(addList==null));
                    if (addList == null || addList.size() == 0) {
                        footviewholder.progressBar.setVisibility(View.GONE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                    } else {
                        Log.e("TAG_底部","addList="+(addList.size()));
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        Log.e("TAG_底部","visibleItemCount="+visibleItemCount+";list="+list.size());
                        if (visibleItemCount <= list.size() && visibleItemCount <= addList.size()) {
                            footviewholder.progressBar.setVisibility(View.GONE);
                            footviewholder.footText.setText(context.getResources().getString(R.string.pullup_to_load));
                        } else {
                            footviewholder.progressBar.setVisibility(View.GONE);
                            footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                        }
                    }
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size() + 1);
    }

    class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView oftenshopTitle;
        TextView oftenshopMoney, tvOftenCount;
        TextView button1, button2, button3;
        TextView autotrophy, purchase, presell;
        ImageView ivLeft, ivOftenShroud,ivAddshopcar;
//        TagsLayout specOftenShop;
        TextView specOftenShop;
        public ViewHolderItem(View itemView) {
            super(itemView);
            oftenshopTitle = (TextView) itemView.findViewById(R.id.title);
            oftenshopMoney = (TextView) itemView.findViewById(R.id.tv_OftenshopMoney);
            oftenshopMoney.setOnClickListener(this);
            tvOftenCount = (TextView) itemView.findViewById(R.id.tv_OftenshopCount);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
//            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
//            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
//            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
            ivLeft = (ImageView) itemView.findViewById(R.id.iv_Oftenshop);
            ivOftenShroud = (ImageView) itemView.findViewById(R.id.iv_OftenShroud);
            Drawable background = ivOftenShroud.getBackground();
            background.setAlpha(255);
            //规格布局
//            specOftenShop = (TagsLayout) itemView.findViewById(R.id.spec_OftenShop);
            specOftenShop = (TextView) itemView.findViewById(R.id.spec_OftenShop);
            //添加购物车
            ivAddshopcar = (ImageView) itemView.findViewById(R.id.iv_Addshopcar);
            ivAddshopcar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.lable_button1:
//                    onItemClickListener.OnClickRecyButton(1, getLayoutPosition());
//                    break;
//                case R.id.lable_button2:
//                    onItemClickListener.OnClickRecyButton(2, getLayoutPosition());
//                    break;
//                case R.id.lable_button3:
//                    onItemClickListener.OnClickRecyButton(3, getLayoutPosition());
//                    break;
                case R.id.tv_OftenshopMoney:
                    String trim = oftenshopMoney.getText().toString().trim();
                    if ("登录看价格".equals(trim)) {
                        ((OftenShopActivity)context).startBaseActivity(context,LoginActivity.class);
                    } else if ("认证看价格".equals(trim)) {
//                        startWebViewActivity(Config.ATTESTATION);
                        ((SimpleTopbarActivity)context).showStartAuthorDialog(AuthorActivity.class);
                    }
                    break;
                case R.id.iv_Addshopcar://添加购物车
                    onAddListener.OnAddShopCarClick(v,getLayoutPosition());
                    break;
            }
        }
    }

    private void onItemEventClick(RecyclerView.ViewHolder holder) {
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

    private float sp2px(float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

    private void startWebViewActivity(String url) {
        Intent intent = new Intent(context, WebViewH5Activity.class);
        intent.putExtra("webViewUrl", url);
        context.startActivity(intent);
    }
    public interface OnOftenAddShopCarListener {
        //添加购物车
        void OnAddShopCarClick(View view, int position);
    }
}

