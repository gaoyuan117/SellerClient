package com.kaichaohulian.baocms.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.fragment.PayedFragment;
import com.kaichaohulian.baocms.fragment.UnpayFragment;

public class ActivityScanOrder extends BaseActivity {

    private Fragment[] fragments;
    private UnpayFragment unpayFragment;
    private PayedFragment payedFragment;

    private TextView tvUnpay;
    private TextView tvPayed;

    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    @Override
    public void setContent() {
        //       setCenterTitle("扫描订单");
        setContentView(R.layout.activity_scan_order);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        tvPayed = getId(R.id.tv_payed);
        tvUnpay = getId(R.id.tv_unpay);

        tvUnpay.setTextColor(getResources().getColor(R.color.green));

        unpayFragment = new UnpayFragment(MyApplication.getInstance(), getActivity(), getActivity());
        payedFragment = new PayedFragment(MyApplication.getInstance(), getActivity(), getActivity());
        fragments = new Fragment[]{unpayFragment, payedFragment};

        getSupportFragmentManager().beginTransaction()
                .add(R.id.re_fragment_container, unpayFragment)
                .add(R.id.re_fragment_container, payedFragment)
                .hide(payedFragment)
                .show(unpayFragment).commit();
    }

    @Override
    public void initEvent() {

    }

    public void onTabClicked(View view) {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.re_unpay:
                tvUnpay.setTextColor(getResources().getColor(R.color.green));
                tvPayed.setTextColor(getResources().getColor(R.color.dark));

                trx.hide(payedFragment);
                trx.show(unpayFragment).commit();

                break;
            case R.id.re_payed:
                tvPayed.setTextColor(getResources().getColor(R.color.green));
                tvUnpay.setTextColor(getResources().getColor(R.color.dark));

                trx.hide(unpayFragment);
                trx.show(payedFragment).commit();

                break;
        }
    }
}
