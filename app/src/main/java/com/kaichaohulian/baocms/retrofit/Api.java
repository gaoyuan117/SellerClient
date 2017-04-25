package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.BaseEntity;
import com.kaichaohulian.baocms.http.HttpResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 2017/3/27.
 *
 */

public interface Api {

//    @FormUrlEncoded
//    @POST("api/User/login")
//    Observable<HttpResult<LoginBean>> login(@FieldMap Map<String, String> map);
    @FormUrlEncoded
    @POST("api/User/login")
    Observable<HttpResult<BaseEntity>> login(@FieldMap Map<String,String> map);
}
