package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.StatusModel;
import com.kaichaohulian.baocms.fragment.BlackListFragment;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.SwitchButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class PrivateSettingActivity extends BaseActivity {

    RelativeLayout r_addMeWay;
    private boolean isFirst = true;
    private SwitchButton addWithValidate;
    private SwitchButton tuijianFriendSwitch;

    @Override
    public void setContent() {
        setContentView(R.layout.private_settings);
    }

    @Override
    public void initData() {
        showprivacy();
    }

    @Override
    public void initView() {
        r_addMeWay = getId(R.id.add_me_method);
        addWithValidate = getId(R.id.phone_switch);
        tuijianFriendSwitch = getId(R.id.tuijian_friends_switch);
        RelativeLayout blackListRL = getId(R.id.black_list);
        RelativeLayout rejectRL = getId(R.id.dont_let_him_watch_my);
        RelativeLayout cicleblackRL = getId(R.id.no_show_his_picture);

        cicleblackRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlackListActivity.class);
                intent.putExtra(BlackListFragment.FLAG, BlackListFragment.FLAG_CIRCLE_BLACKLIST);
                startActivity(intent);
            }
        });

        rejectRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlackListActivity.class);
                intent.putExtra(BlackListFragment.FLAG, BlackListFragment.FLAG_CIRCLE_REJECT);
                startActivity(intent);
            }
        });

        blackListRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BlackListActivity.class);
                intent.putExtra(BlackListFragment.FLAG, BlackListFragment.FLAG_BLACKLIST);
                startActivity(intent);
            }
        });

        addWithValidate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                setAddWithValidate("addFriendFlag", isChecked ? 1 : 0);
            }
        });

        tuijianFriendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                setAddWithValidate("pushPhoneBook", isChecked ? 1 : 0);
            }
        });

        setCenterTitle("隐私");
    }

    @Override
    public void initEvent() {
        r_addMeWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddMeWayActivity.class);
            }
        });

    }

    private void setAddWithValidate(String paramname, int flag) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        params.put(paramname, flag);
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
                        if (StatusModel.getAddfriendflag() != null && StatusModel
                                .getAddfriendflag().equals("1")) {
                            addWithValidate.setChecked(true);
                        } else {
                            addWithValidate.setChecked(false);
                        }
                        if (StatusModel.getPushphonebook() != null && StatusModel
                                .getPushphonebook().equals("1")) {
                            tuijianFriendSwitch.setChecked(true);
                        } else {
                            tuijianFriendSwitch.setChecked(false);
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
}