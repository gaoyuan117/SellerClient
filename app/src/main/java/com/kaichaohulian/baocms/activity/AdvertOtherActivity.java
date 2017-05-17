package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdverOtherBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    EditText tvJobOtheradvert;
    @BindView(R.id.tv_hobby_otheradvert)
    EditText tvHobbyOtheradvert;
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

    private String sex, minage, maxage, job, hobby, address;
    //选择器
    private OptionsPickerView addRessPickerView;
    private ArrayList<String> provinceList;//创建存放省份实体类的集合
    private ArrayList<String> cities;//创建存放城市名称集合
    private ArrayList<List<String>> citiesList;//创建存放城市名称集合的集合

    private ArrayList<String> areas;//创建存放区县名称的集合
    private ArrayList<List<String>> areasList;//创建存放区县名称集合的集合
    private ArrayList<List<List<String>>> areasListsList;//创建存放区县集合的集合的集合


    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_other);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        agestart = new ArrayList<>();
        ageend = new ArrayList<>();

        for (int i = 10; i < 70; i++) {
            agestart.add(i);
            ageend.add(agestart);
        }

        if (areasListsList == null) {
            provinceList = new ArrayList<>();//创建存放省份实体类的集合
            citiesList = new ArrayList<>();//创建存放城市名称集合的集合
            areasListsList = new ArrayList<>();//创建存放区县集合的集合的集合
            parseJson(getJson("city.json", this));
        }
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
                address = tvAddressOtheradvert.getText().toString();
                job = tvJobOtheradvert.getText().toString();
                hobby = tvHobbyOtheradvert.getText().toString();
                if (map == null) {
                    map = new HashMap<>();
                } else {
                    map.clear();
                }
                if (sex != null) {
                    map.put("sex", sex);
                }
                if (minage != null && maxage != null) {
                    map.put("ageStart", minage);
                    map.put("ageEnd", maxage);
                }
                if (!TextUtils.isEmpty(job)) {
                    map.put("job", job);
                }
                if (!TextUtils.isEmpty(hobby)) {
                    map.put("hobby", hobby);
                }
                if (address != null && !address.equals("地区")) {
                    map.put("address", address);
                }

                RetrofitClient.getInstance().createApi().ReleaseAdviertOfOther(map)
                        .compose(RxUtils.<HttpResult<AdverOtherBean>>io_main())
                        .subscribe(new BaseObjObserver<AdverOtherBean>(AdvertOtherActivity.this) {
                            @Override
                            protected void onHandleSuccess(AdverOtherBean adverOtherBean) {
                                List<Integer> list = adverOtherBean.getList();
                                if (list == null || list.size() == 0) {
                                    Toast.makeText(AdvertOtherActivity.this, "暂时搜索不到", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                StringBuffer buffer = new StringBuffer();

                                for (int i = 0; i < list.size(); i++) {
                                    buffer.append(list.get(i) + ",");
                                }
                                buffer.replace(buffer.length() - 1, buffer.length(), "");
                                Intent intent = new Intent(getActivity(), ReleaseAdvertActivity.class);
                                intent.putExtra("ids", buffer.toString());
                                intent.putExtra("size", adverOtherBean.getCount());
                                intent.putExtra("type", "2");
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
                                minage = agestart.get(options1) + "";
                                maxage = ageend.get(options1).get(options2) + "";
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

                break;
            case R.id.rl_hobby_otheradviert:

                break;
            case R.id.rl_address_otheradviert:
                if (addRessPickerView == null) {
                    addRessPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            String city = provinceList.get(options1);
                            String address;
                            //  如果是直辖市或者特别行政区只设置市和区/县
                            if ("北京市".equals(city) || "上海市".equals(city) || "天津市".equals(city) || "重庆市".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                                address = provinceList.get(options1)
                                        + " " + areasListsList.get(options1).get(options2).get(options3);
                            } else {
                                address = provinceList.get(options1)
                                        + " " + citiesList.get(options1).get(options2)
                                        + " " + areasListsList.get(options1).get(options2).get(options3);
                            }
                            tvAddressOtheradvert.setText(address);

                        }
                    }).build();
                    addRessPickerView.setSelectOptions(0, 0, 0);
                    addRessPickerView.setPicker(provinceList, citiesList, areasListsList);
                    addRessPickerView.show();
                } else {
                    addRessPickerView.show();
                }

                break;
        }
    }

    /**
     * @param json 解析json数据
     */
    private void parseJson(String json) {
        try {
            //得到一个数组类型的json对象
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {//对数组进行遍历得到每一个jsonobject对象
                JSONObject provinceObject = (JSONObject) jsonArray.get(i);
                String provinceName = provinceObject.optString("areaName");//得到省份的名字
                provinceList.add(provinceName);//向集合里面添加元素

                JSONArray cityArray = provinceObject.optJSONArray("cities");
                cities = new ArrayList<>();//创建存放城市名称集合
                areasList = new ArrayList<>();//创建存放区县名称的集合的集合
                for (int j = 0; j < cityArray.length(); j++) {//遍历每个省份集合下的城市列表
                    JSONObject cityObject = (JSONObject) cityArray.get(j);
                    String cityName = cityObject.getString("areaName");
                    cities.add(cityName);//向集合里面添加元素
                    JSONArray areaArray = cityObject.optJSONArray("counties");
                    areas = new ArrayList<>();//创建存放区县名称的集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getJSONObject(k).optString("areaName");
                        areas.add(areaName);
                    }
                    areasList.add(areas);
                }
                citiesList.add(cities);
                areasListsList.add(areasList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化三级联动的数据源

    /**
     * @param fileName 本地assets中的文件名
     * @param context  用来打开manager的context对象
     * @return 返回数据源中的数据
     */
    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @OnClick({R.id.men_sex_advertother, R.id.women_sex_advertother, R.id.else_sex_advertother})
    public void SelectSex(View view) {
        switch (view.getId()) {
            case R.id.men_sex_advertother:
                menSex.setBackgroundColor(getResources().getColor(R.color.blue));
                womenSex.setBackgroundColor(getResources().getColor(R.color.white));
                elseSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex = "0";
                break;
            case R.id.women_sex_advertother:
                womenSex.setBackgroundColor(getResources().getColor(R.color.blue));
                menSex.setBackgroundColor(getResources().getColor(R.color.white));
                elseSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex = "1";
                break;
            case R.id.else_sex_advertother:
                elseSex.setBackgroundColor(getResources().getColor(R.color.blue));
                womenSex.setBackgroundColor(getResources().getColor(R.color.white));
                menSex.setBackgroundColor(getResources().getColor(R.color.white));
                sex = null;
                break;
        }
    }
}
