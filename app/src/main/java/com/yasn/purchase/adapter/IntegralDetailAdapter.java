package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.holder.FootViewHolder;
import com.yasn.purchase.model.IntegralDetailModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

/**
 * /**
 * 常购清单
 * Created by gs on 2017/12/29.
 */

public class IntegralDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ITEM = 1;
    public final static int TYPE_FOOTER = 2;
    private Context context;
    private List<IntegralDetailModel.DataBean.PointHistoryListBean> list;
    private List<IntegralDetailModel.DataBean.PointHistoryListBean> addList;
    private LinearLayoutManager linearLayoutManager;

    private Map viewHolderMap = new HashMap<>();

    public IntegralDetailAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;

    }

    public void setData(List<IntegralDetailModel.DataBean.PointHistoryListBean> list) {
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<IntegralDetailModel.DataBean.PointHistoryListBean> list) {
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

    public List<IntegralDetailModel.DataBean.PointHistoryListBean> getData(){
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
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_integral, parent, false);
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
                IntegralDetailModel.DataBean.PointHistoryListBean pointHistoryListBean = list.get(position);
                //原因
                String reason = pointHistoryListBean.getReason();
                holderItem.tvReason.setText(reason ==null?"":reason);
                //时间
                long time = pointHistoryListBean.getTime();
                String lastTimeString = HelpUtils.getDateToHms(time);
                holderItem.tvTime.setText(lastTimeString);
                //积分
                int mp = pointHistoryListBean.getMp();
                holderItem.tvRight.setText(String.valueOf(mp));
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

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView tvReason;
        TextView tvTime, tvRight;
        public ViewHolderItem(View itemView) {
            super(itemView);
            tvReason = (TextView) itemView.findViewById(R.id.tv_IntegralReason);
            tvTime = (TextView) itemView.findViewById(R.id.tv_IntegralTime);
            tvRight = (TextView) itemView.findViewById(R.id.tv_IntegralRight);
        }
    }
}

