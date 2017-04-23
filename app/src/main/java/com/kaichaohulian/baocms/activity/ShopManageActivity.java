package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MessageShopAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.MessageShopEntity;
import com.kaichaohulian.baocms.entity.ShopInfoEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.map.RoutePlanDemo;
import com.kaichaohulian.baocms.util.VDialog;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.DateUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopManageActivity extends BaseActivity {
    private ImageView imgChat;
    private TextView tvShopInfo;
    private TextView tvShopWelfare;
    private TextView tvShopContact;
    private TextView tvShopManage;
    private ListView listView;
    private MessageShopAdapter adapter;
    ArrayList<MessageShopEntity> data = new ArrayList<>();
    private boolean isMyShop = false;
    String shopName;
    String shopId;
    ShopInfoEntity shopInfoEntity = new ShopInfoEntity();
    private LinearLayout bottomContainerLL;
    private String userid;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_shop__manage_);
    }

    @Override
    public void initData() {
        isMyShop = getIntent().getBooleanExtra("isMyShop", false);
        shopName = getIntent().getStringExtra("shopname");
        shopId = getIntent().getStringExtra("shopid");
        userid = getIntent().getStringExtra("userid");
    }

    @Override
    public void initView() {
        setCenterTitle(shopName);
        imgChat = getId(R.id.img_chat);
        tvShopInfo = getId(R.id.tv_shop_info);
        tvShopWelfare = getId(R.id.tv_shop_welfare);
        tvShopContact = getId(R.id.tv_shop_contact);
        tvShopManage = getId(R.id.tv_shop_manage);
        listView = getId(R.id.listview);
        bottomContainerLL = getId(R.id.layout);
    }

    @Override
    public void initEvent() {
        if (!isMyShop) {
            tvShopManage.setVisibility(View.GONE);
        }

        adapter = new MessageShopAdapter(this, R.layout.item_shop, data);
        listView.setAdapter(adapter);

        getShop();

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(userid) != MyApplication.getInstance().UserInfo.getUserId()) {
                    searchUser(userid);
                } else {
                    ToastUtil.showMessage("这是我的店铺");
                }
            }
        });
        tvShopInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VDialog.getDialogInstance().showFirstDialog(ShopManageActivity.this, tvShopInfo, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        int count = adapter.getCount();
                        if (msg.what == VDialog.OK) {
                            switch (msg.arg1) {
                                case 1:
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity = new MessageShopEntity();
                                    entity.setTitle("营业时间");
                                    entity.setContent(shopInfoEntity.getBusinessTime());
                                    entity.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                                case 2:
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity1 = new MessageShopEntity();
                                    entity1.setTitle("人均缴费");
                                    entity1.setContent(shopInfoEntity.getPrice() + "");
                                    entity1.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity1.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity1);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                                case 3:
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity2 = new MessageShopEntity();
                                    entity2.setTitle("所在区域");
                                    entity2.setContent(shopInfoEntity.getArea());
                                    entity2.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity2.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity2);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                                case 4:
                                    //商家介绍
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity3 = new MessageShopEntity();
                                    entity3.setTitle("商家介绍");
                                    String info = shopInfoEntity.getDetails();
                                    if (TextUtils.isEmpty(info) || info.equals("null")) {
                                        info = "暂无数据";
                                    }
                                    entity3.setContent(info);
                                    entity3.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity3.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    entity3.setDetails(shopInfoEntity.getDetails());
                                    data.add(entity3);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                            }
                        }
                    }
                });
            }
        });
        tvShopWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VDialog.getDialogInstance().showSecondDialog(isMyShop, ShopManageActivity.this, tvShopWelfare, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == VDialog.OK) {
                            int count = adapter.getCount();
                            switch (msg.arg1) {
                                case 1:
                                    //服务比例
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity4 = new MessageShopEntity();
                                    entity4.setTitle("服务比例");
                                    entity4.setContent("暂无信息");
                                    entity4.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity4.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity4);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                                case 2:
                                    //额外福利
                                    if (shopInfoEntity == null)
                                        return;
                                    MessageShopEntity entity5 = new MessageShopEntity();
                                    entity5.setTitle("额外福利");
                                    entity5.setContent("暂无信息");
                                    entity5.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity5.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity5);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    break;
                            }
                        }
                    }
                });
            }
        });

        tvShopContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMyShop) {
                    VDialog.getDialogInstance().showThreeDialog2(isMyShop, ShopManageActivity.this, tvShopManage, new Handler() {
                        //                    VDialog.getDialogInstance().showThreeDialog(isMyShop, ShopManageActivity.this, tvShopManage, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == VDialog.OK) {
                                int count = adapter.getCount();
                                switch (msg.arg1) {
                                    case 1:
                                        //一键拨号
                                        if (shopInfoEntity == null || TextUtils.isEmpty(shopInfoEntity.getTelPhone()))
                                            return;
                                        MessageShopEntity entity5 = new MessageShopEntity();
                                        entity5.setTitle("一键拨号");
                                        String info1 = shopInfoEntity.getTelPhone();
                                        if (TextUtils.isEmpty(info1) || info1.equals("null")) {
                                            info1 = "暂无数据";
                                        }
                                        entity5.setContent(info1);
                                        entity5.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                        entity5.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                        data.add(entity5);
                                        adapter.notifyDataSetChanged();
                                        if (count > 1) {
                                            listView.setSelection(adapter.getCount() - 1);
                                        }
                                        if (!info1.equals("暂无数据")) {
                                            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shopInfoEntity.getTelPhone()));
                                            ShopManageActivity.this.startActivity(it);
                                        }
                                        break;
                                    case 2:
                                        try {
                                            String latitude = shopInfoEntity.getLatitude();
                                            String longitud = shopInfoEntity.getLongitud();
                                            String shopName = shopInfoEntity.getShopName();
                                            //TODO 地图导航 MAP
                                            if (isAvilible(getActivity(), "com.baidu.BaiduMap")) {//传入指定应用包名
                                                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:" + latitude + "," + longitud + "|name:" + shopName + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                                                startActivity(intent);
                                            } else {
                                                if (isAvilible(getActivity(), "com.autonavi.minimap")) {
                                                    Intent intent = new Intent();
                                                    intent.setData(Uri.parse("androidamap://route?sourceApplication=softname"
                                                            + "&dlat=" + latitude
                                                            + "&dlon=" + longitud + "&dname=" + shopName + "&dev=0&m=0&t=1&showType=1"));
                                                    startActivity(intent);
                                                } else {
                                                    Intent routeIntent = new Intent(getActivity(), RoutePlanDemo.class);
                                                    startActivity(routeIntent);
                                                }
                                            }
                                        } catch (Exception e) {
                                            Log.e("intent", e.getMessage());
                                        }
                                        break;
                                    case 3:
                                        MessageShopEntity entity6 = new MessageShopEntity();
                                        entity6.setTitle("商家地址");
                                        String address = shopInfoEntity.getAddress();
                                        if (!TextUtils.isEmpty(address)) {
                                            entity6.setContent("暂无信息");
                                        } else {
                                            if ("null".equals(address)) {
                                                entity6.setContent("暂无信息");
                                            } else {
                                                entity6.setContent(address);
                                            }
                                        }
                                        entity6.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                        entity6.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                        data.add(entity6);
                                        adapter.notifyDataSetChanged();
                                        if (count > 1) {
                                            listView.setSelection(adapter.getCount() - 1);
                                        }

                                        break;
                                }
                            }
                        }
                    });
                    return;
                }
//                VDialog.getDialogInstance().showThreeDialog(isMyShop, ShopManageActivity.this, tvShopContact, new Handler() {
                VDialog.getDialogInstance().showThreeDialog(isMyShop, ShopManageActivity.this, tvShopManage, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == VDialog.OK) {
                            int count = adapter.getCount();
                            switch (msg.arg1) {
                                case 1:
                                    //一键拨号
                                    if (shopInfoEntity == null || TextUtils.isEmpty(shopInfoEntity.getTelPhone()))
                                        return;
                                    MessageShopEntity entity5 = new MessageShopEntity();
                                    entity5.setTitle("一键拨号");
                                    String info1 = shopInfoEntity.getTelPhone();
                                    if (TextUtils.isEmpty(info1) || info1.equals("null")) {
                                        info1 = "暂无数据";
                                    }
                                    entity5.setContent(info1);
                                    entity5.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity5.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity5);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }
                                    if (!info1.equals("暂无数据")) {
                                        Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + shopInfoEntity.getTelPhone()));
                                        ShopManageActivity.this.startActivity(it);
                                    }
                                    break;
                                case 2:
                                    try {
                                        String latitude = shopInfoEntity.getLatitude();
                                        String longitud = shopInfoEntity.getLongitud();
                                        String shopName = shopInfoEntity.getShopName();
                                        //TODO 地图导航 MAP
                                        if (isAvilible(getActivity(), "com.baidu.BaiduMap")) {//传入指定应用包名
                                            Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:" + latitude + "," + longitud + "|name:" + shopName + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                                            startActivity(intent);
                                        } else {
                                            if (isAvilible(getActivity(), "com.autonavi.minimap")) {
                                                Intent intent = new Intent();
                                                intent.setData(Uri.parse("androidamap://route?sourceApplication=softname"
                                                        + "&dlat=" + latitude
                                                        + "&dlon=" + longitud + "&dname=" + shopName + "&dev=0&m=0&t=1&showType=1"));
                                                startActivity(intent);
                                            } else {
                                                Intent routeIntent = new Intent(getActivity(), RoutePlanDemo.class);
                                                startActivity(routeIntent);
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.e("intent", e.getMessage());
                                    }
                                    break;
                                case 3:
                                    MessageShopEntity entity6 = new MessageShopEntity();
                                    entity6.setTitle("商家地址");
                                    String address = shopInfoEntity.getAddress();
                                    if (!TextUtils.isEmpty(address)) {
                                        entity6.setContent("暂无信息");
                                    } else {
                                        if ("null".equals(address)) {
                                            entity6.setContent("暂无信息");
                                        } else {
                                            entity6.setContent(address);
                                        }
                                    }
                                    entity6.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                                    entity6.setPath("http://www.maijia01.com/" + shopInfoEntity.getAvatar());
                                    data.add(entity6);
                                    adapter.notifyDataSetChanged();
                                    if (count > 1) {
                                        listView.setSelection(adapter.getCount() - 1);
                                    }

//                                    Intent intent = new Intent(getActivity(), BaiduMapActivity.class);
//                                    intent.putExtra("latitude", Double.parseDouble(shopInfoEntity.getLatitude()));
//                                    intent.putExtra("longitude", Double.parseDouble(shopInfoEntity.getLongitud()));
//                                    intent.putExtra("isShowBtn", false);
//                                    getActivity().startActivity(intent);
                                    break;
                            }
                        }
                    }
                });
            }
        });

        tvShopManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VDialog.getDialogInstance().showFourDialog(ShopManageActivity.this, tvShopManage, new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == VDialog.OK) {
                            switch (msg.arg1) {
                                case 1:
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("shopname", shopName);
                                    bundle2.putString("shopid", shopId);
                                    ActivityUtil.next(getActivity(), ScanOrderActivity.class, bundle2);
                                    break;
                                case 2:
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putString("shopname", shopName);
                                    bundle1.putString("shopid", shopId);
                                    ActivityUtil.next(getActivity(), SalesStatisticsActivity.class, bundle1);
                                    break;
                                case 3:
                                    ActivityUtil.next(getActivity(), WithdrawApplyActivity.class);
                                    break;
                                case 4:
                                    ActivityUtil.next(getActivity(), WithDrawalsHistoryActivity.class);
                                    break;
                                case 5:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("shopname", shopName);
                                    bundle.putString("shopid", shopId);
                                    ActivityUtil.next(getActivity(), ShopInfoActivity.class, bundle);
                                    break;
                                case 6:
                                    Bundle bundle3 = new Bundle();
                                    bundle3.putString("shopname", shopName);
                                    bundle3.putString("shopid", shopId);
                                    bundle3.putString("qrpic", shopInfoEntity.getQrcode());
                                    ActivityUtil.next(getActivity(), PaymentPicActivity.class, bundle3);
                                    break;
                            }
                        }
                    }
                });
            }
        });

    }

    public void getShop() {
        RequestParams params = new RequestParams();
        params.put("shopid", getIntent().getStringExtra("shopid"));
        HttpUtil.get(Url.business_getByShopId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取商家信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        shopInfoEntity.setAddress(jsonObject.getString("address"));
                        shopInfoEntity.setArea(jsonObject.getString("area"));
                        shopInfoEntity.setAvatar(jsonObject.getString("avatar"));
                        shopInfoEntity.setBusinessTime(jsonObject.getString("businessTime"));
                        shopInfoEntity.setDetails(jsonObject.getString("details"));
                        shopInfoEntity.setDistriDetail(jsonObject.getString("distriDetail"));
                        shopInfoEntity.setDistriwaitTime(jsonObject.getString("distriwaitTime"));
                        shopInfoEntity.setLatitude(jsonObject.getString("latitude"));
                        shopInfoEntity.setLongitud(jsonObject.getString("longitud"));
                        try {
                            shopInfoEntity.setPrice(jsonObject.getDouble("price"));
                        } catch (Exception exception) {
                            shopInfoEntity.setPrice(0);
                        }
                        shopInfoEntity.setTelPhone(jsonObject.getString("telPhone"));
                        shopInfoEntity.setShopName(jsonObject.getString("shopName"));
                        shopInfoEntity.setUserName(jsonObject.getString("userName"));
                        shopInfoEntity.setQrcode(jsonObject.getString("qrcode"));
//                        Glide.with(getActivity()).load(shopInfoEntity.getQrcode()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).into(im_QrCode);
//                        Glide.with(getActivity()).load("http://115.29.99.167:8081/"+shopInfoEntity.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).into(imgHead);

                        MessageShopEntity defMessage = new MessageShopEntity();
                        defMessage.setTitle("商家介绍");
                        defMessage.setDetails(shopInfoEntity.getDetails());
                        String info = shopInfoEntity.getDetails();
                        if (TextUtils.isEmpty(info) || info.equals("null")) {
                            info = "暂无数据";
                        }
                        defMessage.setPath(Url.PIC_ROOT + shopInfoEntity.getAvatar());
                        defMessage.setContent(info);
                        defMessage.setSubTitle(DateUtil.getCurrDateWithFormat("MM月dd日"));
                        data.add(defMessage);
                        adapter.notifyDataSetChanged();
                    }
                    showToastMsg(response.getString("errorDescription"));
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
            }
        });
    }

    /* 检查手机上是否安装了指定的软件
    * @param context
    * @param packageName：应用包名
    * @return
    */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    private void searchUser(final String id) {
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
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(jsonObject.getInt("id"));
//                        UserInfo.setCreatedTime(jsonObject.getString("createdTime"));
//                        UserInfo.setLocked(jsonObject.getBoolean("isLocked"));
//                        UserInfo.setLastModifiedTime(jsonObject.getString("lastModifiedTime"));
//                        UserInfo.setLastModifier(jsonObject.getString("lastModifier"));
                        UserInfo.setUsername(jsonObject.getString("username"));
//                        UserInfo.setPassword(jsonObject.getString("password"));
//                        UserInfo.setAccountNumber(jsonObject.getString("accountNumber"));
//                        UserInfo.setQrCode(jsonObject.getString("qrCode"));
//                        UserInfo.setDistrictId(jsonObject.getString("districtId"));
//                        UserInfo.setSex(jsonObject.getString("sex"));
//                        UserInfo.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
//                        UserInfo.setUserEmail(jsonObject.getString("userEmail"));
//                        UserInfo.setBalance(jsonObject.getString("balance"));
                        UserInfo.setAvatar(jsonObject.getString("avatar"));
//                        UserInfo.setBackAvatar(jsonObject.getString("backAvatar"));
//                        UserInfo.setLoginFailedCount(jsonObject.getInt("loginFailedCount"));
//                        UserInfo.setIsfriend(jsonObject.getInt("isfriend"));
//                        UserInfo.setImages(jsonObject.getString("images"));
//                        Bundle Bundle = new Bundle();
//                        Bundle.putSerializable("data", UserInfo);


                        Bundle Bundle = new Bundle();
                        Bundle.putString("userId", jsonObject.getString("phoneNumber"));
                        Bundle.putString("userNick", jsonObject.getString("username"));
                        Bundle.putString("userAvatar", jsonObject.getString("avatar"));
//                        Bundle.putString("imNumber", user.getImNumber());
                        CCPAppManager.startChattingAction(getActivity(), jsonObject.getString("phoneNumber"), jsonObject.getString("username"), true);

                    }
                    showToastMsg(response.getString("errorDescription"));
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
