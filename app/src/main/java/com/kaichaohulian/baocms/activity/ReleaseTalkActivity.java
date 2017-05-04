package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 发布说说
 * Created by ljl on 2016/10/29.
 */

public class ReleaseTalkActivity extends BaseActivity implements View.OnClickListener {

    private EditText content;
    private RelativeLayout whoCanSee, currentAddress, remindFriendsSee;
    private TextView status_tv;

    private GridView GridView;
    private List<String> list;

    private ImageBaseAdapter ImageBaseAdapter;
    int index = 0;

    private int REQUEST_CODE = 200;
    public int CAN_SEE = 201;
    public int ADDRESS_CODE = 300;
    public int REMIND_CODE = 400;

    private String TitleWhoSEE;
    private String privilegeType;
    private ArrayList<Integer> reminds;
    private ArrayList<Integer> users;
    private TextView currentLocTV;

    @Override
    public void setContent() {
        setContentView(R.layout.releasetalk_activity);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        ImageBaseAdapter = new ImageBaseAdapter();
    }

    @Override
    public void initView() {
        setCenterTitle("");
        TextView rightTv = setRightTitle("发送");
        rightTv.setBackgroundResource(R.mipmap.add_contactbtn);
        rightTv.setOnClickListener(this);
        content = (EditText) findViewById(R.id.content);
        GridView = (GridView) findViewById(R.id.gridview);
        whoCanSee = (RelativeLayout) findViewById(R.id.who_can_see_rlt);
        status_tv = (TextView) findViewById(R.id.status_tv);
        currentLocTV = (TextView) findViewById(R.id.tv_current_loc);
        currentAddress = (RelativeLayout) findViewById(R.id.current_address_rlt);
        remindFriendsSee = (RelativeLayout) findViewById(R.id.remind_friends_rlt);

        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index != 9 && index - 1 == position) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(ReleaseTalkActivity.this);
                    intent.setPhotoCount(9);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(getActivity(), list, position, imageSize);
                }
            }
        });

        GridView.setAdapter(ImageBaseAdapter);
    }

    @Override
    public void initEvent() {
        whoCanSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReleaseTalkActivity.this, ReleaseTalkWhoCanSeeActivity.class);
                startActivityForResult(intent, CAN_SEE);
            }
        });
        currentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReleaseTalkActivity.this, MyCurrentLocationActivity.class);
                startActivityForResult(intent, ADDRESS_CODE);
            }
        });
        remindFriendsSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReleaseTalkActivity.this, RemindFriendsSeeActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, REMIND_CODE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_text:
                if (content.getText().toString().isEmpty()) {
                    showToastMsg("请输入心得内容！");
                    return;
                }
                InServer();
                break;
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
            }
        } else if (requestCode == CAN_SEE) {
            if (data != null) {
                TitleWhoSEE = data.getStringExtra("title");
                privilegeType = data.getStringExtra("privilegeType");
                users = data.getIntegerArrayListExtra("users");
                status_tv.setText(TitleWhoSEE);
                DBLog.i("谁可以看", users);

            }
        } else if (requestCode == REMIND_CODE) {
            if (data != null) {
                reminds = data.getIntegerArrayListExtra("reminds");
                DBLog.i("提醒的好友发接受", this.reminds);
            }
        } else if (requestCode == ADDRESS_CODE) {
            if (data != null) {
                String addr = data.getStringExtra("addr");
                if (!TextUtils.isEmpty(addr)) {
                    currentLocTV.setText(addr);
                }
            }
        }
    }

    private UploadManager uploadManager;

    private void InServer() {
        ShowDialog.showDialog(getActivity(), "上传中...", false, null);
        JSONArray = new JSONArray();
        if (list.size() == 0) {
            Commt();
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


    private JSONArray JSONArray;

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
                                if (JSONArray.length() == index - 1) {
                                    Commt();
                                }
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

    public void Commt() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("content", content.getText().toString());
        if (list.size() != 0) {
            params.put("images", JSONArray);
        }
        params.put("privilegeType", privilegeType);
        if (null != reminds && reminds.size() != 0) {
            params.put("reminds", reminds);
        }
        if (null != users && users.size() != 0) {
            params.put("users", users);
        }
        HttpUtil.post(Url.release, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.e("上传图片：", response.toString());
                    if (response.getInt("code") == 0) {
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("数据解析异常...");
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
                ShowDialog.dissmiss();
            }
        });

    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }
}
