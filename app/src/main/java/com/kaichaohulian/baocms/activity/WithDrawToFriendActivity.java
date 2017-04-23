package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class WithDrawToFriendActivity extends BaseActivity {
    private ImageView head;
    private TextView name;
    private EditText mount;
    private TextView addMessage;
    private Button withDraw;
    private String mountNum;
    private String mPhoneNum;
    private String mUsername;
    private String mUserId;
    private String mLogoUrl;
    private static final int VERIFY_PASSWORD_REQUEST = 7;
    private static final int SET_PASSWORD_REQUEST = 8;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_with_draw_right);
    }

    @Override
    public void initData() {
        mPhoneNum = getIntent().getStringExtra("mRecipients");
        mUsername = getIntent().getStringExtra("contact_user");
        mLogoUrl = getIntent().getStringExtra("contact_logo");
        mUserId = getIntent().getStringExtra("contact_id");
    }

    @Override
    public void initView() {
        setCenterTitle("转账给朋友");
        head = getId(R.id.img_friend);
        name = getId(R.id.txt_name);
        mount = getId(R.id.edt_mount);
        addMessage = getId(R.id.add_message);
        withDraw = getId(R.id.withdraw_cash_btn);
        name.setText(mUsername);
        if (!StringUtils.isEmpty(mLogoUrl)) {
            Glide.with(getApplicationContext()).load(mLogoUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(head);
        } else {
            head.setImageResource(R.mipmap.default_useravatar);
        }
    }

    @Override
    public void initEvent() {
        withDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mountNum = mount.getText().toString();
                if (TextUtils.isEmpty(mountNum)) {
                    showToastMsg("请输入转账金额");
                    return;
                }
//                if (MyApplication.getInstance().UserInfo.getPayPassword() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), SetPasswordActivity.class);
//                    startActivityForResult(intent, SET_PASSWORD_REQUEST);
//                } else {
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), VerifyPasswordActivity.class);
//                    intent.putExtra("price", Double.valueOf(mountNum));
//                    startActivityForResult(intent, VERIFY_PASSWORD_REQUEST);
//                }
                if (Integer.parseInt(mountNum) > 1000000) {
                    Toast.makeText(getApplicationContext(), "转账金额不能超过100万元", Toast.LENGTH_SHORT).show();
                    return;
                }
                tansferToFriend(mountNum);
            }
        });
        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dlg = new AlertDialog.Builder(WithDrawToFriendActivity.this).create();
                final View dialogView = getLayoutInflater().inflate(R.layout.dialog_with_draw_to_friend, null);
                final EditText msg = (EditText) dialogView.findViewById(R.id.transfer_tips);
                dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dlg.dismiss();
                    }
                });
                dialogView.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addMessage.setText(msg.getText());
                        dlg.dismiss();
                    }
                });
                dlg.setView(dialogView);
                dlg.show();
            }
        });
    }


    public void tansferToFriend(final String amount) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("toUser", Long.parseLong(mUserId));
        params.put("amount", amount);
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        HttpUtil.post(Url.transfer, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(getActivity());
                            return;
                        }
                    }
                    if (response.getInt("code") == 1) {
                        String errorDescription = response.getString("errorDescription");
                        ToastUtil.showMessage(errorDescription);
                        return;
                    }
                    JSONObject jsonObject = response.getJSONObject("dataObject");
                    String transferid = jsonObject.getString("id");
                    searchUser(mUserId, amount, transferid);
                } catch (Exception e) {
                    showToastMsg("验证密码，解析json失败");
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
            }
        });
    }

    private void searchUser(final String id, final String amount, final String transferid) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        DBLog.e("转账", response.toString());
                        if (response.getInt("code") == 0) {
                            UserInfoManager.getInstance().updateUserCache(getActivity());
                            Intent intent = new Intent(WithDrawToFriendActivity.this, RechargeSuccedActicity.class);
                            intent.putExtra(RechargeSuccedActicity.RECEIVER_ID, "" + id);
                            intent.putExtra(RechargeSuccedActicity.IS_TRANSFER, true);
                            intent.putExtra(RechargeSuccedActicity.PHONE_NUM, jsonObject.getString("phoneNumber"));
                            intent.putExtra(RechargeSuccedActicity.AMOUNT, amount);
                            intent.putExtra(RechargeSuccedActicity.TRANSFER_ID, transferid);

                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VERIFY_PASSWORD_REQUEST:
//                    showToastMsg("充值成功！");
                    Bundle bundle = new Bundle();
                    bundle.putString("mount", mountNum);
                    ActivityUtil.next(WithDrawToFriendActivity.this, SendingRedBagNextActivity.class, bundle);
                    break;
                case SET_PASSWORD_REQUEST:
                    DBLog.e("RechargeActivity", "设置密码成功");
                    break;
            }
        } else {
            showToastMsg("密码错误！转账失败！");
        }
    }

    public void back(View view) {
        finish();
    }
}
