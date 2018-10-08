package com.yasn.purchase.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import www.xcd.com.mylibrary.activity.PermissionsActivity;
import www.xcd.com.mylibrary.activity.PermissionsChecker;

public class AuthorImageActivity extends PhotoActivity {

    private TextView tvUploadCard, tvUploadShop;
    private ImageView ivUploadCard, ivUploadShop;
    private String proveFileA;//营业执照
    private String proveFileB;//店铺门面照片
    private TextView tvAuthorUploadOk;
    private static final String[] AUTHORIMAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.CAMERA
    };
    private PermissionsChecker mChecker ;
    @Override
    protected Object getTopbarTitle() {
        return "会员认证";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_image);
        mChecker = new PermissionsChecker(this);
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
                if (sampleDialog ==null || !sampleDialog.isShowing()){
                    showSampleDialog(R.mipmap.yangbentu1);
                }
                break;
            case R.id.tv_UploadShop:
                if (sampleDialog ==null || !sampleDialog.isShowing()){
                    showSampleDialog(R.mipmap.yangbentu2);
                }
                break;
            case R.id.iv_UploadCard://上传营业执照

                if (mChecker.lacksPermissions(AUTHORIMAGE)) {
                    // 请求权限
                    PermissionsActivity.startActivityForResult(this,11000,AUTHORIMAGE);
//                    ActivityCompat.requestPermissions(this, BaseActivity.WRITEREADPERMISSIONS, 11000);
                } else {
                    // 全部权限都已获取
                    setShowViewid(R.id.iv_UploadCard);
                    getChoiceDialog().show();
                }
                break;
            case R.id.iv_UploadShop://上传门店照片
                if (mChecker.lacksPermissions(AUTHORIMAGE)) {
                    // 请求权限
                    PermissionsActivity.startActivityForResult(this,11000,AUTHORIMAGE);
//                    ActivityCompat.requestPermissions(this, BaseActivity.WRITEREADPERMISSIONS, 11000);
                } else {
                    // 全部权限都已获取
                    setShowViewid(R.id.iv_UploadShop);
                    getChoiceDialog().show();
                }
                break;
            case R.id.tv_AuthorUploadOk://上传
                if (TextUtils.isEmpty(proveFileA)){
                    ToastUtil.showToast("请上传营业执照/手持身份证照片！");
                    return;
                }
                if (TextUtils.isEmpty(proveFileB)){
                    ToastUtil.showToast("请上传店铺门面照片！");
                    return;
                }
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
//        authorDialog.setCancelable(false);
//        authorDialog.setCanceledOnTouchOutside(false);
//        authorDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        sampleDialog.show();
        sampleDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }

    //认证弹窗
    protected AlertDialog authorDialog;
    private void showAuthDialog(int isUploadOk) {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_authorupload_image, null);
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
                    authorDialog.dismiss();
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
                    authorDialog.dismiss();
                }
            });
        }
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        authorDialog = builder.create();
        authorDialog.setCancelable(false);
        authorDialog.setCanceledOnTouchOutside(false);
        authorDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        authorDialog.show();
        authorDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        super.onSuccessResult(requestCode,returnCode,returnMsg,returnData,paramsMaps);
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    if (authorDialog ==null || !authorDialog.isShowing()){
                        showAuthDialog(1);
                    }
                } else if (returnCode == 400) {
                    if (authorDialog ==null || !authorDialog.isShowing()){
                        showAuthDialog(0);
                    }
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
        if (authorDialog !=null && authorDialog.isShowing()){

        }else {
            finish();
        }
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 11000:
                if (hasAllPermissionsGranted(grantResults)){
                    getChoiceDialog().show();
                }else {
                    ToastUtil.showToast("请在应用管理中打开“相机”访问权限！");
                }
                break;
        }
    }
    // 含有全部的权限
    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
