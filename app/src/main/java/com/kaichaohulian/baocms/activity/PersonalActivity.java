package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人信息界面
 */
public class PersonalActivity extends BaseActivity {

    @BindView(R.id.imgHead)
    ImageView imgHead;
    @BindView(R.id.head_icon_linear)
    RelativeLayout headIconLinear;
    @BindView(R.id.personal_name)
    TextView personalName;
    @BindView(R.id.name_linear)
    RelativeLayout nameLinear;
    @BindView(R.id.personal_sex)
    TextView personalSex;
    @BindView(R.id.sexy_linear)
    RelativeLayout sexyLinear;
    @BindView(R.id.im_QrCode)
    ImageView imQrCode;
    @BindView(R.id.twocode_linear)
    RelativeLayout twocodeLinear;
    @BindView(R.id.personal_age)
    TextView personalAge;
    @BindView(R.id.personal_age_linear)
    RelativeLayout AgeLinear;
    @BindView(R.id.personal_job)
    TextView personalJob;
    @BindView(R.id.personal_job_linear)
    RelativeLayout JobLinear;
    @BindView(R.id.personal_hobby)
    TextView personalHobby;
    @BindView(R.id.personal_hobby_linear)
    RelativeLayout HobbyLinear;
    @BindView(R.id.personal_address)
    TextView personalAddress;
    @BindView(R.id.address_linear)
    RelativeLayout addressLinear;
    private String edtName = new String("");
    private String sign = new String("");
    private String sexyChosen = new String("");
    private String[] sexy;
    private int sexyWhich = 0;
    private String imageName;
    private UploadManager uploadManager;

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
        setCenterTitle("个人信息");
        //头像
        headIconLinear = getId(R.id.head_icon_linear);
        imgHead = getId(R.id.imgHead);
        //名字
        nameLinear = getId(R.id.name_linear);
        personalName = getId(R.id.personal_name);
        //性别
        sexyLinear = getId(R.id.sexy_linear);
        personalSex = getId(R.id.personal_sex);
        //二维码
        twocodeLinear = getId(R.id.twocode_linear);
        imQrCode = getId(R.id.im_QrCode);
        //年龄
        AgeLinear=getId(R.id.personal_age_linear);
        personalAge=getId(R.id.personal_age);
        //职业
        JobLinear=getId(R.id.personal_job_linear);
        personalJob=getId(R.id.personal_job);
        //爱好
        HobbyLinear=getId(R.id.personal_hobby_linear);
        personalHobby=getId(R.id.personal_hobby);
        //地址
        addressLinear = getId(R.id.address_linear);
        personalAddress=getId(R.id.personal_address);
        Glide.with(getActivity()).load("http://115.29.99.167:8081/SellerNet/" + MyApplication.getInstance().UserInfo.getQrCode()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imQrCode);
        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgHead);
        showText();
    }

    private void showText() {
        if (MyApplication.getInstance().UserInfo.getUsername() != null) {
            personalName.setText(MyApplication.getInstance().UserInfo.getUsername());
        }
        if (MyApplication.getInstance().UserInfo.getSex() != null) {
            String sexT = MyApplication.getInstance().UserInfo.getSex();
            if (sexT.equals("0")) {
                personalSex.setText("男");
            } else {
                personalSex.setText("女");
            }
        }
        if (MyApplication.getInstance().UserInfo.getHobby() != null) {
            personalHobby.setText(MyApplication.getInstance().UserInfo.getHobby());
        }else{
            personalHobby.setText("无");
        }
        if (MyApplication.getInstance().UserInfo.getJob() != null) {
            personalJob.setText(MyApplication.getInstance().UserInfo.getJob());
        }else{
            personalJob.setText("未知");
        }
        if (MyApplication.getInstance().UserInfo.getAge() != 0) {
            personalAge.setText(MyApplication.getInstance().UserInfo.getAge()+"");
        }else{
            personalAge.setText("未知");

        }
        if (MyApplication.getInstance().UserInfo.getDistrictId() == null || MyApplication.getInstance().UserInfo.getDistrictId().equals("null")) {
            personalAddress.setText("暂未获取到地区");
        } else {
            personalAddress.setText(MyApplication.getInstance().UserInfo.getDistrictId());
        }
    }

    @Override
    public void initEvent() {
        nameLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PersonalActivity.this, MeNameEditActivity.class);
                startActivityForResult(intent, ME_NAME_EDIT_CODE);
            }
        });
        twocodeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PersonalActivity.this, MyTwoCode.class);
            }
        });
        addressLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PersonalActivity.this, MeAddressListActivity.class);
            }
        });
        imgHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoDialog();
            }
        });
        sexyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
                builder.setTitle(getResources().getString(R.string.please_choose));
                //TODO 选择男女
//                builder.setSingleChoiceItems(sexy, sexyWhich, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        sexyWhich = which;
//                        sexyChosen = sexy[sexyWhich];
//                        personalSex.setText(sexyChosen);
//                        dialog.dismiss();
//                        updateMyUser();
//                    }
//                });
//                builder.show();
            }
        });
        AgeLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 年龄选择
            }
        });
        JobLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 职业选择
            }
        });
        HobbyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 爱好选择
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
                    break;

                case ME_NAME_EDIT_CODE:
                    if (data == null) {
                        return;
                    }
                    edtName = data.getStringExtra("name");
                    personalName.setText(edtName);
                    updateMyUser();
                    break;

                case PERSONAL_SIGN_CODE:
                    if (data == null) {
                        return;
                    }
                    sign = data.getStringExtra("sign");
//                    personSign.setText(sign);
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

    public void updateMyUser() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("username", personalName.getText().toString().trim());
        //TODO
//        params.put("thermalSignatrue", personSign.getText().toString().trim());
        params.put("avatar", avatar);
        params.put("districtName", "成都市");
        params.put("sex", personalSex.getText().toString().equals("男") ? "0" : "1");
        HttpUtil.get(Url.changePersonalInformation, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改个人资料：", response.toString());
                    if (response.getInt("code") == 0) {
                        MyApplication.getInstance().UserInfo.setUsername(personalName.getText().toString());
                        //TODO
//                        MyApplication.getInstance().UserInfo.setThermalSignatrue(personSign.getText().toString());
                        MyApplication.getInstance().UserInfo.setAvatar(avatar);
                        MyApplication.getInstance().UserInfo.setDistrictId("成都市");
                        MyApplication.getInstance().UserInfo.setSex(personalSex.getText().toString());
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

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
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


}
