package com.kaichaohulian.baocms.widget;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.CityModel;
import com.kaichaohulian.baocms.entity.ProvinceModel;
import com.kaichaohulian.baocms.util.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * 地址选择
 * 项目名称:  DateSelector
 * 类名称:   DateSelectorWheelView
 * 版本:    v1.0
 */
public class AddressSelectorWheelView extends RelativeLayout implements OnWheelChangedListener {
    /**
     * Current value & label text color
     */
    private int VALUE_TEXT_COLOR = 0xFF0076ff;

    /**
     * Items text color
     */
    private int ITEMS_TEXT_COLOR = 0xFF5e5e5e;

    /**
     * Top and bottom shadows colors
     */
    private int[] SHADOWS_COLORS = new int[]{0x00000000,
            0x00000000, 0x00000000};

    private LinearLayout rlTitle;
    private TextView mAddressTitleTV;
    private TextView mSelectAddressTV;
    private LinearLayout mAddressLL;
    private LinearLayout mWheelLL;
    private WheelView mViewProvince;
    private WheelView mViewCity;

    public AddressSelectorWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public AddressSelectorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public AddressSelectorWheelView(Context context) {
        super(context);
        initLayout(context);
    }

    /***
     * 设置滚动标题
     *
     * @param title
     */
    public void setAddressTitle(String title, int color) {
        if (mAddressTitleTV != null) {
            mAddressTitleTV.setText(title);
            mAddressTitleTV.setTextColor(color);
        }
    }

    /****
     * 设置滚动栏标题是否显示
     *
     * @param flag
     */
    public void setRlTitleVis(boolean flag) {
        rlTitle.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    /***
     * 设置滚动的背景颜色
     *
     * @param bgColor
     */
    public void setWheellView(int bgColor) {
        mWheelLL.setBackgroundColor(bgColor);
    }

    /***
     * 设置列表区域是否显示
     *
     * @param flag
     */
    public void setAddressLLVis(boolean flag) {
        mAddressLL.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    /***
     * 设置item字体颜色
     *
     * @param itemsTextColor
     */
    public void setItemsTextColor(int itemsTextColor) {
        this.ITEMS_TEXT_COLOR = itemsTextColor;
    }

    /***
     * 设置当前选中的字体颜色
     *
     * @param currentTextColor
     */
    public void setCurrentTextColor(int currentTextColor) {
        this.VALUE_TEXT_COLOR = currentTextColor;
    }

    /***
     * 设置背景区域颜色
     *
     * @param SHADOWS_COLORS
     */
    public void setShadowsColors(int[] SHADOWS_COLORS) {
        this.SHADOWS_COLORS = SHADOWS_COLORS;
    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.dete_address_layout, this, true);
        rlTitle = (LinearLayout) findViewById(R.id.rl_address_title);
        mWheelLL = (LinearLayout) findViewById(R.id.ll_wheel_views);
        mAddressTitleTV = (TextView) findViewById(R.id.tv_address_subtitle);
        mSelectAddressTV = (TextView) findViewById(R.id.select_address_tv);
        mAddressLL = (LinearLayout) findViewById(R.id.address_ll);
        mSelectAddressTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddressLL.setVisibility(mAddressLL.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
        mViewProvince = (WheelView) findViewById(R.id.wv_province);
        mViewCity = (WheelView) findViewById(R.id.wv_city);

        setUpListener();
        setUpData();

        mViewProvince.setItemsTextColor(ITEMS_TEXT_COLOR);
        mViewProvince.setCurrentTextColor(VALUE_TEXT_COLOR);
        mViewProvince.setShadowsColors(SHADOWS_COLORS);

        mViewCity.setItemsTextColor(ITEMS_TEXT_COLOR);
        mViewCity.setCurrentTextColor(VALUE_TEXT_COLOR);
        mViewCity.setShadowsColors(SHADOWS_COLORS);

        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);

    }

    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;

    /***
     * 设置当前的省份
     *
     * @param province
     */
    public void setCurrentProviceName(String province, String city) {
        for (int i = 0; i < mProvinceDatas.length; i++) {
            if (province.equals(mProvinceDatas[i])) {
                mViewProvince.setAdapter(new StrericWheelAdapter(mProvinceDatas));
                mViewProvince.setCurrentItem(i);
                mViewProvince.finishScrolling();
                String[] cities = mCitisDatasMap.get(province);
                if (cities == null) {
                    cities = new String[]{""};
                }
                mViewCity.setAdapter(new StrericWheelAdapter(cities));
                for (int k = 0; k < cities.length; k++) {
                    if (city.equals(cities[k])) {
                        mViewCity.setCurrentItem(k);
                        break;
                    }
                }
            }
        }
    }

    /***
     * 初始化数据
     */
    protected void initProvinceDatas() {

        List<ProvinceModel> provinceList = null;
        AssetManager asset = getContext().getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                }
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setAdapter(new StrericWheelAdapter(mProvinceDatas));
        mViewProvince.setVisibleItems(5);
        mViewCity.setVisibleItems(5);
        mViewCity.setCurrentItem(1);
        mViewProvince.setCurrentItem(1);
        updateCities();
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
    }

    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setAdapter(new StrericWheelAdapter(cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        }
        mSelectAddressTV.setText(mCurrentProviceName + "," + mCurrentCityName);
    }

    /***
     * 获取当前选择的位置
     *
     * @return
     */
    public String getSelectAddress() {
        return mSelectAddressTV.getText().toString();
    }
}
