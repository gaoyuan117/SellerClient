package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.BusinessAllEntity;
import com.kaichaohulian.baocms.entity.CatesEtity;
import com.kaichaohulian.baocms.entity.CityEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class BusinessApplyThressActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 200;
    private int indexType = 0;
    private List<String> photoUrl;

    private Intent intent;

    private ImageView apply_img_license;
    private ImageView apply_img_logo;
    private ImageView apply_img_corperate_image;

    private Spinner apply_ShopArea, apply_classification, apply_address1, apply_address2;

    private Button btn_next;

    private int cateId, areaId = 0, cityId, businessId;

    private String[] photoArray = new String[3];

    @Override
    public void setContent() {
        setContentView(R.layout.apply_business_in_3);
    }

    @Override
    public void initData() {
        intent = getIntent();
    }

    @Override
    public void initView() {
        setCenterTitle("申请商家入驻");

        apply_img_license = getId(R.id.apply_img_license);
        apply_img_logo = getId(R.id.apply_img_logo);
        apply_img_corperate_image = getId(R.id.apply_img_corperate_image);
        apply_ShopArea = getId(R.id.apply_ShopArea);
        apply_classification = getId(R.id.apply_classification);
        apply_address1 = getId(R.id.apply_address1);
        apply_address2 = getId(R.id.apply_address2);
        btn_next = getId(R.id.btn_next);
    }

    @Override
    public void initEvent() {
        apply_img_license.setOnClickListener(this);
        apply_img_logo.setOnClickListener(this);
        apply_img_corperate_image.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        business_getAll();
        cates_getAll();
        area_getCity();


        apply_ShopArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                businessId = business_getAllList.get(position).getBusinessId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        apply_classification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cateId = cates_getAllList.get(position).getCateId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        apply_address1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityId = area_getCityList.get(position).getCityId();
                area_getAreaByCityid(area_getCityList.get(position).getCityId() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        apply_address2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                areaId = users_userCashList.get(position).getAreaId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_img_license:
                indexType = 0;
                photo();
                break;
            case R.id.apply_img_logo:
                indexType = 1;
                photo();
                break;
            case R.id.apply_img_corperate_image:
                indexType = 2;
                photo();
                break;
            case R.id.btn_next:
                InServer();
                break;
        }
    }

    public void photo() {
        PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (data != null) {
                        photoUrl = new ArrayList<String>();
                        photoUrl = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        switch (indexType) {
                            case 0:
                                photoArray[0] = photoUrl.get(0);
                                Glide.with(getActivity()).load(photoUrl.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(apply_img_license);
                                break;
                            case 1:
                                photoArray[1] = photoUrl.get(0);
                                Glide.with(getActivity()).load(photoUrl.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(apply_img_logo);
                                break;
                            case 2:
                                photoArray[2] = photoUrl.get(0);
                                Glide.with(getActivity()).load(photoUrl.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).into(apply_img_corperate_image);
                                break;
                        }
                    }
                    break;
            }
        }
    }

    private UploadManager uploadManager;

    private void InServer() {
        ShowDialog.showDialog(getActivity(), "正在提交...", false, null);
        JSONArray = new JSONArray();
        for (int i = 0; i < photoArray.length; i++) {
            if (photoArray[i] == null || photoArray[i].equals("")) {
                showToastMsg("请上传相关图片");
                ShowDialog.dissmiss();
                return;
            }
            File mFile = new File(photoArray[i]);
            if (mFile.exists()) {
                upload(getToken(), mFile, i);
            } else {
                ShowDialog.dissmiss();
                showToastMsg("获取图片异常");
            }
        }
    }


    private JSONArray JSONArray;

    private void upload(String uploadToken, File uploadFile, final int i) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                                JSONArray.put(url);
                                if (JSONArray.length() == 3) {
                                    addShopping();
                                }
                            } catch (Exception e) {
                                ShowDialog.dissmiss();
                                showToastMsg("上传图片失败");
                            }
                        } else {
                            ShowDialog.dissmiss();
                            showToastMsg("上传图片失败");
                        }
                    }
                }, null);
    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

    public void addShopping() throws JSONException {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("shopName", intent.getStringExtra("shopName"));  // 店铺名称
        params.put("rate", intent.getStringExtra("techIncome"));      // 技术服务费用
        params.put("address", intent.getStringExtra("addr"));   // 详细地址
        params.put("telPhone", intent.getStringExtra("contact"));  // 联系方式
        params.put("userName", intent.getStringExtra("phone"));   // 店铺联系人
        params.put("cateId", cateId);   // 分类Id
        params.put("areaId", areaId);   // 省份id
        params.put("cityId", cityId);   // 城市Id
        params.put("businessId", businessId);   // 所属商圈
        params.put("logo", JSONArray.getString(0));   // Logo
        params.put("photo", JSONArray.getString(1));   // 店铺形象照片
        params.put("buinessPhoto", JSONArray.getString(2));   // 营业执照图片
//        params.put("isPei", "");   // 是否自主配送	传1不选择不用传递
        params.put("deliverTime", "30");   // 等待配送时间	10位时间戳（PHP服务器要求的格式）
        HttpUtil.post(Url.business_merchantCheckIn, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("申请入驻商户：", response.toString());
                    if (response.getInt("code") == 0) {
                        BusinessApplyOneActivity.BusinessApplyOneActivity.finish();
                        BusinessApplyTwoActivity.BusinessApplyTwoActivity.finish();
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("申请入驻商户失败");
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

    private int business_getAll_page = 0;
    private List<BusinessAllEntity> business_getAllList = new ArrayList<>();

    private void business_getAll() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", business_getAll_page);
        params.put("year", "2016-01");
        HttpUtil.post(Url.business_getAll, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取商圈信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            BusinessAllEntity BusinessAllEntity = new BusinessAllEntity();
                            BusinessAllEntity.setAreaId(json.getInt("areaId"));
                            BusinessAllEntity.setBusinessId(json.getInt("businessId"));
                            BusinessAllEntity.setBusinessName(json.getString("businessName"));
                            BusinessAllEntity.setHot(json.getBoolean("isHot"));
                            BusinessAllEntity.setOrderby(json.getInt("orderby"));
                            business_getAllList.add(BusinessAllEntity);
                        }
                        businessAllAdapter businessAllAdapter = new businessAllAdapter();
                        apply_ShopArea.setAdapter(businessAllAdapter);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("商圈信息解析失败");
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

    public class businessAllAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return business_getAllList.size();
        }

        @Override
        public BusinessAllEntity getItem(int position) {
            return business_getAllList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(getItem(position).getBusinessName());
            return convertView;
        }
    }


    private List<CatesEtity> cates_getAllList = new ArrayList<>();

    private void cates_getAll() {
        RequestParams params = new RequestParams();
        HttpUtil.post(Url.cates_getAll, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取分类信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            CatesEtity CatesEtity = new CatesEtity();
                            CatesEtity.setCateId(json.getInt("cateId"));
                            CatesEtity.setCateName(json.getString("cateName"));
                            CatesEtity.setParentId(json.getInt("parentId"));
                            CatesEtity.setHot(json.getBoolean("isHot"));
                            CatesEtity.setOrderby(json.getInt("orderby"));
                            CatesEtity.setTitle(json.getString("title"));
                            CatesEtity.setD1(json.getString("d1"));
                            CatesEtity.setD2(json.getString("d2"));
                            CatesEtity.setD3(json.getString("d3"));
                            cates_getAllList.add(CatesEtity);
                        }
                        catesAllAdapter catesAllAdapter = new catesAllAdapter();
                        apply_classification.setAdapter(catesAllAdapter);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("分类信息解析失败");
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

    public class catesAllAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cates_getAllList.size();
        }

        @Override
        public CatesEtity getItem(int position) {
            return cates_getAllList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(getItem(position).getCateName());
            return convertView;
        }
    }

    private List<CityEntity> area_getCityList = new ArrayList<>();

    private void area_getCity() {
        RequestParams params = new RequestParams();
        HttpUtil.post(Url.area_getCity, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取城市信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            CityEntity CityEntity = new CityEntity();
                            CityEntity.setCityId(json.getInt("cityId"));
                            CityEntity.setFirstLetter(json.getString("firstLetter"));
                            CityEntity.setLat(json.getString("lat"));
                            CityEntity.setLng(json.getString("lng"));
                            CityEntity.setOpen(json.getBoolean("isOpen"));
                            CityEntity.setOrderby(json.getInt("orderby"));
                            CityEntity.setPinyin(json.getString("pinyin"));
                            CityEntity.setTheme(json.getString("theme"));
                            CityEntity.setName(json.getString("name"));
                            area_getCityList.add(CityEntity);
                        }
                        areaCityAdapter areaCityAdapter = new areaCityAdapter();
                        apply_address1.setAdapter(areaCityAdapter);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("城市信息解析失败");
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

    public class areaCityAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return area_getCityList.size();
        }

        @Override
        public CityEntity getItem(int position) {
            return area_getCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(getItem(position).getName());
            return convertView;
        }
    }


    private List<CityEntity> users_userCashList = new ArrayList<>();

    private void area_getAreaByCityid(String cityId) {
        RequestParams params = new RequestParams();
        params.put("cityId", cityId);
        HttpUtil.post(Url.area_getAreaByCityid, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("根据ID获取城市信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        users_userCashList.clear();
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            CityEntity CityEntity = new CityEntity();
                            CityEntity.setCityId(json.getInt("cityId"));
                            CityEntity.setOrderby(json.getInt("orderby"));
                            CityEntity.setAreaId(json.getInt("areaId"));
                            CityEntity.setAreaName(json.getString("areaName"));
                            users_userCashList.add(CityEntity);
                        }
                        apply_address2Adapter apply_address2Adapter = null;
                        if (apply_address2Adapter != null)
                            apply_address2Adapter.notifyDataSetChanged();
                        else
                            apply_address2Adapter = new apply_address2Adapter();
                        apply_address2.setAdapter(apply_address2Adapter);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("根据ID城市信息解析失败");
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

    public class apply_address2Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return users_userCashList.size();
        }

        @Override
        public CityEntity getItem(int position) {
            return users_userCashList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(getItem(position).getAreaName());
            return convertView;
        }
    }

}
