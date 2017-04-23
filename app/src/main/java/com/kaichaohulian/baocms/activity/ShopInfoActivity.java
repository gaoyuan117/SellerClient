package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ShopInfoEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.GlideCircleTransform;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopInfoActivity extends BaseActivity {

    ShopInfoEntity shopInfoEntity;

    TextView shop_name;
    TextView shop_area;
    TextView shop_userName;
    TextView shop_time;
    TextView shop_sign;
    TextView shop_content;
    ImageView im_QrCode;
    ImageView imgHead;
    private String avatar;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 4;// 结果
    public static final int EDIT_NAME = 100;// 店铺名
    public static final int EDIT_ADDR = 101;// 店铺地址
    public static final int EDIT_CONTRACT = 102;// 店铺联系人

    public static final int EDIT_BUINESSTIME = 103;// 店铺联系人
    public static final int EDIT_DELIVERYTIME = 104;// 店铺联系人

//    params.put("buinessTime", shop_time.getText().toString().trim());
//    params.put("deliveryTime", shop_sign.getText().toString().trim());
//    params.put("shopDetail", shop_content.getText().toString().trim());


    private String imageName;
    private UploadManager uploadManager;

    private String shopQrCode;
    private RelativeLayout head_icon_linear;
    private String mCode;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_shop_info);
    }

    @Override
    public void initData() {
        shopInfoEntity = new ShopInfoEntity();
    }

    @Override
    public void initView() {
        setCenterTitle("店铺维护");
        shop_name = getId(R.id.shop_name);
        shop_area = getId(R.id.shop_area);
        shop_userName = getId(R.id.shop_userName);
        shop_time = getId(R.id.shop_time);
        shop_sign = getId(R.id.shop_sign);
        shop_content = getId(R.id.shop_desc);
        im_QrCode = getId(R.id.im_QrCode);
        imgHead = getId(R.id.imgHead);
        head_icon_linear = getId(R.id.head_icon_linear);
        RelativeLayout rl_name = getId(R.id.name_linear);
        rl_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopNameEditActivity.class);
                in.putExtra("shopid", getIntent().getStringExtra("shopid"));
                startActivityForResult(in, EDIT_NAME);
            }
        });

        RelativeLayout my_address_linear = getId(R.id.my_address_linear);
        my_address_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopAddressEditActivity.class);
                in.putExtra("shopid", getIntent().getStringExtra("shopid"));
                startActivityForResult(in, EDIT_ADDR);
            }
        });
        RelativeLayout sexy_linear = getId(R.id.sexy_linear);
        sexy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopContractEditActivity.class);
                in.putExtra("shopid", getIntent().getStringExtra("shopid"));
                startActivityForResult(in, EDIT_CONTRACT);
            }
        });

        RelativeLayout area_linear = getId(R.id.area_linear);
        area_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopDurationEditActivity.class);
                in.putExtra("shopid", getIntent().getStringExtra("shopid"));
                startActivityForResult(in, EDIT_BUINESSTIME);
            }
        });

        RelativeLayout shop_sign_linear = getId(R.id.shop_sign_linear);
        shop_sign_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopWaittingTimeEditActivity.class);
                in.putExtra("shopid", getIntent().getStringExtra("shopid"));
                startActivityForResult(in, EDIT_DELIVERYTIME);
            }
        });

        RelativeLayout twocode_linear = getId(R.id.twocode_linear);
        twocode_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), ShopCodeActivity.class);
                in.putExtra("shopname", mName);
                in.putExtra("shopaddr", mAddress);
                in.putExtra("shopcode", mCode);
                in.putExtra("shopavatar", avatar);

                startActivityForResult(in, EDIT_DELIVERYTIME);
            }
        });

        head_icon_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });

        bindView();
    }

    private void bindView() {

    }

    private void showPhotoDialog() {
        final android.app.AlertDialog dlg = new android.app.AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("拍照");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
                imageName = getNowTime() + ".png";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(MyApplication.getInstance().SAVE_PIC_PATH, imageName)));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("相册");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getNowTime();
                imageName = getNowTime() + ".png";
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                dlg.cancel();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    private void updateAvatarInServer(final String image) {
        ShowDialog.showDialog(getActivity(), "上传图片中...", false, null);
        File mFile = new File(MyApplication.getInstance().SAVE_PIC_PATH + image);
        if (mFile.exists()) {
            upload(getToken(), mFile);
        } else {
            ShowDialog.dissmiss();
            showToastMsg("获取图片异常");
        }
    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

    private void upload(String uploadToken, File uploadFile) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, final com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                                avatar = url;
                                ImageLoader.getInstance().displayImage(avatar, imgHead);
                                ShowDialog.dissmiss();
                            } catch (Exception e) {
                                ShowDialog.dissmiss();
                                showToastMsg("上传图片失败");
                            }
                        } else {
                            ShowDialog.dissmiss();
                            showToastMsg("上传图片失败");
                        }
                    }
                }, null);
    }

    @SuppressLint("SdCardPath")
    private void startPhotoZoom(Uri uri1, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri1, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(MyApplication.getInstance().SAVE_PIC_PATH, imageName)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:
                    startPhotoZoom(Uri.fromFile(new File(MyApplication.getInstance().SAVE_PIC_PATH, imageName)), 480);
                    break;

                case PHOTO_REQUEST_GALLERY:
                    if (data != null)
                        startPhotoZoom(data.getData(), 480);
                    break;

                case PHOTO_REQUEST_CUT:
                    updateAvatarInServer(imageName);
                    break;

                case EDIT_NAME:
                    String shopname = data.getStringExtra("shopname");
                    if (!TextUtils.isEmpty(shopname) && !"null".equals(shopname)) {
                        shop_name.setText(shopname);
                    } else {
                        shop_name.setText("");
                    }
                    break;

                case EDIT_ADDR:
                    String shopaddr = data.getStringExtra("shopaddr");
                    if (!TextUtils.isEmpty(shopaddr) && !"null".equals(shopaddr)) {
                        shop_area.setText(shopaddr);
                    } else {
                        shop_area.setText("");
                    }
                    break;

                case EDIT_CONTRACT:
                    String userName = data.getStringExtra("userName");
                    if (!TextUtils.isEmpty(userName) && !"null".equals(userName)) {
                        shop_userName.setText(data.getStringExtra("userName"));
                    } else {
                        shop_userName.setText("");
                    }
                    break;

                case EDIT_BUINESSTIME:
                    String buinessTime = data.getStringExtra("buinessTime");
                    if (!TextUtils.isEmpty(buinessTime) && !"null".equals(buinessTime)) {
                        shop_time.setText(data.getStringExtra("buinessTime"));
                    } else {
                        shop_time.setText("");
                    }
                    break;

                case EDIT_DELIVERYTIME:
                    String deliveryTime = data.getStringExtra("deliveryTime");
                    if (!TextUtils.isEmpty(deliveryTime) && !"null".equals(deliveryTime)) {
                        shop_sign.setText(data.getStringExtra("deliveryTime"));
                    } else {
                        shop_sign.setText("");
                    }
                    break;

            }
        }
    }

    public void updateShop() {
        RequestParams params = new RequestParams();
        params.put("shopid", getIntent().getStringExtra("shopid"));
        params.put("shopName", shop_name.getText().toString().trim());
        params.put("address", shop_area.getText().toString().trim());
        params.put("userName", shop_userName.getText().toString().trim());

        params.put("buinessTime", shop_time.getText().toString().trim());
        params.put("deliveryTime", shop_sign.getText().toString().trim());
        params.put("shopDetail", shop_content.getText().toString().trim());
        params.put("logo", avatar);
        DBLog.e("params", params.toString());
        HttpUtil.post(Url.business_updateShop, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改商家信息：", response.toString());
                    if (response.getInt("code") == 0) {

                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    @Override
    public void initEvent() {
        getShop();
    }

    private String mAddress;
    private String mName;

    public void getShop() {
        RequestParams params = new RequestParams();
        params.put("shopid", getIntent().getStringExtra("shopid"));
        HttpUtil.get(Url.business_getByShopId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取商家信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        shopInfoEntity.setAddress(jsonObject.getString("address"));
                        mAddress = jsonObject.getString("address");
                        shopInfoEntity.setArea(jsonObject.getString("area"));
                        avatar = "http://www.maijia01.com/" + jsonObject.getString("avatar");
                        shopInfoEntity.setAvatar(jsonObject.getString("avatar"));
                        shopInfoEntity.setBusinessTime(jsonObject.getString("businessTime"));
                        shopInfoEntity.setDetails(jsonObject.getString("details"));
                        shopInfoEntity.setDistriDetail(jsonObject.getString("distriDetail"));
                        shopInfoEntity.setDistriwaitTime(jsonObject.getString("distriwaitTime"));
                        shopInfoEntity.setLatitude(jsonObject.getString("latitude"));
                        shopInfoEntity.setLongitud(jsonObject.getString("longitud"));

                        try {
                            shopInfoEntity.setPrice(jsonObject.getDouble("price"));
                        } catch (Exception e) {
                            shopInfoEntity.setPrice(0.0);
                        }
                        shopInfoEntity.setTelPhone(jsonObject.getString("telPhone"));
                        shopInfoEntity.setShopName(jsonObject.getString("shopName"));
                        mName = jsonObject.getString("shopName");
                        shopInfoEntity.setUserName(jsonObject.getString("userName"));
                        shopInfoEntity.setQrcode(jsonObject.getString("qrcode"));
                        shop_name.setText(shopInfoEntity.getShopName());
                        if (shopInfoEntity.getArea().equals("null") || shopInfoEntity.getArea() == null) {
                            shop_area.setText("暂未获取到店铺地址");
                        } else {
                            shop_area.setText(shopInfoEntity.getArea());
                        }
                        shop_userName.setText(shopInfoEntity.getUserName());
                        shop_time.setText(shopInfoEntity.getBusinessTime());
                        shop_sign.setText(shopInfoEntity.getDistriwaitTime() + "分钟");
                        shop_content.setText(shopInfoEntity.getDetails());
                        mCode = shopInfoEntity.getQrcode();
                        Glide.with(getActivity()).load(shopInfoEntity.getQrcode()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).into(im_QrCode);
                        Glide.with(getActivity()).load("http://www.maijia01.com/" + shopInfoEntity.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.default_useravatar).into(imgHead);
                        shopQrCode = shopInfoEntity.getQrcode();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

}
