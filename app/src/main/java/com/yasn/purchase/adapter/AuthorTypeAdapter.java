package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.model.AuthorMemberInfoModel;

import java.util.List;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class AuthorTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<AuthorMemberInfoModel.DataBean.AuthenticationTypeBean> mData;
    private Context mContext;

    public AuthorTypeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    public void setData(List<AuthorMemberInfoModel.DataBean.AuthenticationTypeBean> data){
        mData = data;
        notifyDataSetChanged();
    }
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_authortype, parent,false);
        TypeViewHolder viewHolder = new TypeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        TypeViewHolder viewHolder = (TypeViewHolder) holder;
        AuthorMemberInfoModel.DataBean.AuthenticationTypeBean authenticationTypeBean = mData.get(position);
        int id = authenticationTypeBean.getId();
        if (authenticationTypeBean.isChecked()){
            viewHolder.ivAuthorType.setBackgroundResource(R.mipmap.checkbox_checked);
        }else {
            viewHolder.ivAuthorType.setBackgroundResource(R.mipmap.checkbox_unchecked);
        }

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }
        viewHolder.tvName.setText(authenticationTypeBean.getType());
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivAuthorType;
        public TypeViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivAuthorType = (ImageView) itemView.findViewById(R.id.iv_AuthorType);
        }
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<AuthorMemberInfoModel.DataBean.AuthenticationTypeBean> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }
}
