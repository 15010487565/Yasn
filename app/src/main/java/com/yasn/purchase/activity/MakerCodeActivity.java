package com.yasn.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xyzlf.share.library.bean.ShareEntity;
import com.yasn.purchase.R;
import com.yasn.purchase.common.Config;
import com.yasn.purchase.utils.ToastUtil;
import com.yasn.purchase.utils.VersionUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.xcd.com.mylibrary.PhotoActivity;
import www.xcd.com.mylibrary.help.HelpUtils;

public class MakerCodeActivity extends PhotoActivity implements View.OnLongClickListener {

    private ImageView ivMakerCode;
    private TextView tvMakerName;
    private LinearLayout llMakerShareCode, llPopwindow;
    private ShareEntity testBean;
    private Bitmap bitmap;

    @Override
    protected Object getTopbarTitle() {
        return "推广二维码";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maker_code);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        Map<String, Object> params = new HashMap<String, Object>();
        if (token != null && !"".equals(token)) {
            params.put("access_token", token);
        } else if (resetToken != null && !"".equals(resetToken)) {
            params.put("access_token", resetToken);
        }
        okHttpGet(100, Config.MAKERQRCODE, params);
    }

    @Override
    protected void afterSetContentView() {
        super.afterSetContentView();
        ivMakerCode = (ImageView) findViewById(R.id.iv_MakerCode);
        tvMakerName = (TextView) findViewById(R.id.tv_MakerName);
        llPopwindow = (LinearLayout) findViewById(R.id.ll_Popwindow);
        llMakerShareCode = (LinearLayout) findViewById(R.id.ll_MakerShareCode);
        llMakerShareCode.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_DialogMakerShare://发送给朋友
                if (bitmap == null) {
                    ToastUtil.showToast("分享失败！");
                    return;
                }
                String llshare = HelpUtils.showPic(this, llMakerShareCode);
                Log.e("TAG_分享二维码", "llshare=" + llshare);
                if (!TextUtils.isEmpty(llshare)) {
                    mAuthNotifyDialog.dismiss();
                    shareMsg(this, llshare);
                } else {
                    ToastUtil.showToast("分享失败！");
                }
                break;
            case R.id.tv_DialogMakerPhone://保存到手机
                if (bitmap == null) {
                    ToastUtil.showToast("保存到手机失败！");
                    return;
                }
                String bSave = HelpUtils.showPic(this, llMakerShareCode);
                if (!TextUtils.isEmpty(bSave)) {
                    ToastUtil.showToast("保存到手机成功！");
                    mAuthNotifyDialog.dismiss();
                } else {
                    ToastUtil.showToast("保存到手机失败！");
                }
                break;
//            case R.id.tv_DialogMakerCode://扫一扫
//                Intent inteng = new Intent(this, WeChatCaptureActivity.class);
//                startActivity(inteng);
//                submitOneDialog.dismiss();
//                break;
//            case R.id.tv_DialogMakerImageCode://识别图中二维码
//                Intent albumIntent = new Intent(this, AlbumPhotoActivity.class);
//                if (getTpye().equals(AlbumPhotoActivity.TYPE_SINGLE)) {
//                    albumIntent.putExtra(AlbumPhotoActivity.EXTRA_TYPE, AlbumPhotoActivity.TYPE_SINGLE);
//                } else {
//                    albumIntent.putExtra(AlbumPhotoActivity.EXTRA_TYPE, "");
//                }
//                // start
//                startActivityForResult(albumIntent, REQUEST_CODE_HEAD_ALBUM);
//                submitOneDialog.dismiss();
//                break;
        }
    }

    private void shareMsg(Context context, String imgPath) {
        if (!VersionUtil.isAvilible(MakerCodeActivity.this, "com.tencent.mm")) {
            ToastUtil.showToast("未安装微信App");
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        if ((imgPath == null) || (imgPath.equals(""))) {
            intent.setType("text/plain");
        } else {
            File f = new File(imgPath);
            if ((f != null) && (f.exists()) && (f.isFile())) {
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            }
        }
//        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, "微信");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
        context.startActivity(intent);
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {
        switch (requestCode) {
            case 100:
                if (returnCode == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(returnData);
                        String img = jsonObject.optString("img");
                        bitmap = HelpUtils.stringtoBitmap(img);
                        ivMakerCode.setImageBitmap(bitmap);
                        String name = jsonObject.optString("name");
                        tvMakerName.setText(name == null ? "" : name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToast(returnMsg);
                }
                break;
        }
    }

    @Override
    public void uploadImage(List<File> list) {
        super.uploadImage(list);
        // 调用上传
        for (File imagepath : list) {
            String imageurl = imagepath.getPath();
            if (imageurl != null) {
                try {
                    FileInputStream fis = new FileInputStream(imageurl);
                    Bitmap obmp = BitmapFactory.decodeStream(fis);
                    int width = obmp.getWidth();
                    int height = obmp.getHeight();
                    int[] data = new int[width * height];
                    obmp.getPixels(data, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                    BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                    QRCodeReader reader = new QRCodeReader();
                    Result re = reader.decode(bitmap1);
                    getInviteCode(re.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void getInviteCode(String url){
        Intent intent = new Intent(this,MakerInviteRegisterAActivity.class);
        if (TextUtils.isEmpty(url)){//邀请码无效
            intent.putExtra("isValid",0);
        }else {
            int i = url.indexOf("&");
            String substring = url.substring(i + 1);
            Log.e("TAG_相册","substring="+substring);
            String[] split = substring.split("=");
            String key = split[0];
            Log.e("TAG_相册","key="+key);
            String value = split[1];
            Log.e("TAG_相册","value="+value);
            intent.putExtra("key",key);
            intent.putExtra("value",value);
            intent.putExtra("isValid",1);
            intent.putExtra("MakerInviteName",tvMakerName.getText().toString());
        }
        startActivity(intent);
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

    @Override
    public boolean onLongClick(View view) {
        showMakerCodeDialog();
        return false;
    }

    //长按弹窗
    protected AlertDialog mAuthNotifyDialog;

    private void showMakerCodeDialog() {
        LayoutInflater factor = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View serviceView = factor.inflate(R.layout.dialog_makercode, null);
        TextView tvDialogMakerShare = (TextView) serviceView.findViewById(R.id.tv_DialogMakerShare);
        tvDialogMakerShare.setOnClickListener(this);
        TextView tvDialogMakerPhone = (TextView) serviceView.findViewById(R.id.tv_DialogMakerPhone);
        tvDialogMakerPhone.setOnClickListener(this);
//        TextView tvDialogMakerCode = (TextView) serviceView.findViewById(R.id.tv_DialogMakerCode);
//        tvDialogMakerCode.setOnClickListener(this);
//        TextView tvDialogMakerImageCode = (TextView) serviceView.findViewById(R.id.tv_DialogMakerImageCode);
//        tvDialogMakerImageCode.setOnClickListener(this);
        Activity activity = MakerCodeActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        mAuthNotifyDialog = builder.create();
        mAuthNotifyDialog.show();
        mAuthNotifyDialog.setContentView(serviceView);
        FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.WRAP_CONTENT);
        //layout.setMargins(WallspaceUtil.dip2px(this, 10), 0, FeatureFunction.dip2px(this, 10), 0);
        serviceView.setLayoutParams(layout);
    }
}
