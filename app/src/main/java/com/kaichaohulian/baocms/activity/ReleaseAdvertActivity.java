package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.AdvertParmEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.PayDialog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONArray;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class ReleaseAdvertActivity extends BaseActivity {


    @BindView(R.id.title_releaseadvert)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.tv_time_releaseadvert)
    TextView time;
    @BindView(R.id.current_address_rlt)
    RelativeLayout timeLayout;


    private int REQUEST_CODE = 200;
    private int PAY_CODE = 100;

    private List<String> list;
    private ImageBaseAdapter ImageBaseAdapter;
    int index = 0;
    private double payMoney = 0;
    private StringBuffer img = new StringBuffer();
    private StringBuffer ids = new StringBuffer();
    private PayDialog payDialog;
    private int size;
    private String type;//发布的广告类型


    private TimePickerView timePickerView;
    SimpleDateFormat DayTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat DayTimeFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String sendTime;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_release_advert);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        size = getIntent().getIntExtra("size", 0);
        type = getIntent().getStringExtra("type");
        Log.e("gy", "长度：" + size);

//        DateFormat format = new SimpleDateFormat("HH:mm yyyy.MM.dd");
        Date date = new Date(System.currentTimeMillis());
        sendTime = DayTimeFormat2.format(date);
        time.setText(DayTimeFormat.format(date));
        list = new ArrayList<>();
        ImageBaseAdapter = new ImageBaseAdapter();
        gridview.setAdapter(ImageBaseAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index != 9 && index - 1 == position) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                    intent.setPhotoCount(9);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, REQUEST_CODE);

                } else {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(getActivity(), list, position, imageSize);
                }
            }
        });
    }

    @Override
    public void initView() {
        setCenterTitle("发布广告");
        TextView tv = setRightTitle("发布");
        tv.setBackgroundResource(R.mipmap.rounded_rectangle);
        tv.setTextColor(getResources().getColor(R.color.ccp_green_alpha));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payMoney == 0) {
                    initEvent();
                }
                if (list.size() == 0) {
                    Toast.makeText(ReleaseAdvertActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
                    ShowDialog.dissmiss();
                    return;
                }
                if (TextUtils.isEmpty(title.getText().toString().trim())) {
                    ToastUtil.showMessage("请输入标题");
                    ShowDialog.dissmiss();
                    return;
                }
                if (TextUtils.isEmpty(content.getText().toString().trim())) {
                    ToastUtil.showMessage("请输入内容");
                    ShowDialog.dissmiss();
                    return;
                }

                if (payDialog == null) {
                    payDialog = new PayDialog(getActivity()).setMessage("本次群发需要支付" + (payMoney * size) + "元手续费").setSureClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            payDialog.dismissDialog();
                            Intent intent = new Intent(ReleaseAdvertActivity.this, PayActivity.class);
                            intent.putExtra("pay_money", (payMoney * size) + "");
                            intent.putExtra("type", 2 + "");
                            startActivityForResult(intent, PAY_CODE);
                        }

                    }).showDialog();
                } else {
                    payDialog.showDialog();
                }
            }
        });
    }

    @Override
    public void initEvent() {
        RetrofitClient.getInstance().createApi().getAdvertParm().compose(RxUtils.<HttpResult<AdvertParmEntity>>io_main())
                .subscribe(new BaseObjObserver<AdvertParmEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(AdvertParmEntity advertParmEntity) {
                        payMoney = advertParmEntity.eachPay;
                    }
                });

        timeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePickerView == null) {
                    timePickerView = new TimePickerView.Builder(ReleaseAdvertActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            time.setText(DayTimeFormat.format(date));

                            sendTime = DayTimeFormat2.format(date);
                        }
                    }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                            .setDate(Calendar.getInstance())
                            .build();
                    timePickerView.show();

                } else {
                    timePickerView.show();
                }
            }
        });
    }

    private void showImageView(ImageView iamgeView, String avatar) {
        Glide.with(getActivity())
                .load(avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iamgeView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                ImageBaseAdapter.notifyDataSetChanged();
                if (gridview.getVisibility() == View.GONE) {
                    gridview.setVisibility(View.VISIBLE);
                } else if (list.size() == 0) {
                    gridview.setVisibility(View.GONE);
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == PAY_CODE) {
            InServer();
        }
    }


    @OnClick(R.id.ib_addphoto_first)
    public void onClick() {
        if (index != 9) {
            PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
            intent.setPhotoCount(9);
            intent.setShowCamera(true);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Toast.makeText(this, "不能添加更多图片", Toast.LENGTH_SHORT).show();
        }
    }

    public class ImageBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            index = list.size() != 9 ? list.size() + 1 : list.size();
            return index;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.release_imageitem, null);
            ImageView ImageView = (android.widget.ImageView) convertView.findViewById(R.id.image);
            if (index != 9 && index - 1 == position)
                ImageView.setBackgroundResource(R.mipmap.icon_releasetalk_add);
            else
                showImageView(ImageView, list.get(position));
            return convertView;
        }
    }

    private UploadManager uploadManager;

    private void InServer() {
        ShowDialog.showDialog(getActivity(), "上传中...", false, null);
        JSONArray = new JSONArray();
        if (list.size() == 0) {
            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
            ShowDialog.dissmiss();
        } else {
            for (int i = 0; i < list.size(); i++) {
                File mFile = new File(list.get(i));
                if (mFile.exists()) {
                    upload(getToken(), mFile, i);
                } else {
                    ShowDialog.dissmiss();
                    showToastMsg("获取图片异常");
                }
            }
        }
    }

    private void upload(String uploadToken, File uploadFile, final int i) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                                JSONArray.put(url);
                                Log.e(TAG, "complete: " + url);
                                img.append(fileKey + ",");
                                if (JSONArray.length() == index - 1) {
                                    img.replace(img.length() - 1, img.length(), "");
                                    Log.e(TAG, img.toString().trim());
                                    Commt();
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "complete: " + e);
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

    public void Commt() {
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("title", title.getText().toString().trim());
        map.put("context", content.getText().toString().trim());
        map.put("type", type);
        map.put("createdTime", sendTime);
        if (!img.equals("")) {
            map.put("images", img.toString().trim());
        }
        map.put("ids", getIntent().getStringExtra("ids"));
        map.put("pay", (payMoney * size) + "");
        RetrofitClient.getInstance().createApi().ReleaseAdvert(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ShowDialog.dissmiss();
                        ToastUtil.showMessage("发布成功");
                        finish();
                    }
                });
//        RequestParams params = new RequestParams();
//        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
//        params.put("content", content.getText().toString());
//        if (list.size() != 0) {
//            params.put("images", JSONArray);
//        }
//        params.put("privilegeType", privilegeType);
//        if (null != reminds && reminds.size() != 0) {
//            params.put("reminds", reminds);
//        }
//        if (null != users && users.size() != 0) {
//            params.put("users", users);
//        }
//        HttpUtil.post(Url.release, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
//                try {
//                    DBLog.e("上传图片：", response.toString());
//                    if (response.getInt("code") == 0) {
//                        finish();
//                    }
//                    showToastMsg(response.getString("errorDescription"));
//                } catch (Exception e) {
//                    showToastMsg("数据解析异常...");
//                    e.printStackTrace();
//                } finally {
//                    ShowDialog.dissmiss();
//                }
//            }
//
//            @Override
//            public void onFinish() {
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                showToastMsg("请求服务器失败");
//                DBLog.e("tag", statusCode + ":" + responseString);
//                ShowDialog.dissmiss();
//            }
//        });

    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

    private org.json.JSONArray JSONArray;

}
