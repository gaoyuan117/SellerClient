package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.BusinessApplyOneActivity;
import com.kaichaohulian.baocms.activity.DriftBottleActivity;
import com.kaichaohulian.baocms.activity.NearByActivity;
import com.kaichaohulian.baocms.activity.ShakeActivity;
import com.kaichaohulian.baocms.activity.ShopInfoActivity;
import com.kaichaohulian.baocms.activity.ShoppingWebView;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.circledemo.ShoppingCircleActivity;
import com.kaichaohulian.baocms.entity.ShopEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.zxing.activity.CaptureActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 发现
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class FindFragment extends BaseFragment {
    public final static int REQUEST_CODE = 0x0123;

    RelativeLayout mBuyer;
    RelativeLayout mBusinessIn;
    RelativeLayout mScan;
    RelativeLayout mShake;
    RelativeLayout mNearBy;
    RelativeLayout mShopping;
    RelativeLayout mBottle;
    private AlertDialog alertDialog;

    public FindFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.discovery_layout, null);
    }

    @Override
    public void initView() {
        mBuyer = getId(R.id.discovery_relative_buyer);
        mBusinessIn = getId(R.id.discovery_relative_business_in);
        mScan = getId(R.id.discovery_relative_scan);
        mShake = getId(R.id.discovery_relative_shake);
        mNearBy = getId(R.id.discovery_relative_nearby);
        mShopping = getId(R.id.discovery_relative_shopping);
        mBottle = getId(R.id.discovery_relative_bottle);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

        mBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), ShoppingCircleActivity.class);
            }
        });
        mBusinessIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
        mShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), ShakeActivity.class);
            }
        });

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        mNearBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityUtil.next(getActivity(), NearByPeopleListActivity.class);
                ActivityUtil.next(getActivity(), NearByActivity.class);

            }
        });

        mShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), ShoppingWebView.class);
            }
        });

        mBottle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), DriftBottleActivity.class);
            }
        });
    }

    public void validate() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("status", "SAYHELLO");
        HttpUtil.post(Url.judgeIsSuccess, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取我加入和我创建的群", response.toString());
                    if (response.getInt("code") == 0) {
                        String errorDescription = response.getString("errorDescription");
                        if ("该用户还不是商家".equals(errorDescription)) {
                            ActivityUtil.next(getActivity(), BusinessApplyOneActivity.class);
                            return;
                        }
                        if ("商家信息审核中!敬请期待!".equals(errorDescription)) {
                            showToastMsg(response.getString("errorDescription"));
                            return;
                        }
                        if ("商家信息审核成功，是否要修改商家信息!".equals(errorDescription)) {
                            showNormalDialog();
                            return;
                        }
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

    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("买家网");
        normalDialog.setMessage("商家信息审核成功，是否要修改商家信息!");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getShoppingList();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        // 显示
        alertDialog = normalDialog.show();
    }

    private void getShoppingList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.business_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取商家：", response.toString());
                    if (response.getInt("code") == 0) {
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

                            Bundle bundle = new Bundle();
                            bundle.putString("shopname", myShopEntity.getShopName());
                            bundle.putString("shopid", myShopEntity.getShopId() + "");
                            ActivityUtil.next(getActivity(), ShopInfoActivity.class, bundle);
                            if (alertDialog != null && alertDialog.isShowing()) {
                                alertDialog.dismiss();
                            }
                        }
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
}
