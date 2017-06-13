package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.HobbyActivity;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.EarnestMoneyEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    @BindView(R.id.personal_sex)
    TextView personalSex;
    @BindView(R.id.im_QrCode)
    ImageView imQrCode;
    @BindView(R.id.personal_age)
    TextView personalAge;
    @BindView(R.id.personal_job)
    TextView personalJob;
    @BindView(R.id.personal_hobby)
    TextView personalHobby;
    @BindView(R.id.personal_address)
    TextView personalAddress;
    @BindView(R.id.tv_EarnestMoney)
    TextView tvEarnestMoney;
    @BindView(R.id.tv_GetMoney)
    TextView tvGetMoney;
    @BindView(R.id.tv_BeAddFriend)
    TextView tvBeAddFriend;
    @BindView(R.id.tv_BeInvite)
    TextView tvBeInvite;
    @BindView(R.id.tv_appointment)
    TextView tvAppointment;
    @BindView(R.id.tv_BeMiss)
    TextView tvBeMiss;
    private String edtName = new String("");
    private String sign = new String("");
    private String sexyChosen = new String("");
    private String[] sexy;
    private int sexyWhich = 0;
    private String imageName;
    private UploadManager uploadManager;

    //选择器
    private OptionsPickerView addRessPickerView;
    private OptionsPickerView AgePickView;


    private ArrayList<String> provinceList;//创建存放省份实体类的集合

    private ArrayList<String> cities;//创建存放城市名称集合
    private ArrayList<List<String>> citiesList;//创建存放城市名称集合的集合

    private ArrayList<String> areas;//创建存放区县名称的集合
    private ArrayList<List<String>> areasList;//创建存放区县名称集合的集合
    private ArrayList<List<List<String>>> areasListsList;//创建存放区县集合的集合的集合

    private ArrayList<String> ages;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    public static final int SET_SEX = 4;
    public static final int SET_NAME = 5;//修改姓名
    public static final int SET_PASSWORD_CODE = 6; //设置密码
    public static final int SET_LOC = 7; //修改地址
    public static final int SET_HOBBY = 8;//修改爱好
    public static final int SET_JOB = 9;//修改职业
    public static final int SET_AGE = 10;//修改年龄
    public static final int SET_HEADIMG = 11;//修改头像
    public static final int SET_SIGN = 12;//修改签名


    private String avatar;
    private String[] sexs = {"男", "女"};
    private DataHelper mDataHelper;
    private UserInfo userinfo;

    @Override
    public void setContent() {
        setContentView(R.layout.personal_layout);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        sexy = getResources().getStringArray(R.array.sexy);
        avatar = MyApplication.getInstance().UserInfo.getAvatar();
        userinfo = MyApplication.getInstance().UserInfo;
    }

    @Override
    public void initView() {
        setCenterTitle("个人信息");
        SetData();
    }

    private void SetData() {
        try {
            //设置姓名
            if (userinfo.getUsername() != null
                    && !personalName.getText().equals(userinfo.getUsername())) {
                personalName.setText(userinfo.getUsername());
            }
            //设置性别
            if (userinfo.getSex() != null) {
                String sexT = userinfo.getSex();
                if (sexT.equals("0") || sexT.equals("男")) {
                    personalSex.setText("男");
                } else {
                    personalSex.setText("女");
                }
            }
            //设置爱好
            if (userinfo.getHobby() != null && !userinfo.getHobby().equals("null")) {
                //如果已经设置切值不变时不做操作
                if (!personalHobby.getText().equals(userinfo.getHobby()))
                    personalHobby.setText(userinfo.getHobby());

            } else {
                personalHobby.setText("无");
            }
            //设置职业
            if (userinfo.getJob() != null && !userinfo.getJob().equals("null")) {
                if (!personalJob.getText().equals(userinfo.getJob()))
                    personalJob.setText(userinfo.getJob());
            } else {
                personalJob.setText("未知");
            }
            //设置年龄
            if (userinfo.getAge() != 0) {
                if (Integer.parseInt(personalAge.getText().toString()) != userinfo.getAge())
                    personalAge.setText(userinfo.getAge() + "");
            } else {
                personalAge.setText("未知");

            }
            //设置地区
            if (userinfo.getDistrictId() == null || userinfo.getDistrictId().equals("null")) {
                personalAddress.setText("暂未获取到地区");
            } else {
                personalAddress.setText(userinfo.getDistrictId());
            }
            String l = Url.BASE_URL + MyApplication.getInstance().UserInfo.getQrCode();
            Log.e(TAG, "initView: " + l);
////        Glide.with(getActivity()).load(l).error(R.mipmap.qrcode).diskCacheStrategy(DiskCacheStrategy.ALL).into(imQrCode);
            showUserAvator(imQrCode, l, R.mipmap.qrcode);
//        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgHead);
            showUserAvator(imgHead, userinfo.getAvatar(), R.mipmap.default_useravatar);
        } catch (Exception e) {
            Log.e("gy", e.toString());
        }
    }

    @Override
    public void initEvent() {
        RetrofitClient.getInstance().createApi().Getfaith(Long.parseLong(MyApplication.getInstance().UserInfo.getPhoneNumber()))
                .compose(RxUtils.<HttpResult<EarnestMoneyEntity>>io_main())
                .subscribe(new BaseObjObserver<EarnestMoneyEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(EarnestMoneyEntity earnestMoneyEntity) {
                        try {


                            if (earnestMoneyEntity.payEarnestMoney == null) {
                                tvEarnestMoney.setText("0");
                            } else {
                                tvEarnestMoney.setText(earnestMoneyEntity.payEarnestMoney + "");
                            }
                            if (earnestMoneyEntity.getEarnestMoney == null) {
                                tvGetMoney.setText("0");
                            } else {
                                tvGetMoney.setText((earnestMoneyEntity.getEarnestMoney + ""));
                            }
                            if (earnestMoneyEntity.beToAdd == 0) {
                                tvBeAddFriend.setText("0");
                            } else {
                                tvBeAddFriend.setText(earnestMoneyEntity.beToAdd + "");
                            }
                            if (earnestMoneyEntity.appointment == 0) {
                                tvAppointment.setText("0");
                            } else {
                                tvAppointment.setText(earnestMoneyEntity.appointment + "");
                            }
                            if (earnestMoneyEntity.beInvite == 0) {
                                tvBeInvite.setText("0");
                            } else {
                                tvBeInvite.setText(earnestMoneyEntity.beInvite + "");
                            }
                            if (earnestMoneyEntity.noAppointment == 0) {
                                tvBeMiss.setText("0");
                            } else {
                                tvBeMiss.setText(earnestMoneyEntity.noAppointment + "");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

                case SET_NAME:
                    if (data == null) {
                        return;
                    }
                    edtName = data.getStringExtra("result");
                    personalName.setText(edtName);
                    updateMyUser(SET_NAME);
                    break;

                case SET_JOB:
                    if (data == null) {
                        return;
                    }
                    personalJob.setText(data.getStringExtra("result"));
                    updateMyUser(SET_JOB);
                    break;
                case SET_HOBBY:
                    if (data == null) {
                        return;
                    }
                    personalHobby.setText(data.getStringExtra("result"));
                    updateMyUser(SET_HOBBY);
                    break;

                case SET_SIGN:
                    if (data == null) {
                        return;
                    }
                    sign = data.getStringExtra("sign");
//                    personSign.setText(sign);
                    updateMyUser(SET_SIGN);
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

    public void updateMyUser(int type) {
        HashMap<String, String> map = new HashMap<>();
        String cache = "";
        map.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        switch (type) {
            case SET_NAME:
                String s = personalName.getText().toString().trim();
                map.put("username", s);
                break;
            case SET_SEX:
                map.put("sex", personalSex.getText().toString().equals("男") ? "0" : "1");
                break;
            case SET_AGE:
                map.put("age", personalAge.getText().toString().trim());
                break;
            case SET_JOB:
                map.put("job", personalJob.getText().toString().trim());
                break;
            case SET_HOBBY:
                map.put("hobby", personalHobby.getText().toString().trim());
                break;
            case SET_LOC:
                map.put("districtName", personalAddress.getText().toString());
                break;
            case SET_HEADIMG:
                map.put("avatar", avatar);
                break;
        }

        RetrofitClient.getInstance().createApi().ChangeInfo(map)
                .compose(RxUtils.<HttpResult>io_main())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult value) {
                        Log.e(TAG, "onNext: " + value.errorDescription);
                        ShowDialog.dissmiss();
                        UpdateInfo();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void UpdateInfo() {
        userinfo.setAvatar(avatar);
        userinfo.setUsername(personalName.getText().toString().trim());
        userinfo.setSex(personalSex.getText().toString().trim());
        userinfo.setHobby(personalHobby.getText().toString().trim());
        userinfo.setJob(personalJob.getText().toString().trim());
        if (personalAge.getText().toString().trim().equals("未知")) {
            userinfo.setAge(0);
        } else {
            userinfo.setAge(Integer.parseInt(personalAge.getText().toString().trim()));
        }
        userinfo.setDistrictId(personalAddress.getText().toString().trim());
        SetData();
        Log.e(TAG, userinfo.toString());
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

    //获取当前时间
    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    //获取TOKEN
    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

    //上传图片
    private void upload(String uploadToken, File uploadFile) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, final ResponseInfo respInfo, JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                                avatar = url;
                                updateMyUser(SET_HEADIMG);
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

    //刷新头像
    public void showUserAvator(ImageView imgHead, String avatorUrl, int resId) {
        if (resId == -500) {
            Glide.with(getActivity())
                    .load(avatorUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgHead);
        } else {
            Glide.with(getActivity())
                    .load(avatorUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(resId)
                    .into(imgHead);
        }

        //        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgHead);

    }

    //拍照或相册
    private void showPhotoDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(this).create();
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

    //性别选项
    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.please_choose));
        builder.setSingleChoiceItems(sexy, sexyWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sexyWhich = which;
                sexyChosen = sexy[sexyWhich];
                personalSex.setText(sexyChosen);
                dialog.dismiss();
                updateMyUser(SET_SEX);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //签名编辑
    public void SignEdit(String URL, RequestParams params) {
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

    //初始化三级联动的数据源

    /**
     * @param fileName 本地assets中的文件名
     * @param context  用来打开manager的context对象
     * @return 返回数据源中的数据
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * @param json 解析json数据
     */
    private void parseJson(String json) {
        try {
            //得到一个数组类型的json对象
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {//对数组进行遍历得到每一个jsonobject对象
                JSONObject provinceObject = (JSONObject) jsonArray.get(i);
                String provinceName = provinceObject.optString("areaName");//得到省份的名字
                provinceList.add(provinceName);//向集合里面添加元素

                JSONArray cityArray = provinceObject.optJSONArray("cities");
                cities = new ArrayList<>();//创建存放城市名称集合
                areasList = new ArrayList<>();//创建存放区县名称的集合的集合
                for (int j = 0; j < cityArray.length(); j++) {//遍历每个省份集合下的城市列表
                    JSONObject cityObject = (JSONObject) cityArray.get(j);
                    String cityName = cityObject.getString("areaName");
                    cities.add(cityName);//向集合里面添加元素
                    JSONArray areaArray = cityObject.optJSONArray("counties");
                    areas = new ArrayList<>();//创建存放区县名称的集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getJSONObject(k).optString("areaName");
                        areas.add(areaName);
                    }
                    areasList.add(areas);
                }
                citiesList.add(cities);
                areasListsList.add(areasList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.imgHead, R.id.name_linear, R.id.sexy_linear, R.id.twocode_linear, R.id.personal_age_linear, R.id.personal_job_linear, R.id.personal_hobby_linear, R.id.address_linear, R.id.address_label})
    public void onClick(View view) {
        if (areasListsList == null) {
            provinceList = new ArrayList<>();//创建存放省份实体类的集合
            citiesList = new ArrayList<>();//创建存放城市名称集合的集合
            areasListsList = new ArrayList<>();//创建存放区县集合的集合的集合
            parseJson(getJson("city.json", this));
        }
        if (ages == null) {
            ages = new ArrayList<>();
            for (int i = 15; i <= 60; i++) {
                ages.add(i + "");
            }
        }
        switch (view.getId()) {
            case R.id.imgHead:
                showPhotoDialog();
                break;
            case R.id.name_linear:
                Intent intent = new Intent();
                intent.setClass(PersonalActivity.this, MeNameEditActivity.class);
                intent.putExtra("mTitleName", "setName");
                startActivityForResult(intent, SET_NAME);
                break;
            case R.id.sexy_linear:
                showSexDialog();
                break;
            case R.id.twocode_linear:
                ActivityUtil.next(PersonalActivity.this, MyTwoCode.class);
                break;
            case R.id.personal_age_linear:
                if (AgePickView == null) {
                    AgePickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            Toast.makeText(PersonalActivity.this, ages.get(options1), Toast.LENGTH_SHORT).show();
                            personalAge.setText(ages.get(options1));
                            updateMyUser(SET_AGE);
                        }
                    }).build();
                    AgePickView.setPicker(ages);
                    AgePickView.show();
                } else {
                    AgePickView.show();
                }
                break;
            case R.id.personal_job_linear:
                Intent intentjob = new Intent();
                intentjob.setClass(PersonalActivity.this, PositionActivity.class);
                intentjob.putExtra("mTitleName", "setJob");
                startActivityForResult(intentjob, SET_JOB);
                break;
            case R.id.personal_hobby_linear:
                Intent intenthobby = new Intent();
                intenthobby.setClass(PersonalActivity.this, HobbyActivity.class);
                intenthobby.putExtra("mTitleName", "setHobby");
                startActivityForResult(intenthobby, SET_HOBBY);
                break;
            case R.id.address_linear:
//                ActivityUtil.next(PersonalActivity.this, MeAddressListActivity.class);
                if (addRessPickerView == null) {
                    addRessPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            String city = provinceList.get(options1);
                            String address;
                            //  如果是直辖市或者特别行政区只设置市和区/县
                            if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                                address = provinceList.get(options1)
                                        + " " + areasListsList.get(options1).get(options2).get(options3);
                            } else {
                                address = provinceList.get(options1)
                                        + " " + citiesList.get(options1).get(options2)
                                        + " " + areasListsList.get(options1).get(options2).get(options3);
                            }
                            personalAddress.setText(address);
                            updateMyUser(SET_LOC);
                        }
                    }).build();
                    addRessPickerView.setSelectOptions(0, 0, 0);
                    addRessPickerView.setPicker(provinceList, citiesList, areasListsList);
                    addRessPickerView.show();
                } else {
                    addRessPickerView.show();
                }
                break;
            case R.id.personal_label:

                break;
        }
    }

}


