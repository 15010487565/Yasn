package www.xcd.com.mylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yonyou.sns.im.entity.album.YYPhotoItem;
import com.yonyou.sns.im.util.common.FileUtils;
import com.yonyou.sns.im.util.common.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import www.xcd.com.mylibrary.activity.AlbumPhotoActivity;
import www.xcd.com.mylibrary.base.activity.SimpleTopbarActivity;
import www.xcd.com.mylibrary.entity.GlobalParam;
import www.xcd.com.mylibrary.utils.YYStorageUtil;

import static android.graphics.BitmapFactory.decodeFile;
import static www.xcd.com.mylibrary.activity.AlbumPhotoActivity.IS_ORIGANL;
import static www.xcd.com.mylibrary.activity.PermissionsActivity.PERMISSIONS_DENIED;
import static www.xcd.com.mylibrary.activity.PermissionsActivity.PERMISSIONS_GRANTED;
import static www.xcd.com.mylibrary.func.IFuncRequestCode.REQUEST_CODE_HEAD_ALBUM;
import static www.xcd.com.mylibrary.func.IFuncRequestCode.REQUEST_CODE_HEAD_CAMERA;
import static www.xcd.com.mylibrary.func.IFuncRequestCode.REQUEST_CODE_HEAD_CROP;
import static www.xcd.com.mylibrary.utils.handler.ResizerBitmapHandler.calculateInSampleSize;

/**
 * Created by Android on 2017/6/26.
 */

public class PhotoActivity extends SimpleTopbarActivity {

    public static final String IMAGE_UNSPECIFIED = "image/*";
    /**
     * 头像Image
     */
    public ImageView imageHead;
    /**
     * 头像修改菜单
     */
    public View viewChoice;
    /**
     * 头像修改dialog
     */
    public Dialog dlgChoice;

    /**
     * 照片地址
     */
    public String photoPath;
    public File photoFile;
    public String photoName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            photoPath = savedInstanceState.getString("photoPath");
            photoName = savedInstanceState.getString("photoName");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.account_head_choice_cancel) {// 关闭对话框
            closeChoiceDialog();

        } else if (i == R.id.account_head_choice_album) {// 关闭对话框
            closeChoiceDialog();
            // album
//            PictureSelector pictureSelector = PictureSelector.create(PhotoActivity.this);
//            //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//            PictureSelectionModel  pictureSelectorModel = pictureSelector.openGallery(PictureMimeType.ofImage());
//            // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//            if (getTpye().equals(AlbumPhotoActivity.TYPE_SINGLE)) {
//                pictureSelectorModel.selectionMode(PictureConfig.SINGLE);
//            } else {
//                pictureSelectorModel.selectionMode(PictureConfig.MULTIPLE);
//            }
//            pictureSelectorModel .enableCrop(false);// 是否裁剪 true or false
//            pictureSelectorModel.imageSpanCount(4);// 每行显示个数 int
//            pictureSelectorModel.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            Intent albumIntent = new Intent(PhotoActivity.this, AlbumPhotoActivity.class);
            if (getTpye().equals(AlbumPhotoActivity.TYPE_SINGLE)) {
                albumIntent.putExtra(AlbumPhotoActivity.EXTRA_TYPE, AlbumPhotoActivity.TYPE_SINGLE);
            } else {
                albumIntent.putExtra(AlbumPhotoActivity.EXTRA_TYPE, "");
            }
            // start
            PhotoActivity.this.startActivityForResult(albumIntent, REQUEST_CODE_HEAD_ALBUM);
            // 直接返回图片
        } else if (i == R.id.account_head_choice_camera) {
            try {
                // 关闭对话框
                closeChoiceDialog();
                // 生成photoPath
//                photoFile = new File(YYStorageUtil.getImagePath(PhotoActivity.this), UUID.randomUUID().toString() + ".jpg");
                photoFile = new File(YYStorageUtil.getImagePath(PhotoActivity.this), getphotoName() + ".jpg");
                photoPath = photoFile.getPath();
                Log.e("TAG_相机路径", "照片photoPath=" + photoPath + "");
                //判断是否是AndroidN以及更高的版本
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
                        Log.e("TAG_", "照片photoFile=" + photoFile + "");
                        if (photoFile != null) {
                            //FileProvider 是一个特殊的 ContentProvider 的子类，
                            //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
                            Uri photoURI = FileProvider.getUriForFile(this,
                                    GlobalParam.APPLICATIONID,
                                    photoFile);
//                            photoURI = geturi(takePictureIntent, this);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_CODE_HEAD_CAMERA);
                            // 直接返回图片
//                            startActivityForResult(takePictureIntent, REQUEST_CODE_HEAD_CROP);
                        }
                    }
                } else {
                    closeChoiceDialog();
                    // uri
                    Uri photoUri = Uri.fromFile(new File(photoPath));
                    // 调用系统相机
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                   photoUri = geturi(cameraIntent, this);

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    // 调用剪切功能
                    PhotoActivity.this.startActivityForResult(cameraIntent, REQUEST_CODE_HEAD_CAMERA);
                    // 直接返回图片
//                    PhotoActivity.this.startActivityForResult(cameraIntent, REQUEST_CODE_HEAD_CROP);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static Uri geturi(Intent intent,Context context) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);

                    index = cur.getInt(index);
                }
                if (index == 0) {
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 菜单View
     *
     * @param
     * @return
     */
    public View getChoiceView() {
        if (viewChoice == null) {
            // 初始化选择菜单
            viewChoice = LayoutInflater.from(PhotoActivity.this).inflate(R.layout.view_head_choice, null);
            viewChoice.findViewById(R.id.account_head_choice_album).setOnClickListener(this);
            viewChoice.findViewById(R.id.account_head_choice_camera).setOnClickListener(this);
            viewChoice.findViewById(R.id.account_head_choice_cancel).setOnClickListener(this);
        }
        return viewChoice;
    }
    /**
     * 显示图片的view
     */
    private int showViewid;
    public int getShowViewid() {
        return  showViewid;
    }

    public void setShowViewid(int showViewid) {
        this.showViewid = showViewid;
    }

    /**
     * 修改头像对话框
     *
     * @return
     */
    public Dialog getChoiceDialog() {
        if (dlgChoice == null) {
            dlgChoice = new Dialog(PhotoActivity.this, R.style.DialogStyle);
            dlgChoice.setContentView(getChoiceView());
            return dlgChoice;
        }
        return dlgChoice;
    }
    /**
     * 是否显示更改头像后的dialog,默认不显示
     */
    public boolean getIsShowChoiceDialog(){
        return false;
    }
    /**
     * 关闭对话框
     */
    public void closeChoiceDialog() {
        if (dlgChoice != null && dlgChoice.isShowing()) {
            dlgChoice.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG_相机", "requestCode=" + requestCode + ";resultCode=" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_HEAD_ALBUM:
                    boolean is_origanl = data.getBooleanExtra(IS_ORIGANL, true);
                    YYPhotoItem photoItem = null;
                    if (is_origanl) {
                        photoItem = (YYPhotoItem) data.getSerializableExtra(AlbumPhotoActivity.BUNDLE_RETURN_PHOTO);
                        if (photoItem != null) {
                            startCrop(photoItem.getPhotoPath());
                        }
                    } else {
                        final List<File> list = new ArrayList<>();
                        List<YYPhotoItem> photoList = (List<YYPhotoItem>) data.getSerializableExtra(AlbumPhotoActivity.BUNDLE_RETURN_PHOTOS);
                        for (YYPhotoItem photo : photoList) {
                            // 存储图片到图片目录
                            list.add(new File(photo.getPhotoPath()));
                        }
                        uploadImage(list);
                    }

                    break;
                case REQUEST_CODE_HEAD_CAMERA:
                    //剪切功能
                    Log.e("TAG_剪切","photoPath="+photoPath);
                    if (photoPath!=null){
                        startCrop(photoPath);
                    }
                    break;
                //直接返回图片
                case REQUEST_CODE_HEAD_CROP:
                    try {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap cropPhoto = extras.getParcelable("data");
                            if (cropPhoto!=null){
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                // (0 - 100)压缩文件
                                cropPhoto.compress(Bitmap.CompressFormat.JPEG, 75, stream);

                                File cropFile = new File(YYStorageUtil.getImagePath(PhotoActivity.this), getphotoName() + ".jpg");
                                final List<File> list = new ArrayList<>();
                                list.add(cropFile);
                                FileUtils.compressBmpToFile(cropPhoto, cropFile);
                                uploadImage(list);
                            }
//
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择结果回调
//                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    // 例如 LocalMedia 里面返回三种path
//                    // 1.media.getPath(); 为原图path
//                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
//                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    uploadImageLocalMedia(selectList);
//                    break;
                default:
                    break;
            }
        } else {
            // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
            if (requestCode == PERMISSIONS_GRANTED && resultCode == PERMISSIONS_DENIED) {
                finish();
            } else {
                if (getIsShowChoiceDialog()){
                    getChoiceDialog().show();
                }
            }
        }
    }

    public void uploadImage(final List<File> list) {
        // 调用上传

    }
//    public void uploadImageLocalMedia( List<LocalMedia> list) {
//        // 调用上传
//
//    }

    /**
     * AlbumPhotoActivity.TYPE_SINGLE为单选
     * ""多选
     */

    public void startCrop(String imagePath) {
        Log.e("TAG_裁剪","imagePath="+imagePath);
        File cropFile = new File(YYStorageUtil.getImagePath(PhotoActivity.this), getphotoName() + ".jpg");
        final List<File> list = new ArrayList<>();
        list.add(cropFile);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeFile(imagePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap cropPhoto =  BitmapFactory.decodeFile(imagePath, options);
        if (cropPhoto !=null){
            FileUtils.compressBmpToFile(cropPhoto, cropFile,true,75,true,0);
            uploadImage(list);
        }else {
            ToastUtil.showShort(this,"请选择正确图片");
        }
//        try {
//            Uri uri = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                uri = FileProvider.getUriForFile(this,
//                        GlobalParam.APPLICATIONID,
//                        new File(imagePath));
//            } else {
//                uri = Uri.fromFile(new File(imagePath));
//            }
//            Intent intent = new Intent("com.android.camera.action.CROP");
//            intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
//            intent.putExtra("crop", "true");
//            // aspectX aspectY 是宽高的比例
//            intent.putExtra("aspectX", 1);
//            intent.putExtra("aspectY", 1);
//            // outputX outputY 是裁剪图片宽高
////            intent.putExtra("outputX", 480);
////            intent.putExtra("outputY", 800);
////            intent.putExtra("return-data", true);
//            Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
//            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            PhotoActivity.this.startActivityForResult(intent, REQUEST_CODE_HEAD_CROP);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onSuccessResult(int requestCode, int returnCode, String returnMsg, String returnData, Map<String, Object> paramsMaps) {

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
    //单选还是多选
    public String getTpye() {
        return AlbumPhotoActivity.TYPE_SINGLE;
    }

    public void setphotoName(String photoName){
        this.photoName = photoName;
    }
    public String getphotoName() {
//        return photoName;
        return UUID.randomUUID().toString();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("photoPath", photoPath);
        savedInstanceState.putString("photoName", photoName);
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }
}
