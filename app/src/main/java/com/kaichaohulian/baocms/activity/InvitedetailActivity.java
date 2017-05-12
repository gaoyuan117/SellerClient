package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.MyGridView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitedetailActivity extends BaseActivity {


    @BindView(R.id.tv_inviteloadding)
    TextView loaddingView;
    @BindView(R.id.scroll_invitedetail)
    ScrollView ThisView;

    @BindView(R.id.ll_invite_detail_state_isGoing)
    LinearLayout llInviteDetailStateIsGoing;    //状态为进行中
    @BindView(R.id.ll_invite_detail_state_NoGoing)
    LinearLayout llInviteDetailStateNoGoing;    //状态完成或取消
    @BindView(R.id.tv_invite_detail_state)
    TextView tv_DetailState;
    @BindView(R.id.img_invit_detail_avatar)
    ImageView img_DetailAvatar;
    @BindView(R.id.img_invit_detail_name)
    TextView tv_DetailUserName;
    @BindView(R.id.tv_invit_detail_id)
    TextView tv_DetailUserId;
    @BindView(R.id.tv_invit_detail_theme)
    TextView tv_DetailTheme;
    @BindView(R.id.tv_invite_detail_shoujian)
    TextView tv_DetailReciverNum;
    @BindView(R.id.tv_invite_detail_shoujian_name)
    TextView tv_DetailRevicerNames;
    @BindView(R.id.tv_invit_detail_nums)
    TextView tv_DetailInviteNum;
    @BindView(R.id.tv_invit_detail_money)
    TextView tv_DetailInviteMoney;
    @BindView(R.id.tv_invit_detail_time)
    TextView tv_DetailInviteTime;
    @BindView(R.id.tv_invit_detail_response)
    TextView tv_DetailInviteReponseTime;
    @BindView(R.id.tv_invit_detail_address)
    TextView tv_DetailInviteAddress;
    @BindView(R.id.tv_invite_detail_canyu)
    TextView tv_DetailInviteJoinNum;
    @BindView(R.id.gv_invite_detail)
    MyGridView grid_DetailInvitePeopleNum;
    @BindView(R.id.bt_invite_detail_sure)
    Button btn_reciverOrRefuse;
    @BindView(R.id.ll_invite_detail_state_ids)
    LinearLayout llInviteIds;
    private int UserId, InviteId;
    private boolean IsHost = false;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_invitedetail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        //初始化
        setCenterTitle("详情");
        loaddingView.setVisibility(View.VISIBLE);
        ThisView.setVisibility(View.GONE);

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        IsHost = intent.getBooleanExtra("IsOwn", false);
        if (!IsHost) {
            tv_DetailInviteJoinNum.setText("发起人");
        }
        InviteId = intent.getIntExtra("inviteId", 0);
        UserId = intent.getIntExtra("UserId", 0);
    }

    @Override
    public void initEvent() {
        if (map == null) {
            map = new HashMap<>();
        } else {
            map.clear();
        }
        if (UserId == 0 || InviteId == 0) {
            Toast.makeText(this, "不可预知的错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (IsHost) {
            RetrofitClient.getInstance().createApi().GetInviteDetailForHost(UserId + "", InviteId + "")
                    .compose(RxUtils.<HttpResult<InviteDetailEntity>>io_main())
                    .subscribe(new BaseObjObserver<InviteDetailEntity>(getActivity()) {
                        @Override
                        protected void onHandleSuccess(InviteDetailEntity inviteDetailEntity) {
                            setDataToView(inviteDetailEntity, 0);
                            loaddingView.setVisibility(View.GONE);
                            ThisView.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            RetrofitClient.getInstance().createApi().GetInviteDetailForReciver(UserId + "", InviteId + "")
                    .compose(RxUtils.<HttpResult<InviteDetailEntity>>io_main())
                    .subscribe(new BaseObjObserver<InviteDetailEntity>(getActivity()) {
                        @Override
                        protected void onHandleSuccess(InviteDetailEntity inviteDetailEntity) {
                            setDataToView(inviteDetailEntity, 1);
                            loaddingView.setVisibility(View.GONE);
                            ThisView.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    public void setDataToView(InviteDetailEntity inviteDetailEntity, int type) {
        if (inviteDetailEntity.getActiveStatus().contains("进行中")) {
            llInviteDetailStateIsGoing.setVisibility(View.VISIBLE);
            btn_reciverOrRefuse.setVisibility(View.VISIBLE);
        } else if (inviteDetailEntity.getActiveStatus().contains("失效")) {
            llInviteIds.setVisibility(View.GONE);
        }
        tv_DetailUserName.setText("发起人： " + inviteDetailEntity.getInvite().getNickName());
        Glide.with(getActivity()).load(inviteDetailEntity.getInvite().getAvatar()).into(img_DetailAvatar);
        tv_DetailUserId.setText(inviteDetailEntity.getInvite().getUserId() + "");
        tv_DetailTheme.setText(inviteDetailEntity.getInvite().getTitle());
        tv_DetailReciverNum.setText(inviteDetailEntity.getInvite().getUserNum() + "位收件人");
        tv_DetailInviteMoney.setText(inviteDetailEntity.getInvite().getInviteMoney() + "");
        tv_DetailInviteAddress.setText(inviteDetailEntity.getInvite().getInviteAddress());
        String time = inviteDetailEntity.getInvite().getInvateTime().substring(0, inviteDetailEntity.getInvite().getInvateTime().length() - 3);
        tv_DetailInviteTime.setText(time);
        tv_DetailInviteNum.setText(inviteDetailEntity.getInvite().getUserNum() + "");
        if (IsHost) {
            tv_DetailInviteJoinNum.setText("参与者(" + inviteDetailEntity.getInvite().getUserNum() + ")");
        } else {
            tv_DetailInviteJoinNum.setText("发起人");
        }

    }


    @OnClick(R.id.bt_invite_detail_sure)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_invite_detail_sure:
                RetrofitClient.getInstance().createApi().GetSureMeet(UserId+"",InviteId+"")
                        .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                        .subscribe(new BaseObjObserver<CommonEntity>(getActivity(),"确认中...") {
                            @Override
                            protected void onHandleSuccess(CommonEntity commonEntity) {
                                Toast.makeText(InvitedetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
