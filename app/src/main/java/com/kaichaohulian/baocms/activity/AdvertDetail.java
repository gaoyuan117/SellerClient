package com.kaichaohulian.baocms.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.text.SimpleDateFormat;
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
    ImageView imgdetailadvert;
    SimpleDateFormat format=new SimpleDateFormat("MM月dd日 HH:mm");
    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        int adverId=getIntent().getIntExtra("advertId",0);
        if(adverId!=0){
            map=new HashMap<>();
            map.put("advertId",adverId+"");
            map.put("userId",MyApplication.getInstance().UserInfo.getUserId()+"");
            RetrofitClient.getInstance().createApi().GetDetailForAdvert(map)
                    .compose(RxUtils.<HttpArray<AdviertisementEntity>>io_main())
                    .subscribe(new BaseListObserver<AdviertisementEntity>(getActivity(),"获取广告详情中...") {
                        @Override
                        protected void onHandleSuccess(List<AdviertisementEntity> list) {
                            AdviertisementEntity data=list.get(0);
                            Date date=new Date(data.timeStamp);
                            time.setText(format.format(date));
                            if(data.receive==null){
                                addressee.setText("0位收件人");
                            }else{
                                addressee.setText(data.receive+"位收件人:\n");
                            }

                            title.setText(data.title);
                            content.setText(data.context);
                            Glide.with(getActivity()).load(data.image).error(R.mipmap.default_advertmass).into(imgdetailadvert);
                        }
                    });
        }
    }

    @Override
    public void initView() {
        setCenterTitle("广告详情");
    }

    @Override
    public void initEvent() {

    }


}
