package com.yasn.purchase.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yasn.purchase.R;
import com.yasn.purchase.activityold.WebViewH5Activity;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.HelpFragmentModel;

import java.util.List;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_CONTENT;
import static com.yasn.purchase.common.ItemTypeConfig.ITEM_HEADER;

/**
 * Created by gs on 2017/12/26.
 */

public class HelpRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HelpFragmentModel> list;
    private LayoutInflater inflater;

    public HelpRecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<HelpFragmentModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        HelpFragmentModel helpFragmentModel = list.get(position);
        int itemType = helpFragmentModel.getItemType();
        return itemType;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case ITEM_HEADER:
                view = inflater.inflate(R.layout.fragment_help_itemtitle, parent, false);
                holder = new ViewHolderTitle(view);
                break;
            case ITEM_CONTENT:
                view = inflater.inflate(R.layout.fragment_help_itemcontent, parent, false);
                holder = new ViewHolderContent(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        HelpFragmentModel helpFragmentModel = list.get(position);
        switch (getItemViewType(position)) {
            case ITEM_HEADER:
                ViewHolderTitle holderTitle = (ViewHolderTitle) holder;
                holderTitle.tvHelpItemTitle.setText(helpFragmentModel.getTitle());
//                getHtmlData(intro,webView);
                Glide.with(context)
                        .load(helpFragmentModel.getDescript())
                        .fitCenter()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.errorimage)
                        .error(R.mipmap.errorimage)
                        .into(holderTitle.ivHelpItemTitle);
                onItemEventClick(holderTitle);
                break;
            case ITEM_CONTENT:
                ViewHolderContent holderContent = (ViewHolderContent) holder;
                String title = helpFragmentModel.getTitle();
                holderContent.tvHelpItemContent.setText(title);
                String descript = helpFragmentModel.getContent();
                getHtmlData(descript,holderContent.wbHelpItemContent);
                onItemEventClick(holderContent);
                break;
        }
    }

    public void getHtmlData(String bodyHTML, WebView webView) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        webView.loadData("<html>" + head + "<body bgcolor=\"#f5f5f5\">" + bodyHTML + "</body></html>", "text/html; charset=UTF-8", null);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolderTitle extends RecyclerView.ViewHolder {
        ImageView ivHelpItemTitle;
        TextView tvHelpItemTitle;
        public ViewHolderTitle(View itemView) {
            super(itemView);
            ivHelpItemTitle = (ImageView) itemView.findViewById(R.id.iv_HelpItemTitle);
            tvHelpItemTitle = (TextView) itemView.findViewById(R.id.tv_HelpItemTitle);
        }
    }

    class ViewHolderContent extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvHelpItemContent;
        WebView wbHelpItemContent;
        ImageView ivHelpItemTitleRight;
        boolean isShowWebView = false;
        public ViewHolderContent(View itemView) {
            super(itemView);
            tvHelpItemContent = (TextView) itemView.findViewById(R.id.tv_HelpItemContent);
            tvHelpItemContent.setOnClickListener(this);
            ivHelpItemTitleRight = (ImageView) itemView.findViewById(R.id.iv_HelpItemTitleRight);
            wbHelpItemContent = (WebView) itemView.findViewById(R.id.wb_HelpItemContent);
            wbHelpItemContent.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_HelpItemContent:
                    if (!isShowWebView){
                        wbHelpItemContent.setVisibility(View.VISIBLE);
                        ivHelpItemTitleRight.setBackgroundResource(R.mipmap.shang);
                        isShowWebView = true;
                    }else {
                        wbHelpItemContent.setVisibility(View.GONE);
                        ivHelpItemTitleRight.setBackgroundResource(R.mipmap.xia);
                        isShowWebView = false;
                    }
                    break;
            }
        }
    }
    public OnRcItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
    private void startWebViewActivity(String url) {
        Intent intent = new Intent(context, WebViewH5Activity.class);
        intent.putExtra("webViewUrl", url);
        context.startActivity(intent);
    }
}

