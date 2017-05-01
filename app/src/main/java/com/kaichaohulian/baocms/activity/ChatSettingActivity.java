package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.ContactStatusEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.SwitchButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatSettingActivity extends BaseActivity implements View.OnClickListener {
    private SwitchButton cant_see_me, dont_see_it, add_to_the_list;
    private long cUId;
    private List<ContactStatusEntity> contactList;
    private boolean isFirst = true;
    private RelativeLayout complaintRL;
    private RelativeLayout setUplable;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_chat_setting);
    }

    @Override
    public void initData() {
        cUId = getIntent().getIntExtra("cUId", 0);
        contactList = new ArrayList<ContactStatusEntity>();
        getListStatus();
    }

    @Override
    public void initView() {
        TextView title = (TextView) findViewById(R.id.center_title_tv);
        complaintRL = (RelativeLayout) findViewById(R.id.rl_complaint_container);
        complaintRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                intent.putExtra(FeedbackActivity.FLAG_COMPLAINT, true);
                startActivity(intent);
            }
        });

        title.setText(R.string.chat_setting_title);
        setUplable = getId(R.id.setUplable);
        cant_see_me = getId(R.id.cant_see_me);
        cant_see_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                if (isChecked) {
                    setStatus(String.valueOf(1), cUId);
                } else {
                    PrivacyRemove(1);
                }
            }
        });
        dont_see_it = getId(R.id.dont_see_it);
        dont_see_it.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                if (isChecked) {
                    setStatus(String.valueOf(2), cUId);
                } else {
                    PrivacyRemove(2);
                }
            }
        });
        add_to_the_list = getId(R.id.add_to_the_list);
        add_to_the_list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isFirst) {
                    return;
                }
                if (isChecked) {
                    setStatus(String.valueOf(3), cUId);
                } else {
                    PrivacyRemove(3);
                }
            }
        });
        setUplable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatSettingActivity.this, AddLabelAndNameActivity.class);
                intent.putExtra("friendId", cUId);
                startActivity(intent);
            }
        });
//        TextView back = (TextView) findViewById(R.id.iv_back);
        TextView removeBtn = (TextView) findViewById(R.id.btn_remove);
        removeBtn.setOnClickListener(this);
//        removeBtn.setOnClickListener(this);
//        back.setOnClickListener(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.btn_remove:
//                startActivity(new Intent(this, MainActivity.class));
                deleteFriend();
                break;

        }
    }
    private void deleteFriend(){
        map.put("id",MyApplication.getInstance().UserInfo.getId()+"");
        map.put("friendId",cUId+"");
        RetrofitClient.getInstance().createApi().deleteFriend(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {

                    }
                });

    }


    public void setStatus(String flag, long cUId) {
        RequestParams params = new RequestParams();
        params.put("uId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("flag", flag);
        params.put("cUId", cUId);
        HttpUtil.post(Url.privacyAdd, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("朋友圈隐私相关操作：", response.toString());
                    if (response.getInt("code") == 0) {
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("设置失败,服务器异常");
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

    public void PrivacyRemove(int position) {
        RequestParams params = new RequestParams();
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getFlag().equals(position + "")) {
                params.put("id", contactList.get(i).getId());
            }
        }
        HttpUtil.post(Url.privacyRemove, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("移除朋友圈权限：", response.toString());
                    if (response.getInt("code") == 0) {
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("设置失败,服务器异常");
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

    public void getListStatus() {
        RequestParams params = new RequestParams();
        params.put("uId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("cUId", cUId);
        HttpUtil.post(Url.privacyList, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取权限状态：", response.toString());
                    if (response.getInt("code") == 0) {
                        contactList.clear();
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            ContactStatusEntity contactStatusEntity = new ContactStatusEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            contactStatusEntity.setId(jsonObject.getInt("id"));
                            contactStatusEntity.setUId(jsonObject.getInt("uId"));
                            contactStatusEntity.setCUId(jsonObject.getInt("cUId"));
                            contactStatusEntity.setFlag(jsonObject.getString("flag"));
                            contactStatusEntity.setCuImg(jsonObject.getString("cuImg"));
                            contactStatusEntity.setCuname(jsonObject.getString("cuname"));
                            contactList.add(contactStatusEntity);

                        }
                        for (int i = 0; i < contactList.size(); i++) {
                            if (contactList.get(i).getFlag().equals("1")) {
                                cant_see_me.setChecked(true);
                            } else if (contactList.get(i).getFlag().equals("2")) {
                                dont_see_it.setChecked(true);
                            } else if (contactList.get(i).getFlag().equals("3")) {
                                add_to_the_list.setChecked(true);
                            }
                        }
                        isFirst = false;
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("获取状态失败，服务器异常");
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
