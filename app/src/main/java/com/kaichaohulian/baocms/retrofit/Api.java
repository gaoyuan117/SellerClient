package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2017/3/27.
 *
 */

public interface Api {

//    @FormUrlEncoded
//    @POST("api/User/login")
//    Observable<HttpResult<LoginBean>> login(@FieldMap Map<String, String> map);
    @GET(Url.changePersonalInformation)
    Observable<HttpResult> ChangeInfo(@QueryMap Map<String,String> map);

    @GET(Url.verificatPassword)
    Observable<HttpResult> verificatPassword(@QueryMap Map<String,String> map);
}
