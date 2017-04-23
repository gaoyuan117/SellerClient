package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.StatusModel;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.SwitchButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 *
 */
public class AddMeWayActivity extends BaseActivity {

    SwitchButton switchButtonPhone, switchButtonWechat, switchButtonQQ, switchButtonGroupChat;
    SwitchButton switchButtonTwoCode, switchButtonNameCard;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.add_me_way);
    }

    @Override
    public void initData() {
        showprivacy();
    }

    @Override
    public void initView() {
        setCenterTitle("添加我的方式");
        switchButtonPhone = getId(R.id.phone_switch);
        switchButtonWechat = getId(R.id.wechat_swtich);
        switchButtonQQ = getId(R.id.qq_swtich);
        switchButtonGroupChat = getId(R.id.qunliao_swtich);
        switchButtonTwoCode = getId(R.id.twocode_swtich);
        switchButtonNameCard = getId(R.id.namecard_swtich);

    }

    @Override
    public void initEvent() {
        switchButtonPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                setAddValidate("cardAdd", isChecked ? 1 : 0);
            }
        });
        switchButtonTwoCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                setAddValidate("erweiAdd", isChecked ? 1 : 0);
            }
        });
        switchButtonNameCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        switchButtonGroupChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                setAddValidate("groupChatAdd", isChecked ? 1 : 0);
            }
        });
        switchButtonQQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        switchButtonWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    public void showprivacy() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        HttpUtil.post(Url.showprivacy, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject json = response.getJSONObject("dataObject");
                        StatusModel StatusModel = new StatusModel();
                        StatusModel.setUId(json.getInt("uId"));
                        StatusModel.setAddfriendflag(json.getString("addfriendflag"));
                        StatusModel.setPushphonebook(json.getString("pushphonebook"));
                        StatusModel.setAllowtenphoto(json.getString("allowtenphoto"));
                        StatusModel.setPhonefind(json.getString("phonefind"));
                        StatusModel.setWeixinfind(json.getString("weixinfind"));
                        StatusModel.setGroupchatadd(json.getString("groupchatadd"));
                        StatusModel.setQqfind(json.getString("qqfind"));
                        StatusModel.setErweiadd(json.getString("erweiadd"));
                        StatusModel.setCardadd(json.getString("cardadd"));
                        if (StatusModel.getCardadd() != null && StatusModel
                                .getCardadd().equals("1")) {
                            switchButtonPhone.setChecked(true);
                        } else {
                            switchButtonPhone.setChecked(false);
                        }
                        if (StatusModel.getGroupchatadd() != null && StatusModel
                                .getGroupchatadd().equals("1")) {
                            switchButtonGroupChat.setChecked(true);
                        } else {
                            switchButtonGroupChat.setChecked(false);
                        }
                        if (StatusModel.getErweiadd() != null && StatusModel
                                .getErweiadd().equals("1")) {
                            switchButtonTwoCode.setChecked(true);
                        } else {
                            switchButtonTwoCode.setChecked(false);
                        }
                        isFirst = false;
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
                ShowDialog.dissmiss();
            }
        });
    }

    private void setAddValidate(String field, int flag) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        params.put(field, flag);
        HttpUtil.post(Url.privacy, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
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
                ShowDialog.dissmiss();
            }
        });
    }
}
