package com.yasn.purchase.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yasn.purchase.R;

/**
 * Created by gs on 2018/7/23.
 */

public class FootViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout footView;
    public ProgressBar progressBar;
    public TextView footText;

    public FootViewHolder(View view) {
        super(view);
        footView = (LinearLayout) itemView.findViewById(R.id.footView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        footText = (TextView) itemView.findViewById(R.id.footText);
    }

}