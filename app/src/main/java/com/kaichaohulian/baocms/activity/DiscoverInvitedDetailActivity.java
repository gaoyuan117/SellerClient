package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.GlideUtils;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private InviteReciverEntity mEntity;

    private String inviteId,type;

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
        new TitleUtils(this).setTitle("邀请信息");
    }

    @Override
    public void initEvent() {
        tvInvitedDetailChat.setOnClickListener(this);
        tvInvitedDetailAgree.setOnClickListener(this);
        tvInvitedDetailRefuse.setOnClickListener(this);
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
                    .error(R.mipmap.default_image)
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
}
