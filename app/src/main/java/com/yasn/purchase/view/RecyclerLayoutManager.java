package com.yasn.purchase.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by gs on 2017/12/27.
 */

public class RecyclerLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public RecyclerLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
