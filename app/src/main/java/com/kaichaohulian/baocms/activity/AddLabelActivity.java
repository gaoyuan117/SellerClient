package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AddLabelAdatper;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加标签界面
 * Created by Administrator on 2016/12/14 0014.
 */

public class AddLabelActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvSave, mTvAddMember;
    private EditText mEtLabel;
    public static final int NextAddMember = 998;
    private List<ContactFriendsEntity> contactList = new ArrayList<>();
    private MyApplication application;
    private ListView listView;
    private AddLabelAdatper addLabelAdatper;


    @Override
    public void setContent() {
        setContentView(R.layout.addlabel_activity);
    }

    @Override
    public void initData() {
        application = (MyApplication) getApplication();
    }


    @Override
    public void initView() {
        setCenterTitle("标签");
        listView=getId(R.id.addlable_activity_listView);
        mTvSave = getId(R.id.tv_right_text);
        mTvSave.setText("保存");
        mEtLabel = getId(R.id.et_label);
        mTvAddMember = getId(R.id.tv_addLabel);
        adatper();
    }

    private void adatper(){
        addLabelAdatper=new AddLabelAdatper(this, R.layout.item_contactlist_listview_checkbox, contactList);
        listView.setAdapter(addLabelAdatper);

    }


    @Override
    public void initEvent() {
        mTvSave.setOnClickListener(this);
        mTvAddMember.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_text:
                if (!StringUtils.isEmpty(mEtLabel.getText().toString().trim())) {

                    List<ContactFriendsEntity> contactList = application.getContactList();
                    JSONArray jsonArray = new JSONArray();
                    for(ContactFriendsEntity contact:contactList){
                        jsonArray.put(contact.getId());
                    }
                    createLabel(jsonArray);
                } else {
                    DBLog.showToast("未设置标签名字", getActivity());
                }
                break;
            case R.id.tv_addLabel:
//                ActivityUtil.next(getActivity(), AddMemberActivity.class);
                ActivityUtil.next(getActivity(), AddMemberActivity.class, new Bundle(), NextAddMember);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == NextAddMember) {
            contactList.addAll(application.getContactList());
            addLabelAdatper.notifyDataSetChanged();
        }
    }

    /**
     * 创建标签
     */
    private void createLabel(JSONArray jsonArray) {
        ShowDialog.showDialog(getActivity(), "请稍后...", true, null);
        try {
            RequestParams params = new RequestParams();
            JSONObject mJSONObject = new JSONObject();

            mJSONObject.put("id", MyApplication.getInstance().UserInfo.getUserId());
            mJSONObject.put("name", mEtLabel.getText().toString().trim());
            mJSONObject.put("userToInviteIds", jsonArray);
            params.put("JsonString", mJSONObject);
            Log.e("params：", params.toString());
            HttpUtil.post(Url.CREATE_LABEL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.e("保存：", response.toString());
                        if (response.getInt("code") == 0) {
                            Intent Intent = new Intent();
                            Intent.setAction("save_label");
                            sendBroadcast(Intent);
                            getActivity().finish();
                        }
                    } catch (Exception e) {
                        showToastMsg("保存失败");
                        e.printStackTrace();
                    } finally {
                        ShowDialog.dissmiss();
                    }
                }

                @Override
                public void onFinish() {
                    ShowDialog.dissmiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    showToastMsg("请求服务器失败");
                    DBLog.e("tag", statusCode + ":" + responseString);
                    ShowDialog.dissmiss();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
