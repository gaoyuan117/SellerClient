package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 发送好友邀请
 */
public class AddFriendsFinalActivity extends BaseActivity {

    private String friendId;
    private TextView tv_send;
    private EditText et_reason;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_addfriends_final);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        friendId = getIntent().getStringExtra("friendId");
        tv_send = getId(R.id.tv_send);
        et_reason= getId(R.id.et_reason);
    }

    @Override
    public void initEvent() {
        tv_send.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                ShowDialog.showDialog(getActivity(), "正在添加...", false, null);
                RequestParams params = new RequestParams();
                params.put("id", MyApplication.getInstance().UserInfo.getUserId());
                params.put("friendId", friendId);
                params.put("message", et_reason.getText().toString().equals("") ? "你需要发送验证申请，等对方通过" : et_reason.getText().toString());
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
                        }finally {
                            ShowDialog.dissmiss();
                        }
                    }

                    @Override
                    public void onFinish() {}

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        showToastMsg("请求服务器失败");
                        DBLog.e("tag", statusCode + ":" + responseString);
                        ShowDialog.dissmiss();
                    }
                });
            }
        });
    }


    public void back(View view ){
        finish();
    }
}
