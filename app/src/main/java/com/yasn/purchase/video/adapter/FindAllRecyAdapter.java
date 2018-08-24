package com.yasn.purchase.video.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.yasn.purchase.R;
import com.yasn.purchase.adapter.GridViewImageAdapter;
import com.yasn.purchase.listener.OnRcItemClickListener;
import com.yasn.purchase.model.FindAllModel;
import com.yasn.purchase.view.NoScrollGridView;

import java.util.List;

import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_IMAGE;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_TXT;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_VIDEO;

/**
 * Created by shuyu on 2016/11/11.
 */

public class FindAllRecyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static String TAG = "TT2";

    private List<FindAllModel.DataBean> list;
    private LayoutInflater inflater = null;
    private Context context;

    private ViewGroup rootView;
    private OrientationUtils orientationUtils;

    private boolean isFullVideo;

    private ListVideoUtil listVideoUtil;

    public OnRcItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnRcItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FindAllRecyAdapter(Context context, ListVideoUtil listVideoUtil) {
        super();
        this.context = context;
        this.listVideoUtil = listVideoUtil;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<FindAllModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        FindAllModel.DataBean dataBean = list.get(position);
        return dataBean.getFileType();

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_TXT:
                view = inflater.inflate(R.layout.listitem_txt_find, null);
                holder = new TxtViewHolder(view);
                break;

            case TYPE_IMAGE:
                view = inflater.inflate(R.layout.listitem_image_find, null);
                holder = new ImageViewHolder(view);
                break;

            case TYPE_VIDEO:
                view = inflater.inflate(R.layout.listitem_video_find, null);
                holder = new VideoViewHolder(view);
                break;

            case ITEM_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.footview_listview, parent, false);
                holder = new ViewHolderFootView(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FindAllModel.DataBean dataBean = list.get(position);
        String contentString = dataBean.getContent();
        int modifyTime = dataBean.getModifyTime();
//        String fileUrl = dataBean.getFileUrl();
        switch (getItemViewType(position)) {
            case TYPE_TXT:
                final TxtViewHolder holdertxt = (TxtViewHolder) holder;
                holdertxt.find_title.setText(contentString==null?"":contentString);
                holdertxt.find_time.setText(HelpUtils.getDateToString(modifyTime));
                break;
            case TYPE_IMAGE:
                ImageViewHolder holderImage = (ImageViewHolder) holder;

                holderImage.find_title.setText(contentString==null?"":contentString);
                holderImage.find_time.setText(HelpUtils.getDateToString(modifyTime));

                List<String> fileUrlMin = dataBean.getFileUrlMin();
                GridViewImageAdapter gridViewAdapter = new GridViewImageAdapter(context, fileUrlMin);
                holderImage.iamge_gridview.setAdapter(gridViewAdapter);
                break;
            case TYPE_VIDEO:
                VideoViewHolder holderVideo = (VideoViewHolder) holder;
                holderVideo.find_title.setText(contentString==null?"":contentString);
                holderVideo.find_time.setText(HelpUtils.getDateToString(modifyTime));
                listVideoUtil.addVideoPlayer(position, holderVideo.imageView, TAG, holderVideo.videoContainer, holderVideo.playerBtn);
                onItemEventClick(holderVideo);
                break;
        }
    }

    class TxtViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView titleImage;
        TextView find_title;
        TextView find_time;

        public TxtViewHolder(View itemView) {
            super(itemView);
            titleImage = (ImageView) itemView.findViewById(R.id.titleImage);
            find_title = (TextView) itemView.findViewById(R.id.title_findtxt);
            find_time = (TextView) itemView.findViewById(R.id.time_findtxt);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_titlemore:
                    onItemClickListener.OnClickTabMore(getLayoutPosition());
                    break;
            }
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView titleImage;
        TextView find_title;
        TextView find_time;
        NoScrollGridView iamge_gridview;

        public ImageViewHolder(View itemView) {
            super(itemView);
            titleImage = (ImageView) itemView.findViewById(R.id.titleImage);
            find_title = (TextView) itemView.findViewById(R.id.title_findtxt);
            find_time = (TextView) itemView.findViewById(R.id.time_findtxt);

            iamge_gridview = (NoScrollGridView) itemView.findViewById(R.id.iamge_gridview);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_titlemore:
                    onItemClickListener.OnClickTabMore(getLayoutPosition());
                    break;
            }
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView titleImage;
        TextView find_title;
        TextView find_time;

        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            titleImage = (ImageView) itemView.findViewById(R.id.titleImage);
            find_title = (TextView) itemView.findViewById(R.id.title_findtxt);
            find_time = (TextView) itemView.findViewById(R.id.time_findtxt);

            videoContainer = (FrameLayout) itemView.findViewById(R.id.list_item_container);
            playerBtn = (ImageView) itemView.findViewById(R.id.list_item_btn);
            playerBtn.setOnClickListener(this);
            imageView = new ImageView(context);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.OnClickTabMore(getLayoutPosition());
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

    class ViewHolderFootView extends RecyclerView.ViewHolder {
        LinearLayout footview;

        public ViewHolderFootView(View itemView) {
            super(itemView);
            footview = (LinearLayout) itemView.findViewById(R.id.footview);
        }
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }
}
