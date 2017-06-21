package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyBankCardListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.PasswordEdittext;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyBankCardListActivity extends BaseActivity implements AdapterView.OnItemLongClickListener {
    private ListView listView;
    private List<BankCardEntity> data;
    private MyBankCardListAdapter adapter;
    public static final int ADD_BANKCARD_REQUEST = 1000;
    private View SignPassword;
    private PasswordEdittext paywordEdt;
    private PopupWindow PopSignPassword;
    private int del;
    //    private PasswordEdittext paywordEdt;
//    private PopupWindow PopSignPassword;
//    private View SignPassword;
    @Override
    public void setContent() {
        setContentView(R.layout.my_bank_card_list);
    }

    @Override
    public void initData() {
        data = new ArrayList<BankCardEntity>();
        adapter = new MyBankCardListAdapter(getActivity(), data);
        getBindBankCard();
    }

    @Override
    public void initView() {
        listView = getId(R.id.my_bankcard_list);
        if (getIntent().getBooleanExtra("IsChoose", false)) {
            setCenterTitle("选择银行卡");
        } else {
            setCenterTitle("我的银行卡");
            setIm1_view(R.mipmap.add_part).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyBankCardListActivity.this, AddBankCardActivity.class);
                    startActivityForResult(intent, ADD_BANKCARD_REQUEST);
                }
            });
        }
    }

    @Override
    public void initEvent() {
        listView.setAdapter(adapter);
        if (getIntent().getBooleanExtra("IsChoose", false)) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent();
                    intent.putExtra("bankcard", data.get(i));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            listView.setOnItemLongClickListener(this);
        }

    }
//    private void ShowPayWord() {
//        if(MyApplication.getInstance().UserInfo.getPayPassword()==null){
//            Toast.makeText(this, "请到设置-设置支付密码页面设置支付密码", Toast.LENGTH_SHORT).show();return;
//        }
//        if (SignPassword == null) {
//            SignPassword = View.inflate(this, R.layout.sign_paypassword, null);
//            paywordEdt = (PasswordEdittext) SignPassword.findViewById(R.id.paypassword_edt);
//            ImageView iv = (ImageView) SignPassword.findViewById(R.id.img_exit_signpassword);
//            iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    PopSignPassword.dismiss();
//                }
//            });
//            paywordEdt.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//                    if (editable.length() == 6) {
//                        SignPayPassWord(editable.toString().trim());
//                    }
//                }
//            });
//        }
//        if (PopSignPassword == null) {
//            int H;
//            H = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            SignPassword.measure(0, H);
//            H = SignPassword.getMeasuredHeight();
//            PopSignPassword = new PopupWindow(SignPassword, ViewGroup.LayoutParams.MATCH_PARENT, H);
//            PopSignPassword.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//            PopSignPassword.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            PopSignPassword.setTouchable(true); // 设置popupwindow可点击
//            PopSignPassword.setFocusable(true); // 获取焦点
//            ColorDrawable dw = new ColorDrawable(Color.WHITE);
//            //设置SelectPicPopupWindow弹出窗体的背景
//            PopSignPassword.setBackgroundDrawable(dw);
//            PopSignPassword.setAnimationStyle(R.style.popPassword_animation);
//            PopSignPassword.showAtLocation(findViewById(R.id.main_activity_withdraw_apply),
//                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//            paywordEdt.setFocusable(true);
//        } else {
//            PopSignPassword.showAtLocation(findViewById(R.id.main_activity_withdraw_apply),
//                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//            paywordEdt.setFocusable(true);
//
//        }
//
//        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        //这里给它设置了弹出的时间，
//        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    //网络请求验证支付密码
//    private void SignPayPassWord(String payword) {
//        if (map == null) {
//            map = new HashMap<>();
//        } else {
//            map.clear();
//        }
//        map.put("id", String.valueOf(MyApplication.getInstance().UserInfo.getUserId()));
//        map.put("password", payword);
//        RetrofitClient.getInstance().createApi().verificatPassword(map)
//                .compose(RxUtils.<HttpResult>io_main())
//                .subscribe(new Observer<HttpResult>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(HttpResult value) {
//                        PopSignPassword.dismiss();
//                        if (value.errorDescription.contains("重新输入")) {
//                            Toast.makeText(getActivity(), "密码错误请重新输入", Toast.LENGTH_SHORT).show();
//                            paywordEdt.getText().clear();
//                        }else{
//                            deleteCard(del);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }

    public void deleteCard(int i) {
        RequestParams params = new RequestParams();
        params.put("bankId", data.get(i).getId());
        HttpUtil.post(Url.deleteCard, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("删除银行卡：", response.toString());
                    if (response.getInt("code") == 0) {
                        data.remove(del);
                        adapter.notifyDataSetChanged();
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
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_BANKCARD_REQUEST:
                    String cardnumber = intent.getStringExtra("cardNo");
                    String lastFour = cardnumber.substring(cardnumber.length() - 4, cardnumber.length());

                    String username = intent.getStringExtra("username");
                    String cardType = intent.getStringExtra("cardType");
                    String phone = intent.getStringExtra("phoneNumber");
                    String idcard = intent.getStringExtra("idcard");
                    String openbank = intent.getStringExtra("openbank");
                    RequestParams params = new RequestParams();
                    params.put("id", MyApplication.getInstance().UserInfo.getUserId());
                    params.put("username", username);
                    params.put("bankName", openbank);
                    params.put("cardType", cardType);
                    params.put("phoneNumber", phone);
                    params.put("cardNo", cardnumber);
                    params.put("idcard", idcard);
                    HttpUtil.post(Url.bindCard, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                DBLog.e("添加银行卡:", response.toString());
                                if (response.getInt("code") == 0) {
                                    getBindBankCard();
                                }
                            } catch (Exception e) {
                                showToastMsg("添加银行卡，登录失败");
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
                            ShowDialog.dissmiss();
                        }
                    });

                    break;
            }
        }
    }

    public void getBindBankCard() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.getBindCard, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取银行卡:", response.toString());
                    if (response.getInt("code") == 0) {
                        data.clear();
                        JSONArray array = response.getJSONArray("dataObject");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            BankCardEntity entity = new BankCardEntity();
                            entity.setId(object.getInt("id"));
                            entity.setCreatedTime(object.getString("createdTime"));
                            entity.setCreator(object.getInt("creator"));
                            entity.setIsLocked(object.getBoolean("isLocked"));
                            entity.setLastModifiedTime(object.getString("lastModifiedTime"));
                            entity.setLastModifier(object.getInt("lastModifier"));
                            entity.setTimeStamp(object.getString("timeStamp"));
                            entity.setCardNo(object.getString("cardNo"));
                            entity.setCardType(object.getString("cardType"));
                            entity.setIdcard(object.getString("idcard"));
                            entity.setUsername(object.getString("username"));
                            entity.setOneOuota(object.getString("oneOuota"));
                            entity.setDayusername(object.getString("dayusername"));
                            entity.setBankName(object.getString("bankName"));
//                            entity.setNumberAll(object.getString("cardNo"));
                            data.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    showToastMsg("获取银行卡，解析json异常");
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
                ShowDialog.dissmiss();
            }
        });
    }

    //显示支付密码的验证框
    private void ShowPayWord() {
        try{
            if(MyApplication.getInstance().UserInfo.getPayPassword()==null){
                Toast.makeText(this, "请到设置-设置支付密码页面设置支付密码", Toast.LENGTH_SHORT).show();return;
            }
            if (SignPassword == null) {
                SignPassword = View.inflate(this, R.layout.sign_paypassword, null);
                paywordEdt = (PasswordEdittext) SignPassword.findViewById(R.id.paypassword_edt);
                ImageView iv = (ImageView) SignPassword.findViewById(R.id.img_exit_signpassword);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PopSignPassword.dismiss();
                    }
                });
                paywordEdt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() == 6) {
                            SignPayPassWord(editable.toString().trim());
                        }
                    }
                });
            }
            if (PopSignPassword == null) {
                int H;
                H = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                SignPassword.measure(0, H);
                H = SignPassword.getMeasuredHeight();
                PopSignPassword = new PopupWindow(SignPassword, ViewGroup.LayoutParams.MATCH_PARENT, H);
                PopSignPassword.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                PopSignPassword.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                PopSignPassword.setTouchable(true); // 设置popupwindow可点击
                PopSignPassword.setFocusable(true); // 获取焦点
                ColorDrawable dw = new ColorDrawable(Color.WHITE);
                //设置SelectPicPopupWindow弹出窗体的背景
                PopSignPassword.setBackgroundDrawable(dw);
                PopSignPassword.setAnimationStyle(R.style.popPassword_animation);
                PopSignPassword.showAtLocation(findViewById(R.id.bankcarklist),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                paywordEdt.setFocusable(true);
                paywordEdt.requestFocus();
            } else {
                PopSignPassword.showAtLocation(findViewById(R.id.bankcarklist),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                paywordEdt.setFocusable(true);
                paywordEdt.requestFocus();

            }

            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            //这里给它设置了弹出的时间，
            imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //网络请求验证支付密码
    private void SignPayPassWord(String payword) {
        try{
            if (map == null) {
                map = new HashMap<>();
            } else {
                map.clear();
            }
            map.put("id", String.valueOf(MyApplication.getInstance().UserInfo.getUserId()));
            map.put("password", payword);
            RetrofitClient.getInstance().createApi().verificatPassword(map)
                    .compose(RxUtils.<HttpResult>io_main())
                    .subscribe(new Observer<HttpResult>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(HttpResult value) {
                            PopSignPassword.dismiss();
                            if (value.errorDescription.contains("重新输入")) {
                                Toast.makeText(getActivity(), "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                                paywordEdt.getText().clear();
                            }else{
                                deleteCard(del);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new AlertDialog.Builder(MyBankCardListActivity.this)
                .setMessage("是否删除？")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                ShowPayWord();
                                del=i;
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(true).create().show();
        return false;
    }
}
