package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class AddFriendsTwoActivity extends BaseActivity {

    private String addFriendCode;

    private RelativeLayout re_search;

    private TextView tv_search;

    private EditText et_search;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_addfriends_two);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            addFriendCode = getIntent().getStringExtra("addFriendCode");
        }
    }

    @Override
    public void initView() {
        re_search = getId(R.id.re_search);
        tv_search = getId(R.id.tv_search);
        et_search = getId(R.id.et_search);
    }

    @Override
    public void initEvent() {
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    re_search.setVisibility(View.VISIBLE);
                    tv_search.setText(et_search.getText().toString().trim());
                } else {

                    re_search.setVisibility(View.GONE);
                    tv_search.setText("");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });
        re_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String terms = et_search.getText().toString().trim();
                if (terms == null || terms.equals("")) {
                    return;
                }
                searchUser(terms);

            }

        });
        if (!StringUtils.isEmpty(addFriendCode)) {
            et_search.setText(addFriendCode);
            searchUser(addFriendCode);
        }
    }

    private void searchUser(final String terms) {
        ShowDialog.showDialog(getActivity(), "正在查找联系人...", false, null);
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("terms", terms);
        HttpUtil.post(Url.addfriend_search, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("搜索用户：", response.toString());
                    showToastMsg(response.getString("errorDescription"));
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(jsonObject.getInt("id"));
//                        UserInfo.setCreatedTime(jsonObject.getString("createdTime"));
//                        UserInfo.setLocked(jsonObject.getBoolean("isLocked"));
//                        UserInfo.setLastModifiedTime(jsonObject.getString("lastModifiedTime"));
//                        UserInfo.setLastModifier(jsonObject.getString("lastModifier"));
                        UserInfo.setUsername(jsonObject.getString("username"));
                        UserInfo.setPassword(jsonObject.getString("password"));
                        UserInfo.setAccountNumber(jsonObject.getString("accountNumber"));
                        UserInfo.setQrCode(jsonObject.getString("qrCode"));
                        UserInfo.setDistrictId(jsonObject.getString("districtId"));
                        UserInfo.setSex(jsonObject.getString("sex"));
                        UserInfo.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        UserInfo.setUserEmail(jsonObject.getString("userEmail"));
                        UserInfo.setBalance(jsonObject.getString("balance"));
                        UserInfo.setAvatar(jsonObject.getString("avatar"));
                        UserInfo.setBackAvatar(jsonObject.getString("backAvatar"));
//                        UserInfo.setLoginFailedCount(jsonObject.getInt("loginFailedCount"));
                        UserInfo.setIsfriend(jsonObject.getInt("isfriend"));
//                        UserInfo.setImages(jsonObject.getString("images"));
                        Bundle Bundle = new Bundle();
                        Bundle.putSerializable("data", UserInfo);
//                        ActivityUtil.next(getActivity(), FriendDetailActivity.class, Bundle);

                        Intent intent = new Intent(getActivity(),FriendInfoActivity.class);
                        intent.putExtra("phone",UserInfo.getPhoneNumber()+"");
                        intent.putExtra("friendId",UserInfo.getUserId()+"");
                        intent.putExtra("type","2");
                        startActivity(intent);

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
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    public void back(View view) {
        finish();
    }

}