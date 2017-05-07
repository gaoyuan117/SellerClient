package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.EarnestMoneyEntity;
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
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by admin on 2017/3/27.
 */

public interface Api {
    /************
     * 密码相关***
     **********/
    //验证支付密码
    @GET(Url.verificatPassword)
    Observable<HttpResult> verificatPassword(@QueryMap Map<String, String> map);
    //修改登录密码
    @GET(Url.ChangePassWord)
    Observable<HttpResult<CommonEntity>> ChangePassWord(@QueryMap Map<String, String> map);
    //忘记登录密码
    @GET(Url.forgetPassword)
    Observable<HttpResult<CommonEntity>> ForgetPwd(@QueryMap Map<String, String> map);
    //设置支付密码
    @GET(Url.setPayPassword)
    Observable<HttpResult<CommonEntity>> setPayWord(@QueryMap Map<String, String> map);
    //忘记支付密码
    @GET(Url.ForGetPayWord)
    Observable<HttpResult<CommonEntity>> ForGetPayWord(@Query("phoneNumber") String PhoneNum,
                                                       @Query("password") String PayWord,
                                                       @Query("code") String Code
                                                           );
    //修改支付密码
    @GET(Url.ChangePayWord)
    Observable<HttpResult<CommonEntity>> ChangePayWord(@QueryMap Map<String,String> map);
    //发送短信验证码
    @GET(Url.forgetPassword)
    Observable<HttpResult<CommonEntity>> GetCodeForPhone(@Query("phoneNumber") String phoneNum);

    /*************
     * 用户信息相关**
     **************/
    //修改个人信息
    @GET(Url.changePersonalInformation)
    Observable<HttpResult> ChangeInfo(@QueryMap Map<String, String> map);

    //设置被加好友需要的金额
    @GET(Url.SetNeedPay)
    Observable<HttpResult<CommonEntity>> SetNeedPay(@QueryMap Map<String, String> map);

    //获取诚意金 被加好友 被邀请 赴约 爽约 信息
    @GET(Url.GetSomeInfoForFriend)
    Observable<HttpResult<EarnestMoneyEntity>> Getfaith(@Query("phoneNumber") long phoneNum);


    //删除好友
    @GET(Url.delete)
    Observable<HttpResult<CommonEntity>> deleteFriend(@QueryMap Map<String, String> map);

    //获取客服列表
    @GET(Url.onlineService_list)
    Observable<HttpArray<OnlineServiceEntity>> getOnlineServicelist();


    /**************
     * 广告相关*****
     **************/
    //好友群发
    @GET(Url.Sendadviertisement)
    Observable<HttpResult<CommonEntity>> ReleaseAdvert(@QueryMap Map<String, String> map);

    //获取我的广告
    @GET(Url.GetMyadviertisement)
    Observable<HttpArray<AdviertisementEntity>> GetMyadviertisement(@QueryMap Map<String, String> map);

    //获取广告列表
    @GET(Url.Getadviertisement)
    Observable<HttpArray<AdviertisementEntity>> Getadviertisement(@QueryMap Map<String, String> map);

    //删除广告
    @GET(Url.DeleteAdvert)
    Observable<HttpResult<CommonEntity>> DeleteAdvert(@Query("userId") long UserId, @Query("advertId") long AdvertId);

    //获取广告详情
    @GET(Url.GetadvertDetail)
    Observable<HttpArray<AdviertisementEntity>> GetDetailForAdvert(@QueryMap Map<String, String> map);

    //其他群发
    /*字段名     数据类型 是否必须
    * Sex       long    no
    * ageStart  long    no
    * ageEnd    long    no
    * job       String  no
    * hobby     String  no
    * address   String  no
    * */
    @GET(Url.SendAdviertOfOther)
    Observable<HttpArray<Integer>> ReleaseAdviertOfOther(@QueryMap Map<String, String> map);


    /**************
     * 钱包相关*****
     * ***********/



//    @GET(Url.MyAlbum)
//    Observable<HttpArray<AdviertisementEntity>> GetMyAlbum(@QueryMap Map<String,String> map);

    @FormUrlEncoded
    @POST
    Observable<HttpArray<ContactFriendsEntity>> getFriend(@FieldMap Map<String, String> map);


    @GET(Url.GetQiNiuConFig)
    Observable<HttpResult<QiNiuConfigEntity>> GetQiNiuConfig();


    /*********************************
     * gy
     **********************************************/
    @GET("users/addNeedPay.do")
    Observable<HttpResult> getPayMoney(@QueryMap Map<String, String> map);

    //每隔5秒更新用户当前位置经纬度
    @GET("business/updateLocations.do")
    Observable<HttpResult<CommonEntity>> updateLocation(@QueryMap Map<String, String> map);
    //附近的人


}
