package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.PayDialog;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送好友邀请
 */
public class AddFriendsFinalActivity extends BaseActivity {

    private TextView tv_send;
    private EditText et_reason;

    private String friendId, money, type;
    private String message;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_addfriends_final);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        friendId = intent.getStringExtra("friend_id");
        money = intent.getStringExtra("add_money");
        type = intent.getStringExtra("type");
        Log.e("gy","id："+friendId);
        Log.e("gy","add_money："+money);
        Log.e("gy","type："+type);
    }

    @Override
    public void initView() {

        tv_send = getId(R.id.tv_send);
        et_reason = getId(R.id.et_reason);
    }

    @Override
    public void initEvent() {
        tv_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                message = et_reason.getText().toString();
                if (money.equals("0")){
                    addFriend();
                }else {
                    openDialog();
                }
            }
        });
    }

    private void addFriend() {
        Map<String, Object> addMap = new HashMap<>();
        addMap.put("id", MyApplication.getInstance().UserInfo.getUserId());
        addMap.put("friendId", friendId);
        if (!TextUtils.isEmpty(message)) {
            addMap.put("message", message);
        }
        if(type.equals("3")){
            addMap.put("status","SAYHELLO");
        }
        RetrofitClient.getInstance().createApi().addFriend(addMap)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("好友申请发送成功!");
                        finish();
                    }
                });
    }

    private void openDialog() {

        new PayDialog(this).setMessage("添加好友需要支付" + money + "元")
                .setSureClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddFriendsFinalActivity.this, PayActivity.class);
                        intent.putExtra("pay_money", money);
                        intent.putExtra("friend_id",friendId+"");
                        intent.putExtra("add_money",money);
                        intent.putExtra("type",type);
                        intent.putExtra("message",message);
                        startActivity(intent);
                    }
                }).showDialog();
    }


    public void back(View view) {
        finish();
    }
}

//  ShowDialog.showDialog(getActivity(), "正在添加...", false, null);
//          RequestParams params = new RequestParams();
//          params.put("id", MyApplication.getInstance().UserInfo.getUserId());
//          params.put("friendId", friendId);
//          params.put("message", et_reason.getText().toString().equals("") ? "你需要发送验证申请，等对方通过" : et_reason.getText().toString());
//          HttpUtil.get(Url.friends_apply, params, new JsonHttpResponseHandler() {
//@Override
//public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//        try {
//        DBLog.e("添加好友：", response.toString());
//        if (response.getInt("code") == 0) {
//        finish();
//        }
//        showToastMsg(response.getString("errorDescription"));
//        } catch (Exception e) {
//        e.printStackTrace();
//        } finally {
//        ShowDialog.dissmiss();
//        }
//        }
//
//@Override
//public void onFinish() {
//        }
//
//@Override
//public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//        showToastMsg("请求服务器失败");
//        DBLog.e("tag", statusCode + ":" + responseString);
//        ShowDialog.dissmiss();
//        }
//        });
//        }
//        });