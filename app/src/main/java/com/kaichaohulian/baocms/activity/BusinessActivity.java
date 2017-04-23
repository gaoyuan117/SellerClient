package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.BusinessAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ShopEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.KeyBoardUtils;
import com.kaichaohulian.baocms.view.BusinessSidebar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 商家界面
 * Created by ljl on 2016/12/19 0019.
 */

public class BusinessActivity extends BaseActivity {

    private BusinessAdapter adapter;
    private List<ShopEntity> contactList;
    private ListView listView;
    private BusinessSidebar sidebar;

    private TextView tv_total;
    private LayoutInflater infalter;
    private EditText searchET;

    @Override
    public void setContent() {
        setContentView(R.layout.business_activity);
    }

    @Override
    public void initData() {
        contactList = new ArrayList<ShopEntity>();
    }

    @Override
    public void initView() {
        setCenterTitle("商家");
        listView = getId(R.id.listHaoYou);
        searchET = getId(R.id.et_search);
        infalter = LayoutInflater.from(getActivity());
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);

        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        // 设置adapter
        adapter = new BusinessAdapter(getActivity(), R.layout.item_shopping_list, contactList);
        listView.setAdapter(adapter);
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String targetText = searchET.getText().toString().trim();
                searchFromList(targetText);
            }
        });

        // 获取设置shoppinglist
        getShoppingList();
    }

    private void searchFromList(String targetText) {
        List<ShopEntity> targetList = new ArrayList<>();
        for (ShopEntity shop : contactList) {
            if (shop.getShopName().contains(targetText)) {
                targetList.add(shop);
            }
        }
        adapter = new BusinessAdapter(getActivity(), R.layout.item_shopping_list, targetList);
        listView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position < contactList.size() + 1) {
                    ShopEntity ShopEntity = contactList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("shopname", ShopEntity.getShopName() + "");
                    bundle.putString("shopid", ShopEntity.getShopId() + "");
                    bundle.putString("userid", ShopEntity.getUserId() + "");
                    if (ShopEntity.getUserId() == MyApplication.getInstance().UserInfo.getUserId()) {
                        bundle.putBoolean("isMyShop", true);
                    }
                    ActivityUtil.next(getActivity(), ShopManageActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 获取商家
     */
    private void getShoppingList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.business_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取商家：", response.toString());
                    if (response.getInt("code") == 0) {
                        contactList.clear();
                        JSONObject JSONObject = response.getJSONObject("dataObject");
                        JSONObject mysShop = null;
                        try {
                            mysShop = JSONObject.getJSONObject("mysShop");//我的店铺
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (mysShop != null) {
                            ShopEntity myShopEntity = new ShopEntity();
                            try {
                                myShopEntity.setShopId(mysShop.getInt("shopId"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setUserId(mysShop.getInt("userId"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setCateName(mysShop.getString("cateName"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setShopName(mysShop.getString("shopName"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setLogo(mysShop.getString("logo"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setPhoto(mysShop.getString("photo"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                myShopEntity.setHeader("我的店铺");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (myShopEntity != null) {
                                contactList.add(myShopEntity);
                            }
                        }
                        //我的推广商店
                        JSONArray extensionShopsArray = JSONObject.getJSONArray("extensionShops");
                        for (int i = 0; i < extensionShopsArray.length(); i++) {
                            ShopEntity ShopEntity = new ShopEntity();
                            JSONObject jsonObject = extensionShopsArray.getJSONObject(i);
                            ShopEntity.setShopId(jsonObject.getInt("shopId"));
                            ShopEntity.setUserId(jsonObject.getInt("userId"));
                            ShopEntity.setCateName(jsonObject.getString("cateName"));
                            ShopEntity.setShopName(jsonObject.getString("shopName"));
                            ShopEntity.setLogo(jsonObject.getString("logo"));
                            ShopEntity.setPhoto(jsonObject.getString("photo"));
                            ShopEntity.setHeader("我的推广店铺");
                            contactList.add(ShopEntity);
                        }

                        //我的好友商店
                        JSONArray myFriendShopsArray = JSONObject.getJSONArray("myFriendShops");
                        for (int i = 0; i < myFriendShopsArray.length(); i++) {
                            ShopEntity ShopEntity = new ShopEntity();
                            JSONObject jsonObject = myFriendShopsArray.getJSONObject(i);
                            ShopEntity.setShopId(jsonObject.getInt("shopId"));
                            ShopEntity.setUserId(jsonObject.getInt("userId"));
                            ShopEntity.setCateName(jsonObject.getString("cateName"));
                            ShopEntity.setShopName(jsonObject.getString("shopName"));
                            ShopEntity.setLogo(jsonObject.getString("logo"));
                            ShopEntity.setPhoto(jsonObject.getString("photo"));
                            ShopEntity.setHeader("我的好友店铺");
                            contactList.add(ShopEntity);
                        }
                        tv_total.setText(String.valueOf(contactList.size()) + "个商家");
                        // 对list进行排序
                        Collections.sort(contactList, new PinyinComparator() {
                        });
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
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

    public class PinyinComparator implements Comparator<ShopEntity> {
        @Override
        public int compare(ShopEntity o1, ShopEntity o2) {
            String py1 = o1.getHeader();
            String py2 = o2.getHeader();
            // 判断是否为空""
            if (isEmpty(py1) && isEmpty(py2))
                return 0;
            if (isEmpty(py1))
                return -1;
            if (isEmpty(py2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
                str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
            } catch (Exception e) {
                System.out.println("某个str为\" \" 空");
            }
            return str1.compareTo(str2);
        }

        private boolean isEmpty(String str) {
            return "".equals(str);
        }
    }
}
