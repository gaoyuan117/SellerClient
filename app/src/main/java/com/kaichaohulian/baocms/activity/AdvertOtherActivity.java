package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
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

    @BindView(R.id.men_sex_advertother)
    RelativeLayout menSex;
    @BindView(R.id.women_sex_advertother)
    RelativeLayout womenSex;
    @BindView(R.id.else_sex_advertother)
    TextView elseSex;
    /*年龄选择器*/
    private ArrayList<Integer> agestart;
    private ArrayList<List<Integer>> ageend;
    private OptionsPickerView AgePickView;

    private String sex,minage,maxage,job,hobby,address;
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
                if (map == null) {
                    map = new HashMap<>();
                } else {
                    map.clear();
                }
                if(sex!=null){
                    map.put("sex",sex);
                }
                if(minage!=null&&maxage!=null){
                    map.put("ageStart",minage);
                    map.put("ageEnd",maxage);
                }
                if(job!=null){
                    map.put("job",job);
                }
                if(hobby!=null){
                    map.put("hobby",hobby);
                }
                if(address!=null){
                    map.put("address",address);
                }
                RetrofitClient.getInstance().createApi().ReleaseAdviertOfOther(map)
                        .compose(RxUtils.<HttpArray<Integer>>io_main())
                        .subscribe(new BaseListObserver<Integer>(getActivity()) {
                            @Override
                            protected void onHandleSuccess(List<Integer> list) {
                                StringBuffer buffer = new StringBuffer();

                                if (list.size() == 0) {
                                    Toast.makeText(AdvertOtherActivity.this, "暂时搜索不到", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                for (int i = 0; i < list.size(); i++) {
                                    buffer.append(list.get(i) + ",");
                                }
                                buffer.replace(buffer.length() - 1, buffer.length(), "");
                                Intent intent = new Intent(getActivity(), ReleaseAdvertActivity.class);
                                intent.putExtra("ids", buffer.toString());
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

                    AgePickView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {

                            if (agestart.get(options1) < ageend.get(options1).get(options2)) {
                                tvAgeOtheradvert.setText(agestart.get(options1) + "-" + ageend.get(options1).get(options2) + "岁");
                                minage=agestart.get(options1)+"";
                                maxage=ageend.get(options1).get(options2)+"";
                            } else {
                                Toast.makeText(AdvertOtherActivity.this, "起始年龄不能大于或等于截止年龄", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).build();
                    AgePickView.setPicker(agestart, ageend);
                    AgePickView.show();
                } else {
                    AgePickView.show();
                }
                break;
            case R.id.rl_job_otheradviert:
                Toast.makeText(this, "暂未开发此功能", Toast.LENGTH_SHORT).show();

                break;
            case R.id.rl_hobby_otheradviert:
                Toast.makeText(this, "暂未开发此功能", Toast.LENGTH_SHORT).show();

                break;
            case R.id.rl_address_otheradviert:
                Toast.makeText(this, "暂未开发此功能", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    @OnClick({R.id.men_sex_advertother, R.id.women_sex_advertother, R.id.else_sex_advertother})
    public void SelectSex(View view) {
        switch (view.getId()) {
            case R.id.men_sex_advertother:
                menSex.setBackgroundColor(getResources().getColor(R.color.blue));
                womenSex.setBackgroundColor(getResources().getColor(R.color.white));
                elseSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex="0";
                break;
            case R.id.women_sex_advertother:
                womenSex.setBackgroundColor(getResources().getColor(R.color.blue));
                menSex.setBackgroundColor(getResources().getColor(R.color.white));
                elseSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex="1";
                break;
            case R.id.else_sex_advertother:
                elseSex.setBackgroundColor(getResources().getColor(R.color.blue));
                womenSex.setBackgroundColor(getResources().getColor(R.color.white));
                menSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex=null;
                break;
        }
    }
}
