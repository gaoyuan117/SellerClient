package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/14.
 */

public class InviteReciverEntity {

    /**
     * dto : {"id":1,"userId":7237,"nickName":"嘻嘻","avatar":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","phoneNumber":null,"title":"日狗大队","inviteMoney":2,"inviteUsers":null,"userNum":10,"inviteAddress":"中国北京市海淀区黑泉路","longitud":"116.379572","latitude":"40.039063","status":2,"invateTime":"2017-05-10 23:10:00","applyTime":"12å°\u008fæ\u0097¶","createdTime":"2017-05-10 23:06:17","userApplyStatus":0}
     * user : {"user_id":7237,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","username":"嘻嘻","balance":9993.12,"createdTime":"2017-05-14 17:31:29","lastTime":null,"phoneNumber":"18572651299","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":null,"spreadMan":0,"addPay":5,"lockStatus":null,"inviteStatus":null}
     */

    public DtoBean dto;
    public UserBean user;

    public static class DtoBean {
        /**
         * id : 1
         * userId : 7237
         * nickName : 嘻嘻
         * avatar : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
         * phoneNumber : null
         * title : 日狗大队
         * inviteMoney : 2
         * inviteUsers : null
         * userNum : 10
         * inviteAddress : 中国北京市海淀区黑泉路
         * longitud : 116.379572
         * latitude : 40.039063
         * status : 2
         * invateTime : 2017-05-10 23:10:00
         * applyTime : 12å°æ¶
         * createdTime : 2017-05-10 23:06:17
         * userApplyStatus : 0
         */

        public int id;
        public int userId;
        public String nickName;
        public String avatar;
        public Object phoneNumber;
        public String title;
        public int inviteMoney;
        public Object inviteUsers;
        public int userNum;
        public String inviteAddress;
        public String longitud;
        public String latitude;
        public int status;
        public String invateTime;
        public String applyTime;
        public String createdTime;
        public int userApplyStatus;
    }

    public static class UserBean {
        /**
         * user_id : 7237
         * account : null
         * password : e10adc3949ba59abbe56e057f20f883e
         * avator : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
         * username : 嘻嘻
         * balance : 9993.12
         * createdTime : 2017-05-14 17:31:29
         * lastTime : null
         * phoneNumber : 18572651299
         * email : null
         * bshop : 0
         * audit : 1
         * regip : null
         * invite6 : 0
         * customerServiceStatus : null
         * spreadMan : 0
         * addPay : 5.0
         * lockStatus : null
         * inviteStatus : null
         */

        public int user_id;
        public Object account;
        public String password;
        public String avator;
        public String username;
        public double balance;
        public String createdTime;
        public Object lastTime;
        public String phoneNumber;
        public Object email;
        public int bshop;
        public int audit;
        public Object regip;
        public int invite6;
        public Object customerServiceStatus;
        public int spreadMan;
        public double addPay;
        public Object lockStatus;
        public Object inviteStatus;
    }
}
