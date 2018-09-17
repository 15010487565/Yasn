package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.holder.FootViewHolder;
import com.yasn.purchase.model.MakerExploitshopModel;

import java.util.List;

import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_ONE;

/**
 * /**
 * 常购清单
 * Created by gs on 2017/12/29.
 */

public class MakerShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MakerExploitshopModel.DataBean> list;

    public MakerShopAdapter(Context context) {
        super();
        this.context = context;

    }

    public void setData(List<MakerExploitshopModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
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
                view = LayoutInflater.from(context).inflate(R.layout.recycleritem_makershop, parent, false);
                holder = new ViewHolderItem(view);
                break;
            case ITEM_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
                holder = new FootViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ONE:
                ViewHolderItem viwHoder = (ViewHolderItem) holder;
                MakerExploitshopModel.DataBean dataBean = list.get(position);
                String shopName = dataBean.getShopName();
                viwHoder.tvMakerShopName.setText(shopName == null ? "" : shopName);
                String address = dataBean.getAddress();
                viwHoder.tvMakerShopAddress.setText(address == null ? "" : address);
                String mobile = dataBean.getMobile();
                String substring1 = mobile.substring(0, 3);
                String substring2 = mobile.substring(mobile.length() - 4, mobile.length());
                String mobileSub = substring1 + "****" +substring2;
                viwHoder.tvMakerShopMobile.setText(mobileSub == null ? "" : mobileSub);
                long time = dataBean.getTime();
                String dateToString1 = HelpUtils.getDateToString1(time);
                viwHoder.tvMakerShopTime.setText(dateToString1 == null ? "0000-00-00 00:00" : dateToString1);
                break;
            case ITEM_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;
                if (list == null || list.size() == 0) {
                    footviewholder.footView.setVisibility(View.GONE);
                } else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    footviewholder.progressBar.setVisibility(View.GONE);
                    footviewholder.footText.setText("亲，您已经把人家看光了~");
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size()+1);
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView tvMakerShopName;
        TextView tvMakerShopAddress, tvMakerShopMobile, tvMakerShopTime;
        public ViewHolderItem(View itemView) {
            super(itemView);
            tvMakerShopName = (TextView) itemView.findViewById(R.id.tv_MakerShopName);
            tvMakerShopAddress = (TextView) itemView.findViewById(R.id.tv_MakerShopAddress);
            tvMakerShopMobile = (TextView) itemView.findViewById(R.id.tv_MakerShopMobile);
            tvMakerShopTime = (TextView) itemView.findViewById(R.id.tv_MakerShopTime);
        }
    }
}

