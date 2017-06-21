package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class FriendDetailActivity extends BaseActivity implements OnClickListener {
    public static final String FROM_GROUP = "FROM_GROUP";
    public static final String IS_FROM_ZXING = "IS_FROM_ZXING";
    private UserInfo mUserInfo;
    private boolean isFromZxing;
    private TextView tv_fxid;

    @Override
    public void setContent() {
        setContentView(R.layout.myfrienddetail_activity);
    }

    @Override
    public void initData() {
        isFromZxing = getIntent().getBooleanExtra(IS_FROM_ZXING, false);
        mUserInfo = (com.kaichaohulian.baocms.entity.UserInfo) getIntent().getSerializableExtra("data");
        if (mUserInfo != null) {
            initView();
        } else {
            finish();
        }
    }

    @Override
    public void initView() {
        boolean isFromGroup = getIntent().getBooleanExtra(FROM_GROUP, false);
        RelativeLayout regionRL = getId(R.id.rl_region_container);
        if (isFromGroup) {
            regionRL.setVisibility(View.VISIBLE);
        }
        if (MyApplication.getInstance().UserInfo != null) {
            List<String> images = mUserInfo.getImages();
            setCenterTitle(mUserInfo.getUsername());
            TextView tv_name = getId(R.id.tv_name);
            TextView tv_region = getId(R.id.tv_region);
            tv_fxid = getId(R.id.tv_fxid);
            TextView tv_sign = getId(R.id.tv_sign);
            ImageView rightIV = getId(R.id.iv_image1);
            ImageView image2 = getId(R.id.iv_image2);
            ImageView image3 = getId(R.id.iv_image3);
            ImageView image4 = getId(R.id.iv_image4);
            ImageView image5 = getId(R.id.iv_image5);

            if (mUserInfo.getImages() != null) {
                switch (images.size()) {
                    case 0:
                        break;
                    case 1:
                        ImageLoader.getInstance().displayImage(images.get(0), image2);
                        break;
                    case 2:
                        ImageLoader.getInstance().displayImage(images.get(0), image2);
                        ImageLoader.getInstance().displayImage(images.get(1), image3);
                        break;
                    case 3:
                        ImageLoader.getInstance().displayImage(images.get(0), image2);
                        ImageLoader.getInstance().displayImage(images.get(1), image3);
                        ImageLoader.getInstance().displayImage(images.get(2), image4);
                        break;
                    default:
                        ImageLoader.getInstance().displayImage(images.get(0), image2);
                        ImageLoader.getInstance().displayImage(images.get(1), image3);
                        ImageLoader.getInstance().displayImage(images.get(2), image4);
                        ImageLoader.getInstance().displayImage(images.get(3), image5);
                        break;
                }
            }
            rightIV.setImageResource(R.mipmap.threepoints);
            rightIV.setOnClickListener(this);

            RelativeLayout rl_sign = getId(R.id.rl_sign_container);
            ImageView iv_sex = getId(R.id.iv_sex);
            ImageView iv_avatar = getId(R.id.iv_avatar);
            RelativeLayout geren_xiangce = getId(R.id.geren_xiangce);
            Button btn_sendmsg = getId(R.id.btn_sendmsg);
            rl_sign.setOnClickListener(this);
            if (!StringUtils.isEmpty(mUserInfo.getUsername())) {
                tv_name.setText(mUserInfo.getUsername());
            } else {
                tv_name.setText("未命名");
            }
            if (!StringUtils.isEmpty(mUserInfo.getDistrictId()) && !"null".equals(mUserInfo.getDistrictId())) {
                tv_region.setText(mUserInfo.getDistrictId());
            } else {
                tv_region.setText("暂无地区");
            }
            String accountNumber = mUserInfo.getPhoneNumber();
            if (!TextUtils.isEmpty(accountNumber) && !"null".equals(accountNumber)) {
                tv_fxid.setText("ID:" + mUserInfo.getUserId());
            } else {
                searchUser(mUserInfo.getUserId());
            }
            if (!StringUtils.isEmpty(mUserInfo.getLabelName()) && !"null".equals(mUserInfo.getLabelName())) {
                tv_sign.setText(mUserInfo.getLabelName());
            } else {
                tv_sign.setText("这个人很懒，什么都没留下");
            }
            if (mUserInfo.getSex().equals("1")) {
                iv_sex.setImageResource(R.mipmap.ic_sex_male);
            } else {
                iv_sex.setImageResource(R.mipmap.ic_sex_female);
            }
            geren_xiangce.setOnClickListener(this);
//            geren_xiangce.setVisibility(View.GONE);
            Glide.with(getActivity()).load(mUserInfo.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_avatar);
            btn_sendmsg.setOnClickListener(this);

            if (!StringUtils.isEmpty(String.valueOf(mUserInfo.getUserId()))) {
                if (mUserInfo.getIsfriend() == 1) {
                    btn_sendmsg.setText("发消息");
                } else {
                    btn_sendmsg.setText("添加到通讯录");
                }
            }
        }

    }

    private void searchUser(final int id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.get(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        String phoneNumber = jsonObject.getString("phoneNumber");
                        if (!StringUtils.isEmpty(phoneNumber) && !"null".equals(phoneNumber)) {
                            tv_fxid.setText("帐号:" + phoneNumber);
                        } else {
                            tv_fxid.setText("暂无帐号");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    finish();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendmsg:
                if (MyApplication.getInstance().UserInfo.getId() == mUserInfo.getUserId()) {
                    showToastMsg("不能和自己聊天。。");
                    return;
                }
                if (isFromZxing) {
                    Bundle Bundle = new Bundle();
                    Bundle.putString("friendId", String.valueOf(mUserInfo.getUserId()));
                    ActivityUtil.next(getActivity(), AddFriendsFinalActivity.class, Bundle);
                } else {
                    if (mUserInfo.getIsfriend() == 1) {
//                    ActivityUtil.next(getActivity(), Chat.class, Bundle);
                        finish();
                    } else {
                        Bundle Bundle = new Bundle();
                        Bundle.putString("friendId", String.valueOf(mUserInfo.getUserId()));
                        ActivityUtil.next(getActivity(), AddFriendsFinalActivity.class, Bundle);
                    }
                }
                break;

            case R.id.rl_sign_container:
                //TODO modify tag for user

                break;

            case R.id.geren_xiangce:
//                if (images != null && images.size() > 0) {
                Intent intent = new Intent(getActivity(), MyAlbumActivity.class);
                intent.putExtra(MyAlbumActivity.IS_FRIEND, true);
                intent.putExtra(MyAlbumActivity.FRIEND_ID, mUserInfo.getUserId());
                startActivity(intent);
//                } else {
//                    ToastUtil.showMessage("当前好友无相册");
//                }
                break;

            case R.id.iv_image1:
                Intent intent2 = new Intent(getActivity(), ChatSettingActivity.class);
                intent2.putExtra("cUId", mUserInfo.getUserId());
                startActivity(intent2);
                break;
        }
    }
}
