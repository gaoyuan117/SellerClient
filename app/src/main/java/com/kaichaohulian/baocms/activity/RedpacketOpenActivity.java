package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.RedpacketHistoryAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.RedpacketHistory;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RedpacketOpenActivity extends BaseActivity {
    public static final String AVATER = "AVATER";
    public static final String SENDER_NAME = "SENDER_NAME";
    public static final String SENDER_ID = "SENDER_ID";
    public static final String BLANCE = "BLANCE";
    public static final String RED_ID = "RED_ID";
    public static final String IS_MYSELF = "IS_MYSELF";
    public static final String DESC = "DESC";

    private int mId;
    private boolean mIsMySelf;
    private TextView summaryTV;
    private ListView historyLV;
    private TextView blanceTV;
    private String currUserName;
    private ImageView avatarIV;
    private TextView nameTV;
    private TextView messageTV;
    private TextView redpacketTipTV;
    private TextView cancelTV;

    @Override
    public void setContent() {
        setContentView(R.layout.redpacket_open);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        currUserName = MyApplication.getInstance().UserInfo.getUsername();
        data = new ArrayList<>();
        mId = getIntent().getIntExtra(RED_ID, -1);
        mIsMySelf = getIntent().getBooleanExtra(IS_MYSELF, false);

        cancelTV = (TextView) findViewById(R.id.tv_redpacket_cancel);
        avatarIV = (ImageView) findViewById(R.id.tv_redpacket_sender);
        nameTV = (TextView) findViewById(R.id.tv_redpacket_sender_name);
        redpacketTipTV = (TextView) findViewById(R.id.tv_redpacket_tip);

        if (mIsMySelf) {
            redpacketTipTV.setVisibility(View.GONE);
        }

        blanceTV = (TextView) findViewById(R.id.tv_redpacket_amount);
        TextView blanceTipTV = (TextView) findViewById(R.id.tv_redpacket_amount_tip);
        messageTV = (TextView) findViewById(R.id.tv_redpacket_desc);
        TextView historyTV = (TextView) findViewById(R.id.tv_redpacket_history);
        TextView cancelTV = (TextView) findViewById(R.id.tv_redpacket_cancel);
        summaryTV = (TextView) findViewById(R.id.tv_redpacket_summary);
        historyLV = (ListView) findViewById(R.id.lv_redpacket);

        historyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (mIsMySelf) {
            blanceTV.setVisibility(View.GONE);
            blanceTipTV.setVisibility(View.GONE);
        } else {
            blanceTV.setVisibility(View.GONE);
            blanceTipTV.setVisibility(View.GONE);
        }
        if (mIsMySelf) {
            getMyPckList();
        } else {
            getList();
        }
    }

    @Override
    public void initEvent() {

    }

    private List<RedpacketHistory> data;

    private void getList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("redId", mId);
        LogUtil.d("TRACE", "target red id : " + mId);
        HttpUtil.post(Url.moneyreds_open_mems, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            if (response.getInt("code") == 0) {
                                String redpacketMsg = "";
                                String userName = "";
                                String userAvatar = "";
                                try {
                                    redpacketMsg = response.getString("message");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userName = response.getString("userName");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userAvatar = response.getString("userAvatar");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                response = response.getJSONObject("dataObject");
                                int redCount = response.getInt("redCount");
                                double redsum = response.getDouble("redsum");

                                try {
                                    redpacketMsg = response.getString("message");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userName = response.getString("userName");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userAvatar = response.getString("userAvatar");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                nameTV.setText(userName);
                                messageTV.setText(redpacketMsg);
                                ImageLoader.getInstance().displayImage(userAvatar, avatarIV);


                                JSONArray JSONArray = response.getJSONArray("reds");

                                double sum = 0;
                                double useracount;

                                for (int i = 0; i < JSONArray.length(); i++) {
                                    JSONObject json = JSONArray.getJSONObject(i);
                                    RedpacketHistory redpacketHistory = new RedpacketHistory();
                                    redpacketHistory.name = json.getString("userName");
                                    redpacketHistory.time = json.getString("createdTime");
                                    redpacketHistory.avatar = json.getString("avatar");
                                    redpacketHistory.useracount = json.getString("useracount");
                                    useracount = Double.parseDouble(json.getString("useracount"));
                                    sum += useracount;
                                    redpacketHistory.best = json.getBoolean("best");
                                    data.add(redpacketHistory);
//                            if (redpacketHistory.useracount != null && currUserName != null && redpacketHistory.useracount != null && redpacketHistory.name.equals(currUserName)) {
//                                BigDecimal bd = new BigDecimal(redpacketHistory.useracount);
//                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
//                                blanceTV.setText(bd.toString());
//                                messageTV.setText(redpacketMsg);
//                                nameTV.setText(json.getString("userName"));
//                                String avatar = json.getString("avatar");
//                                LogUtil.d("TRACE", "the avatar is :" + avatar);
//                                LogUtil.d("TRACE", "the message is :" + message);
//                                ImageLoader.getInstance().displayImage(avatar, avatarIV);
//                            }
                                }
                                String showText = "已领取" + data.size() + "/" + redCount + "个，" + "共" + sum + "/" + redsum + "元";
                                summaryTV.setText(showText);

                                RedpacketHistoryAdapter adapter = new RedpacketHistoryAdapter(data, getActivity());
                                historyLV.setAdapter(adapter);

                                LogUtil.d("TRACE", "target response : " + response);
                                LogUtil.d("TRACE", "target data : " + data);
                            }
                        } catch (
                                Exception e
                                )

                        {
                            e.printStackTrace();
                        } finally

                        {
                            ShowDialog.dissmiss();
                        }
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                            throwable) {
                        ToastUtil.showMessage("请求服务器失败");
                        DBLog.e("tag", statusCode + ":" + responseString);
                        ShowDialog.dissmiss();
                    }
                }

        );
    }

    private void showPhotoDialog() {
        final android.app.AlertDialog dlg = new android.app.AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("我发的红包");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), SendRedBagActivity.class);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("收到的红包");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), ReceviedRedBagListActivity.class);
                dlg.cancel();
            }
        });
    }

    public void getMyPckList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("redId", mId);
        LogUtil.d("TRACE", "target red id : " + mId);
        HttpUtil.post(Url.moneyreds_open_mems, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        String redpacketMsg = response.getString("message");
                        String userName = response.getString("userName");
                        String userAvatar = response.getString("userAvatar");

                        nameTV.setText(userName);
                        messageTV.setText(redpacketMsg);
                        ImageLoader.getInstance().displayImage(userAvatar, avatarIV);

                        JSONArray JSONArray = response.getJSONArray("reds");
                        int redCount = response.getInt("redCount");
                        double redsum = response.getDouble("redsum");

                        double sum = 0;
                        double useracount;

                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            RedpacketHistory redpacketHistory = new RedpacketHistory();
                            redpacketHistory.name = json.getString("userName");
                            redpacketHistory.time = json.getString("createdTime");
                            redpacketHistory.avatar = json.getString("avatar");
                            redpacketHistory.useracount = json.getString("useracount");
                            useracount = Double.parseDouble(json.getString("useracount"));
                            sum += useracount;
                            redpacketHistory.best = json.getBoolean("best");
                            data.add(redpacketHistory);
                            if (redpacketHistory.useracount != null && currUserName != null && redpacketHistory.useracount != null && redpacketHistory.name.equals(currUserName)) {
                                BigDecimal bd = new BigDecimal(redpacketHistory.useracount);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                blanceTV.setText(bd.toString());
                                messageTV.setText(redpacketMsg);
                                nameTV.setText(json.getString("userName"));
                                String avatar = json.getString("avatar");
                                ImageLoader.getInstance().displayImage(avatar, avatarIV);
                            }
                        }

                        String showText = "已领取" + data.size() + "/" + redCount + "个，" + "共" + sum + "/" + redsum + "元";
                        summaryTV.setText(showText);


                        RedpacketHistoryAdapter adapter = new RedpacketHistoryAdapter(data, getActivity());
                        historyLV.setAdapter(adapter);

                        LogUtil.d("TRACE", "target response : " + response);
                        LogUtil.d("TRACE", "target data : " + data);
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }
}
