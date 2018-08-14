package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ReadImgToBinary;
import com.yasn.purchase.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.PhotoActivity;

public class AuthorImageActivity extends PhotoActivity {

    private TextView tvUploadCard, tvUploadShop;
    private ImageView ivUploadCard, ivUploadShop;
    private String proveFileA;//营业执照
    private String proveFileB;//店铺门面照片
    private TextView tvAuthorUploadOk;

    @Override
    protected Object getTopbarTitle() {
        return "会员认证";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_image);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        //营业执照样本图
        tvUploadCard = (TextView) findViewById(R.id.tv_UploadCard);
        tvUploadCard.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvUploadCard.getPaint().setAntiAlias(true);//抗锯齿
        tvUploadCard.setOnClickListener(this);

        tvUploadShop = (TextView) findViewById(R.id.tv_UploadShop);
        tvUploadShop.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvUploadShop.getPaint().setAntiAlias(true);//抗锯齿
        tvUploadShop.setOnClickListener(this);
        //上传营业执照
        ivUploadCard = (ImageView) findViewById(R.id.iv_UploadCard);
        ivUploadCard.setOnClickListener(this);
        //上传门店照片
        ivUploadShop = (ImageView) findViewById(R.id.iv_UploadShop);
        ivUploadShop.setOnClickListener(this);
        //上传
        tvAuthorUploadOk = (TextView) findViewById(R.id.tv_AuthorUploadOk);
        tvAuthorUploadOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_UploadCard:
                showSampleDialog(R.mipmap.yangbentu1);
                break;
            case R.id.tv_UploadShop:
                showSampleDialog(R.mipmap.yangbentu2);
                break;
            case R.id.iv_UploadCard://上传营业执照
                setShowViewid(R.id.iv_UploadCard);
                getChoiceDialog().show();
                break;
            case R.id.iv_UploadShop://上传门店照片
                setShowViewid(R.id.iv_UploadShop);
                getChoiceDialog().show();
                break;
            case R.id.tv_AuthorUploadOk://上传
                dialogshow();
                Map<String, String> params = new HashMap<String, String>();
                if (token != null && !"".equals(token)) {
                    params.put("access_token", token);
                } else if (resetToken != null && !"".equals(resetToken)) {
                    params.put("access_token", resetToken);
                }
                params.put("proveFileA", proveFileA);
                params.put("proveFileB", proveFileB);
                okHttpPost(100, Config.AUTHORMEMBERSUBMITIMAGE, params);
                break;
        }
    }
    String imageurl;
    @Override
    public void uploadImage(List<File> list) {
        super.uploadImage(list);
        int showViewid = getShowViewid();
        Log.e("TAG_上传图片", "showViewid=" + showViewid);
        Log.e("TAG_上传图片", "上传营业执照=" + (R.id.iv_UploadCard));
        Log.e("TAG_上传图片", "上传门店照片=" + (R.id.iv_UploadShop));
        try {
            for (File imagepath : list) {
                imageurl = imagepath.getPath();
                Log.e("TAG_认证上传","imageurl="+imageurl);
                switch (showViewid) {
                    case R.id.iv_UploadCard://上传营业执照
                        Glide.with(this)
                                .load(imageurl)
                                .fitCenter()
                                .dontAnimate()
                                .placeholder(R.mipmap.login_n_yasn)
                                .error(R.mipmap.login_n_yasn)
                                .into(ivUploadCard);
                        if (imageurl != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //将图片转化为字符串
                                    proveFileA = ReadImgToBinary.imgToBase64(imageurl);
                                }
                            });
                        }
                        break;
                    case R.id.iv_UploadShop://上传门店照片

                        Glide.with(this)
                                .load(imageurl)
                                .fitCenter()
                                .dontAnimate()
                                .placeholder(R.mipmap.login_n_yasn)
                                .error(R.mipmap.login_n_yasn)
                                .into(ivUploadShop);
                        if (imageurl != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //将图片转化为字符串
                                    proveFileB = ReadImgToBinary.imgToBase64(imageurl);
                                }
                            });
                        }
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //样本弹窗
    protected AlertDialog sampleDialog;

    private void showSampleDialog(int imageID) {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_sample_image, null);
        ImageView tvCancel = (ImageView) serviceView.findViewById(R.id.iv_Cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleDialog.dismiss();
            }
        });
        ImageView ivShowSample = (ImageView) serviceView.findViewById(R.id.iv_ShowSample);
        ivShowSample.setBackgroundResource(imageID);
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        sampleDialog = builder.create();
        sampleDialog.show();
        sampleDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }

    /**
     * 显示popupWindow
     */
    PopupWindow window;
    private void showPopwindow(int isUploadOk) {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_authorupload_image, null);
       window = new PopupWindow(serviceView,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(false);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(this, R.color.transparent));
//        window.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        backgroundAlpha(0.5f); //0.0-1.0
        getWindow().setAttributes(lp);
//        int width =  getWindowManager().getDefaultDisplay().getWidth();
//        window.setWidth(width * 6/10);
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(false);
//        window.setTouchable(false);
        // 设置popWindow的显示和消失动画
//        window.setAnimationStyle(R.style.popwindow_anim_leftandright);
        // 在底部显示
        window.showAtLocation(serviceView, Gravity.CENTER, 0, 0);
        //添加控件绑定并配置适配器
        TextView tvTopHint = (TextView) serviceView.findViewById(R.id.tv_AuthorUploadTopHint);
        TextView tvUploadHint = (TextView) serviceView.findViewById(R.id.tv_AuthorUploadHint);
        TextView tvUploadOk = (TextView) serviceView.findViewById(R.id.tv_AuthorDialogOk);
        LinearLayout llCancel = (LinearLayout) serviceView.findViewById(R.id.ll_ShowSample);
        if (isUploadOk == 1) {
            tvTopHint.setText("提交资料成功");
            tvUploadHint.setText("恭喜您资料提交成功，\n我们将尽快为您审核，\n请您耐心等待");
            llCancel.setBackgroundResource(R.mipmap.authorsucceed);
            tvUploadOk.setText("随便逛逛");
            tvUploadOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.dismiss();
                    startActivity(new Intent(AuthorImageActivity.this,MainActivity.class));
                }
            });
        } else {
            tvTopHint.setText("提交资料失败");
            tvUploadHint.setText("由于您上传的图片不符\n合要求，资料提交失败\n，请重新上传");
            tvUploadOk.setText("重新上传");
            llCancel.setBackgroundResource(R.mipmap.authorfailuren);
            tvUploadOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    window.dismiss();
                    backgroundAlpha(1f);
                }
            });
        }

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
                window.dismiss();
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
//                    showUpLoadImageDialog(1);
                    showPopwindow(1);
                } else if (returnCode == 400) {
//                    showUpLoadImageDialog(0);
                    showPopwindow(0);
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                dialogDissmiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }

    @Override
    public void onCancelResult() {

    }

    @Override
    public void onErrorResult(int errorCode, IOException errorExcep) {

    }

    @Override
    public void onParseErrorResult(int errorCode) {

    }

    @Override
    public void onFinishResult() {

    }
}
