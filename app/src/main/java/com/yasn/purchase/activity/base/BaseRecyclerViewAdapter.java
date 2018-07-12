package com.yasn.purchase.activity.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by gs on 2018/7/12.
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
}
