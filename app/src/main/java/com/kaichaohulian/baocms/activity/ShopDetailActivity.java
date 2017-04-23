package com.kaichaohulian.baocms.activity;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.settings.WebViewUI;

/**
 * Created by Jimq on 2017/2/24.
 */

public class ShopDetailActivity extends BaseActivity {
    private WebView mDetail;
    private String mShopDetail;

    @Override
    public void setContent() {
        setContentView(R.layout.shop_detail);
        setCenterTitle("商家介绍");
        mDetail = getId(R.id.shop_desc);
    }

    @Override
    public void initData() {
        mShopDetail = getIntent().getStringExtra("shop_detail");
    }

    @Override
    public void initView() {
        if (!TextUtils.isEmpty(mShopDetail)) {
            Log.e("TRACE", "shop detail : " + mShopDetail);
            mDetail.getSettings().setAppCacheEnabled(true);
            mDetail.getSettings().setBuiltInZoomControls(true);
            mDetail.getSettings().setSaveFormData(true);
            mDetail.loadUrl(mShopDetail);
            mDetail.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }

    @Override
    public void initEvent() {

    }
}
