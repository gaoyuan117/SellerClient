package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.HiToMeAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.HiToMeEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 有人向我打招呼页面
 */
public class SayHiToMeActivity extends BaseActivity {


    private ListView listView;
    private HiToMeAdapter hiToMeAdapter;
    private List<HiToMeEntity> data;

    @Override
    public void setContent() {
        setContentView(R.layout.hi_to_me);
    }

    @Override
    public void initData() {
        data = new ArrayList<HiToMeEntity>();
        testData();
    }

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.list);

        setCenterTitle("附近打招呼的人");

    }

    @Override
    public void initEvent() {
        listView.setAdapter(new HiToMeAdapter(getApplicationContext(),data));

        getData();
    }



    public void testData(){
        for (int i = 0; i <=6; i++){

            HiToMeEntity entity = new HiToMeEntity();
            entity.setName("liuyu" + i);
            data.add(entity);
        }
    }

    public  void getData(){
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page",1);
        HttpUtil.post(Url.sayhello,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("打招呼的人", response.toString());
                    if (response.getInt("code") == 0) {
                        String dataObject = response.getString("dataObject");
                        if(!StringUtils.isEmpty("dataObject")){
                            JSONArray array = response.getJSONArray("dataObject");
                            if (array.length() != 0){
                                for (int i =0; i< array.length(); i++){
                                    JSONObject object = (JSONObject) array.get(i);
                                    HiToMeEntity entity = new HiToMeEntity();
                                    entity.setName(object.getString("username"));
                                    entity.setImgUrl(object.getString("avatar"));
                                    data.add(entity);
                                }
                                hiToMeAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("打招呼的人，解析json失败");
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
