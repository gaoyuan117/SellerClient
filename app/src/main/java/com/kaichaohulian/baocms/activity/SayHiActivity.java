package com.kaichaohulian.baocms.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.apache.http.Header;
import org.json.JSONObject;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SayHiActivity extends BaseActivity {

    EditText etHello;
    private String friendId;
    boolean tousu = false;

    @Override
    public void setContent() {
        setContentView(R.layout.say_hi);
    }

    @Override
    public void initData() {
        friendId = getIntent().getStringExtra("friendId");
        tousu = getIntent().getBooleanExtra("tousu", false);
    }

    @Override
    public void initView() {
        etHello = getId(R.id.say_hi_edittext);
        if (tousu) {
            setCenterTitle("投诉");
        } else {
            setCenterTitle("打招呼");
        }
        setRightTitle("发送").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = etHello.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    if (tousu) {
                        showToastMsg("请输入投诉内容");
                    }
                }
                ShowDialog.showDialog(getActivity(), "正在发送...", false, null);
                if (tousu) {
                    RequestParams params = new RequestParams();
                    params.put("id", Long.parseLong(friendId));
                    params.put("content", str);
                    HttpUtil.get(Url.complaint_apply, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                DBLog.e("投诉：", response.toString());
                                if (response.getInt("code") == 0) {
                                    finish();
                                }
                                showToastMsg(response.getString("errorDescription"));
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
                } else {
                    RequestParams params = new RequestParams();
                    params.put("id", MyApplication.getInstance().UserInfo.getUserId());
                    params.put("friendId", friendId);
                    params.put("message", etHello.getText().toString().equals("") ? "你需要发送验证申请，等对方通过" : etHello.getText().toString());
                    HttpUtil.get(Url.friends_apply, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                DBLog.e("添加好友：", response.toString());
                                if (response.getInt("code") == 0) {
                                    finish();
                                }
                                showToastMsg(response.getString("errorDescription"));
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
            }
        });
    }

    @Override
    public void initEvent() {

    }
}
