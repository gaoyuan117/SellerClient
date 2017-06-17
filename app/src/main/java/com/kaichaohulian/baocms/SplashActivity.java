package com.kaichaohulian.baocms;

/**
 * 开屏页
 * Created by ljl on 2016/12/11.
 */

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.fragment.ContactListFragment;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.utils.ChineseToEnglish;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 开屏页
 * Created by ljl on 2016/12/11.
 */
public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 2000;

    private DataHelper mDataHelper;
    private List<ContactFriendsEntity> contactList;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        contactList = new ArrayList<>();
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(new Runnable() {
            public void run() {
                boolean isFirstIn = (boolean) SPUtils.get(getActivity(), "isFirstIn", true);
                if (isFirstIn) {
                    //进入引导页
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    boolean isLogin = (boolean) SPUtils.get(getActivity(), "isLogin", false);
                    if (isLogin) {
                        MyApplication.getInstance().UserInfo = mDataHelper.GetUser(String.valueOf(SPUtils.get(getActivity(), "Login_UserId", "0")));
                        String userToken = (String) SPUtils.get(getActivity(), SPContent.USER_TOKEN, "");
                        if (MyApplication.getInstance().UserInfo != null) {
                            MyApplication.getInstance().UserInfo.setToken(userToken);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                getContactList();
                            }
                        });

                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }).start();

    }

    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }

    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.getFriends, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取通讯录好友：", response.toString());
                    if (response.getInt("code") == 0) {
                        contactList.clear();
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            ContactFriendsEntity contract = new ContactFriendsEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            contract.setId(jsonObject.getInt("id"));
                            contract.setAvatar(jsonObject.getString("avatar"));
                            contract.setCreatedTime(jsonObject.getString("createdTime"));
                            contract.setImNumber(jsonObject.getString("imNumber"));
                            contract.setPhoneNumber(jsonObject.getString("phoneNumber"));
                            contract.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                            contract.setUsername(jsonObject.getString("username"));
                            contract.setRemark(jsonObject.optString("remark"));

                            if (Character.isDigit(contract.getUsername().charAt(0))) {
                                contract.setHeader("#");
                            } else {
                                contract.setHeader(ChineseToEnglish.getInstance().getSelling(contract.getUsername()).trim().substring(0, 1));
                                char header = contract.getHeader().toLowerCase().charAt(0);
                                if (header < 'a' || header > 'z') {
                                    contract.setHeader("#");
                                }

                            }
                            boolean flag = false;
                            for (ContactFriendsEntity tempContact : contactList) {
                                if (tempContact.getId() == contract.getId()) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                contactList.add(contract);
                                SharedPrefsUtil.putValue(getActivity(), contract.getPhoneNumber(), contract.getAvatar() + "-x-" + contract.getUsername());
                                SharedPrefsUtil.putValue(getActivity(), contract.getId() + "", contract.getAvatar() + "-x-" + contract.getUsername());
                            }
                        }


                        // 对list进行排序
                        Collections.sort(contactList, new PinyinComparator() {
                        });

                        for (int i = 0; i < contactList.size(); i++) {
                            String remark = contactList.get(i).getRemark();
                            String phoneNumber = contactList.get(i).getPhoneNumber();
                            String username = contactList.get(i).getUsername();
                            if (!TextUtils.isEmpty(remark) && !remark.equals("null")) {
                                MyApplication.getInstance().contactMap.put(phoneNumber, remark);
                            } else {
                                MyApplication.getInstance().contactMap.put(phoneNumber, username);
                            }
                        }
                        Log.e("gy", "通讯录数据个数:" + MyApplication.getInstance().contactMap.size());


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

    public class PinyinComparator implements Comparator<ContactFriendsEntity> {
        @Override
        public int compare(ContactFriendsEntity o1, ContactFriendsEntity o2) {
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


    public void getData() {
        RequestParams params = new RequestParams();
        if (MyApplication.getInstance().UserInfo == null)
            return;
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.groups_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取我加入和我创建的群", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray jsonArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            GroupEntity GroupEntity = new GroupEntity();
                            JSONArray avatarArray = jsonObject.getJSONArray("images");

                            SharedPrefsUtil.putValue(getActivity(), jsonObject.getString("chatGroupId"), avatarArray.toString()
                                    + "-x-" + jsonObject.getString("name") + "-x-" + jsonObject.getInt("id"));

                        }
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
            }
        });
    }
}
