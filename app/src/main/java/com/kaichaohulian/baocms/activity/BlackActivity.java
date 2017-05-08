package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;
import com.kaichaohulian.baocms.view.MyGridView;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class BlackActivity extends BaseActivity {

    @BindView(R.id.tv_black_title)
    TextView tvBlackTitle;
    @BindView(R.id.et_black_content)
    EditText etContent;
    @BindView(R.id.ll_black_content)
    LinearLayout llBlackContent;
    @BindView(R.id.gv_black)
    MyGridView mGridView;
    @BindView(R.id.img_black_updown)
    ImageView imgBlackUpdown;
    @BindView(R.id.cb_black_xl)
    CheckBox cbBlackXl;
    @BindView(R.id.ll_black_xl)
    LinearLayout llBlackXl;
    @BindView(R.id.cb_black_weixie)
    CheckBox cbBlackWeixie;
    @BindView(R.id.ll_black_weixie)
    LinearLayout llBlackWeixie;
    @BindView(R.id.bt_black_commit)
    Button btCommit;

    private List<String> imgList;

    private String type;//上传类型 1 加入黑名单  2 删除好友
    private String friendId;
    private String chooseType; //选择的类型 1 下流  2 威胁
    private boolean isShow = true;//选择是否打开  默认打开
    private BlackGvAdapter mAdapter;
    private org.json.JSONArray JSONArray;
    private StringBuffer img = new StringBuffer();
    private UploadManager uploadManager;
    private String content;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_black);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
        friendId = getIntent().getStringExtra("friend_id");
        if (type.equals("1")) {
            tvBlackTitle.setText("拉入黑名单");
            new TitleUtils(this).setTitle("拉入黑名单");
        } else if (type.equals("2")) {
            tvBlackTitle.setText("删除好友");
            new TitleUtils(this).setTitle("删除好友");
        }
    }

    @Override
    public void initView() {
        imgList = new ArrayList<>();
        mAdapter = new BlackGvAdapter(this, imgList);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.ll_black_xl, R.id.ll_black_weixie, R.id.ll_black_content, R.id.ll_black_choose, R.id.bt_black_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_black_xl:
                cbBlackXl.setChecked(true);
                cbBlackWeixie.setChecked(false);
                chooseType = "下流邀请";
                break;
            case R.id.ll_black_weixie:
                cbBlackXl.setChecked(false);
                cbBlackWeixie.setChecked(true);
                chooseType = "威胁邀请";
                break;
            case R.id.ll_black_content:
                break;
            case R.id.ll_black_choose:
                isShow = !isShow;
                if (isShow) {
                    llBlackXl.setVisibility(View.VISIBLE);
                    llBlackWeixie.setVisibility(View.VISIBLE);
                    imgBlackUpdown.setImageResource(R.mipmap.arrow_up2);
                } else {
                    llBlackXl.setVisibility(View.GONE);
                    llBlackWeixie.setVisibility(View.GONE);
                    imgBlackUpdown.setImageResource(R.mipmap.arrow_down2);
                }
                break;
            case R.id.bt_black_commit:
                content = etContent.getText().toString();

                if (imgList.size() == 0) {
                    if (type.equals("1")) {
                        addBlack();
                    } else if (type.equals("2")) {
                        deleteFriend();
                    }
                } else {
                    InServer(imgList);
                }
                break;
        }
    }

    private void selectPhoto() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(9);
        intent.setShowCamera(true);
        startActivityForResult(intent, 123);
    }


    private void InServer(List<String> list) {
        ShowDialog.showDialog(getActivity(), "上传中...", false, null);
        JSONArray = new org.json.JSONArray();
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

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }


    private void upload(String uploadToken, File uploadFile, final int i) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                if (respInfo.isOK()) {
                    try {
                        ShowDialog.dissmiss();
                        String fileKey = jsonData.getString("key");
                        final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                        JSONArray.put(url);
                        Log.e(TAG, "complete: " + url);
                        img.append(fileKey + ",");
                        Log.e("gy", "图片地址：" + img.toString());

                        if (type.equals("1")) {
                            addBlack();
                        } else if (type.equals("2")) {
                            deleteFriend();
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

    /**
     * 删除好友
     */
    private void deleteFriend() {
        Map<String, String> map = new HashMap<>();
        if (TextUtils.isEmpty(chooseType)) {
            ToastUtil.showMessage("请选择类型");
            return;
        }
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("beUserId", friendId);
        if (!TextUtils.isEmpty(img.toString())) {
            map.put("imageList", img.toString());
        }
        map.put("reason", chooseType);
        if (!TextUtils.isEmpty(content)) {
            map.put("otherReason", content);
        }
        RetrofitClient.getInstance().createApi().deleteFriend(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("删除好友申请成功!");
                        finish();
                    }
                });
    }

    /**
     * 拉入黑名单
     */
    private void addBlack() {
        if (TextUtils.isEmpty(chooseType)) {
            ToastUtil.showMessage("请选择类型");
            return;
        }
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("beUserId", friendId);
        if (!TextUtils.isEmpty(img.toString())) {
            map.put("imageList", img.toString());
        }
        map.put("reason", chooseType);
        if (!TextUtils.isEmpty(content)) {
            map.put("otherReason", content);
        }
        RetrofitClient.getInstance().createApi().addBlack(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("拉黑好友申请成功!");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 123) {
            if (data != null) {
                imgList.clear();
                imgList.addAll(data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS));
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 图片适配器
     */
    public class BlackGvAdapter extends BaseAdapter {
        private Context context;
        private List<String> mList;
        private LayoutInflater mInflater;

        public BlackGvAdapter(Context context, List<String> mList) {
            this.context = context;
            this.mList = mList;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (mList == null) {
                return 1;
            } else if (mList.size() == 9) {
                return 9;
            } else {
                return mList.size() + 1;
            }
        }

        @Override
        public Object getItem(int position) {
            if (mList != null && mList.size() == 9) {
                return mList.get(position);
            } else if (mList == null || position - 1 < 0 || position > mList.size()) {
                return null;
            } else {
                return mList.get(position - 1);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_black_photo, parent, false);

            ImageView imgItemBlackPic = (ImageView) convertView.findViewById(R.id.img_item_black_pic);

            Log.e("gy", "position:" + position);
            Log.e("gy", "size:" + mList.size());

            if (position == mList.size()) {
                imgItemBlackPic.setImageResource(R.mipmap.shangchuan);
                imgItemBlackPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 点击小图片进入图片的选择界面(相册界面)
                        selectPhoto();
                    }
                });
            } else {
                Glide.with(context)
                        .load("file://" + mList.get(position))
                        .placeholder(R.mipmap.default_image)
                        .into(imgItemBlackPic);
            }
            return convertView;
        }

    }


}
