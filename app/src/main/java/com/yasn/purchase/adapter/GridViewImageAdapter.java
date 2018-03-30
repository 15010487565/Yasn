package com.yasn.purchase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;

import java.util.List;

/**
 * Created by gs on 2018/1/8.
 */

public class GridViewImageAdapter extends BaseAdapter {
    private Context mContext;
    private   List<String> listFindImage;

    public GridViewImageAdapter(Context mContext, List<String> listFindImage) {
        super();
        this.mContext = mContext;
        this.listFindImage = listFindImage;
    }
    @Override
    public int getCount() {
        if (listFindImage == null) {
            return 0;
        } else {
            return this.listFindImage.size();
        }
    }
    @Override
    public Object getItem(int position) {
        if (listFindImage == null) {
            return null;
        } else {
            return this.listFindImage.get(position);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from
                    (this.mContext).inflate(R.layout.listview_gridview_video, null, false);
            holder.imageView = (ImageView)convertView.findViewById(R.id.itemImage);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.listFindImage != null) {
            String imageUrl = listFindImage.get(position);
            if (holder.imageView != null) {
                Glide.with(mContext)
                        .load(imageUrl)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.imageView);
//                holder.tv_spec.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "第"+(position+1)+"个", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
