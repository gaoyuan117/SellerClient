package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;

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
    @POST(Url.changePersonalInformation)
    Observable<HttpResult> ChangeInfo(@FieldMap Map<String,Object> map);
}
