package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HighProfitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class HighProfitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;
    private Context mContext;
    private List<HighProfitModel> newses = new ArrayList<>();
    private OnRcItemClickListener onItemClickListener;

    public HighProfitAdapter(List<HighProfitModel> newses, Context context) {
        super();
        this.mContext = context;
        this.newses = newses;
    }

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void add(HighProfitModel news) {
        this.newses.add(news);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return newses.get(position).getItemType();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(mContext).inflate(R.layout.fragment_homelistitemone, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case TYPE_TWO:
                view = LayoutInflater.from(mContext).inflate(R.layout.recycleritem_highprofit, parent, false);
                holder = new ViewHolderTwo(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ONE:
                final ViewHolderOne holderOne = (ViewHolderOne) holder;
                HighProfitModel news = newses.get(position);
                holderOne.hometitle.setText(news.getText());
                onItemEventClick(holderOne);
                break;
            case TYPE_TWO:
                ViewHolderTwo holderTwo = (ViewHolderTwo) holder;
                HighProfitModel twonews = newses.get(position);
                if (position == 2) {
                    holderTwo.autotrophy.setVisibility(View.GONE);
                    holderTwo.purchase.setVisibility(View.VISIBLE);
                    holderTwo.presell.setVisibility(View.GONE);
                    SpannableStringBuilder span = new SpannableStringBuilder("限购"+twonews.getText());
                    span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.white)), 0, 2,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holderTwo.twotitle.setText(span);
                } else if (position == 3) {
                    holderTwo.autotrophy.setVisibility(View.VISIBLE);
                    holderTwo.purchase.setVisibility(View.GONE);
                    holderTwo.presell.setVisibility(View.GONE);
                    SpannableStringBuilder span = new SpannableStringBuilder("自营"+twonews.getText());
                    span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.white)), 0, 2,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holderTwo.twotitle.setText(span);
                } else if (position == 5) {
                    holderTwo.autotrophy.setVisibility(View.VISIBLE);
                    holderTwo.purchase.setVisibility(View.VISIBLE);
                    holderTwo.presell.setVisibility(View.GONE);
                    SpannableStringBuilder span = new SpannableStringBuilder("自营限购"+twonews.getText());
                    span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.white)), 0, 4,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holderTwo.twotitle.setText(span);
                } else if (position == 6) {
                    holderTwo.autotrophy.setVisibility(View.VISIBLE);
                    holderTwo.purchase.setVisibility(View.VISIBLE);
                    holderTwo.presell.setVisibility(View.VISIBLE);
                    SpannableStringBuilder span = new SpannableStringBuilder("自营限购预售"+twonews.getText());
                    span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.white)), 0, 6,
                            Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    holderTwo.twotitle.setText(span);
                } else {
                    holderTwo.autotrophy.setVisibility(View.GONE);
                    holderTwo.purchase.setVisibility(View.GONE);
                    holderTwo.presell.setVisibility(View.GONE);
                    holderTwo.twotitle.setText(twonews.getText());
                }
                if (position == 3) {
                    holderTwo.twomoney.setText("认证看价格");
                    String soldout = "已售1234笔";
                    SpannableString styledText = new SpannableString(soldout);
                    styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_black_66), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_black_66), soldout.length() - 1, soldout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_orange), 2, soldout.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holderTwo.home_count.setText(styledText, TextView.BufferType.SPANNABLE);
                } else {
                    holderTwo.twomoney.setText(twonews.getMoney());
                }
                holderTwo.twoaction.setText(twonews.getText());
                onItemEventClick(holderTwo);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newses == null?0:newses.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hometitle, home_more;

        public ViewHolderOne(View itemView) {
            super(itemView);
            hometitle = (TextView) itemView.findViewById(R.id.home_title);
            home_more = (TextView) itemView.findViewById(R.id.home_titlemore);
            home_more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_titlemore:
                    onItemClickListener.OnClickTabMore(getLayoutPosition());
                    break;
            }
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView twotitle;
        //        MixtureTextView nameRecy;
        TextView twomoney, twoaction, home_count;
        TextView button1, button2, button3;
        TextView autotrophy, purchase,presell;
//        ImageView addshopcar;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            twotitle = (TextView) itemView.findViewById(R.id.title);
            twomoney = (TextView) itemView.findViewById(R.id.highprofit_money);
            twoaction = (TextView) itemView.findViewById(R.id.highprofit_action);
            home_count = (TextView) itemView.findViewById(R.id.highprofit_count);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
//            addshopcar = (ImageView) itemView.findViewById(R.id.addshopcar);
//            addshopcar.setOnClickListener(this);
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
                case R.id.addshopcar:
                    onItemClickListener.OnClickRecyButton(4, getLayoutPosition());
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.OnItemLongClick(v, position);
                return true;
            }
        });
    }
    private float sp2px(float spValue) {
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }
}
