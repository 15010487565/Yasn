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
import com.yasn.purchase.model.MakerShopOrderModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ONE;

/**
 * /**
 * 常购清单
 * Created by gs on 2017/12/29.
 */

public class MakerShopOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<MakerShopOrderModel.DataBean> list;
    private List<MakerShopOrderModel.DataBean> addList;
    private Map viewHolderMap = new HashMap<>();
    private LinearLayoutManager linearLayoutManager;

    public MakerShopOrderAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setData(List<MakerShopOrderModel.DataBean> list) {
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<MakerShopOrderModel.DataBean> list) {
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

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_FOOTER;
        } else {
            return TYPE_ONE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ONE:
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_makershoporder, parent, false);
                holder = new ViewHolderItem(view);
                break;
            case ITEM_FOOTER:
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
            case TYPE_ONE:
                ViewHolderItem viwHoder = (ViewHolderItem) holder;
                MakerShopOrderModel.DataBean dataBean = list.get(position);

                String shopName = dataBean.getShop_name();
                viwHoder.tvOrderName.setText(shopName == null ? "" : shopName);
                String sn = dataBean.getSn();
                viwHoder.tvOrderNumber.setText(sn == null ? "" : sn);
                String statusText = dataBean.getStatusText();
                viwHoder.tvOrderStatus.setText(statusText == null ? "" : statusText);
                double needPayMoney = dataBean.getNeedPayMoney();
                String strMoney = String.format("%.2f", needPayMoney);
                viwHoder.tvOrderMoney.setText(strMoney == null ? "" : ("￥" + strMoney));
                long time = dataBean.getCreateTime();
                String dateToString1 = HelpUtils.getDateToString1(time);
                viwHoder.tvOrderTime.setText(dateToString1 == null ? "0000-00-00 00:00" : dateToString1);
                String mobile = dataBean.getMobile();
                String substring1 = mobile.substring(0, 3);
                String substring2 = mobile.substring(mobile.length() - 4, mobile.length());
                String mobileSub = substring1 + "****" +substring2;
                viwHoder.tvOrderMobile.setText(mobileSub == null ? "" : mobileSub);
                break;
            case ITEM_FOOTER:
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
                        Log.e("TAG_底部","visibleItemCount="+visibleItemCount+";list="+list.size());
                        if (visibleItemCount <= list.size()&&visibleItemCount<=addList.size()) {
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
        return list == null ? 0 : (list.size()+1);
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView tvOrderName, tvOrderNumber, tvOrderMoney, tvOrderTime, tvOrderMobile, tvOrderStatus;
        public ViewHolderItem(View itemView) {
            super(itemView);
            tvOrderName = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderName);
            tvOrderNumber = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderNumber);
            tvOrderMoney = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderMoney);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderTime);
            tvOrderMobile = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderMobile);
            tvOrderStatus = (TextView) itemView.findViewById(R.id.tv_MakerShopOrderStatus);
        }
    }
}

