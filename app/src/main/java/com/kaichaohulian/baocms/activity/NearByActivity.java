package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.HiToMeEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class NearByActivity extends BaseActivity {

    private Button nearby_people_btn;
    private RelativeLayout nearby_people_linear_say_hi;
    private TextView number;

    private int hiToMeNum;

    @Override
    public void setContent() {
        setContentView(R.layout.nearby_people);
    }

    @Override
    public void initData() {
        getData();
    }

    @Override
    public void initView() {
        setCenterTitle("附近的商家");
        setLeftTitle("发现");
        nearby_people_btn = getId(R.id.nearby_people_btn);
        nearby_people_linear_say_hi = getId(R.id.nearby_people_linear_say_hi);
        number = getId(R.id.nearby_people_linear_text_number);
    }

    @Override
    public void initEvent() {
        nearby_people_linear_say_hi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), SayHiToMeActivity.class);
            }
        });

        nearby_people_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), NearByPeopleListActivity.class);
            }
        });
    }


    public void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", 1);
        HttpUtil.post(Url.sayhello, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("打招呼的人", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray array = response.getJSONArray("dataObject");
                        if (null != array && array.length() != 0) {
                            hiToMeNum = array.length();
                            if (hiToMeNum == 0) {
                                nearby_people_linear_say_hi.setVisibility(View.GONE);
                            } else {
                                nearby_people_linear_say_hi.setVisibility(View.VISIBLE);
                                number.setText(hiToMeNum);
                            }
                        } else {

                        }
                    } else {

                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
//                    showToastMsg("打招呼的人，解析json失败");
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
}
