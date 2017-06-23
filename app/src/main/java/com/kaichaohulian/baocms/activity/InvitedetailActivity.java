package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.InviteDetailGrid2Adapter;
import com.kaichaohulian.baocms.adapter.InviteDetailGridAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.MyGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitedetailActivity extends BaseActivity {


    @BindView(R.id.tv_inviteloadding)
    TextView loaddingView;
    @BindView(R.id.scroll_invitedetail)
    ScrollView ThisView;
    @BindView(R.id.tv_invite_detail_applytime)
    TextView ApplyTime;
    @BindView(R.id.ll_invite_detail_state_isGoing)
    LinearLayout llInviteDetailStateIsGoing;    //状态为进行中
    @BindView(R.id.ll_invite_detail_state_NoGoing)
    LinearLayout llInviteDetailStateNoGoing;    //状态完成或取消
    @BindView(R.id.tv_invite_detail_state)
    TextView tv_DetailState;    //状态
    @BindView(R.id.img_invit_detail_avatar)
    ImageView img_DetailAvatar;//头像
    @BindView(R.id.img_invit_detail_name)
    TextView tv_DetailUserName;//名字
    @BindView(R.id.tv_invit_detail_id)
    TextView tv_DetailUserId;//userID
    @BindView(R.id.tv_invit_detail_theme)
    TextView tv_DetailTheme;//主题
    @BindView(R.id.tv_invite_detail_shoujian)
    TextView tv_DetailReciverNum;//接受人数
    @BindView(R.id.tv_invite_detail_shoujian_name)
    TextView tv_DetailRevicerNames;//接受人名字
    @BindView(R.id.tv_invit_detail_nums)
    TextView tv_DetailInviteNum;//邀请人数
    @BindView(R.id.tv_invit_detail_money)
    TextView tv_DetailInviteMoney;//邀请金额
    @BindView(R.id.tv_invit_detail_time)
    TextView tv_DetailInviteTime;//邀请时间
    @BindView(R.id.tv_invit_detail_response)
    TextView tv_DetailInviteReponseTime;//响应时间
    @BindView(R.id.tv_invit_detail_address)
    TextView tv_DetailInviteAddress;//地址
    @BindView(R.id.tv_invite_detail_canyu)
    TextView tv_DetailInviteJoinNum;//参与人数
    @BindView(R.id.gv_invite_detail)
    MyGridView grid_DetailInvitePeopleNum;//参与人列表
    @BindView(R.id.bt_invite_detail_sure)
    Button btn_reciverOrRefuse;//按钮_见面确认
    @BindView(R.id.ll_invite_detail_state_ids)
    LinearLayout llInviteIds;//参与人布局


    private int UserId, InviteId;
    private boolean IsHost = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
            //发布人查看
            RetrofitClient.getInstance().createApi().GetInviteDetailForHost(UserId + "", InviteId + "")
                    .compose(RxUtils.<HttpResult<InviteDetailEntity>>io_main())
                    .subscribe(new BaseObjObserver<InviteDetailEntity>(getActivity()) {
                        @Override
                        protected void onHandleSuccess(InviteDetailEntity inviteDetailEntity) {
                            loaddingView.setVisibility(View.GONE);
                            ThisView.setVisibility(View.VISIBLE);
                            setDataForHost(inviteDetailEntity);
                        }
                    });
        } else {
            //受邀人查看
            RetrofitClient.getInstance().createApi().GetInviteDetailForReciver(UserId + "", InviteId + "")
                    .compose(RxUtils.<HttpResult<InviteReciverEntity>>io_main())
                    .subscribe(new BaseObjObserver<InviteReciverEntity>(getActivity()) {
                        @Override
                        protected void onHandleSuccess(InviteReciverEntity inviteReciverEntity) {
                            loaddingView.setVisibility(View.GONE);
                            ThisView.setVisibility(View.VISIBLE);
                            setDataForNoHost(inviteReciverEntity);
                        }
                    });
        }
    }

    //我发起的-邀请详情
    public void setDataForHost(InviteDetailEntity inviteDetailEntity) {
        InviteDetailGridAdapter adapter = null;
        InviteDetailGrid2Adapter adapter2 = null;
        switch (inviteDetailEntity.invite.status) {
            case 0:
                //显示剩余时间
                llInviteDetailStateIsGoing.setVisibility(View.VISIBLE);
                //见面确认按钮
                btn_reciverOrRefuse.setVisibility(View.VISIBLE);
                long time1 = (getTimeStamp(inviteDetailEntity.invite.invateTime) - new Date().getTime());
                ApplyTime.setText("倒计时："+getStrTime(time1));
                //参与者列表
                adapter = new InviteDetailGridAdapter(getActivity(), inviteDetailEntity.list);
                adapter.setLayoutIds(R.layout.item_inviteinfo);
                grid_DetailInvitePeopleNum.setAdapter(adapter);
                break;
            case 2:
                //完成
                llInviteDetailStateNoGoing.setVisibility(View.VISIBLE);
                tv_DetailState.setText("活动已完成");
                adapter2 = new InviteDetailGrid2Adapter(getActivity(), inviteDetailEntity.list, null);
                adapter2.setLayoutIds(R.layout.item_inviteinfo2);
                grid_DetailInvitePeopleNum.setAdapter(adapter);
                break;
            case 3:
            case 4:
                //失效退款
                llInviteIds.setVisibility(View.GONE);
                llInviteDetailStateNoGoing.setVisibility(View.VISIBLE);
                tv_DetailState.setText("已退款\n" + inviteDetailEntity.invite.invateTime + "\n退款金额：￥" + inviteDetailEntity.invite.inviteMoney);
                break;
        }
        //设置发布人名字
        tv_DetailUserName.setText("发起人： " + inviteDetailEntity.invite.nickName);
        //设置发布人头像
        Glide.with(getActivity()).load(inviteDetailEntity.invite.avatar).error(R.mipmap.default_useravatar).into(img_DetailAvatar);
        //设置发布人id
        tv_DetailUserId.setText("ID:"+inviteDetailEntity.invite.userId + "");
        //设置邀请主题
        tv_DetailTheme.setText(inviteDetailEntity.invite.title);
        //设置收件人人数
        Log.d("InvitedetailActivity", "inviteDetailEntity.invite.userNum:" + inviteDetailEntity.invite.userNum);
        tv_DetailReciverNum.setText((int)((double)inviteDetailEntity.invite.userNum)+ "位收件人");
        //设置邀请金额
        double i=(double)inviteDetailEntity.invite.inviteMoney;
        if(i==0.0){
            tv_DetailInviteMoney.setText("0");
        }else{
            tv_DetailInviteMoney.setText((int)i+"");
        }
        //设置邀请地址
        tv_DetailInviteAddress.setText(inviteDetailEntity.invite.inviteAddress);
        //设置邀请时间
        String time = inviteDetailEntity.invite.invateTime.substring(0, inviteDetailEntity.invite.invateTime.length() - 3);
        tv_DetailInviteTime.setText(time);
        //设置响应时间
        tv_DetailInviteReponseTime.setText(inviteDetailEntity.invite.applyTime);
        //设置邀请人数
        tv_DetailInviteNum.setText((int)((double)inviteDetailEntity.invite.userNum) + "");
        //设置受邀人的名字
        String names = inviteDetailEntity.invite.inviteUsers;
        try {
            names = names.substring(0, names.length() - 1);
            names.replace("null,", "");
        } catch (IndexOutOfBoundsException e) {
            names = "";
        }
        tv_DetailRevicerNames.setText(names);

        if (IsHost && adapter != null) {
            tv_DetailInviteJoinNum.setText("参与者(" + adapter.getList().size() + ")");
        } else {
            llInviteIds.setVisibility(View.GONE);
        }
    }

    //邀请我的-邀请详情
    public void setDataForNoHost(InviteReciverEntity inviteReciverEntity) {
        try {
            InviteDetailGrid2Adapter adapter2 = null;
            switch (inviteReciverEntity.dto.status) {
                case 0:
                    //进行中
                    long time1 = (getTimeStamp(inviteReciverEntity.dto.invateTime) - new Date().getTime());
                    ApplyTime.setText("倒计时："+getStrTime(time1));
                    llInviteIds.setVisibility(View.GONE);
                    llInviteDetailStateIsGoing.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    //完成
                    llInviteIds.setVisibility(View.VISIBLE);
                    llInviteDetailStateNoGoing.setVisibility(View.VISIBLE);
//                    findViewById(R.id.ll_invite_detail_shoujian).setVisibility(View.GONE);
                    adapter2 = new InviteDetailGrid2Adapter(getActivity(), null, inviteReciverEntity.user);
                    adapter2.setLayoutIds(R.layout.item_inviteinfo2);
                    grid_DetailInvitePeopleNum.setAdapter(adapter2);
                    tv_DetailState.setText("活动已完成");
                    break;
                case 3:
                case 4:
                    //失效退款
                    llInviteDetailStateNoGoing.setVisibility(View.VISIBLE);
                    tv_DetailState.setText("活动已失效");
                    llInviteIds.setVisibility(View.GONE);

                    break;
            }
            tv_DetailUserName.setText("发起人： " + inviteReciverEntity.user.username);
            Glide.with(getActivity()).load(inviteReciverEntity.user.avator).error(R.mipmap.default_useravatar).into(img_DetailAvatar);
            tv_DetailUserId.setText("ID:"+inviteReciverEntity.user.user_id + "");
            tv_DetailTheme.setText(inviteReciverEntity.dto.title);
//            tv_DetailReciverNum.setVisibility(View.GONE);
            tv_DetailInviteNum.setText((int)((double)inviteReciverEntity.dto.userNum) + "");
            tv_DetailInviteMoney.setText((double)inviteReciverEntity.dto.inviteMoney + "");
            String time = inviteReciverEntity.dto.invateTime.substring(0, inviteReciverEntity.dto.invateTime.length() - 3);
            tv_DetailInviteTime.setText(time);
            tv_DetailInviteAddress.setText(inviteReciverEntity.dto.inviteAddress);
            tv_DetailInviteReponseTime.setText(inviteReciverEntity.dto.applyTime);
            findViewById(R.id.ll_invite_detail_shoujian).setVisibility(View.GONE);
            tv_DetailRevicerNames.setVisibility(View.GONE);
            if (llInviteIds.getVisibility() == View.VISIBLE) {
                tv_DetailInviteJoinNum.setText("发起人");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getTimeStamp(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getStrTime(long cc_time) {
        Log.e("gy", "时间差：" + cc_time);
        long days = cc_time / (1000 * 60 * 60 * 24);
        long hours = (cc_time-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (cc_time-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);

        return hours + "小时" + minutes + "分钟";
    }

    @OnClick(R.id.bt_invite_detail_sure)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_invite_detail_sure:
                RetrofitClient.getInstance().createApi().GetSureMeet(UserId + "", InviteId + "")
                        .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                        .subscribe(new BaseObjObserver<CommonEntity>(getActivity(), "确认中...") {
                            @Override
                            protected void onHandleSuccess(CommonEntity commonEntity) {
                                Toast.makeText(InvitedetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }


}
