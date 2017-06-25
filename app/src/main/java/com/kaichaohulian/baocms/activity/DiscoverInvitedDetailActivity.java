package com.kaichaohulian.baocms.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.kaichaohulian.baocms.adapter.DiscoverInviteAdapter;
import com.kaichaohulian.baocms.adapter.InviteDetailGrid2Adapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.GlideUtils;
import com.kaichaohulian.baocms.util.TitleUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class DiscoverInvitedDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.tv_adver_detail_time)
    TextView tvAdverDetailTime;
    @BindView(R.id.img_invited_detail_avatar)
    ImageView imgInvitedDetailAvatar;
    @BindView(R.id.img_invited_detail_name)
    TextView imgInvitedDetailName;
    @BindView(R.id.tv_invited_detail_id)
    TextView tvInvitedDetailId;
    @BindView(R.id.tv_invited_detail_theme)
    TextView tvInvitedDetailTheme;
    @BindView(R.id.tv_invited_detail_nums)
    TextView tvInvitedDetailNums;
    @BindView(R.id.tv_invited_detail_money)
    TextView tvInvitedDetailMoney;
    @BindView(R.id.tv_invited_detail_time)
    TextView tvInvitedDetailTime;
    @BindView(R.id.tv_invited_detail_response)
    TextView tvInvitedDetailResponse;
    @BindView(R.id.tv_invited_detail_address)
    TextView tvInvitedDetailAddress;
    @BindView(R.id.tv_invited_detail_chat)
    TextView tvInvitedDetailChat;
    @BindView(R.id.tv_invited_detail_refuse)
    ImageView tvInvitedDetailRefuse;
    @BindView(R.id.tv_invited_detail_agree)
    ImageView tvInvitedDetailAgree;
    @BindView(R.id.activity_adver_detail)
    RelativeLayout activityAdverDetail;
    @BindView(R.id.tv_invited_detail_tousu)
    TextView tousu;
    private InviteReciverEntity mEntity;

    private String inviteId, type;

    public List<String> list = new ArrayList<>();
    public ImageBaseAdapter imageBaseAdapter;
    public GridView gridView;
    private Dialog dialog;
    private ImageView dialogClose;
    private TextView dialogCancle;
    private TextView dialogSure;
    private UploadManager uploadManager;
    private EditText editText;
    public int index = 0;
    private String content;
    private List<String> picList;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        inviteId = getIntent().getStringExtra("inviteId");
//        type = getIntent().getStringExtra("type");
//        if(type.equals("1")){
//            tvInvitedDetailAgree.setVisibility(View.INVISIBLE);
//            tvInvitedDetailRefuse.setVisibility(View.INVISIBLE);
//        }else {
//            tvInvitedDetailAgree.setVisibility(View.VISIBLE);
//            tvInvitedDetailRefuse.setVisibility(View.VISIBLE);
//        }
        loadDetail();
    }

    @Override
    public void initView() {
        picList = new ArrayList<>();
        new TitleUtils(this).setTitle("邀请信息");
    }

    @Override
    public void initEvent() {
        tvInvitedDetailChat.setOnClickListener(this);
        tvInvitedDetailAgree.setOnClickListener(this);
        tvInvitedDetailRefuse.setOnClickListener(this);
        tousu.setOnClickListener(this);
    }

    private void loadDetail() {
        RetrofitClient.getInstance().createApi().GetInviteDetailForReciver(MyApplication.getInstance().UserInfo.getUserId() + "", inviteId)
                .compose(RxUtils.<HttpResult<InviteReciverEntity>>io_main())
                .subscribe(new BaseObjObserver<InviteReciverEntity>(this, "加载中") {
                    @Override
                    protected void onHandleSuccess(InviteReciverEntity inviteReciverEntity) {
                        if (inviteReciverEntity == null) return;
                        mEntity = inviteReciverEntity;
                        setData();
                    }
                });
    }

    private void setData() {
        try {
            tvAdverDetailTime.setText(mEntity.user.createdTime);
            Glide.with(MyApplication.getInstance())
                    .load(mEntity.user.avator)
                    .error(R.mipmap.default_useravatar)
                    .crossFade()
                    .into(imgInvitedDetailAvatar);
            imgInvitedDetailName.setText("发起人：" + mEntity.user.username);
            tvInvitedDetailId.setText("ID:" + mEntity.user.user_id);
            tvInvitedDetailTheme.setText(mEntity.dto.title);
            tvInvitedDetailNums.setText(mEntity.dto.userNum + "");
            tvInvitedDetailMoney.setText(mEntity.dto.inviteMoney + "");
            tvInvitedDetailResponse.setText(mEntity.dto.applyTime + "");
            tvInvitedDetailTime.setText(mEntity.dto.invateTime + "");
            tvInvitedDetailAddress.setText(mEntity.dto.inviteAddress);
        } catch (Exception e) {
            Log.e("gy", e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_invited_detail_chat:
                toChat();
                break;
            case R.id.tv_invited_detail_tousu:
                Observable.just(1)
                        .compose(RxUtils.<Integer>io_main())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
//                                startActivity(new Intent(getActivity(), OnlineServiceActivity.class));
                                openComplainDialog(mEntity.user.user_id);
                            }
                        });
                break;
            case R.id.tv_invited_detail_refuse:
                acceptOrRefuse(2);
                break;

            case R.id.tv_invited_detail_agree:
                acceptOrRefuse(1);
                break;
        }
    }

    private void toChat() {
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra(ChattingFragment.RECIPIENTS, mEntity.user.phoneNumber + "");
        intent.putExtra(ChattingFragment.CONTACT_USER, mEntity.user.username);
        intent.putExtra("user_id", mEntity.user.user_id + "");
        intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
        startActivity(intent);
    }

    /**
     * 接受或拒绝邀请
     *
     * @param
     * @param state
     */
    public void acceptOrRefuse(final int state) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        map.put("inviteId", inviteId);
        map.put("status", state);
        RetrofitClient.getInstance().createApi().acceptOrRefuse(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        if (state == 1) {
                            ToastUtil.showMessage("已接受");
                        } else if (state == 2) {
                            ToastUtil.showMessage("已拒绝");
                        }
                    }
                });
    }

    private void openComplainDialog(final int toUserId) {
        View view = View.inflate(this, R.layout.dialog_complain, null);
        dialogClose = (ImageView) view.findViewById(R.id.img_complain_close);
        dialogCancle = (TextView) view.findViewById(R.id.tv_complain_cancle);
        dialogSure = (TextView) view.findViewById(R.id.tv_complain_sure);
        gridView = (GridView) view.findViewById(R.id.gv_complain_photos);
        editText = (EditText) view.findViewById(R.id.et_complain_content);
        imageBaseAdapter = new ImageBaseAdapter();
        gridView.setAdapter(imageBaseAdapter);
        dialog = new Dialog(this, R.style.dialog_type);
        dialog.setContentView(view);
        dialog.show();
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (index != 3 && index - 1 == position) {
                    PhotoPickerIntent intent = new PhotoPickerIntent(DiscoverInvitedDetailActivity.this);
                    intent.setPhotoCount(3);
                    intent.setShowCamera(true);
                    startActivityForResult(intent, 112);
                } else {
                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                    ImagePagerActivity.startImagePagerActivity(getActivity(), list, position, imageSize);
                }
            }
        });


        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showMessage("请输入投诉理由");
                    return;
                }
                if (list.size() == 0) {
                    Commt(toUserId);
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    File mFile = new File(list.get(i));
                    if (mFile.exists()) {
                        upload(getToken(), mFile, i, toUserId);
                    } else {
                        ShowDialog.dissmiss();
                        ToastUtil.showMessage("获取图片异常");
                    }
                }
            }
        });
    }


    public class ImageBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            index = list.size() != 3 ? list.size() + 1 : list.size();
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
            convertView = LayoutInflater.from(DiscoverInvitedDetailActivity.this).inflate(R.layout.release_imageitem, null);
            ImageView ImageView = (android.widget.ImageView) convertView.findViewById(R.id.image);
            if (index != 3 && index - 1 == position)
                ImageView.setBackgroundResource(R.mipmap.icon_releasetalk_add);
            else
                showImageView(ImageView, list.get(position));
            return convertView;
        }
    }

    private void showImageView(ImageView iamgeView, String avatar) {
        Glide.with(this)
                .load(avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iamgeView);
    }

//    private JSONArray JSONArray = new JSONArray();

    private void upload(String uploadToken, File uploadFile, final int i, final int toUserId) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        picList.clear();
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
//                                JSONArray.put(url);
                                picList.add(url);
                                if (picList.size() == index - 1) {
                                    Commt(toUserId);
                                }
                            } catch (Exception e) {
                                Log.e("gy", "图片失败：" + e.toString());
                                ShowDialog.dissmiss();
                                ToastUtil.showMessage("上传图片失败");
                            }
                        } else {
                            ShowDialog.dissmiss();
                            ToastUtil.showMessage("上传图片失败");
                        }
                    }
                }, null);
    }

    public void Commt(int toUserId) {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("toUserId", toUserId);

        params.put("remark", content);
        if (list.size() != 0) {
//            params.put("images", JSONArray);

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < picList.size(); i++) {
                sb.append(picList.get(i)+",");
            }
            params.put("images", sb.toString());
        }

        HttpUtil.get(Url.complain, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.e("投诉：", response.toString());
                    if (response.getInt("code") == 0) {
                        ToastUtil.showMessage("投诉信息已提交，请联系客服");
                        startActivity(new Intent(getActivity(), OnlineServiceActivity.class));
                        dialog.dismiss();

                    }else {
                        ToastUtil.showMessage(response.getString("errorDescription"));
                    }
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });

    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 112) {
            if (data != null) {
                list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (imageBaseAdapter != null) {
                    imageBaseAdapter.notifyDataSetChanged();

                }
            }
        }
    }
}
