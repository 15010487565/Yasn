package com.yasn.purchase.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yasn.purchase.R;
import com.yasn.purchase.activity.base.RecyclerViewCursorAdapter;
import com.yasn.purchase.listener.OnRcItemClickListener;

/**
 * Created by gs on 2018/1/5.
 */

public class RcCursorAdapter extends RecyclerViewCursorAdapter<RcCursorAdapter.MyViewHolder> {

    private OnRcItemClickListener onItemClickListener;
    private  LayoutInflater mLayoutInflater;

    public RcCursorAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public RcCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    // new view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recycleritem_historysearch, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        onItemEventClick(myViewHolder);
        return myViewHolder ;
    }

    // bind view
    @Override
    public void onBindViewHolder(final MyViewHolder holder, Cursor cursor) {
//        String name = cursor.getString(cursor.getColumnIndex(
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        Log.e("TAG_历史记录","name="+name);
        holder.contactName.setText(name);

    }

    @Override
    protected void onContentChanged() {

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView contactName;
        public MyViewHolder(View itemView) {
            super(itemView);
            contactName = (TextView) itemView.findViewById(R.id.historysearch_title);
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