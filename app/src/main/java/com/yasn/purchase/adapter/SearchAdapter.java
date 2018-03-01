package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.SearchModel;

import java.util.List;

import www.xcd.com.mylibrary.utils.SharePrefHelper;

import static com.yasn.purchase.R.id.addshopcar;


/**
 * Created by gs on 2017/12/29.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Context context;
    private List<SearchModel.DataBean> list;
    private OnRcItemClickListener onItemClickListener;
    private String loginState;
    private boolean isShowProgressBar;

    public void setShowProgressBar(boolean isShowProgressBar) {
        this.isShowProgressBar = isShowProgressBar;
    }

    public SearchAdapter(Context context) {
        super();
        this.context = context;
        loginState = SharePrefHelper.getInstance(context).getSpString("loginState");
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData( List<SearchModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData( List<SearchModel.DataBean> list) {
        if (this.list != null){
            this.list.addAll(list);
        }else {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public List<SearchModel.DataBean> getData(){
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

        switch (viewType){
            case TYPE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_search, parent, false);
                holder = new ViewHolderSearch(view);
                break;

            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent,
                        false);
                holder = new FootViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case TYPE_ITEM:
                ViewHolderSearch holderSearch = (ViewHolderSearch) holder;
                SearchModel.DataBean dataBean = list.get(position);
                StringBuffer sb = new StringBuffer();
                int goneNum = 0;
                int store_id = dataBean.getStore_id();
                if (store_id == 1){
                    sb.append("自营 ");
                    goneNum += 3;
                    holderSearch.autotrophy.setVisibility(View.VISIBLE);
                }else {
                    holderSearch.autotrophy.setVisibility(View.GONE);
                }

                int is_limit_buy = dataBean.getIs_limit_buy();
                if (is_limit_buy>0){
                    holderSearch.purchase.setVisibility(View.VISIBLE);
                    sb.append("限购 ");
                    goneNum += 3;
                }else {
                    holderSearch.purchase.setVisibility(View.GONE);
                }
                String is_before_sale = dataBean.getIs_before_sale();
                if ("1".equals(is_before_sale)){
                    holderSearch.presell.setVisibility(View.VISIBLE);
                    goneNum += 3;
                    sb.append("预售 ");
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

                if ("0".equals(loginState)){
                    holderSearch.searchmoney.setText(String.valueOf("￥"+dataBean.getPrice()));
                }else {
                    holderSearch.searchmoney.setText(loginState == null?"登录看价格":loginState);
                }

                String minNumberString = String.format("已售%s笔", String.valueOf(dataBean.getTotal_buy_count()));
                SpannableStringBuilder countRecySpan = new SpannableStringBuilder(minNumberString);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), 0, 2,
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                countRecySpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.black_99)), minNumberString.length() - 1, minNumberString.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holderSearch.searchcount.setText(countRecySpan);

                String has_discount = dataBean.getHas_discount();
                if ("1".equals(has_discount)){
                    holderSearch.buyingspreeLinear.setVisibility(View.VISIBLE);
                    holderSearch.buyingspreeMoney.setText(dataBean.getDiscount_price());
                }else {
                    holderSearch.buyingspreeLinear.setVisibility(View.GONE);
                }
                Glide.with(context)
                        .load(dataBean.getThumbnail())
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(holderSearch.titleimage);
                onItemEventClick(holderSearch);
                break;
            case TYPE_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;
                if (list == null||list.size()==0){
                    footviewholder.footView.setVisibility(View.GONE);
                }else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    if (isShowProgressBar){
                        footviewholder.progressBar.setVisibility(View.VISIBLE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.pullup_to_load));
                    }else {
                        footviewholder.progressBar.setVisibility(View.GONE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.pullup_to_load));
                    }
                }
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size()+1;
    }


    class ViewHolderSearch extends RecyclerView.ViewHolder implements View.OnClickListener {
       TextView searchTitle;
        TextView searchmoney, searchAdvert, searchcount;
        TextView button1, button2, button3;
        TextView autotrophy, purchase,presell;
        ImageView titleimage;
        LinearLayout buyingspreeLinear;
        TextView buyingspreeMoney;
        public ViewHolderSearch(View itemView) {
            super(itemView);
            searchTitle = (TextView) itemView.findViewById(R.id.title);
            searchmoney = (TextView) itemView.findViewById(R.id.search_money);
            searchAdvert = (TextView) itemView.findViewById(R.id.search_action);
            searchcount = (TextView) itemView.findViewById(R.id.search_count);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
            titleimage = (ImageView) itemView.findViewById(R.id.titleimage);
            buyingspreeLinear = (LinearLayout) itemView.findViewById(R.id.buyingspreeLinear);
            buyingspreeMoney = (TextView) itemView.findViewById(R.id.buyingspreeMoney);
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
                case addshopcar:
                    onItemClickListener.OnClickRecyButton(4, getLayoutPosition());
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
    protected void onItemEventClick(RecyclerView.ViewHolder holder) {
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
}
