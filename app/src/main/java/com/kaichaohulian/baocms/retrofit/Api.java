package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;
import com.kaichaohulian.baocms.entity.QiNiuConfigEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2017/3/27.
 *
 */

public interface Api {

//    @FormUrlEncoded
//    @POST("api/User/login")
//    Observable<HttpResult<LoginBean>> login(@FieldMap Map<String, String> map);
//    @FormUrlEncoded
    //修改个人信息
    @GET(Url.changePersonalInformation)
    Observable<HttpResult> ChangeInfo(@QueryMap Map<String,String> map);

    @GET(Url.delete)
    Observable<HttpResult<CommonEntity>> deleteFriend(@QueryMap Map<String,String> map);
    //验证支付密码
    @GET(Url.verificatPassword)
    Observable<HttpResult> verificatPayword(@QueryMap Map<String,String> map);
    //获取客服列表
    @GET(Url.onlineService_list)
    Observable<HttpArray<OnlineServiceEntity>> getOnlineServicelist();
    //设置支付密码
    @GET(Url.setPayPassword)
    Observable<HttpResult> setPayWord(@QueryMap Map<String,String> map);
    //忘记登录密码
    @GET(Url.forgetPassword)
    Observable<HttpResult<CommonEntity>> ForgetPwd(@QueryMap Map<String,String> map);

    @GET(Url.GetMyadviertisement)
    Observable<HttpArray<AdviertisementEntity>> GetMyadviertisement(@QueryMap Map<String,String> map);

    @GET(Url.Getadviertisement)
    Observable<HttpArray<AdviertisementEntity>> Getadviertisement(@QueryMap Map<String,String> map);

//    @GET(Url.MyAlbum)
//    Observable<HttpArray<AdviertisementEntity>> GetMyAlbum(@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Observable<HttpArray<ContactFriendsEntity>> getFriend(@FieldMap Map<String,String> map);

    @GET(Url.GetadvertDetail)
    Observable<HttpArray<AdviertisementEntity>> GetDetailForAdvert(@QueryMap Map<String,String> map);

    @GET(Url.GetQiNiuConFig)
    Observable<HttpResult<QiNiuConfigEntity>> GetQiNiuConfig();

    @GET(Url.Sendadviertisement)
    Observable<HttpResult<CommonEntity>> ReleaseAdvert(@QueryMap Map<String,String> map);




}
