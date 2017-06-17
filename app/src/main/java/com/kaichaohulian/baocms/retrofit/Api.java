package com.kaichaohulian.baocms.retrofit;

import com.kaichaohulian.baocms.entity.AblumEntity;
import com.kaichaohulian.baocms.entity.AboutBean;
import com.kaichaohulian.baocms.entity.AdverOtherBean;
import com.kaichaohulian.baocms.entity.AdversDetailBean;
import com.kaichaohulian.baocms.entity.AdvertDetailEntity;
import com.kaichaohulian.baocms.entity.AdvertParmEntity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.EarnestMoneyEntity;
import com.kaichaohulian.baocms.entity.GreetBean;
import com.kaichaohulian.baocms.entity.HasGetAdverBean;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.entity.InviteInfoBean;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;
import com.kaichaohulian.baocms.entity.InvitedBean;
import com.kaichaohulian.baocms.entity.LableBean;
import com.kaichaohulian.baocms.entity.MyInviteBean;
import com.kaichaohulian.baocms.entity.MyInviteEntity;
import com.kaichaohulian.baocms.entity.NearbyBean;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;
import com.kaichaohulian.baocms.entity.QiNiuConfigEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.entity.UserInfoBean;
import com.kaichaohulian.baocms.entity.VersionBean;
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
 */

public interface Api {

//    @FormUrlEncoded
//    @POST("api/User/login")
//    Observable<HttpResult<LoginBean>> login(@FieldMap Map<String, String> map);
//    @FormUrlEncoded

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
    Observable<HttpResult<CommonEntity>> ChangePayWord(@QueryMap Map<String, String> map);

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


    /**************
     * 广告相关*****
     **************/
    //获取支付金额
    @GET(Url.GetAdvertParm)
    Observable<HttpResult<AdvertParmEntity>> getAdvertParm();

    //好友群发
    @GET(Url.Sendadviertisement)
    Observable<HttpResult<CommonEntity>> ReleaseAdvert(@QueryMap Map<String, String> map);

    //获取我的广告
    @GET(Url.GetMyadviertisement)
    Observable<HttpResult<AdviertisementEntity>> GetMyadviertisement(@QueryMap Map<String, String> map);

    //获取广告列表
    @GET(Url.Getadviertisement)
    Observable<HttpResult<AdviertisementEntity>> Getadviertisement(@QueryMap Map<String, String> map);

    //删除广告
    @GET(Url.DeleteAdvert)
    Observable<HttpResult<CommonEntity>> DeleteAdvert(@Query("userId") long UserId, @Query("advertId") long AdvertId);

    //获取广告详情
    @GET(Url.GetadvertDetail)
    Observable<HttpResult<AdvertDetailEntity>> GetDetailForAdvert(@QueryMap Map<String, String> map);

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
    Observable<HttpResult<AdverOtherBean>> ReleaseAdviertOfOther(@QueryMap Map<String, String> map);

    //删除好友
    @GET(Url.delete)
    Observable<HttpResult<CommonEntity>> deleteFriend(@QueryMap Map<String, String> map);

    //加入黑名单
    @GET("im/friend/defriend.do")
    Observable<HttpResult<CommonEntity>> addBlack(@QueryMap Map<String, String> map);

    //验证支付密码
    @GET(Url.verificatPassword)
    Observable<HttpResult> verificatPayword(@QueryMap Map<String, String> map);


    //获取客服列表
    @GET(Url.onlineService_list)
    Observable<HttpArray<OnlineServiceEntity>> getOnlineServicelist();


    /****************
     * **邀请相关*****
     **************/
    //获取我发起的邀请
    @GET(Url.getMyInvite)
    Observable<HttpArray<MyInviteEntity>> getMyInvite(@Query("userId") long id, @Query("page") int page);

    @GET(Url.GetMyJoinInvite)
    Observable<HttpArray<MyInviteEntity>> GetMyJoinInvite(@Query("userId") long id, @Query("page") int page);


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
    @GET("business/getNearUser.do")
    Observable<HttpArray<NearbyBean>> getNearUser(@QueryMap Map<String, String> map);

    //个人收到的广告
    @GET("adviertisement/getAdvert.do")
    Observable<HttpResult<HasGetAdverBean>> hasGetAdver(@QueryMap Map<String, String> map);

    //个人收到的广告详情
    @GET("adviertisement/getAdvertDetail.do")
    Observable<HttpResult<AdversDetailBean>> adverDetail(@QueryMap Map<String, String> map);

    //ID获取用户信息
    @GET("/api/users/getUserInfo.do")
    Observable<HttpResult<UserInfoBean>> loadUserInfo(@QueryMap Map<String, String> map);

    //手机号获取用户信息
    @GET("users/getByIdAndPhone.do")
    Observable<HttpResult<UserInfoBean>> loadUserInfoPhone(@QueryMap Map<String, String> map);

    //获取用户应约信息
    @GET("users/getOtherByPhone.do")
    Observable<HttpResult<InviteInfoBean>> loadInviteInfo(@QueryMap Map<String, String> map);

    //获取用户相册信息
    @GET("imageManager/getImages.do")
    Observable<UserPhotoBean> userPhotoInfo(@QueryMap Map<String, String> map);

    //用户失去网络状态或者退出时候清除用户当前位置信息
    @GET("business/clearLocations.do")
    Observable<HttpResult<CommonEntity>> clearLocation(@Query("id") int id);

    //添加好友
    @GET("im/requests/friends/apply.do")
    Observable<HttpResult<CommonEntity>> addFriend(@QueryMap Map<String, Object> map);

    //通过钱添加好友
    @GET("im/requests/friends/addFirendByMoney.do")
    Observable<HttpResult<CommonEntity>> addFriendByMoney(@QueryMap Map<String, Object> map);

    //打招呼列表
    @GET("business/sayhello.do")
    Observable<HttpResult<GreetBean>> greetList(@QueryMap Map<String, String> map);

    //处理还有申请
    @GET("im/requests/friends/handle.do")
    Observable<HttpResult<CommonEntity>> handlerApplication(@QueryMap Map<String, Object> map);

    //邀请信息  我邀请的
    @GET("invite/getMyInviteInfo.do")
    Observable<HttpArray<MyInviteBean>> getMyDiscoverInvite(@QueryMap Map<String, Object> map);

    //邀请信息  邀请我的
    @GET("invite/getBeInviteInfo.do")
    Observable<HttpArray<InvitedBean>> getDiscoverInvited(@QueryMap Map<String, Object> map);

    //邀请 拒绝 接受
    @GET("invite/acceptAndRefuse.do")
    Observable<HttpResult<CommonEntity>> acceptOrRefuse(@QueryMap Map<String, Object> map);

    //发起邀请
    @GET(Url.SendInvite)
    Observable<HttpResult<CommonEntity>> SendInvite(@QueryMap Map<String, String> map);

    //邀请详情(发布人)
    @GET(Url.GetInviteDetailForHost)
    Observable<HttpResult<InviteDetailEntity>> GetInviteDetailForHost(@Query("userId") String UserId, @Query("inviteId") String inviteId);

    //邀请详情(受邀人)
    @GET(Url.GetInviteDetailForReciver)
    Observable<HttpResult<InviteReciverEntity>> GetInviteDetailForReciver(@Query("userId") String UserId, @Query("inviteId") String inviteId);

    //见面确认
    @GET(Url.GetSureMeet)
    Observable<HttpResult<CommonEntity>> GetSureMeet(@Query("userId") String UserId, @Query("inviteId") String inviteId);

    //清空打招呼列表
    @GET("business/delSayhello.do")
    Observable<HttpResult<CommonEntity>> clearGreet(@Query("userId") String UserId);

    //根据手机号获取用户信息
    @GET(Url.dependPhoneGetUserInfo)
    Observable<HttpResult> getUserInfo(@Query("phoneNumber") String phoneNum);

    @GET(Url.findAll)
    Observable<HttpResult<AblumEntity>> GetUserPhoto(@Query("id") long id, @Query("page") String page);


    ////////////////////////////////////////////////////////////////////////////


    @GET("lables/findAll.do")
    Observable<HttpArray<LableBean>> loadLable();

    @GET("invite/userLable.do")
    Observable<HttpResult<CommonEntity>> evaluate(@QueryMap Map<String, Object> map);

    //余额支付
    @FormUrlEncoded
    @POST("balance/gotopay.do")
    Observable<HttpResult<CommonEntity>> yuePay(@FieldMap Map<String, Object> map);

    //设置备注
    @FormUrlEncoded
    @POST("lables2/setUplable.do")
    Observable<HttpResult<CommonEntity>> setRemark(@FieldMap Map<String, Object> map);

    //检查版本更新
    @GET("users/getVersion.do")
    Observable<HttpResult<VersionBean>> checkVersion();

    //关于
    @GET("baseset/about.do")
    Observable<HttpResult<AboutBean>> getAbout();

    //删除邀请
    @GET("invite/delInvite.do")
    Observable<HttpResult<CommonEntity>> delInvite(@Query("userId") int id, @Query("inviteId") String inviteId);

    //获取用二维码
    @GET("qr/userQRCode.do")
    Observable<HttpResult<CommonEntity>> userQRCode(@Query("userId") int id);

    //是否有新的消息
    @GET("users/getNewInfo.do")
    Observable<HttpResult<CommonEntity>> newInfo(@Query("userId") int id);

}
