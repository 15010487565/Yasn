package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.model.SortModel;

import java.util.List;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<SortModel> mData;
    private Context mContext;

    public SortAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }
    public void setData(List<SortModel> data){
        mData = data;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_sort, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
        viewHolder.sortlogo = (ImageView) view.findViewById(R.id.sortlogo);
        viewHolder.selectimage = (ImageView) view.findViewById(R.id.selectiamge);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SortModel sortModel = mData.get(position);
        if (sortModel.isChecked()){
            holder.selectimage.setVisibility(View.VISIBLE);
        }else {
            holder.selectimage.setVisibility(View.GONE);
        }
        String logo = sortModel.getLogo();
//        Log.e("TAG_车型图片","logo="+logo);
        Glide.with(mContext.getApplicationContext())
                .load(logo)
                .fitCenter()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into( holder.sortlogo);

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });

        }
        holder.tvName.setText(sortModel.getName());
//
//        holder.tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, mData.get(position).getName(),Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView sortlogo, selectimage;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给Activity刷新数据
     * @param list
     */
    public void updateList(List<SortModel> list){
        this.mData = list;
        notifyDataSetChanged();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
