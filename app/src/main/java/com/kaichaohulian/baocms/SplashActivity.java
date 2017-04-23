package com.kaichaohulian.baocms;

/**
 * 开屏页
 * Created by ljl on 2016/12/11.
 */

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 开屏页
 * Created by ljl on 2016/12/11.
 */
public class SplashActivity extends BaseActivity {

    private static final int sleepTime = 2000;

    private DataHelper mDataHelper;

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
