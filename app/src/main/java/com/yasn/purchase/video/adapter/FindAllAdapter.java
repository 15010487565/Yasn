package com.yasn.purchase.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.yasn.purchase.R;
import com.yasn.purchase.activity.showbig.ShowBigPictrueActivitiy;
import com.yasn.purchase.adapter.GridViewImageAdapter;
import com.yasn.purchase.model.FindAllModel;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.view.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;

import www.xcd.com.mylibrary.base.view.XListViewFooter;
import www.xcd.com.mylibrary.help.HelpUtils;

import static com.yasn.purchase.common.ItemTypeConfig.ITEM_FOOTER;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_IMAGE;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_TXT;
import static com.yasn.purchase.common.ItemTypeConfig.TYPE_VIDEO;

/**
 * Created by shuyu on 2016/11/11.
 */

public class FindAllAdapter  extends BaseAdapter {

    public final static String TAG = "FINDALLADAPTER";

    private List<FindAllModel.DataBean> list;
    private LayoutInflater inflater = null;
    private Context context;

    private ViewGroup rootView;
    private OrientationUtils orientationUtils;

    private boolean isFullVideo;

    private ListVideoUtil listVideoUtil;

    public FindAllAdapter(Context context, ListVideoUtil listVideoUtil) {
        super();
        this.context = context;
        this.listVideoUtil = listVideoUtil;
        inflater = LayoutInflater.from(context);
    }
    public void setData(List<FindAllModel.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void addData(List<FindAllModel.DataBean> list) {
        if (this.list==null){
            this.list = list;
        }else {
            if (list !=null && list.size() > 0 ){
                this.list.addAll(list);
            }
        }
        notifyDataSetChanged();
    }
    public void cleanData(){
        this.list.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getViewTypeCount() {
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getCount()) {
            return ITEM_FOOTER;
        }else {
            FindAllModel.DataBean dataBean = list.get(position);
            int type = dataBean.getFileType();
            if(type==2){
                return TYPE_TXT;
            }else if(type==0){
                return TYPE_IMAGE;
            }else if(type==1){
                return TYPE_VIDEO;
            }else {
                return ITEM_FOOTER;
            }
        }
    }

    @Override
    public int getCount() {
        return list ==null?0:list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        VideoViewHolder videoHolder = null;
        TxtViewHolder txtHolder = null;
        ImageViewHolder imageHolder = null;
        FooterHolder footerholder = null;
        int itemType = getItemViewType(position);
        if (convertView == null) {
            switch (itemType){
                case TYPE_IMAGE:
                    imageHolder = new ImageViewHolder();
                    convertView = inflater.inflate(R.layout.listitem_image_find, null);
                    imageHolder.time_findtxt = (TextView) convertView.findViewById(R.id.time_findtxt);
                    imageHolder.titleImage = (ImageView) convertView.findViewById(R.id.titleImage);
                    imageHolder.mainBody = (TextView) convertView.findViewById(R.id.mainBody);

                    imageHolder.iamge_gridview = (NoScrollGridView) convertView.findViewById(R.id.iamge_gridview);
                    convertView.setTag(imageHolder);
                    break;
                case TYPE_TXT:
                    txtHolder = new TxtViewHolder();
                    convertView = inflater.inflate(R.layout.listitem_context_find, null);
                    txtHolder.time_findtxt = (TextView) convertView.findViewById(R.id.time_findtxt);
                    txtHolder.titleImage = (ImageView) convertView.findViewById(R.id.titleImage);
                    txtHolder.mainBody = (TextView) convertView.findViewById(R.id.mainBody);

                    convertView.setTag(txtHolder);
                    break;
                case TYPE_VIDEO:
                    videoHolder = new VideoViewHolder();
                    convertView = inflater.inflate(R.layout.listitem_video_find, null);
                    videoHolder.time_findtxt = (TextView) convertView.findViewById(R.id.time_findtxt);
                    videoHolder.titleImage = (ImageView) convertView.findViewById(R.id.titleImage);
                    videoHolder.mainBody = (TextView) convertView.findViewById(R.id.mainBody);

                    videoHolder.videoContainer = (FrameLayout) convertView.findViewById(R.id.list_item_container);
                    videoHolder.playerBtn = (ImageView) convertView.findViewById(R.id.list_item_btn);
                    videoHolder.imageView = new ImageView(context);
                    convertView.setTag(videoHolder);
                    break;
                case ITEM_FOOTER:
                    footerholder = new FooterHolder();
                    convertView = inflater.inflate(R.layout.xlistview_footer, parent,
                            false);
                    footerholder.mFooterView.setState(XListViewFooter.STATE_NORMAL);
                    convertView.setTag(footerholder);
                    break;
            }

        } else {
            switch (itemType){
                case TYPE_IMAGE:
                    imageHolder = (ImageViewHolder) convertView.getTag();
                    break;
                case TYPE_TXT:
                    txtHolder = (TxtViewHolder) convertView.getTag();
                    break;
                case TYPE_VIDEO:
                    videoHolder = (VideoViewHolder) convertView.getTag();
                    break;
                case ITEM_FOOTER:
                    footerholder = (FooterHolder) convertView.getTag();
                    break;
            }
        }
        if (position != list.size()){
            final FindAllModel.DataBean dataBean = list.get(position);
            String contentString = dataBean.getContent();//内容
            int modifyTime = dataBean.getModifyTime();
            String dateToString = HelpUtils.getDateToString(modifyTime);//时间

            switch (itemType){
                case TYPE_IMAGE:
                    if (contentString == null||"".equals(contentString)){
                        imageHolder.mainBody.setText("");
                        imageHolder.mainBody.setVisibility(View.GONE);
                    }else {
                        imageHolder.mainBody.setText(contentString);
                        imageHolder.mainBody.setVisibility(View.VISIBLE);
                    }
                    imageHolder.time_findtxt.setText(dateToString);
                    List<String> fileUrlMin = dataBean.getFileUrlMin();
                    GridViewImageAdapter gridViewAdapter=new GridViewImageAdapter(context, fileUrlMin);
                    imageHolder.iamge_gridview.setAdapter(gridViewAdapter);
                    imageHolder.iamge_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<String> fileUrlMax = (ArrayList<String>) dataBean.getFileUrlMax();
                            // 跳到查看大图界面
                            Intent intent = new Intent(context, ShowBigPictrueActivitiy.class);
                            intent.putStringArrayListExtra("ImageList", fileUrlMax);
                            intent.putExtra("position",position);
                            context.startActivity(intent);
                        }
                    });
                    break;
                case TYPE_TXT:
                    txtHolder.time_findtxt.setText(dateToString);
                    if (contentString == null||"".equals(contentString)){
                        txtHolder.mainBody.setText("");
                        txtHolder.mainBody.setVisibility(View.GONE);
                    }else {
                        txtHolder.mainBody.setText(contentString);
                        txtHolder.mainBody.setVisibility(View.VISIBLE);
                    }
                    break;
                case TYPE_VIDEO:
                    if (contentString == null||"".equals(contentString)){
                        videoHolder.mainBody.setText("");
                        videoHolder.mainBody.setVisibility(View.GONE);
                    }else {
                        videoHolder.mainBody.setText(contentString);
                        videoHolder.mainBody.setVisibility(View.VISIBLE);
                    }
                    videoHolder.time_findtxt.setText(dateToString);
                    //增加封面
                    videoHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(context.getApplicationContext())
                            .load(dataBean.getVideoShowImg())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(videoHolder.imageView);
                    listVideoUtil.addVideoPlayer(position, videoHolder.imageView, TAG, videoHolder.videoContainer, videoHolder.playerBtn);
                    final String fileUrl = dataBean.getFileUrl();
                    videoHolder.playerBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            notifyDataSetChanged();
                            Log.e("TAG_视频播放3","position="+position);
                            //listVideoUtil.setLoop(true);
                            listVideoUtil.setPlayPositionAndTag(position, TAG);
                            listVideoUtil.setTitle("title " + position);
                            //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                            if (fileUrl==null||"".equals(fileUrl)){
                                ToastUtil.showToast("暂无视频链接！");
                            }else {
                                listVideoUtil.startPlay(fileUrl);
                            }
                            //必须在startPlay之后设置才能生效
                            //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
                        }
                    });

                    break;
            }
        }else {
            footerholder.mFooterView.setState(XListViewFooter.STATE_NORMAL);
        }

        return convertView;
    }

    class TxtViewHolder{
        TextView time_findtxt,mainBody;
        ImageView titleImage;
    }

    class ImageViewHolder {
        TextView time_findtxt,mainBody;
        ImageView titleImage;

        NoScrollGridView iamge_gridview;
    }

    class VideoViewHolder {
        TextView time_findtxt,mainBody;
        ImageView titleImage;

        FrameLayout videoContainer;
        ImageView playerBtn;
        ImageView imageView;
    }

    class FooterHolder {
        XListViewFooter mFooterView = new XListViewFooter(context);
    }
    public void setRootView(ViewGroup rootView) {

        this.rootView = rootView;
    }
    public void setFooterHolderState(FooterHolder mFooterView) {

//        mFooterView.setState(XListViewFooter.STATE_NORMAL);
    }
}
