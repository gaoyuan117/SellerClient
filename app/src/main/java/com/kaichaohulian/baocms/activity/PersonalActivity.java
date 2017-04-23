package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 个人信息界面
 */
public class PersonalActivity extends BaseActivity {

    private RelativeLayout mNameEdit, mTwoCode, mArea, mSexy, mSign, mAddress;
    private RelativeLayout mBuyerNumer, mHeadIcon;
    private ImageView imgHead;
    private ImageView qrCode;
    private TextView personName;
    private TextView personSign;
    private TextView perdonSexy;
    private String edtName = new String("");
    private String sign = new String("");
    private String sexyChosen = new String("");
    private String[] sexy;
    private int sexyWhich = 0;
    private String imageName;
    private UploadManager uploadManager;
    private TextView mPersonalArea;
    private TextView personal_buyer_number;
    private TextView personal_buyer_phone;

    //性别选择网络请求Code
    public static final int SEXY_CHOOSE_RESULTCODE = 3;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 4;// 结果
    public static final int PERSONAL_SIGN_CODE = 10;//修改签名
    public static final int ME_NAME_EDIT_CODE = 5;//修改姓名
    public static final int SET_PASSWORD_CODE = 6; //设置密码
    public static final int SET_LOC = 7; //设置地域

    private String avatar;

    private DataHelper mDataHelper;

    @Override
    public void setContent() {
        setContentView(R.layout.personal_layout);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        sexy = getResources().getStringArray(R.array.sexy);
        avatar = MyApplication.getInstance().UserInfo.getAvatar();
    }

    @Override
    public void initView() {
        setCenterTitle("我");

        mNameEdit = getId(R.id.name_linear);
        mTwoCode = getId(R.id.twocode_linear);
        mAddress = getId(R.id.my_address_linear);
        mBuyerNumer = getId(R.id.buyerNumber_linear);
        mHeadIcon = getId(R.id.head_icon_linear);
        personName = getId(R.id.personal_name);
        mSign = getId(R.id.personal_sign_linear);
        personSign = getId(R.id.personal_sign);
        mSexy = getId(R.id.sexy_linear);
        imgHead = getId(R.id.imgHead);
        mArea = getId(R.id.area_linear);
        perdonSexy = getId(R.id.personal_sex);
        qrCode = getId(R.id.im_QrCode);
        personal_buyer_number = getId(R.id.personal_buyer_number);
        personal_buyer_phone = getId(R.id.personal_buyer_phone);
        mPersonalArea = getId(R.id.personal_area);
        Glide.with(getActivity()).load("http://115.29.99.167:8081/SellerNet/" + MyApplication.getInstance().UserInfo.getQrCode()).diskCacheStrategy(DiskCacheStrategy.ALL).into(qrCode);

        showText();
    }

    public void showText() {
        if (MyApplication.getInstance().UserInfo.getUsername() != null) {
            personName.setText(MyApplication.getInstance().UserInfo.getUsername());
        }
        if (MyApplication.getInstance().UserInfo.getSex() != null) {
            String sexT = MyApplication.getInstance().UserInfo.getSex();
            if (sexT.equals("0")) {
                perdonSexy.setText("男");
            } else {
                perdonSexy.setText("女");
            }
        }
        if (MyApplication.getInstance().UserInfo.getThermalSignatrue() == null || MyApplication.getInstance().UserInfo.getThermalSignatrue().equals("null")) {
            personSign.setText("");
        } else {
            personSign.setText(MyApplication.getInstance().UserInfo.getThermalSignatrue());
        }
        if (MyApplication.getInstance().UserInfo.getAvatar() != null) {
            showUserAvator(imgHead, MyApplication.getInstance().UserInfo.getAvatar());
        }
        if (MyApplication.getInstance().UserInfo.getAccountNumber() == null || MyApplication.getInstance().UserInfo.getAccountNumber().equals("null")) {
            personal_buyer_number.setText("");
        } else {
            personal_buyer_number.setText(MyApplication.getInstance().UserInfo.getAccountNumber());
        }
        if (MyApplication.getInstance().UserInfo.getPhoneNumber() == null || MyApplication.getInstance().UserInfo.getPhoneNumber().equals("null")) {
            personal_buyer_phone.setText("暂未获取到手机号");
        } else {
            personal_buyer_phone.setText(MyApplication.getInstance().UserInfo.getPhoneNumber());
        }
        if (MyApplication.getInstance().UserInfo.getDistrictId() == null || MyApplication.getInstance().UserInfo.getDistrictId().equals("null")) {
            mPersonalArea.setText("暂未获取到地区");
        } else {
            mPersonalArea.setText(MyApplication.getInstance().UserInfo.getDistrictId());
        }
    }

    @Override
    public void initEvent() {
        mNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PersonalActivity.this, MeNameEditActivity.class);
                startActivityForResult(intent, ME_NAME_EDIT_CODE);
            }
        });
        mTwoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PersonalActivity.this, MyTwoCode.class);
            }
        });
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PersonalActivity.this, MeAddressListActivity.class);

            }
        });
        mBuyerNumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mHeadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });
        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PersonalActivity.this, PersonalSignActivity.class);
                startActivityForResult(intent, PERSONAL_SIGN_CODE);
            }
        });
        mSexy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle(getResources().getString(R.string.please_choose));
                builder.setSingleChoiceItems(sexy, sexyWhich, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sexyWhich = which;
                        sexyChosen = sexy[sexyWhich];
                        perdonSexy.setText(sexyChosen);
                        dialog.dismiss();
                        updateMyUser();
                    }
                });
                builder.show();
            }
        });
        mArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateAddressActivity.class);
                startActivityForResult(intent, SET_LOC);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SET_LOC:
                    if (data == null) {
                        return;
                    }
                    MyApplication.getInstance().UserInfo.setDistrictId(data.getStringExtra("loc"));
                    mPersonalArea.setText(data.getStringExtra("loc"));
                    break;

                case ME_NAME_EDIT_CODE:
                    if (data == null) {
                        return;
                    }
                    edtName = data.getStringExtra("name");
                    personName.setText(edtName);
                    updateMyUser();
                    break;

                case PERSONAL_SIGN_CODE:
                    if (data == null) {
                        return;
                    }
                    sign = data.getStringExtra("sign");
                    personSign.setText(sign);
                    updateMyUser();
                    break;

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

                case SET_PASSWORD_CODE:

                    break;
            }
        }
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

    public void nameEdit(String URL, RequestParams params) {
        AsyncHttpClient client = HttpUtil.getClient();
        client.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                DBLog.d("sign edit ", "签名修改成功了");
                String s = new String(bytes);
                DBLog.d("json", s);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                DBLog.d("name change ", "姓名修改 服务器链接失败");
            }
        });

    }

    public void updateMyUser() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("username", personName.getText().toString().trim());
        params.put("thermalSignatrue", personSign.getText().toString().trim());
        params.put("avatar", avatar);
        params.put("districtName", "成都市");
        params.put("sex", perdonSexy.getText().toString().equals("男") ? "0" : "1");
        HttpUtil.get(Url.changePersonalInformation, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改个人资料：", response.toString());
                    if (response.getInt("code") == 0) {
                        MyApplication.getInstance().UserInfo.setUsername(personName.getText().toString());
                        MyApplication.getInstance().UserInfo.setThermalSignatrue(personSign.getText().toString());
                        MyApplication.getInstance().UserInfo.setAvatar(avatar);
                        MyApplication.getInstance().UserInfo.setDistrictId("成都市");
                        MyApplication.getInstance().UserInfo.setSex(perdonSexy.getText().toString());
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                    }
//                    showToastMsg(response.getString("errorDescription"));
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
                avatar = MyApplication.getInstance().UserInfo.getAvatar();
            }
        });
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

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
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
                                updateMyUser();
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

    public void showUserAvator(ImageView imgHead, String avatorUrl) {
        Glide.with(getActivity())
                .load(avatorUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHead);
    }
}
