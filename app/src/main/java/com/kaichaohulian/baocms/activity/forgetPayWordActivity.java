package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class forgetPayWordActivity extends BaseActivity {

    @BindView(R.id.edt_phoneNum_forgetpayword)
    EditText PhoneNum;
    @BindView(R.id.edt_num_forgetpayword)
    EditText SignCode;
    @BindView(R.id.tv_getcode_forgetpayword)
    TextView GetCode;
    @BindView(R.id.edt_new_forgetpayword)
    EditText NewPayWord;
    @BindView(R.id.edt_againnew_forgetpayword)
    EditText AgainPayWord;
    @BindView(R.id.changpayword_cash_btn)
    Button BtnNext;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (PhoneNum.getEditableText().length() > 0 && SignCode.getEditableText().length() > 0 && NewPayWord.getEditableText().length() == 6 && AgainPayWord.getEditableText().length() == 6) {
                BtnNext.setBackgroundResource(R.mipmap.deeporange_bar_part);
                BtnNext.setClickable(true);
            } else {
                BtnNext.setBackgroundResource(R.mipmap.deeporange_bar_normal);
                BtnNext.setClickable(false);
            }
        }
    };
    private Timer mTimer = null;
    private int mTime = 60;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_forget_pay_word);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        BtnNext.setClickable(false);
    }

    @Override
    public void initView() {
        setCenterTitle("找回支付密码");
        PhoneNum.addTextChangedListener(mTextWatcher);
        SignCode.addTextChangedListener(mTextWatcher);
        GetCode.addTextChangedListener(mTextWatcher);
        AgainPayWord.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.tv_getcode_forgetpayword, R.id.changpayword_cash_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_getcode_forgetpayword:
                if (mTimer == null) {
                    mTimer = new Timer();
                    mTime = 60;
                    GetCode.setText(mTime + "s后重新获取");
                    GetCode.setEnabled(false);
                } else {
                    return;
                }
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mTime--;
                                GetCode.setText(mTime + "s后重新获取");
                                if (mTime == 0) {
                                    mTimer.cancel();
                                    mTimer = null;
                                    GetCode.setText("重新验证");
                                    GetCode.setEnabled(true);
                                }
                            }
                        });
                    }
                }, 0, 1000);
                String phone = PhoneNum.getText().toString().trim();
                ShowDialog.showDialog(getActivity(), "发送中...", false, null);
                RequestParams params = new RequestParams();
                params.put("phoneNumber", phone);
                HttpUtil.post(Url.sendMessage, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("发送验证码：", response.toString());
                            showToastMsg(response.getString("errorDescription"));
                        } catch (Exception e) {
                            showToastMsg("发送失败");
                            mTimer.cancel();
                            mTimer = null;
                            GetCode.setText("重新验证");
                            GetCode.setEnabled(true);
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
                break;
            case R.id.changpayword_cash_btn:
                RetrofitClient.getInstance().createApi().ForGetPayWord(
                        MyApplication.getInstance().UserInfo.getPhoneNumber(),/*手机号*/
                        NewPayWord.getText().toString().trim()/*新密码*/
                        , SignCode.getText().toString().trim())/*验证码*/
                        .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                        .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                            @Override
                            protected void onHandleSuccess(CommonEntity commonEntity) {
                                Toast.makeText(forgetPayWordActivity.this, "找回支付密码成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                break;
        }
    }
}
