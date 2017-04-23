package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.LableDetailAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.LableDetailEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.HanziToPinyin;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.SidebarLable;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by limengshuai on 2017/2/20.
 */

public class LabelSeeActivity extends BaseActivity {

    private TextView et_label;
    private TextView lable_member;
    private TextView tv_addLabel;
    private ListView listView;

    public static final int NextAddMember = 998;

    private List<LableDetailEntity> lableDetailList;
    private List<ContactFriendsEntity> contactFriendsEntitylist;
    private String lableName;
    private int lableId, lableCount;

    private LableDetailAdapter adapter;
    private SidebarLable sidebar;

    private MyApplication application;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_lable_see);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        lableName = intent.getStringExtra("lableName");
        lableId = intent.getIntExtra("lableId", 1);
        lableCount = intent.getIntExtra("lableCount", 0);
        lableDetailList = new ArrayList<>();
        contactFriendsEntitylist = new ArrayList<>();
        application = (MyApplication) getApplication();

    }

    @Override
    public void initView() {
        et_label = getId(R.id.et_label);
        lable_member = getId(R.id.lable_member);
        tv_addLabel = getId(R.id.tv_addLabel);
        listView = getId(R.id.listlabledetailitem);

        et_label.setText(lableName);
        lable_member.setText("标签成员（" + lableCount + ")");

        setLeftTitle("标签");
        setCenterTitle(lableName);
        TextView rightTitle = setRightTitle("保存");
        rightTitle.setTextColor(getResources().getColor(R.color.green_btn_color_disable));

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);
        // 设置adapter
//        adapter = new LableDetailAdapter(getActivity(), R.layout.item_contact_list, lableDetailList);
//        listView.setAdapter(adapter);

        getLabel();

        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContactFriendsEntity> contactList = application.getContactList();
                JSONArray jsonArray = new JSONArray();
                for (ContactFriendsEntity contact : contactList) {
                    jsonArray.put(contact.getId());
                }
                createLabel(jsonArray);

            }
        });

    }

    @Override
    public void initEvent() {
        tv_addLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddMemberActivity.class, new Bundle(), NextAddMember);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == NextAddMember) {
            contactFriendsEntitylist.addAll(application.getContactList());
            for (int k = 0; k < contactFriendsEntitylist.size(); k++) {
                LableDetailEntity label = new LableDetailEntity();
                label.setName(contactFriendsEntitylist.get(k).getUsername());
                label.setAvatar(contactFriendsEntitylist.get(k).getAvatar());
                label.setUserId(contactFriendsEntitylist.get(k).getId());
                lableDetailList.add(label);
            }
            adapter.notifyDataSetChanged();
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
            mJSONObject.put("lableId", lableId);
            mJSONObject.put("userToInviteIds", jsonArray);
            params.put("JsonString", mJSONObject);
            Log.e("params：", params.toString());
            HttpUtil.post(Url.ADD_LABEL, params, new JsonHttpResponseHandler() {
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

    /**
     * 获取标签详情
     */
    private void getLabel() {
        ShowDialog.showDialog(getActivity(), "请稍后...", false, null);
        try {
            RequestParams params = new RequestParams();
            params.put("lableId", lableId);
            HttpUtil.post(Url.LABEL_DETAIL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.e("获取标签详情：", response.toString());
                        if (response.getInt("code") == 0) {
                            lableDetailList.clear();
                            JSONArray jsonArray = response.getJSONArray("dataObject");
                            for (int k = 0; k < jsonArray.length(); k++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(k);
                                LableDetailEntity label = new LableDetailEntity();
                                label.setName(jsonObject.getString("name"));
                                label.setLableId(jsonObject.getInt("lableId"));
                                label.setAvatar(jsonObject.getString("avatar"));
                                label.setUserId(jsonObject.getInt("userId"));

                                if (Character.isDigit(label.getName().charAt(0))) {
                                    label.setHeader("#");
                                } else {
                                    label.setHeader(HanziToPinyin.getInstance().get(label.getName().substring(0, 1))
                                            .get(0).target.substring(0, 1).toUpperCase());
                                    char header = label.getHeader().toLowerCase().charAt(0);
                                    if (header < 'a' || header > 'z') {
                                        label.setHeader("#");
                                    }
                                }
                                lableDetailList.add(label);
                            }
                            Collections.sort(lableDetailList, new PinyinComparator() {
                            });
//                            adapter.notifyDataSetChanged();

                            adapter = new LableDetailAdapter(getActivity(), R.layout.item_contact_list, lableDetailList);
                            listView.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        showToastMsg("获取所有标签失败");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PinyinComparator implements Comparator<LableDetailEntity> {
        @Override
        public int compare(LableDetailEntity o1, LableDetailEntity o2) {
            String py1 = o1.getHeader();
            String py2 = o2.getHeader();
            // 判断是否为空""
            if (isEmpty(py1) && isEmpty(py2))
                return 0;
            if (isEmpty(py1))
                return -1;
            if (isEmpty(py2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
                str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
            } catch (Exception e) {
                System.out.println("某个str为\" \" 空");
            }
            return str1.compareTo(str2);
        }

        private boolean isEmpty(String str) {
            boolean isEnpty = false;
            if (null == str) {
                isEnpty = true;
            }
            if ("".equals(str)) {
                isEnpty = true;
            }
            return isEnpty;
        }
    }
}
