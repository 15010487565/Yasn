package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.MakerExploitshopModel;

import java.util.List;

import www.xcd.com.mylibrary.help.HelpUtils;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleritem_makershop, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolderItem(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : (list.size());
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

