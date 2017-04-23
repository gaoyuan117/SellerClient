package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.RechargeGridViewAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class RechargeActivity extends BaseActivity {
    private static final int VERIFY_PASSWORD_REQUEST = 7;
    private static final int SET_PASSWORD_REQUEST = 8;

    private RelativeLayout advertise;
    private String[] markPrice = new String[]{"30", "50", "100", "300"};
    private String[] sellPrice = new String[]{"29.97", "49.95", "99.90", "299.70"};
    private GridView gridView;
    private RechargeGridViewAdapter adapter;
    private ImageView closeRelative;
    private EditText phone_number;

    @Override
    public void setContent() {
        setContentView(R.layout.recharge_gridview);
    }

    @Override
    public void initData() {
        adapter = new RechargeGridViewAdapter(getActivity(), markPrice, sellPrice);
    }

    @Override
    public void initView() {
        setCenterTitle("手机充值");
        setRightTitle("充值记录").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RechargeActivity.this, SmallMoneyDetailActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        gridView = getId(R.id.rechage_gridview);
        closeRelative = getId(R.id.close_relative);
        advertise = getId(R.id.advertisement_title);
        phone_number = getId(R.id.phone_number);

        phone_number.setText(MyApplication.getInstance().UserInfo.getPhoneNumber());
    }

    @Override
    public void initEvent() {
        gridView.setAdapter(adapter);

        closeRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advertise.setVisibility(View.GONE);
            }
        });
        gridView.setOnItemClickListener(new GridItemListener());
    }

    class GridItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            if (phone_number.getText().toString().equals("") || phone_number.getText().toString().length() != 11) {
                showToastMsg("请输入正确的手机号");
                return;
            }
            new AlertDialog.Builder(RechargeActivity.this)
                    .setMessage("请确认是否为" + phone_number.getText().toString() + "充值" + markPrice[position] + "元？")
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    recharge(position);
                                    dialog.dismiss();

                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();

                                }
                            }).setCancelable(false).show();

        }
    }

    public void recharge(int position) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("amount", sellPrice[position]);
        params.put("phoneNumber", phone_number.getText().toString());
        params.put("faceValue", markPrice[position]);
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        HttpUtil.post(Url.gotopay, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(getActivity());
                            return;
                        }
                    }
                    DBLog.i("tag", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("充值成功！");
                    }
                } catch (Exception e) {
                    showToastMsg("数据解析异常...");
                    e.printStackTrace();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VERIFY_PASSWORD_REQUEST:
                    showToastMsg("充值成功！");
                    finish();
                    break;
                case SET_PASSWORD_REQUEST:
                    DBLog.e("RechargeActivity", "设置密码成功");
                    finish();
                    break;

            }

        } else {
            showToastMsg("充值失败！");
        }


    }

}
