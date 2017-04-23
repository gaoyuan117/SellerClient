package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

public class DriftBottleRevActivity extends BaseActivity {

    private ImageView avatarIV;
    private TextView nameTV;
    private TextView signTV;
    private EditText contentET;
    private Button confirmBtn;
    private long creator;

    @Override
    public void setContent() {
        setContentView(R.layout.driftbottle_rev);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        avatarIV = (ImageView) findViewById(R.id.drift_avatar);
        nameTV = (TextView) findViewById(R.id.drift_name);
        signTV = (TextView) findViewById(R.id.drift_sign);
        contentET = (EditText) findViewById(R.id.chuck_edit);
        confirmBtn = (Button) findViewById(R.id.drift_confirm);

        creator = getIntent().getLongExtra("creator", -1);
        String content = getIntent().getStringExtra("content");
        searchUser(creator);
        contentET.setText(content);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser2(creator);
            }
        });
    }

    @Override
    public void initEvent() {
    }

    private void searchUser(final long id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.get(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");

                        ImageLoader.getInstance().displayImage(jsonObject.getString("avatar"), avatarIV);
                        nameTV.setText(jsonObject.getString("username"));
                        signTV.setText(jsonObject.getString("thermalSignatrue"));
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

    public void returnBottle(String content, long bottleid) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("driftbottleId", content);
        params.put("content", content);
        HttpUtil.post(Url.returnBottle, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        String errorDescription = response.getString("errorDescription");
                        ToastUtil.showMessage(errorDescription);
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    private void searchUser2(final long id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("扫描人物：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(jsonObject.getInt("id"));
                        UserInfo.setUsername(jsonObject.getString("username"));
                        UserInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        UserInfo.setAvatar(jsonObject.getString("avatar"));
                        Bundle Bundle = new Bundle();
                        Bundle.putString("userId", jsonObject.getString("phoneNumber"));
                        Bundle.putString("userNick", jsonObject.getString("username"));
                        Bundle.putString("userAvatar", jsonObject.getString("avatar"));
                        CCPAppManager.startChattingAction(getActivity(), jsonObject.getString("phoneNumber"), jsonObject.getString("username"), true);
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
}
