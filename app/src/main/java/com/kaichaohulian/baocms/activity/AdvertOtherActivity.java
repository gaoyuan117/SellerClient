package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvertOtherActivity extends BaseActivity {

    @BindView(R.id.tv_age_otheradvert)
    TextView tvAgeOtheradvert;
    @BindView(R.id.tv_job_otheradvert)
    TextView tvJobOtheradvert;
    @BindView(R.id.tv_hobby_otheradvert)
    TextView tvHobbyOtheradvert;
    @BindView(R.id.tv_address_otheradvert)
    TextView tvAddressOtheradvert;
    /*年龄选择器*/
    private ArrayList<Integer> ageStart;
    private ArrayList<Integer> ageEnd;
    private OptionsPickerView AgePickView;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_other);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("好友群发");
        TextView tv = setRightTitle("下一步");
        tv.setBackgroundResource(R.mipmap.rounded_rectangle);
        tv.setTextColor(getResources().getColor(R.color.ccp_green_alpha));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map==null){
                    map=new HashMap<>();
                }else{
                    map.clear();
                }
                //TODO 设置搜索条件

                RetrofitClient.getInstance().createApi().ReleaseAdviertOfOther(map)
                        .compose(RxUtils.<HttpArray<Integer>>io_main())
                        .subscribe(new BaseListObserver<Integer>(getActivity()) {
                            @Override
                            protected void onHandleSuccess(List<Integer> list) {
                                StringBuffer buffer=new StringBuffer();

                                if(list.size()==0){
                                    Toast.makeText(AdvertOtherActivity.this, "暂时搜索不到", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    buffer.append(list.get(i)+",");
                                }
                                buffer.replace(buffer.length()-1,buffer.length(),"");
                                Intent intent=new Intent(getActivity(),ReleaseAdvertActivity.class);
                                intent.putExtra("ids",buffer.toString());
                                startActivity(intent);
                            }
                        });


            }
        });
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.rl_age_otheradviert, R.id.rl_job_otheradviert, R.id.rl_hobby_otheradviert, R.id.rl_address_otheradviert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_age_otheradviert:
                if (AgePickView == null) {
                    ageStart = new ArrayList<>();
                    ageEnd = new ArrayList<>();
                    for (int i = 15; i <= 60; i++) {
                        ageStart.add(i);
                        ageEnd.add(i);
                    }
                    AgePickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            int i=ageStart.get(options1);
                            int j=ageEnd.get(options2);
                            if(j<=i){
                                Toast.makeText(AdvertOtherActivity.this, "截至年龄不能大于或等于起始年龄", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(i<j){
                                tvAgeOtheradvert.setText(i+"-"+j+"岁");
                            }
                        }
                    }).build();
                    AgePickView.setPicker(ageStart, ageEnd);
                    AgePickView.show();
                } else {
                    AgePickView.show();
                }
                break;
            case R.id.rl_job_otheradviert:

                break;
            case R.id.rl_hobby_otheradviert:

                break;
            case R.id.rl_address_otheradviert:

                break;
        }
    }

}
