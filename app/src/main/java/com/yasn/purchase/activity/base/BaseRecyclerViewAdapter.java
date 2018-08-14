package com.yasn.purchase.activity.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yasn.purchase.R;
import com.yasn.purchase.holder.FootViewHolder;

import java.util.HashMap;
import java.util.Map;

import static www.xcd.com.mylibrary.utils.SharePrefHelper.context;

/**
 * Created by gs on 2018/7/12.
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_FOOTER = 1;
    private Map viewHolderMap = new HashMap<>();
    private Map getViewHolderMap() {
        return viewHolderMap;
    }
    public void upFootText(){
        Map viewHolderMap = getViewHolderMap();
        FootViewHolder holder = (FootViewHolder) viewHolderMap.get("holder");
        holder.progressBar.setVisibility(View.GONE);
        holder.footText.setText(context.getResources().getString(R.string.unpullup_to_load));
    }
}
