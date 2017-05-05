package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2017/3/27.
 *
 */

public interface Api {

    @GET(Url.verificatPassword)
    Observable<HttpResult> verificatPassword(@QueryMap Map<String,String> map);
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


}
