package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.AdversDetailBean;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.GreetBean;
import com.kaichaohulian.baocms.entity.HasGetAdverBean;
import com.kaichaohulian.baocms.entity.InviteInfoBean;
import com.kaichaohulian.baocms.entity.NearbyBean;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;
import com.kaichaohulian.baocms.entity.QiNiuConfigEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.entity.UserInfoBean;
import com.kaichaohulian.baocms.event.UserPhotoBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

    //删除好友
    @GET(Url.delete)
    Observable<HttpResult<CommonEntity>> deleteFriend(@QueryMap Map<String,String> map);

    //加入黑名单
    @GET("im/friend/defriend.do")
    Observable<HttpResult<CommonEntity>> addBlack(@QueryMap Map<String,String> map);

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

    /*********************************gy**********************************************/
    @GET("users/addNeedPay.do")
    Observable<HttpResult> getPayMoney(@QueryMap Map<String,String> map);

    //每隔5秒更新用户当前位置经纬度
    @GET("business/updateLocations.do")
    Observable<HttpResult<CommonEntity>> updateLocation(@QueryMap Map<String,String> map);
    //附近的人
    @GET("business/getNearUser.do")
    Observable<HttpArray<NearbyBean>> getNearUser(@QueryMap Map<String,String> map);

    //个人收到的广告
    @GET("adviertisement/getAdvert.do")
    Observable<HttpArray<HasGetAdverBean>> hasGetAdver(@QueryMap Map<String,String> map);

    //个人收到的广告详情
    @GET("adviertisement/getAdvertDetail.do")
    Observable<HttpResult<AdversDetailBean>> adverDetail(@QueryMap Map<String,String> map);

    //ID获取用户信息
    @GET("/api/users/getUserInfo.do")
    Observable<HttpResult<UserInfoBean>> loadUserInfo(@QueryMap Map<String,String> map);

    //手机号获取用户信息
    @GET("users/getByIdAndPhone.do")
    Observable<HttpResult<UserInfoBean>> loadUserInfoPhone(@QueryMap Map<String,String> map);

    //获取用户应约信息
    @GET("users/getOtherByPhone.do")
    Observable<HttpResult<InviteInfoBean>> loadInviteInfo(@QueryMap Map<String,String> map);

    //获取用户相册信息
    @GET("imageManager/getImages.do")
    Observable<HttpResult<UserPhotoBean>> userPhotoInfo(@QueryMap Map<String,String> map);

    //用户失去网络状态或者退出时候清除用户当前位置信息
    @GET("business/clearLocations.do")
    Observable<HttpResult<CommonEntity>> clearLocation(@Query("id") int id);

    //添加好友
    @GET("im/requests/friends/apply.do")
    Observable<HttpResult<CommonEntity>> addFriend(@QueryMap Map<String,Object> map);

    //通过钱添加好友
    @GET("requests/friends/addFirendByMoney.do")
    Observable<HttpResult<CommonEntity>> addFriendByMoney(@QueryMap Map<String,Object> map);

    //打招呼列表
    @GET("business/sayhello.do")
    Observable<HttpResult<GreetBean>> greetList(@QueryMap Map<String,String> map);

    //处理还有申请
    @GET("im/requests/friends/handle.do")
    Observable<HttpResult<CommonEntity>> handlerApplication(@QueryMap Map<String,Object> map);


}
