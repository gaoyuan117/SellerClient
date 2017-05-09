package com.kaichaohulian.baocms.http;

/**
 * 服务器url
 * Created by ljl on 2016/12/11.
 */
public class Url {
    public static final String BASE_URL = "http://www.52yeli.com/api/"; // 服务器地址

    public static final String WX_APP_ID = "wxe2c54efaeeae4ccb";
    public static final String WX_APP_SECRET = "902f4bebd104ed977fee542a10377f2e";
    //    public static final String WX_PARTNER_ID = "1289850701"; // 商户号
    public static final String WX_PARTNER_ID = "1433908302"; // 商户号
    public static final String WX_APP_KEY = "009aa9774b5cf730a880956fe6caab23"; // 微信商户key

    /**
     * 微信接口
     */
    public static final String WX_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token"; // 获取token
    public static final String WX_USERINFO = "https://api.weixin.qq.com/sns/userinfo";// 获取个人信息
    public static final String WX_SCDD = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 生成预支付订单
    public static final String WE_CHAT_LOGIN = BASE_URL + "/api/Login/wechat_login";// 微信登录

    public static final String PIC_ROOT = "http://www.maijia01.com/";

    /***********
     * 用户信息
     */
    //获取诚意金 被加好友 被邀请 赴约 爽约 信息
    public static final String GetSomeInfoForFriend = BASE_URL + "users/getOtherByPhone.do";
    //修改个人信息
    public static final String changePersonalInformation = BASE_URL + "users/updateUser.do";
    //根据手机号获取用户信息
    public static final String dependPhoneGetUserInfo = BASE_URL + "users/byPhoneNumber.do";
    //根据用户id获取用户信息
    public static final String dependIDGetUserInfo = BASE_URL + "users/getUserInfo.do";
    //删除银行卡
    public static final String deleteCard = BASE_URL + "users/banks/deleteCard.do";


    /**********
     * 密码相关
     */
    //修改登录密码
    public static final String ChangePassWord = BASE_URL + "users/editPassword.do";
    //设置加好友所需金额
    public static final String SetNeedPay = BASE_URL + "users/addNeedPay.do";
    //忘记密码
    public static final String forgetPassword = BASE_URL + "users/forgetPassword.do";
    //设置支付密码
    public static final String setPayPassword = BASE_URL + "users/paypassword.do";
    //验证支付密码
    public static final String verificatPassword = BASE_URL + "users/verificationpassword.do";
    //忘记支付密码
    public static final String ForGetPayWord = BASE_URL + "users/forgetPayPassword.do";
    //修改支付密码
    public static final String ChangePayWord = BASE_URL + "users/editPayPassword.do";
    //发送验证码
    public static final String sendMessage = BASE_URL + "users/sendMessage.do";
    //注册账号
    public static final String signUp = BASE_URL + "users/signUp.do";
    //登录
    public static final String signIn = BASE_URL + "users/signIn.do";

    /*********
     * 广告相关
     */
    //好友群发
    public static final String Sendadviertisement = BASE_URL + "adviertisement/release.do";
    //我发送的广告
    public static final String GetMyadviertisement = BASE_URL + "adviertisement/getMyAdvert.do";
    //用户个人收到的广告
    public static final String Getadviertisement = BASE_URL + "adviertisement/getAdvert.do";
    //广告详情
    public static final String GetadvertDetail = BASE_URL + "adviertisement/getAdvertDetail.do";
    //删除广告
    public static final String DeleteAdvert = BASE_URL + "adviertisement/ delAdvert.do";
    //其他群发
    public static final String SendAdviertOfOther = BASE_URL + "adviertisement/getUser.do";


    /****************
     * **邀请相关*****
     **************/
    //获取我发起的邀请
    public static final String getMyInvite=BASE_URL+"invite/getMyInvite.do";
    //获取我参与的邀请
    public static final String GetMyJoinInvite=BASE_URL+"invite/getInvite.do";


    //获取客服列表
    public static final String onlineService_list = BASE_URL + "customerService/searchAll.do";
    //获取城市列表
    public static final String GetCityList = BASE_URL + "area/getCity.do";
    //获取七牛配置
    public static final String GetQiNiuConFig = BASE_URL + "qiniu/getQiniu.do";


    /************
     * 相册相关
     */
    // 对朋友圈进行评论和回复
    public static final String evaulate = BASE_URL + "circleFriends/evaulate.do";
    // 对朋友圈点赞或取消点赞
    public static final String isLike = BASE_URL + "circleFriends/isLike.do";
    // 朋友圈信息
    public static final String findAll = BASE_URL + "circleFriends/findAll.do";
    // 更换朋友圈背景图
    public static final String updateBack = BASE_URL + "circleFriends/updateBack.do";
    // 相册
    public static final String MyAlbum = BASE_URL + "imageManager/getImages.do";
    // 发布朋友圈
    public static final String release = BASE_URL + "circleFriends/release.do";


    //钱包
    // 获取已绑定银行卡
    public static final String getBindCard = BASE_URL + "users/banks/getbindCard.do";


    // 获取我的商家和好友商家
    public static final String business_all = BASE_URL + "business/all.do";
    // 获取好友列表
    public static final String getFriends = BASE_URL + "im/friend/getFriends.do";
    // 搜索好友（添加好友时候用到）
    public static final String addfriend_search = BASE_URL + "im/friend/addfriend/search.do";

    // 搜索好友（通讯录中用到）
    public static final String myfriends_search = BASE_URL + "im/friend/myfriends/search.do";

    // 添加好友
    public static final String friends_apply = BASE_URL + "im/requests/friends/apply.do";

    // 投诉
    public static final String complaint_apply = BASE_URL + "faceBack/complaint.do";

    // 处理好友申请
    public static final String friends_handle = BASE_URL + "im/requests/friends/handle.do";

    // 查询所有的请求（包括我发送的和我待处理的请求）
    public static final String requests_all = BASE_URL + "im/requests/all.do";

    // 创建群聊
    public static final String groups_friends_add = BASE_URL + "im/groups/add.do";
    //群聊添加人
    public static final String groups_friends_invites = BASE_URL + "im/groups/invites.do";
    //群聊删除人
    public static final String groups_friends_removeMembers = BASE_URL + "im/groups/removeMembers.do";
    // 获取我加入和我创建的群
    public static final String groups_all = BASE_URL + "im/groups/all.do";

    // 面对面建群
    public static final String GetUsersByNumber = BASE_URL + "im/groups/getUsersByNumber.do";

    //绑定银行卡
    public static final String bindCard = BASE_URL + "users/banks/bindCard.do";

    //手机充值和系统充值创建订单
    public static final String phoneAndSystemRecharge = BASE_URL + "orders/createOrders.do";

    // 充值
    public static final String recharge = BASE_URL + "orders/gotopay.do";


    // 转账
    public static final String transfer = BASE_URL + "users/banks/transferAccounts.do";
    // 转账
    public static final String transferSure = BASE_URL + "users/banks/transferSure.do";

    // 根据用户id和状态获取相应的充值记录
    public static final String getRecharge = BASE_URL + "users/banks/getRecharge.do";

    // 提现记录
    public static final String withdrawalsById = BASE_URL + "users/banks/withdrawalsById.do";

    // 零钱充值
    public static final String gotopay = BASE_URL + "changePay/gotopay.do";

    // 保存收获地址
    public static final String saveAddress = BASE_URL + "users/receiptAdress/save.do";

    // 获取收获地址
    public static final String getAddress = BASE_URL + "users/receiptAdress/get.do";

    // 修改收货地址
    public static final String changeAddress = BASE_URL + "users/receiptAdress/update.do";

    // 保存收藏
    public static final String collection = BASE_URL + "imageManager/saveCollections.do";

    // 获取用户收藏
    public static final String obtainCollection = BASE_URL + "imageManager/getCollections.do";

    // 附件的人
    public static final String nearbyPeople = BASE_URL + "business/getNearbuiness.do";

    // 附件打招呼的人
    public static final String sayhello = BASE_URL + "business/sayhello.do";

    // 摇一摇(人)
    public static final String shakeOnePeople = BASE_URL + "business/shnkePeople.do";

    // 摇一摇(商家)
//    public static final String shakeOneBusiness = BASE_URL + "business/shnkeStore.do";
    public static final String shakeOneBusiness = BASE_URL + "business/shnkeStroe.do";

    // 获取所有标签
    public static final String All_LABEL = BASE_URL + "lables/findAll.do";

    //获取标签详情
    public static final String LABEL_DETAIL = BASE_URL + "lables/findLableDetails.do";

    //创建标签
    public static final String CREATE_LABEL = BASE_URL + "lables/createlable.do";

    //标签中批量添加成员
    public static final String ADD_LABEL = BASE_URL + "lables/addlable.do";

    // 支付宝充值
    public static final String alipay_gotopay = BASE_URL + "alipay/gotopay.do";

    // 提现
    public static final String users_banks_withdrawals = BASE_URL + "users/banks/withdrawals.do";

    // 微信充值
    public static final String weixin_gotopay = BASE_URL + "weixin/gotopay.do";

    // 获取商家信息
    public static final String business_getByShopId = BASE_URL + "business/getByShopId.do";

    public static final String business_updateShop = BASE_URL + "business/updateShop.do";

    // 获取商家销售统计
    public static final String business_getSalesStatusByShopId = BASE_URL + "orders/salesStatus.do";

    // 获取商家扫描订单
    public static final String business_canCodeOrders = BASE_URL + "orders/canCodeOrders.do";

    // 雷达加好友
    public static final String users_radarUsers = BASE_URL + "users/radarUsers.do";

    //群聊详情
    public static final String detailByChatId = BASE_URL + "im/groups/detailByChatId.do";
    //解散群聊
    public static final String delGroup = BASE_URL + "im/groups/dismiss.do";

    public static final String quitGroup = BASE_URL + "im/groups/quit.do";
    //修改群聊详情
    public static final String updateName = BASE_URL + "im/groups/updateName.do";
    //发布公告
    public static final String notice = BASE_URL + "im/groups/notice.do";

    // 商户入驻
    public static final String business_merchantCheckIn = BASE_URL + "business/merchantCheckIn.do";

    // 获取商圈信息
    public static final String business_getAll = BASE_URL + "buiness/getAll.do";

    // 获取所有的分类
    public static final String cates_getAll = BASE_URL + "cates/getAll.do";

    // 获取所有的城市
    public static final String area_getCity = BASE_URL + "area/getCity.do";

    // 根据城市id获取所有的地址
    public static final String area_getAreaByCityid = BASE_URL + "area/getAreaByCityid.do";
    public static final String ZHUAN_RANG_QUN_ZU = BASE_URL + "im/groups/transfer.do";

    // 用户发出的红包记录
    public static final String moneyreds_getUserRedOutList = BASE_URL + "im/moneyreds/getUserRedOutList.do";

    // 创建红包
    public static final String moneyreds_add = BASE_URL + "im/moneyreds/add.do";

    // 打开红包
    public static final String moneyreds_open = BASE_URL + "im/moneyreds/clicks/add.do";

    // 抢红包人员列表
    public static final String moneyreds_open_mems = BASE_URL + "im/moneyreds/clicks/list.do";

    // 3、根据红包Id获取该红包
    public static final String moneyreds_get = BASE_URL + "im/moneyreds/get.do";

    // 查询用户收到的红包记录
    public static final String moneyreds_getUserRedInList = BASE_URL + "im/moneyreds/clicks/getUserRedInList.do";
    // 查询用户的提现记录
    public static final String moneyreds_getUserCash = BASE_URL + "users/userCash.do";
    // 反馈
    public static final String feedback = BASE_URL + "baseset/addYijian.do";
    // 隐私
    public static final String privacy = BASE_URL + "baseset/privacy.do";
    // 隐私
    public static final String showprivacy = BASE_URL + "baseset/showprivacy.do";
    // 黑名单
    public static final String blacklist = BASE_URL + "baseset/privacyListbyuid.do";
    // 不看他的朋友圈
    public static final String blacklist_circle_of_friends = BASE_URL + "baseset/privacyListbyuid.do";
    // 不看他的朋友圈
    public static final String deleteUsers = BASE_URL + "users/deleteUsers.do";

    //3、	捡一个漂流瓶成功
    public static final String driftbottle_pickUpBottle = BASE_URL + "driftbottle/pickUpBottle.do";
    //关于买家
    public static final String about = BASE_URL + "baseset/about.do";

    //扫描（群组二维码）加入群聊
    public static final String scanToGroup = BASE_URL + "im/groups/scanToGroup.do";

    //设置---隐私—不允许他看我的买家圈
    public static final String privacyList = BASE_URL + "baseset/privacyList.do";

    //设置---隐私---不允许他看我的卖家圈----添加
    public static final String privacyAdd = BASE_URL + "baseset/privacyAdd.do";

    //设置---隐私---不允许他看我的卖家圈----删除
    public static final String privacyRemove = BASE_URL + "baseset/privacyRemove.do";

    //判断商家是否申请入住成功
    public static final String judgeIsSuccess = BASE_URL + "business/judgeIsSuccess.do";

    //退出时删除面对面建群信息
    public static final String deleteCodeUser = BASE_URL + "im/groups/deleteCodeUser.do";
    //更改群用户昵称
    public static final String updateNickName = BASE_URL + "im/groups/members/updateNickName.do";
    //设置是否显示群昵称
    public static final String setUpNickName = BASE_URL + "im/groups/members/setUpNickName.do";
    //删除好友
    public static final String delete = BASE_URL + "im/friend/delete.do";
    //设置好友标签
    public static final String setUplable = BASE_URL + "lables/setUplable.do";
    //保存收藏
    public static final String saveCollections = BASE_URL + "imageManager/saveCollections.do";
    //仍一个漂流瓶
    public static final String stillBottle = BASE_URL + "driftbottle/stillBottle.do";
    //2、	回应漂流瓶
    public static final String returnBottle = BASE_URL + "driftbottle/returnBottle.do";
    //4、	加载我的所有的漂流瓶
    public static final String findMyBottle = BASE_URL + "driftbottle/findMyBottle.do";
    //3、分红推送详情
    public static final String fenhongOrders = BASE_URL + "orders/fenhongOrders.do";
    //4、提现推送详情
    public static final String getWithdrawalsMessage = BASE_URL + "users/banks/getWithdrawalsMessage.do";
    //2、订单推送详情
    public static final String getOrdersMessage = BASE_URL + "orders/getOrdersMessage.do";
    //2、手机充值推送详情
    public static final String getRechargeMessage = BASE_URL + "orders/getRechargeMessage.do";
}
