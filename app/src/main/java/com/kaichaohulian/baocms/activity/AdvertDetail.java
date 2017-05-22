package com.kaichaohulian.baocms.activity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertDetailGridAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdvertDetailEntity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.MyGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertDetail extends BaseActivity {


    @BindView(R.id.tv_timefordetailadvert)
    TextView time;
    @BindView(R.id.tv_Addressee_advert)
    TextView addressee;
    @BindView(R.id.tv_payforadvert)
    TextView paynum;
    @BindView(R.id.title_detailavert)
    TextView title;
    @BindView(R.id.content_detailavert)
    TextView content;
    @BindView(R.id.img_detailadvert)
    MyGridView imgdetailadvert;
    SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
    private AdvertDetailGridAdapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        int adverId = getIntent().getIntExtra("advertId", 0);
        if (adverId != 0) {
            map = new HashMap<>();
            map.put("advertId", adverId + "");
            map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
            RetrofitClient.getInstance().createApi().GetDetailForAdvert(map)
                    .compose(RxUtils.<HttpResult<AdvertDetailEntity>>io_main())
                    .subscribe(new BaseObjObserver<AdvertDetailEntity>(getActivity(), "获取广告详情中...") {
                        @Override
                        protected void onHandleSuccess(AdvertDetailEntity adviertisementEntity) {
                            time.setText(adviertisementEntity.advert.createdTime.substring(0, adviertisementEntity.advert.createdTime.length() - 9));
                            String receive = adviertisementEntity.advert.receive;
                            String[] split = receive.split(",");
                            addressee.setText(split.length + "位收件人 \n" + receive);
                            title.setText(adviertisementEntity.advert.title);
                            paynum.setText("支付费用："+adviertisementEntity.advert.pay+"元");
                            content.setText(adviertisementEntity.advert.context);
                            adapter = new AdvertDetailGridAdapter(getActivity(), getList(adviertisementEntity.advert.image));
                            adapter.setLayoutIds(R.layout.item_advert_detailgrid);
                            imgdetailadvert.setAdapter(adapter);
                        }
                    });
        }
    }

    private List<String> getList(String data) {
        ArrayList<String> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) != ',') {
                buffer.append(data.charAt(i));
            } else if (data.charAt(i) == ',') {
                list.add("http://oez2a4f3v.bkt.clouddn.com/" + buffer.toString());
                buffer.delete(0, buffer.length());
            }
        }
        return list;
    }

    @Override
    public void initView() {
        setCenterTitle("广告详情");
    }

    @Override
    public void initEvent() {

    }


}
