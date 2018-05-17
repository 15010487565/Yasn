package com.yasn.purchasetest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.yasn.purchasetest.R;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    private int[] images;
    private LayoutInflater inflater;
    private OnClickListener listener;

    private List<String> imageUrls = new ArrayList<>();
    private Context context;

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public GuideAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(int[] images) {
        this.images = images;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object object) {
        viewGroup.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (imageUrls != null && imageUrls.size() > 0) {
            return imageUrls.size();
        } else {
            return images.length;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.item_guide, container, false);
        if (imageUrls != null && imageUrls.size() > 0) {
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Glide.with(context).load(imageUrls.get(position)).into(imageView);
        } else {
            view.findViewById(R.id.imageView).setBackgroundResource(images[position]);
        }
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        ImageView main = (ImageView) view.findViewById(R.id.main);
        ImageView register = (ImageView) view.findViewById(R.id.register);
        if (imageUrls != null && imageUrls.size() > 0) {
            if (position == (imageUrls.size() - 1)) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        } else {
            if (position == (images.length - 1)) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }

        }
        main.setOnClickListener(listener);
        register.setOnClickListener(listener);
        container.addView(view);
        return view;
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}
