package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;

import java.util.List;
import java.util.Map;

/**
 * Created by gs on 2018/1/3.
 */

public class ShopFuncAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater mInflater;
    private  List<Map<String,Integer>> mainClassify;
    private OnRcItemClickListener onItemClickListener;
    private Context context;
    public ShopFuncAdapter(Context context,  List<Map<String,Integer>> mainClassify){
        this.context = context;
        this.mInflater=LayoutInflater.from(context);
        this.mainClassify=mainClassify;
    }
    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    /**
     * item显示类型
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.recycleritem_shopfunc,parent,false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    /**
     * 数据的绑定显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder shopholder = (ViewHolder) holder;

        Map<String,Integer> map= mainClassify.get(position);
        int imageUrl = map.get("ImageUrl");
        shopholder.topImage.setBackgroundResource(imageUrl);
        int titleString = map.get("nameTitle");
        shopholder.nameTitle.setText(titleString);
        onItemEventClick(shopholder);
    }

    @Override
    public int getItemCount() {
        return mainClassify==null?0:mainClassify.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
   class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTitle;
        public ImageView topImage;
        public ViewHolder(View view){
            super(view);
            nameTitle = (TextView)view.findViewById(R.id.nameTitle);
            topImage = (ImageView)view.findViewById(R.id.topImage);
        }
    }
    private void onItemEventClick(final RecyclerView.ViewHolder holder) {
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
