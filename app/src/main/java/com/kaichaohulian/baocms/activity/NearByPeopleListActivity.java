package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.NearBusinessListAdapter;
import com.kaichaohulian.baocms.adapter.NearByPeopleListAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcActivity;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.entity.DialogListViewItem;
import com.kaichaohulian.baocms.entity.NearBusinessEntity;
import com.kaichaohulian.baocms.entity.NearByPeopleListEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.listener.OnBottomDialogItemOnClickListener;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.view.BottomDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

public class NearByPeopleListActivity extends BaseEcActivity implements OnBottomDialogItemOnClickListener {
    private ListView listView;
    private NearBusinessListAdapter adapter;
    private List<NearBusinessEntity> data;
    private ECProgressDialog mPostingdialog;

    private LocationClient locationClient = null;
    private double mStartLatitude, mStartLontitude;
    private static final int UPDATE_TIME = 5000;
    private boolean alreadyLocation = false;


    private void dismissPostingDialog() {
        if (mPostingdialog == null || !mPostingdialog.isShowing()) {
            return;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }

    @Override
    public void initData() {
        data = new ArrayList<NearBusinessEntity>();
        adapter = new NearBusinessListAdapter(getApplicationContext(), data);

        locationClient = new LocationClient(this);
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");        //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);    //设置定位优先级
        option.setScanSpan(UPDATE_TIME);    //设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);

        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                if (alreadyLocation) {
                    locationClient.stop();
                }
                mStartLatitude = location.getLatitude();
                mStartLontitude = location.getLongitude();
                alreadyLocation = true;
                getData();
            }

        });
        locationClient.start();
    }

    @Override
    public void initView() {
        listView = getId(R.id.nearby_people_list);
        setCenterTitle("附近的商家");

        final ImageView iv_add = setIm1_view(R.mipmap.threepoints);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    protected void showBottomDialog() {
        BottomDialog dialog = new BottomDialog(this, R.style.transparentFrameWindowStyle);
        dialog.setOnBottomDialogItemOnClickListener(this);
        dialog.addItem(new DialogListViewItem("查看全部"));
        dialog.addItem(new DialogListViewItem("附近打招呼的人"));
        dialog.addItem(new DialogListViewItem("清除位置信息并退出"));
        dialog.show();
    }

    @Override
    public void onItemClick(DialogListViewItem item, int position) {
        switch (position) {
            case 0:
                break;
            case 1:
                ActivityUtil.next(getActivity(), SayHiToMeActivity.class);
                break;
            case 2:
                handleLogout();
                break;
            default:
                break;
        }
    }

    public void handleLogout() {
        SDKCoreHelper.logout(false);
        mPostingdialog = new ECProgressDialog(this, R.string.logout_hint);
        mPostingdialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null && locationClient.isStarted()) {
            locationClient.stop();
            locationClient = null;
        }
    }

    @Override
    public void initEvent() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo UserInfo = new UserInfo();
                UserInfo.setUserId(data.get(position).getUserId());
                UserInfo.setUsername(data.get(position).getUsername());
                UserInfo.setThermalSignatrue(data.get(position).getThermalSignatrue());
                UserInfo.setAvatar(data.get(position).getAvatar());
                UserInfo.setSex("1");
                Bundle Bundle = new Bundle();
                Bundle.putSerializable("data", UserInfo);
                ActivityUtil.next(getActivity(), SayHelloToNearPeopleActivity.class, Bundle);
            }
        });
//        getData();
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("longitud", mStartLontitude);//longitud
        params.put("latitude", mStartLatitude);  //latitude
        params.put("type", 0);
        params.put("page", 1);
        HttpUtil.post(Url.nearbyPeople, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("附近的商家", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray array = response.getJSONArray("dataObject");
                        if (array.length() != 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject) array.get(i);
                                NearBusinessEntity entity = new NearBusinessEntity();
                                entity.setUserId(object.getInt("userId"));
                                entity.setUsername(object.getString("username"));
                                entity.setAvatar(object.getString("avatar"));
                                entity.setThermalSignatrue(object.getString("thermalSignatrue"));
                                entity.setDistance(object.getString("distance"));
                                data.add(entity);
                            }
                            adapter.notifyDataSetChanged();
                        } else {

                        }
                        showToastMsg("获取附近的商家成功");
                    } else {

                    }
                } catch (Exception e) {
                    showToastMsg("附近的商家，解析json失败");
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

    @Override
    protected int getLayoutId() {
        return R.layout.nearby_people_list;
    }

    @Override
    public int getTitleLayout() {
        return -1;
    }

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        super.handleReceiver(context, intent);
        if (SDKCoreHelper.ACTION_LOGOUT.equals(intent.getAction())) {
            dismissPostingDialog();
            //TODO 2017-03-11修复登陆db 空指针异常
            try {
                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_REGIST_AUTO, "", true);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
            AppManager.getAppManager().finishAllActivity();
            ActivityUtil.next(getActivity(), LoginActivity.class);
            SPUtils.put(getActivity(), "Login_UserId", "0");
            SPUtils.put(getActivity(), "isLogin", true);
        }
    }
}
