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
import com.yasn.purchase.model.IntegralFreezeModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.help.HelpUtils;

/**
 * /**
 * 常购清单
 * Created by gs on 2017/12/29.
 */

public class IntegralFreezeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int TYPE_ITEM = 1;
    public final static int TYPE_FOOTER = 2;
    private Context context;
    private List<IntegralFreezeModel.DataBean.FreezePointListBean> list;
    private List<IntegralFreezeModel.DataBean.FreezePointListBean> addList;
    private LinearLayoutManager linearLayoutManager;

    private Map viewHolderMap = new HashMap<>();

    public IntegralFreezeAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;

    }

    public void setData(List<IntegralFreezeModel.DataBean.FreezePointListBean> list) {
        this.addList = list;
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<IntegralFreezeModel.DataBean.FreezePointListBean> list) {
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

    public void upFootText() {
        Map viewHolderMap = getViewHolderMap();
        FootViewHolder holder = (FootViewHolder) viewHolderMap.get("holder");
        holder.progressBar.setVisibility(View.GONE);
        holder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
    }

    public List<IntegralFreezeModel.DataBean.FreezePointListBean> getData() {
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
                IntegralFreezeModel.DataBean.FreezePointListBean freezePointListBean = list.get(position);
                //原因
                String reason = null;
                String type = freezePointListBean.getType();
                if ("order_pay_use".equals(type)) {
                    reason = "订单消费积分";
                } else if ("order_pay_get".equals(type)) {
                    reason = "订单获得积分";
                } else if ("register".equals(type)) {
                    reason = "注册";
                } else if ("email_check".equals(type)) {
                    reason = "邮箱验证";
                } else if ("finish_profile".equals(type)) {
                    reason = "完善个人资料";
                } else if ("buygoods".equals(type)) {
                    reason = "购买商品";
                } else if ("onlinepay".equals(type)) {
                    reason = "在线支付";
                } else if ("operator_adjust".equals(type)) {
                    reason = "管理员改变积分";
                } else if ("consume_gift".equals(type)) {
                    reason = "积分换赠品";
                } else if ("login".equals(type)) {
                    reason = "登录";
                } else if ("comment".equals(type)) {
                    reason = "发表评价";
                } else if ("comment_img".equals(type)) {
                    reason = "贴图评价";
                } else if ("first_comment".equals(type)) {
                    reason = "首次评论";
                } else if ("register_link'".equals(type)) {
                    reason = "推广连接";
                } else if ("activity_point".equals(type)) {
                    reason = "促销活动获得积分";
                }

                holderItem.tvReason.setText(reason == null ? "" : reason);
                //时间
                long time = freezePointListBean.getDateline();
                String lastTimeString = HelpUtils.getDateToHms(time);
                holderItem.tvTime.setText(lastTimeString);
                //积分
                int mp = freezePointListBean.getMp();
                holderItem.tvRight.setText(String.valueOf(mp));
                break;
            case TYPE_FOOTER:
                FootViewHolder footviewholder = (FootViewHolder) holder;
                if (list == null || list.size() == 0) {
                    footviewholder.footView.setVisibility(View.GONE);
                } else {
                    footviewholder.footView.setVisibility(View.VISIBLE);
                    Log.e("TAG_底部", "addList=" + (addList == null));
                    if (addList == null || addList.size() == 0) {
                        footviewholder.progressBar.setVisibility(View.GONE);
                        footviewholder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
                    } else {
                        Log.e("TAG_底部", "addList=" + (addList.size()));
                        int visibleItemCount = linearLayoutManager.getChildCount();
                        Log.e("TAG_底部", "visibleItemCount=" + visibleItemCount + ";list=" + list.size());
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

