package com.kaichaohulian.baocms;

import android.content.Context;

import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class UserInfoManager {
    private static UserInfoManager manager = new UserInfoManager();

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {
        return manager;
    }

    public void updateUserCache(final Context context) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    LogUtil.e("TRACE", " update user info " + response.toString());
                    DBLog.e("update Cache：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(response.getInt("id"));
                        String usertoken = response.getString("token");
                        SPUtils.put(context, SPContent.USER_TOKEN, usertoken);
                        UserInfo.setToken(usertoken);
                        UserInfo.setCreatedTime(System.currentTimeMillis() + "");
                        UserInfo.setLocked(false);
                        UserInfo.setLastModifiedTime(System.currentTimeMillis() + "");
                        UserInfo.setLastModifier("LastModifier");
                        UserInfo.setUsername(response.getString("username"));
                        UserInfo.setPassword(response.getString("password"));
                        UserInfo.setAccountNumber(response.getString("accountNumber"));
                        UserInfo.setQrCode(response.getString("qrCode"));
                        UserInfo.setDistrictId(response.getString("districtId"));
                        UserInfo.setSex(response.getString("sex"));
                        UserInfo.setThermalSignatrue(response.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(response.getString("phoneNumber"));
                        UserInfo.setUserEmail(response.getString("userEmail"));
                        UserInfo.setBalance(response.getString("balance"));
                        UserInfo.setAvatar(response.getString("avatar"));
                        UserInfo.setBackAvatar(response.getString("backAvatar"));
                        UserInfo.setLoginFailedCount(0);
                        UserInfo.setPayPassword(response.getString("paypassword"));
                        UserInfo.setHobby(response.optString("hobby"));
                        UserInfo.setJob(response.optString("job"));
                        UserInfo.setAge(response.optInt("age"));
//                      UserInfo.setImages(response.getString("images"));
                        MyApplication.getInstance().UserInfo = UserInfo;
                        DataHelper mDataHelper = new DataHelper(context);
                        mDataHelper.SaveUserInfo(UserInfo);
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
