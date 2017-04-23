package com.kaichaohulian.baocms.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class FeedbackActivity extends BaseActivity {

    public static final String FLAG_COMPLAINT = "FLAG_COMPLAINT";
    private Button feedbackBtn;
    private EditText contentTV;
    private boolean complaint;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        complaint = getIntent().getBooleanExtra(FLAG_COMPLAINT, false);
        if (!complaint) {
            setTitle("意见反馈");
        } else {
            setTitle("投诉");
        }
        feedbackBtn = (Button) findViewById(R.id.btn_feedback);
        contentTV = (EditText) findViewById(R.id.et_feedback_content);
    }

    @Override
    public void initEvent() {
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!complaint) {
                    feedback();
                } else {
                    complaint();
                }
            }
        });
    }

    private void feedback() {
        String content = contentTV.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showMessage("请填写您的建议");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("yijian", content);
        HttpUtil.post(Url.feedback, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        ToastUtil.showMessage("感谢您的反馈");
                        finish();
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
                LogUtil.e("TRACE", "feedback result : " + responseString);
            }
        });
    }

    private void complaint() {
        String content = contentTV.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showMessage("请填写您的建议");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("content", content);
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
    }
}
