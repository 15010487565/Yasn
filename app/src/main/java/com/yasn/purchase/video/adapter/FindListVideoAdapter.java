package com.yasn.purchase.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.yasn.purchase.R;
import com.yasn.purchase.video.model.FindVideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuyu on 2016/11/11.
 */

public class FindListVideoAdapter extends BaseAdapter {

    public final static String TAG = "TT2";

    private List<FindVideoModel> list = new ArrayList<>();
    private LayoutInflater inflater = null;
    private Context context;

    private ViewGroup rootView;
    private OrientationUtils orientationUtils;

    private boolean isFullVideo;

    private ListVideoUtil listVideoUtil;

    public FindListVideoAdapter(Context context, ListVideoUtil listVideoUtil) {
        super();
        this.context = context;
        this.listVideoUtil = listVideoUtil;

        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 40; i++) {
            list.add(new FindVideoModel());
        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitem_video_find, null);
            holder.find_title = (TextView) convertView.findViewById(R.id.title_findtxt);
            holder.time_findtxt = (TextView) convertView.findViewById(R.id.time_findtxt);
            holder.titleImage = (ImageView) convertView.findViewById(R.id.titleImage);

            holder.videoContainer = (FrameLayout) convertView.findViewById(R.id.list_item_container);
            holder.playerBtn = (ImageView) convertView.findViewById(R.id.list_item_btn);
            holder.imageView = new ImageView(context);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //增加封面
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        listVideoUtil.addVideoPlayer(position, holder.imageView, TAG, holder.videoContainer, holder.playerBtn);
        holder.playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);
                final String url = "https://res.exexm.com/cw_145225549855002";
                listVideoUtil.setTitle("title " + position);
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(url);

                //必须在startPlay之后设置才能生效
                //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView find_title,time_findtxt;
        ImageView titleImage;

        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }
}
