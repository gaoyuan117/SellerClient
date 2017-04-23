package com.kaichaohulian.baocms.activity;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.DirectoriesFriendAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.ApplyAndNoticeEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.HanziToPinyin;
import com.kaichaohulian.baocms.view.DirectoriesFriendSidebar;
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
 * 通讯录朋友
 * Created by ljl on 2016/12/14 0014.
 */

public class DirectoriesFriendActivity extends BaseActivity {

    private ListView listView;
    private DirectoriesFriendSidebar sidebar;

    private DirectoriesFriendAdapter adapter;

    private List<ApplyAndNoticeEntity> List;

    private JSONArray ContactsArray;

    @Override
    public void setContent() {
        setContentView(R.layout.directoriesfriend_activity);
    }

    @Override
    public void initData() {
        ContactsArray = new JSONArray();
//        try {
//            testReadAllContacts();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        List = new ArrayList<>();
        // 设置adapter
        adapter = new DirectoriesFriendAdapter(getActivity(), R.layout.directoriesfriend_listitem, List);
    }

    @Override
    public void initView() {
        setCenterTitle("手机通讯录");
        listView = getId(R.id.listView);
        sidebar = getId(R.id.sidebar);
        listView.setAdapter(adapter);
        sidebar.setListView(listView);
    }

    @Override
    public void initEvent() {
        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取联系人的信息
     */
    private int index = 0;

//    public void testReadAllContacts() throws JSONException {
//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        int contactIdIndex = 0;
//        int nameIndex = 0;
//
//        if (cursor.getCount() > 0) {
//            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
//            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//        }
//
//        while (cursor.moveToNext() && index <= 50) {
//            index++;
//            JSONObject jsonUser = new JSONObject();
//            String contactId = cursor.getString(contactIdIndex);
//            String name = cursor.getString(nameIndex);
//            DBLog.i(TAG, contactId);
//            DBLog.i(TAG, name);
//            jsonUser.put("username", name);
//                /*
//                 * 查找该联系人的phone信息
//                 */
//            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
//            int phoneIndex = 0;
//            if (phones.getCount() > 0) {
//                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//            }
//            while (phones.moveToNext()) {
//                String phoneNumber = phones.getString(phoneIndex);
//                DBLog.i(TAG, phoneNumber.trim());
//                jsonUser.put("phoneNumber", phoneNumber.replace(" ", "").trim());
//            }
//
//                /*
//                 * 查找该联系人的email信息
//                 */
//            Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId, null, null);
//            int emailIndex = 0;
//            if (emails.getCount() > 0) {
//                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
//            }
//            while (emails.moveToNext()) {
//                String email = emails.getString(emailIndex);
//                DBLog.i(TAG, email);
//            }
//            ContactsArray.put(jsonUser);
//        }
//        DBLog.i(TAG, ContactsArray);
//    }

    private int page = 1;

    public void getData() throws JSONException {
        JSONObject httpJson = new JSONObject();
        RequestParams params = new RequestParams();
        httpJson.put("id", MyApplication.getInstance().UserInfo.getUserId());
        httpJson.put("type", "2");
        //2017-03-25 客户修改不在传入mailList
//        httpJson.put("mailList", ContactsArray);
        httpJson.put("page", page + "");
        params.put("JsonString", httpJson);
        HttpUtil.post(Url.requests_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("手机通讯录：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        if (JSONArray != null && JSONArray.length() == 0) {
                            ToastUtil.showMessage("您通讯录中没有已注册买家网好友");
                            return;
                        }
                        for (int i = 0; i < JSONArray.length(); i++) {
                            ApplyAndNoticeEntity ApplyAndNoticeEntity = new ApplyAndNoticeEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
//                                ApplyAndNoticeEntity.setId(jsonObject.getInt("id"));
//                                ApplyAndNoticeEntity.setAuditedBy(jsonObject.getInt("auditedBy"));
//                                ApplyAndNoticeEntity.setCreatedTime(jsonObject.getString("createdTime"));
//                                ApplyAndNoticeEntity.setTargetId(jsonObject.getInt("targetId"));
//                                ApplyAndNoticeEntity.setMessage(jsonObject.getString("message"));
                            ApplyAndNoticeEntity.setAvatar(jsonObject.getString("avatar"));
                            ApplyAndNoticeEntity.setNickname(jsonObject.getString("nickname"));
//                            ApplyAndNoticeEntity.setRequestType(jsonObject.getString("requestType"));
                            ApplyAndNoticeEntity.setStatus(jsonObject.getString("status"));
//                            ApplyAndNoticeEntity.setType(jsonObject.getString("type"));
                            ApplyAndNoticeEntity.setUserId(jsonObject.getInt("userId"));
                            if (Character.isDigit(ApplyAndNoticeEntity.getNickname().charAt(0))) {
                                ApplyAndNoticeEntity.setHeader("#");
                            } else {
                                ApplyAndNoticeEntity.setHeader(HanziToPinyin.getInstance().get(ApplyAndNoticeEntity.getNickname().substring(0, 1)).get(0).target.substring(0, 1).toUpperCase());
                                char header = ApplyAndNoticeEntity.getHeader().toLowerCase().charAt(0);
                                if (header < 'a' || header > 'z') {
                                    ApplyAndNoticeEntity.setHeader("#");
                                }
                            }
                            List.add(ApplyAndNoticeEntity);
                        }
                        // 对list进行排序
                        Collections.sort(List, new PinyinComparator() {
                        });
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    public class PinyinComparator implements Comparator<ApplyAndNoticeEntity> {
        @Override
        public int compare(ApplyAndNoticeEntity o1, ApplyAndNoticeEntity o2) {
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
            return "".equals(str.trim());
        }
    }
}
