package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WithdrawActivity extends BaseActivity {
    private static final int SET_PASS_WORD_REQUEST = 13;
    private static final int VERIFY_PASSWORD_REQUEST = 14;

    private Spinner withdraw_which_back;
    private TextView withdrawAll;
    private TextView cashRestAll;
    private TextView warningWithdraw, no_card;
    private Button btnWithdraw;
    private EditText edtInput;
    private LinearLayout linear_warning_withdraw;
    private LinearLayout linear_withdraw;

    private String inputS;
    private DataHelper mDataHelper;
    private List<BankCardEntity> data;
    private BankCardEntity BankCardEntity;
    private int mSelectItem = 0;

    @Override
    public void setContent() {
        setContentView(R.layout.wiithdraw_cash);
    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        mDataHelper = new DataHelper(getActivity());
        getBindBankCard();
    }

    @Override
    public void initView() {
        setCenterTitle("提现到银行卡");
        withdrawAll = getId(R.id.txt_withdraw_all);
        cashRestAll = getId(R.id.withdraw_cash_rest_money);
        edtInput = getId(R.id.edt_input_number);
        linear_warning_withdraw = getId(R.id.linear_warning_withdraw);
        linear_withdraw = getId(R.id.linear_withdraw);
        btnWithdraw = getId(R.id.withdraw_cash_btn);
        warningWithdraw = getId(R.id.warning_withdraw);
        withdraw_which_back = getId(R.id.withdraw_which_back);
        no_card = getId(R.id.no_card);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cashRestAll.setText(MyApplication.getInstance().UserInfo.getBalance() + "");
    }

    @Override
    public void initEvent() {
        withdrawAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cashAll = cashRestAll.getText().toString();
                edtInput.setText(cashAll);
            }
        });
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputS = edtInput.getText().toString().trim();
                if (inputS.length() == 0) {
                    showToastMsg("请输入提现金额");
                    return;
                }
                if (Double.valueOf(inputS).doubleValue() == 0) {
                    showToastMsg("提现金额不能为0");
                    return;
                }
                if (Double.valueOf(inputS).doubleValue() - Double.valueOf(MyApplication.getInstance().UserInfo.getBalance()).doubleValue() > 0) {
                    showToastMsg("余额不足");
                    return;
                }
                if (data.size() == 0) {
                    showToastMsg("您暂无银行卡，请先绑定后再提现至银行卡");
                    return;
                }
                RequestParams params = new RequestParams();
                if (!TextUtils.isEmpty(edtInput.getText().toString().trim())) {
                    params.put("money", (int) (Double.parseDouble(inputS) * 100));
                    params.put("bankName", data.get(mSelectItem).getBankName());
                    params.put("bankNum", data.get(mSelectItem).getCardNo());
                    params.put("bankRealname", data.get(mSelectItem).getUsername());
                    tiXian(params);
                }
            }

        });
        withdraw_which_back.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BankCardEntity = data.get(position);
                mSelectItem = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edtInput.addTextChangedListener(watcher);
    }

    public void tiXian(RequestParams params) {
        ShowDialog.showDialog(getActivity(), "提现申请中...", false, null);
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        HttpUtil.post(Url.users_banks_withdrawals, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(getActivity());
                            return;
                        }
                    }
                    DBLog.e("提现：", response.toString());
                    if (response.getInt("code") == 0) {
                        MyApplication.getInstance().UserInfo.setBalance((Double.valueOf(MyApplication.getInstance().UserInfo.getBalance()) - Double.valueOf(inputS)) + "");
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                        showToastMsg("提现成功");
                        finish();
                        ActivityUtil.next(WithdrawActivity.this, WithDrawalsHistoryActivity.class);
                    }
                    DBLog.showToast(response.getString("errorDescription"), getApplication());
                } catch (Exception e) {
                    DBLog.showToast("提现失败", getApplication());
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
                DBLog.showToast("请求服务器失败", getActivity());
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(edtInput.getText().toString().trim())) {
                String inputNum = edtInput.getText().toString().trim();
                if (inputCompareRest(inputNum)) {
                    warningWithdraw.setVisibility(View.INVISIBLE);
                    linear_withdraw.setVisibility(View.VISIBLE);
                    btnWithdraw.setEnabled(true);
                } else {
                    warningWithdraw.setVisibility(View.VISIBLE);
                    linear_withdraw.setVisibility(View.INVISIBLE);
                    btnWithdraw.setEnabled(false);
                }
            } else {
                warningWithdraw.setVisibility(View.INVISIBLE);
                linear_withdraw.setVisibility(View.VISIBLE);
                btnWithdraw.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public boolean inputCompareRest(String input) {
        if (input == "") {
            return true;
        }
        String cashRest = cashRestAll.getText().toString();
        double rest = Double.parseDouble(cashRest);
        double inputA = Double.parseDouble(input);
        BigDecimal bigDecimal = new BigDecimal(inputA);
        double inputB = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (rest >= inputB) {
            return true;
        } else {
            return false;
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
                        if (data.size() == 0) {
                            withdraw_which_back.setVisibility(View.GONE);
                            no_card.setVisibility(View.VISIBLE);
                        } else {
                            withdraw_which_back.setVisibility(View.VISIBLE);
                            no_card.setVisibility(View.GONE);
                        }
                        Adapter adapter = new Adapter();
                        withdraw_which_back.setAdapter(adapter);
                        if (data.size() > 0) {
                            BankCardEntity = data.get(0);
                        }
                    }
                    showToastMsg(response.getString("errorDescription"));
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

    public class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public BankCardEntity getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(data.get(position).getBankName() + "(" + data.get(position).getCardNo().substring(data.get(position).getCardNo().length() - 4, data.get(position).getCardNo().length()) + ")");
            return convertView;
        }
    }
}


