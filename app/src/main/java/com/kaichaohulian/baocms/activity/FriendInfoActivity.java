package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntegerRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteInfoBean;
import com.kaichaohulian.baocms.entity.UserInfoBean;
import com.kaichaohulian.baocms.event.UserPhotoBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.GlideUtils;
import com.kaichaohulian.baocms.util.PayDialog;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class FriendInfoActivity extends BaseActivity {

    @BindView(R.id.img_friend_info_avatar)
    ImageView mFriendInfoAvatar;
    @BindView(R.id.tv_friend_info_name)
    TextView mFriendInfoName;
    @BindView(R.id.tv_friend_info_id)
    TextView mFriendInfoId;
    @BindView(R.id.rl_friend_info)
    RelativeLayout mRlFriendInfo;
    @BindView(R.id.ll_friend_info_bz)
    LinearLayout mllFriendInfoBz;
    @BindView(R.id.tv_friend_info_age)
    TextView mFriendInfoAge;
    @BindView(R.id.tv_friend_info_professional)
    TextView mFriendInfoJob;
    @BindView(R.id.tv_friend_info_hobby)
    TextView mFriendInfoHobby;
    @BindView(R.id.tv_friend_info_address)
    TextView mFriendInfoAddress;
    @BindView(R.id.tv_friend_info_bq)
    TextView mFriendInfoBq;
    @BindView(R.id.tv_friend_info_pay)
    TextView mFriendInfoPay;
    @BindView(R.id.tv_friend_info_get)
    TextView mFriendInfoGet;
    @BindView(R.id.tv_friend_info_beijia)
    TextView mFriendInfoBeijia;
    @BindView(R.id.tv_friend_info_beiyao)
    TextView mFriendInfoBeiyao;
    @BindView(R.id.tv_friend_info_fuyue)
    TextView mFriendInfoFuyue;
    @BindView(R.id.tv_friend_info_remark)
    EditText mRemark;
    @BindView(R.id.img_friend_info_sex)
    ImageView mFriendInfoSex;
    @BindView(R.id.tv_friend_info_shaungyue)
    TextView mFriendInfoShaungyue;
    @BindView(R.id.img_friend_info_img1)
    ImageView imgFriendInfoImg1;
    @BindView(R.id.img_friend_info_img2)
    ImageView imgFriendInfoImg2;
    @BindView(R.id.img_friend_info_img3)
    ImageView imgFriendInfoImg3;
    @BindView(R.id.bt_friend_info_add)
    Button mPay;
    private ImageView rightImg;

    private UserInfoBean mUserInfoBean;

    private List<ImageView> imgList;

    private String fiendId, userPhone;
    private String addFriendMoney = "0";
    private String addType;//1.好友  2.加好友  3.打招呼
    private PopupWindow popupWindow;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        fiendId = intent.getStringExtra("friendId");
        userPhone = intent.getStringExtra("phone");
        addType = intent.getStringExtra("type");
        Log.e("gy", "用户ID：" + fiendId);
        Log.e("gy", "用户手机：" + userPhone);

        loadUserInfoPhone();
        loadInviteInfo();

    }

    @Override
    public void initView() {
        initTitlebar();
        imgList = new ArrayList<>();
        imgList.add(imgFriendInfoImg1);
        imgList.add(imgFriendInfoImg2);
        imgList.add(imgFriendInfoImg3);
    }

    @Override
    public void initEvent() {

    }

    private void initTitlebar() {
        TitleUtils titleUtils = new TitleUtils(this).setTitle("详细资料").showRight()
                .setRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopWindow();
                    }
                });
        rightImg = titleUtils.getRirht();
    }

    private void showPopWindow() {
        View popView = View.inflate(this, R.layout.pop_friend_info, null);
        TextView addBlack = (TextView) popView.findViewById(R.id.tv_pop_addblack);
        TextView deleteFriend = (TextView) popView.findViewById(R.id.tv_pop_delete);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(rightImg, 10, 10);

        if (mUserInfoBean.getIsfriend() == 0) {
            deleteFriend.setVisibility(View.GONE);
        } else {
            deleteFriend.setVisibility(View.VISIBLE);
        }

        addBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendBlack();
            }
        });

        deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFriend();
            }
        });
    }


    @OnClick({R.id.rl_friend_info, R.id.ll_friend_info_bz, R.id.bt_friend_info_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_friend_info:
                break;
            case R.id.ll_friend_info_bz:
                Intent intent2 = new Intent(this,RemarkActivity.class);
                intent2.putExtra("friendId",mUserInfoBean.getId()+"");
                startActivityForResult(intent2,111);
                break;
            case R.id.bt_friend_info_add:
                if (mUserInfoBean.getIsfriend() == 1) {
                    toChat();
                } else {
                    Intent intent = new Intent(this, AddFriendsFinalActivity.class);
                    intent.putExtra("friend_id", mUserInfoBean.getId() + "");
                    intent.putExtra("add_money", addFriendMoney);
                    intent.putExtra("type", addType);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 跳转到聊天页面
     */
    private void toChat() {
        if (mUserInfoBean == null) {
            return;
        }
        CCPAppManager.startChattingAction(FriendInfoActivity.this.getActivity()
                , mUserInfoBean.getPhoneNumber(), mUserInfoBean.getUsername(), true);
    }


    private void addFriendBlack() {
        if (mUserInfoBean.getIsfriend() == 0) {
            addBlack();
        } else {
            Intent intent = new Intent(this, BlackActivity.class);
            intent.putExtra("type", "1");
            intent.putExtra("friend_id", mUserInfoBean.getId() + "");
            startActivity(intent);
        }

        popupWindow.dismiss();
    }

    private void deleteFriend() {
        Intent intent = new Intent(this, BlackActivity.class);
        intent.putExtra("type", "2");
        intent.putExtra("friend_id", mUserInfoBean.getId() + "");
        startActivity(intent);
        popupWindow.dismiss();
    }

    /**
     * 加载用户基本信息
     */
    private void loadUserInfoPhone() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("phoneNumber", userPhone);
        userMap.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        RetrofitClient.getInstance().createApi().loadUserInfoPhone(userMap)
                .compose(RxUtils.<HttpResult<UserInfoBean>>io_main())
                .subscribe(new BaseObjObserver<UserInfoBean>(this, "加载中", false) {
                    @Override
                    protected void onHandleSuccess(UserInfoBean userInfoBean) {
                        mUserInfoBean = userInfoBean;
                        setBaseInfo(userInfoBean);
                        Log.e("gy", "用户信息：" + userInfoBean.getToken());
                    }
                });
    }

    /**
     * 加载用户 诚意金 应约信息
     */
    private void loadInviteInfo() {
        map.clear();
        map.put("phoneNumber", userPhone);
        RetrofitClient.getInstance().createApi().loadInviteInfo(map)
                .compose(RxUtils.<HttpResult<InviteInfoBean>>io_main())
                .subscribe(new BaseObjObserver<InviteInfoBean>(this, false) {
                    @Override
                    protected void onHandleSuccess(InviteInfoBean inviteInfoBean) {
                        setInviteInfo(inviteInfoBean);
                    }
                });
    }


    private void setInviteInfo(InviteInfoBean inviteInfoBean) {
        if (inviteInfoBean == null) {
            return;
        }
        mFriendInfoGet.setText(inviteInfoBean.getGetEarnestMoney() == null ? "¥ " + 0 : "¥ " + inviteInfoBean.getGetEarnestMoney());
        mFriendInfoPay.setText(inviteInfoBean.getPayEarnestMoney() == null ? "¥ " + 0 : "¥ " + inviteInfoBean.getPayEarnestMoney());
        mFriendInfoBeiyao.setText(inviteInfoBean.getBeInvite() == null ? "0" : (inviteInfoBean.getBeInvite() + "").replace(".0", ""));
        mFriendInfoBeijia.setText(inviteInfoBean.getBeToAdd() == null ? "0" : (inviteInfoBean.getBeToAdd() + "").replace(".0", ""));
        mFriendInfoFuyue.setText(inviteInfoBean.getAppointment() == null ? "0" : (inviteInfoBean.getAppointment() + "").replace(".0", ""));
        mFriendInfoShaungyue.setText(inviteInfoBean.getNoAppointment() == null ? "0" : ("" + inviteInfoBean.getNoAppointment()).replace(".0", ""));
    }

    private void setBaseInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }

        Glide.with(MyApplication.getInstance())
                .load(userInfoBean.getAvatar())
                .error(R.mipmap.default_useravatar)
                .crossFade()
                .into(mFriendInfoAvatar);

        mFriendInfoName.setText(userInfoBean.getUsername());
        mFriendInfoSex.setImageResource(userInfoBean.getSex() == 0 ? R.mipmap.boy : R.mipmap.gir);
        mFriendInfoId.setText(userInfoBean.getId() + "");
        mFriendInfoAge.setText(userInfoBean.getAge() == null ? "未知" : (userInfoBean.getAge() + "").replace(".0", ""));
        mFriendInfoJob.setText(userInfoBean.getJob() == null ? "未知" : userInfoBean.getJob() + "");
        mFriendInfoHobby.setText(userInfoBean.getHobby() == null ? "未知" : userInfoBean.getHobby() + "");
        mFriendInfoAddress.setText(userInfoBean.getDistrictId() + "");

        addFriendMoney = userInfoBean.getAddPay() + "";

        if (userInfoBean.getRemark()!=null && !userInfoBean.getRemark().equals("null")) {
            mRemark.setText(userInfoBean.getRemark().toString());
        }

        if (userInfoBean.getIsfriend() == 1) {
            mPay.setText("发消息");
        } else {
            if (addType.equals("3")) {
                mPay.setText("打招呼");
            } else {
                mPay.setText("加为好友");
            }
        }
        try {
            for (int i = 0; i < imgList.size(); i++) {
                String url = userInfoBean.getImages().get(i);
                String replace = url.replace("\"", "");
                String replace1 = replace.replace("\\", "");
                Log.e("gy", "replace:" + url);
                Glide.with(this).load(replace1).into(imgList.get(i));
            }
        } catch (Exception e) {

        }

    }

    /**
     * 拉入黑名单
     */
    private void addBlack() {
        Map<String, String> map = new HashMap<>();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("beUserId", fiendId);

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

        String remark = data.getStringExtra("remark");
        mRemark.setText(remark);
    }
}
