package com.yasn.purchase.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.ClassifyLeftModel;
import com.yasn.purchase.view.ChoiceItemLayout;

import java.util.List;

import static com.yasn.purchase.common.Config.TYPE_FOOTVIEW;

/**
 * Created by gs on 2018/1/3.
 */

public class ClassifyLeftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mInflater;
    private List<ClassifyLeftModel> mainClassify;
    private Context context;

    public ClassifyLeftAdapter(Context context){
        this.context = context;
        this.mInflater=LayoutInflater.from(context);
    }

    public void setData(List contentList) {
        this.mainClassify = contentList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        ClassifyLeftModel baserecymodel = mainClassify.get(position);
        return baserecymodel.getItemType();

    }

    /**
     * item显示类型
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 1:
                view=mInflater.inflate(R.layout.recycleritem_left_classify,parent,false);
                holder = new ViewHolder(view);
                break;
            case TYPE_FOOTVIEW:
                view = LayoutInflater.from(context).inflate(R.layout.footview_listview, parent, false);
                holder = new ViewHolderFootView(view);
                break;
        }
        return holder;
    }
    /**
     * 数据的绑定显示
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                ViewHolder leftholder = (ViewHolder) holder;
                ClassifyLeftModel classifyleftinfo= mainClassify.get(position);

                ChoiceItemLayout layout = (ChoiceItemLayout) holder.itemView;
                boolean isChecked = classifyleftinfo.isChecked();
                layout.setChecked(isChecked);

                String titleString = classifyleftinfo.getTitle();
                if (isChecked){
                    leftholder.leftclassify.setTextColor(ContextCompat.getColor(context,R.color.orange));
                    leftholder.line.setBackgroundResource(R.color.orange);
                }else {
                    leftholder.leftclassify.setTextColor(ContextCompat.getColor(context,R.color.black_66));
                    leftholder.line.setBackgroundResource(R.color.white);
                }
                leftholder.leftclassify.setText(titleString);
                onItemEventClick(leftholder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mainClassify==null?0:mainClassify.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
   class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leftclassify;
        View line;
        public ViewHolder(View view){
            super(view);
            leftclassify = (TextView)view.findViewById(R.id.leftclassify);
            line = view.findViewById(R.id.line);
        }
    }
    public OnRcItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    class ViewHolderFootView extends RecyclerView.ViewHolder{
        LinearLayout footview;
        public ViewHolderFootView(View itemView) {
            super(itemView);
            footview = (LinearLayout) itemView.findViewById(R.id.footview);
        }
    }

    private void onItemEventClick(RecyclerView.ViewHolder holder) {
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
