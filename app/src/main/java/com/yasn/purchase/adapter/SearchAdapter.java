package com.yasn.purchase.adapter;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.AuthorActivity;
import com.yasn.purchase.activity.LoginActivity;
import com.yasn.purchase.activity.SearchActivity;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.holder.FootViewHolder;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.SearchModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_CONTENT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;


/**
 * Created by gs on 2017/12/29.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SearchModel.DataBean> list;
    private List<SearchModel.DataBean> addList;
    private OnRcItemClickListener onItemClickListener;
    private String loginState;
    private LinearLayoutManager linearLayoutManager;
    String regionName;
    private String place = " ";
    private int placeNum = 3;
    private Map viewHolderMap = new HashMap<>();

    public SearchAdapter(Context context,LinearLayoutManager linearLayoutManager) {
        super();
        this.linearLayoutManager = linearLayoutManager;
        this.context = context;
        loginState = SharePrefHelper.getInstance(context).getSpString("loginState");
        regionName = SharePrefHelper.getInstance(context).getSpString("regionName");
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData( List<SearchModel.DataBean> list) {
        Log.e("TAG_搜索","setData");
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData( List<SearchModel.DataBean> list) {
        Log.e("TAG_搜索","addData=");
        this.addList = list;

        if (this.list != null){
            this.list.addAll(list);
        }else {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        Log.e("TAG_搜索","clearData=");
        this.addList.clear();
        this.list.clear();
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

    public List<SearchModel.DataBean> getData(){
        return this.list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_FOOTER;
        } else {
            return ITEM_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case ITEM_CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_search, parent, false);
                holder = new ViewHolderSearch(view);
                break;

            case ITEM_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                        false);
                holder = new FootViewHolder(view);
                viewHolderMap.put("holder", holder);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case ITEM_CONTENT:
                ViewHolderSearch holderSearch = (ViewHolderSearch) holder;
                SearchModel.DataBean dataBean = list.get(position);
                StringBuffer sb = new StringBuffer();
                int goneNum = 0;
                int store_id = dataBean.getStore_id();
                if (store_id == 1){
                    sb.append("自营"+place);
                    goneNum += placeNum;
                    holderSearch.autotrophy.setVisibility(View.VISIBLE);
                    holderSearch.autotrophy.setText("自营");
                }else {
                    if (store_id!=99){
                        if (regionName==null||"".equals(regionName)){
                            holderSearch.autotrophy.setVisibility(View.GONE);
                            holderSearch.autotrophy.setText("");
                        }else {
                            holderSearch.autotrophy.setVisibility(View.VISIBLE);
                            holderSearch.autotrophy.setText(regionName+"直供");
                            sb.append(regionName+"直供"+place);
                            goneNum =goneNum+ regionName.length()+placeNum;
                        }
                    }else {
                        holderSearch.autotrophy.setText("");
                        holderSearch.autotrophy.setVisibility(View.GONE);
                    }
                }

                int is_limit_buy = dataBean.getIs_limit_buy();
                if (is_limit_buy>0){
                    holderSearch.purchase.setVisibility(View.VISIBLE);
                    sb.append("限购"+place);
                    goneNum += placeNum;
                }else {
                    holderSearch.purchase.setVisibility(View.GONE);
                }
                String is_before_sale = dataBean.getIs_before_sale();
                if ("1".equals(is_before_sale)){
                    holderSearch.presell.setVisibility(View.VISIBLE);
                    goneNum += placeNum;
                    sb.append("预售"+place);
                }else {
                    holderSearch.presell.setVisibility(View.GONE);
                }
                SpannableStringBuilder span = new SpannableStringBuilder(sb + dataBean.getGoods_name());
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.white)), 0, goneNum,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderSearch.searchTitle.setText(span);

                String advert = dataBean.getAdvert();
                if (advert ==null||"".equals(advert)){
                    holderSearch.searchAdvert.setVisibility(View.GONE);
                }else {
                    holderSearch.searchAdvert.setText(advert);
                    holderSearch.searchAdvert.setVisibility(View.VISIBLE);
                }


                String minNumberString = String.format("已售%s笔", String.valueOf(dataBean.getTotal_buy_count()));
                SpannableStringBuilder countRecySpan = new SpannableStringBuilder(minNumberString);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), 0, 2,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), minNumberString.length() - 1, minNumberString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderSearch.searchcount.setText(countRecySpan);

                String has_discount = dataBean.getHas_discount();
                Log.e("TAG_has_discount","has_discount="+has_discount+";loginState="+loginState);
                //抢购价
                String discount_price = dataBean.getDiscount_price();
                //正常价格
                double price = dataBean.getPrice();
                if ("1".equals(has_discount)){
                    holderSearch.buyingspreeLinear.setVisibility(View.VISIBLE);
                    holderSearch.buyingspreeMoney.setText("￥"+String.format("%.2f", Double.valueOf(discount_price)));
                    if ("0".equals(loginState)){
                        holderSearch.searchmoney.setText("￥"+String.format("%.2f", price));
                        holderSearch.buyingspreeLinear.setVisibility(View.VISIBLE);
                    }else {
                        holderSearch.searchmoney.setText(loginState == null?"登录看价格":loginState);
                        holderSearch.buyingspreeLinear.setVisibility(View.GONE);
                    }
                }else {
                    if ("0".equals(loginState)){
                        holderSearch.searchmoney.setText("￥"+String.format("%.2f", price));
                    }else {
                        holderSearch.searchmoney.setText(loginState == null?"登录看价格":loginState);
                    }
                    holderSearch.buyingspreeMoney.setText("");
                    holderSearch.buyingspreeLinear.setVisibility(View.GONE);
                }
                int have_voice = dataBean.getHave_voice();//是否有音频 1：有
                if (have_voice==1){
                    holderSearch.button3.setVisibility(View.VISIBLE);
                }else {
                    holderSearch.button3.setVisibility(View.GONE);
                }
                int is_success_case = dataBean.getIs_success_case(); //是否成功案例 1：有
                if (is_success_case==1){
                    holderSearch.button2.setVisibility(View.VISIBLE);
                }else {
                    holderSearch.button2.setVisibility(View.GONE);
                }
//                int market_enable = dataBean.getMarket_enable();
//                if (market_enable == 0){//上架1, 下架0
//                    holderSearch.iv_shroud.setVisibility(View.VISIBLE);
//                }else {
//                    holderSearch.iv_shroud.setVisibility(View.GONE);
//                }

                Glide.with(context)
                        .load(dataBean.getThumbnail())
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(holderSearch.titleimage);
                onItemEventClick(holderSearch);
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
                    }
                    else {
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        Log.e("TAG_底部","visibleItemCount="+visibleItemCount+";list="+list.size());
                        if (visibleItemCount <= list.size()){
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
    public int getItemCount() {
        return list==null?0:(list.size()+1);
    }


    class ViewHolderSearch extends RecyclerView.ViewHolder implements View.OnClickListener {
       TextView searchTitle;
        TextView searchmoney, searchAdvert, searchcount;
        TextView button1, button2, button3;
        TextView autotrophy, purchase,presell;
        ImageView titleimage,iv_shroud;
        LinearLayout buyingspreeLinear;
        TextView buyingspreeMoney;
        public ViewHolderSearch(View itemView) {
            super(itemView);
            searchTitle = (TextView) itemView.findViewById(R.id.title);
            searchmoney = (TextView) itemView.findViewById(R.id.tv_SearchMoney);
            searchmoney.setOnClickListener(this);
            searchAdvert = (TextView) itemView.findViewById(R.id.tv_SearchAction);
            searchcount = (TextView) itemView.findViewById(R.id.tv_SearchCount);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
            titleimage = (ImageView) itemView.findViewById(R.id.iv_SearchLeft);
//            iv_shroud = (ImageView) itemView.findViewById(R.id.iv_shroud);
//            Drawable background = iv_shroud.getBackground();
//            background.setAlpha(255);
            buyingspreeLinear = (LinearLayout) itemView.findViewById(R.id.ll_SearchBuy);
            buyingspreeMoney = (TextView) itemView.findViewById(R.id.tv_SearchBuyMoney);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lable_button1:
                    onItemClickListener.OnClickRecyButton(1, getLayoutPosition());
                    break;
                case R.id.lable_button2:
                    onItemClickListener.OnClickRecyButton(2, getLayoutPosition());
                    break;
                case R.id.lable_button3:
                    onItemClickListener.OnClickRecyButton(3, getLayoutPosition());
                    break;
//                case addshopcar:
//                    onItemClickListener.OnClickRecyButton(4, getLayoutPosition());
//                    break;
                case R.id.tv_SearchMoney:
                    String trim = searchmoney.getText().toString().trim();
                    if ("登录看价格".equals(trim)){
                        ((SearchActivity)context).startBaseActivity(context,LoginActivity.class);
                    }else if ("认证看价格".equals(trim)){
//                        startWebViewActivity(Config.ATTESTATION);
                        ((SimpleTopbarActivity)context).showStartAuthorDialog(AuthorActivity.class);
                    }
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
    private void startWebViewActivity(String url) {
        Intent intent = new Intent(context, WebViewH5Activity.class);
        intent.putExtra("webViewUrl", url);
        context.startActivity(intent);
    }
}
