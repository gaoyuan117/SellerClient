package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.util.PayDialog;
import com.kaichaohulian.baocms.util.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    public void setContent() {
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        initTitlebar();
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
        PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(rightImg, 10, 10);

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
                break;
            case R.id.bt_friend_info_add:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        new PayDialog(this).setSureClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendInfoActivity.this, PayActivity.class);
                intent.putExtra("pay_money", "2");
                startActivity(intent);
            }
        }).showDialog();
    }

    private void addFriendBlack() {
        Intent intent = new Intent(this,BlackActivity.class);
        intent.putExtra("type","1");
        startActivity(intent);
    }

    private void deleteFriend() {
        Intent intent = new Intent(this,BlackActivity.class);
        intent.putExtra("type","2");
        startActivity(intent);
    }

    /**
     * 获取加好友所需的金额
     */
    private void getPayMoney() {

    }
}
