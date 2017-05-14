package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SendinvitationActivity extends BaseActivity {


    @BindView(R.id.edt_invitiontitle)
    EditText edtInvitiontitle;
    @BindView(R.id.edt_InvitionPay)
    EditText edtInvitionPay;
    @BindView(R.id.edt_Invitiontime)
    TextView TvInvitiontime;
    @BindView(R.id.edt_actaddress)
    EditText edtActaddress;
    @BindView(R.id.edt_sendInvitionNum)
    EditText edtSendInvitionNum;
    @BindView(R.id.tv_responsetime)
    TextView tvResponsetime;
    SimpleDateFormat AllTtmeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat DayTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private TimePickerView timePickerView;
    private TimePickerView ReponseTimePickerView;
    private final int SELECT_ID = 100;
    private String ids;
    private String InviteTime, ApplyTime;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sendinvitation);
        ButterKnife.bind(this);

    }

    public boolean compareDate(String start, String end) {
        long i = 0;
        try {
            i = AllTtmeFormat.parse(start).getTime() - AllTtmeFormat.parse(end).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("发送邀请");
        TextView tv = setRightTitle("确定");
        tv.setBackgroundResource(R.mipmap.rounded_rectangle);
        tv.setTextColor(getResources().getColor(R.color.blue));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendInvite(view);
                view.setClickable(false);
            }
        });

        TvInvitiontime.setText(DayTimeFormat.format(Calendar.getInstance().getTime()));
        InviteTime = AllTtmeFormat.format(Calendar.getInstance().getTime());

        edtActaddress.setHint(MyApplication.getInstance().BDLocation.getAddrStr());

    }

    private void SendInvite(final View view) {
        if (map == null) {
            map = new HashMap<>();
        } else {
            map.clear();
        }
        if (ids == null) {
            Toast.makeText(this, "请选择要邀请的人", Toast.LENGTH_SHORT).show();
            return;
        } else {
            map.put("ids", ids);
        }
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        if (edtInvitiontitle.getText().toString().equals(""))
            return;
        map.put("title", edtInvitiontitle.getText().toString().trim());
        if (edtInvitionPay.getText().toString().equals("")) {
            map.put("inviteMoney", 200 + "");
        } else {
            map.put("inviteMoney", edtInvitionPay.getText().toString());
        }
        if (edtSendInvitionNum.getText().toString().equals("")) {
            map.put("userNum", 10 + "");
        } else {
            map.put("userNum", edtSendInvitionNum.getText().toString().trim());
        }
        if (edtActaddress.getText().toString().equals("")) {
            map.put("inviteAddress", edtActaddress.getHint().toString());
        } else {
            map.put("inviteAddress", edtActaddress.getText().toString().trim());
        }
        map.put("longitud", MyApplication.getInstance().BDLocation.getLongitude() + "");
        map.put("latitude", MyApplication.getInstance().BDLocation.getLatitude() + "");
        map.put("invateTime", InviteTime);
        if (ApplyTime == null) {
            Toast.makeText(this, "请选择响应时间", Toast.LENGTH_SHORT).show();
            return;
        } else {
            map.put("applyTime", ApplyTime);
        }
        RetrofitClient.getInstance().createApi().SendInvite(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new Observer<HttpResult<CommonEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult<CommonEntity> value) {
                        if (value.errorDescription.contains("成功")) {
                            Toast.makeText(SendinvitationActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(SendinvitationActivity.this, value.errorDescription, Toast.LENGTH_SHORT).show();
                            view.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_ID && resultCode == RESULT_OK) {
            ids = data.getStringExtra("ids");
        }
    }

    @OnClick({R.id.rl_reciver_sendInvition, R.id.rl_sendInvitionNum, R.id.rl_InvitionPay, R.id.rl_Invitiontime, R.id.rl_responsetime, R.id.rl_Activity_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_reciver_sendInvition:
                Intent intent = new Intent(getActivity(), AdvertMassSelectActivity.class);
                intent.putExtra("JustSelect", true);
                intent.putExtra("title", "选择好友");
                startActivityForResult(intent, SELECT_ID);
                break;

            case R.id.rl_Invitiontime:
                if (timePickerView == null) {
                    timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            TvInvitiontime.setText(DayTimeFormat.format(date));

                            InviteTime = AllTtmeFormat.format(date);
                        }
                    }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                            .setDate(Calendar.getInstance())
                            .build();
                    timePickerView.show();

                } else {
                    timePickerView.show();
                }
                break;
            case R.id.rl_responsetime:
                if (ReponseTimePickerView == null) {
                    ReponseTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            ApplyTime = DayTimeFormat.format(date);
                            tvResponsetime.setText(ApplyTime);
                        }
                    }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                            .setDate(Calendar.getInstance())
                            .setTitleText("选择响应截止时间")
                            .build();
                    ReponseTimePickerView.show();
                } else {
                    ReponseTimePickerView.show();
                }
                break;
        }
    }
}

