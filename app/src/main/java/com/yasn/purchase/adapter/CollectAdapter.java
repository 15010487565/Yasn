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
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HighProfitModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gs on 2017/12/29.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<HighProfitModel> newses = new ArrayList<>();
    private OnRcItemClickListener onItemClickListener;

    public CollectAdapter(List<HighProfitModel> newses, Context context) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.recycleritem_collect, parent, false);
        holder = new ViewHolderTwo(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

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
        }else if (position == 6) {
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
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_black_66), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_black_66), soldout.length() - 1, soldout.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            styledText.setSpan(new TextAppearanceSpan(mContext, R.style.style_textcolor_orange), 2, soldout.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holderTwo.collect_count.setText(styledText, TextView.BufferType.SPANNABLE);
        } else {
            holderTwo.twomoney.setText(twonews.getMoney());
        }
        holderTwo.twoaction.setText(twonews.getText());
        onItemEventClick(holderTwo);

    }

    @Override
    public int getItemCount() {
        return newses==null?0:newses.size();
    }


    class ViewHolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView twotitle;
        //        MixtureTextView nameRecy;
        TextView twomoney, twoaction, collect_count;
        TextView button1, button2, button3;
        TextView autotrophy, purchase,presell;
        ImageView removeiamge;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            twotitle = (TextView) itemView.findViewById(R.id.title);
            twomoney = (TextView) itemView.findViewById(R.id.collect_money);
            twoaction = (TextView) itemView.findViewById(R.id.collect_action);
            collect_count = (TextView) itemView.findViewById(R.id.collect_count);

            button1 = (TextView) itemView.findViewById(R.id.lable_button1);
            button1.setOnClickListener(this);
            button2 = (TextView) itemView.findViewById(R.id.lable_button2);
            button2.setOnClickListener(this);
            button3 = (TextView) itemView.findViewById(R.id.lable_button3);
            button3.setOnClickListener(this);

            autotrophy = (TextView) itemView.findViewById(R.id.autotrophy);
            purchase = (TextView) itemView.findViewById(R.id.purchase);
            presell = (TextView) itemView.findViewById(R.id.presell);
            removeiamge = (ImageView) itemView.findViewById(R.id.removeiamge);
            removeiamge.setOnClickListener(this);
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
                case R.id.removeiamge:
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
}
