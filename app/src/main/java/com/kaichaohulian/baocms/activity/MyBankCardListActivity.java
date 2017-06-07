package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyBankCardListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyBankCardListActivity extends BaseActivity {

    private ListView listView;
    private List<BankCardEntity> data;
    private MyBankCardListAdapter adapter;
    public static final int ADD_BANKCARD_REQUEST = 1000;

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
        if(getIntent().getBooleanExtra("IsChoose",false)){
            setCenterTitle("选择银行卡");
        }else{
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
        if(getIntent().getBooleanExtra("IsChoose",false)){
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent();
                    intent.putExtra("bankcard",data.get(i));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }else{
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    new AlertDialog.Builder(MyBankCardListActivity.this)
                            .setMessage("是否删除？")
                            .setPositiveButton(R.string.ok,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            deleteCard(i);
                                            dialog.dismiss();

                                        }
                                    })
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();

                                        }
                                    }).setCancelable(false).show();
                    return false;
                }
            });
        }

    }

    public void deleteCard(int i) {
        RequestParams params = new RequestParams();
        params.put("bankId", data.get(i).getId());
        HttpUtil.post(Url.deleteCard, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("删除银行卡：", response.toString());
                    if (response.getInt("code") == 0) {
                        finish();
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

}
