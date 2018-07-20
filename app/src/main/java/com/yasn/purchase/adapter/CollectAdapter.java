package com.yasn.purchase.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewActivity;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.CollectModel;

import java.util.List;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

/**
 * Created by gs on 2017/12/29.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ITEM = 1;
    public final static int TYPE_FOOTER = 2;
    private Context context;
    private List<CollectModel.ListFavoriteBean> list;
    private List<CollectModel.ListFavoriteBean> addList;
    private OnRcItemClickListener onItemClickListener;
    private LinearLayoutManager linearLayoutManager;
    private String loginState;
    private String regionName;
    private String place = " ";
    private int placeNum = 3;

    public CollectAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
        loginState = SharePrefHelper.getInstance(context).getSpString("loginState");
        regionName = SharePrefHelper.getInstance(context).getSpString("regionName");
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<CollectModel.ListFavoriteBean> list) {
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<CollectModel.ListFavoriteBean> list) {
        this.addList = list;
        if (this.list != null) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }
        notifyDataSetChanged();
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
                view = LayoutInflater.from(context).inflate(R.layout.item_collect, parent, false);
                holder = new ViewHolderItem(view);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                final ViewHolderItem holderOneItem = (ViewHolderItem) holder;
                CollectModel.ListFavoriteBean listFavoriteBean = list.get(position);
                StringBuffer sb = new StringBuffer();
                int goneNum = 0;
                int store_id = listFavoriteBean.getStore_id();
                if (store_id == 1) {
                    sb.append("自营" + place);
                    goneNum += placeNum;
                    holderOneItem.autotrophy.setVisibility(View.VISIBLE);
                    holderOneItem.autotrophy.setText("自营");
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
                    holderOneItem.autotrophy.setText("");
                    holderOneItem.autotrophy.setVisibility(View.GONE);
//                }
        }
                int is_limit_buy = listFavoriteBean.getIs_limit_buy();
                if (is_limit_buy > 0) {
                    holderOneItem.purchase.setVisibility(View.VISIBLE);
                    sb.append("限购" + place);
                    goneNum += placeNum;
                } else {
                    holderOneItem.purchase.setVisibility(View.GONE);
                }
                int is_before_sale = listFavoriteBean.getIs_before_sale();
                if (is_before_sale == 1) {
                    holderOneItem.presell.setVisibility(View.VISIBLE);
                    goneNum += placeNum;
                    sb.append("预售" + place);
                } else {
                    holderOneItem.presell.setVisibility(View.GONE);
                }
                SpannableStringBuilder span = new SpannableStringBuilder(sb + listFavoriteBean.getName());
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)), 0, goneNum,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderOneItem.tvCollectTitle.setText(span);

                String advert = listFavoriteBean.getAdvert();
                if (advert == null || "".equals(advert)) {
                    holderOneItem.tvAdvert.setVisibility(View.GONE);
                } else {
                    holderOneItem.tvAdvert.setText(advert);
                    holderOneItem.tvAdvert.setVisibility(View.VISIBLE);
                }


                String minNumberString = String.format("已售%s笔", String.valueOf(listFavoriteBean.getTotalBuyCount()));
                SpannableStringBuilder countRecySpan = new SpannableStringBuilder(minNumberString);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), 0, 2,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), minNumberString.length() - 1, minNumberString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderOneItem.tvCollectCount.setText(countRecySpan);

                int has_discount = listFavoriteBean.getHas_discount();
                //抢购价
                String discount_price = listFavoriteBean.getDiscount_price();
                //正常价格
                double price = listFavoriteBean.getPrice();
                if (has_discount == 1) {
                    holderOneItem.llBuy.setVisibility(View.VISIBLE);
                    holderOneItem.tvbuyMoney.setText("￥" + String.format("%.2f", Double.valueOf(discount_price)));
                    if ("0".equals(loginState)) {
                        holderOneItem.tvCollectMoney.setText("￥" + String.format("%.2f", price));
                        holderOneItem.llBuy.setVisibility(View.VISIBLE);
                    } else {
                        holderOneItem.tvCollectMoney.setText(loginState == null ? "登录看价格" : loginState);
                        holderOneItem.llBuy.setVisibility(View.GONE);
                    }
                } else {
                    if ("0".equals(loginState)) {
                        holderOneItem.tvCollectMoney.setText("￥" + String.format("%.2f", price));
                    } else {
                        holderOneItem.tvCollectMoney.setText(loginState == null ? "登录看价格" : loginState);
                    }
                    holderOneItem.tvbuyMoney.setText("");
                    holderOneItem.llBuy.setVisibility(View.GONE);
                }
                int have_voice = listFavoriteBean.getHave_voice();//是否有音频 1：有
                if (have_voice == 1) {
                    holderOneItem.button3.setVisibility(View.VISIBLE);
                } else {
                    holderOneItem.button3.setVisibility(View.GONE);
                }
                int is_success_case = listFavoriteBean.getIs_success_case(); //是否成功案例 1：有
                if (is_success_case == 1) {
                    holderOneItem.button2.setVisibility(View.VISIBLE);
                } else {
                    holderOneItem.button2.setVisibility(View.GONE);
                }
//                int market_enable = listFavoriteBean.getMarket_enable();
//                if (market_enable == 0) {//上架1, 下架0
//                    holderOneItem.iv_shroud.setVisibility(View.VISIBLE);
//                } else {
//                    holderOneItem.iv_shroud.setVisibility(View.GONE);
//                }

                Glide.with(context)
                        .load(listFavoriteBean.getSmall())
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(holderOneItem.ivLeft);
                onItemEventClick(holderOneItem);
                break;
            case TYPE_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;
                if (list == null || list.size() == 0) {
                    footviewholder.footView.setVisibility(View.GONE);
                } else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    if (addList == null || addList.size() == 0) {
                        footviewholder.progressBar.setVisibility(View.GONE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                    } else {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        if (visibleItemCount != list.size()) {
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
        TextView tvCollectTitle;
        TextView tvCollectMoney, tvAdvert, tvCollectCount;
        TextView button1, button2, button3;
        TextView autotrophy, purchase, presell;
        ImageView ivLeft, iv_shroud,ivCollectClean;
        LinearLayout llBuy;
        TextView tvbuyMoney;

        public ViewHolderItem(View itemView) {
            super(itemView);
            tvCollectTitle = (TextView) itemView.findViewById(R.id.title);
            tvCollectMoney = (TextView) itemView.findViewById(R.id.tv_CollectMoney);
            tvCollectMoney.setOnClickListener(this);
            tvAdvert = (TextView) itemView.findViewById(R.id.tv_CollectAction);
            tvCollectCount = (TextView) itemView.findViewById(R.id.tv_CollectCount);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
//            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
//            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
//            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
            ivLeft = (ImageView) itemView.findViewById(R.id.iv_CollectLeft);
//            iv_shroud = (ImageView) itemView.findViewById(R.id.iv_shroud);
//            Drawable background = iv_shroud.getBackground();
//            background.setAlpha(255);
            llBuy = (LinearLayout) itemView.findViewById(R.id.ll_CollectBuy);
            tvbuyMoney = (TextView) itemView.findViewById(R.id.tv_CollectBuyMoney);
            //删除
            ivCollectClean = (ImageView) itemView.findViewById(R.id.iv_CollectClean);
            ivCollectClean.setOnClickListener(this);
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
                case R.id.tv_CollectMoney:
                    String trim = tvCollectMoney.getText().toString().trim();
                    if ("登录看价格".equals(trim)) {
                        startWebViewActivity(Config.LOGINWEBVIEW);
                    } else if ("认证看价格".equals(trim)) {
                        startWebViewActivity(Config.ATTESTATION);
                    }
                    break;
                case R.id.iv_CollectClean:
                    onItemClickListener.OnItemLongClick(v,getLayoutPosition());
                    break;
            }
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
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
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("webViewUrl", url);
        context.startActivity(intent);
    }
}
