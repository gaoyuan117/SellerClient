package com.kaichaohulian.baocms.view.zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.FriendDetailActivity;
import com.kaichaohulian.baocms.activity.FriendInfoActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.GroupMMEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.zxing.decode.DecodeThread;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResultActivity extends BaseActivity {

    private ImageView mResultImage;
    private TextView mResultText;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_result);
    }

    @Override
    public void initData() {
        Bundle extras = getIntent().getExtras();

        mResultImage = (ImageView) findViewById(R.id.result_image);
        mResultText = (TextView) findViewById(R.id.result_text);

        if (null != extras) {
            int width = extras.getInt("width");
            int height = extras.getInt("height");

            LayoutParams lps = new LayoutParams(width, height);
            lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
            lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

            mResultImage.setLayoutParams(lps);

            String result = extras.getString("result");

            try {
                JSONObject json = new JSONObject(result);
                switch (json.getString("type")) {
                    case "userType":
                        searchUser(json.getInt("userId"));
                        break;
                    case "groupType":
                        searchGroup(json.getInt("groupId"));
                        break;
                }
            } catch (JSONException e) {
                showToastMsg("请扫描正确的二维码");
                e.printStackTrace();
            }

            mResultText.setText(result);

            Bitmap barcode = null;
            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.RGB_565, true);
            }

            mResultImage.setImageBitmap(barcode);
        }
    }

    private void searchGroup(int groupId) {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", groupId);
        HttpUtil.post(Url.scanToGroup, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("扫描加入群组:", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("扫描加入群组成功");
                    }
                    showToastMsg(response.getString("errorDescription"));
                    finish();
                } catch (Exception e) {
                    showToastMsg("数据异常，请重试");
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
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    private void searchUser(final int id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("扫描人物：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                        intent.putExtra("phone", jsonObject.getString("phoneNumber"));
                        intent.putExtra("friendId", id + "");
                        intent.putExtra("type", "1");
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
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
