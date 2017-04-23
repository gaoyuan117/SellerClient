package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MeAddressListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.MeAddressEntity;
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

/**
 * Created by liuyu on 2016/12/21.
 */

public class MeAddressListActivity extends BaseActivity {
    private ListView listView;
    private List<MeAddressEntity> list;
    private MeAddressListAdapter adapter;
    public static final int ADD_ADDRESS_REQUEST = 11; //进入
    public static final int CHANGE_ADDRESS_REQUEST = 12;
    @Override
    public void setContent() {
        setContentView(R.layout.me_address_list);
    }

    public List<MeAddressEntity> getList() {
        return list;
    }

    public void setList(List<MeAddressEntity> list) {
        this.list = list;
    }

    @Override
    public void initData() {
        list = new ArrayList<MeAddressEntity>();
    }

    @Override
    public void initView() {
        setCenterTitle("我的地址");
        listView=  getId(R.id.me_address_listview);
        adapter = new MeAddressListAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
        getreceiptAddress();
    }
    @Override
    public void initEvent() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size()){
                    Intent intent = new Intent(getActivity(), MeAddAddressActivity.class);
                    startActivityForResult(intent, ADD_ADDRESS_REQUEST);
                }else {
                    Intent intent = new Intent(getActivity(),MeAddAddressActivity.class);
                    intent.putExtra("change", list.get(position));
                    startActivityForResult(intent,CHANGE_ADDRESS_REQUEST);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case ADD_ADDRESS_REQUEST:
                    MeAddressEntity entity= (MeAddressEntity) intent.getSerializableExtra("result");
                    if (!(entity == null)){
                        list.add(entity);
                        adapter.notifyDataSetChanged();
                        addAddress(entity);
                    break;
                    }
                case CHANGE_ADDRESS_REQUEST:
                    MeAddressEntity entity2 = (MeAddressEntity) intent.getSerializableExtra("result");
                    if (entity2 != null){
                        changeAddress(entity2);
                    }
                    break;
        }
    }
    }

    public void changeAddress(MeAddressEntity entity){
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getId());
        params.put("goodsReceipt",entity.getShouHuoname());
        params.put("region",entity.getArea());
        params.put("detailedAddress",entity.getAddress());
        params.put("postcode",entity.getPostCode());
        params.put("phoneNumber",entity.getShouHuoPhone());
        params.put("addressId", entity.getAddressId());
        HttpUtil.post(Url.changeAddress,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改地址", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("修改地址成功");
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("修改地址，解析json失败");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {}
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
    public void addAddress(MeAddressEntity entity){
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getId());
        params.put("goodsReceipt",entity.getShouHuoname());
        params.put("region",entity.getArea());
        params.put("detailedAddress",entity.getAddress());
        params.put("postcode",entity.getPostCode());
        params.put("phoneNumber",entity.getShouHuoPhone());

        HttpUtil.post(Url.saveAddress,params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("添加地址", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("添加地址成功");
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("添加地址，登录失败");
                    e.printStackTrace();
                }finally {
                    ShowDialog.dissmiss();
                }
            }
            @Override
            public void onFinish() {}
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });

    }



    public void getreceiptAddress(){

        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getId());

        list.clear();
        HttpUtil.post(Url.getAddress, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取地址", response.toString());

                    if (response.getInt("code") == 0) {
                        JSONArray jsonArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            MeAddressEntity entity = new MeAddressEntity();
                            entity.setShouHuoname(object.getString("goodsReceipt"));
                            entity.setAddress(object.getString("detailedAddress"));
                            entity.setShouHuoPhone(object.getString("phoneNumber"));
                            entity.setAddressId(object.getString("id"));
                            entity.setArea(object.getString("region"));
                            entity.setPostCode(object.getString("postcode"));
                            list.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                        DBLog.e("list.size()", list.size());
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("获取地址，解析json失败");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {}
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
}
