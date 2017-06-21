package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/14.
 */

public class InviteReciverEntity {
    /**
     * dto : {"id":83,"recordId":52,"userId":7265,"nickName":"哈哈镜","avatar":"images/default.jpg","phoneNumber":null,"title":"羽毛球","inviteMoney":1.2,"inviteUsers":"哈哈镜,","userNum":1,"inviteAddress":"李沧万达","longitud":"36.152519","latitude":"120.419348","status":4,"invateTime":"2017-06-19 13:40:00","applyTime":"0:10","createdTime":"2017-06-19 13:29:26","userApplyStatus":0,"ifRead":1}
     * user : {"user_id":7265,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"images/default.jpg","username":"我的哈哈","balance":4998.85,"createdTime":"2017-06-19 10:42:51","lastTime":"2017-06-19 13:35:57","phoneNumber":"18153254126","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":0,"spreadMan":0,"addPay":0,"lockStatus":0,"inviteStatus":null}
     */

    public DtoBean dto;
    public UserBean user;

    public static class DtoBean {
        /**
         * id : 83
         * recordId : 52
         * userId : 7265
         * nickName : 哈哈镜
         * avatar : images/default.jpg
         * phoneNumber : null
         * title : 羽毛球
         * inviteMoney : 1.2
         * inviteUsers : 哈哈镜,
         * userNum : 1
         * inviteAddress : 李沧万达
         * longitud : 36.152519
         * latitude : 120.419348
         * status : 4
         * invateTime : 2017-06-19 13:40:00
         * applyTime : 0:10
         * createdTime : 2017-06-19 13:29:26
         * userApplyStatus : 0
         * ifRead : 1
         */

        public int id;
        public int recordId;
        public int userId;
        public String nickName;
        public String avatar;
        public Object phoneNumber;
        public String title;
        public double inviteMoney;
        public String inviteUsers;
        public int userNum;
        public String inviteAddress;
        public String longitud;
        public String latitude;
        public int status;
        public String invateTime;
        public String applyTime;
        public String createdTime;
        public int userApplyStatus;
        public int ifRead;
    }

    public static class UserBean {
        /**
         * user_id : 7265
         * account : null
         * password : e10adc3949ba59abbe56e057f20f883e
         * avator : images/default.jpg
         * username : 我的哈哈
         * balance : 4998.85
         * createdTime : 2017-06-19 10:42:51
         * lastTime : 2017-06-19 13:35:57
         * phoneNumber : 18153254126
         * email : null
         * bshop : 0
         * audit : 1
         * regip : null
         * invite6 : 0
         * customerServiceStatus : 0
         * spreadMan : 0
         * addPay : 0.0
         * lockStatus : 0
         * inviteStatus : null
         */

        public int user_id;
        public Object account;
        public String password;
        public String avator;
        public String username;
        public double balance;
        public String createdTime;
        public String lastTime;
        public String phoneNumber;
        public Object email;
        public int bshop;
        public int audit;
        public Object regip;
        public int invite6;
        public int customerServiceStatus;
        public int spreadMan;
        public double addPay;
        public int lockStatus;
        public Object inviteStatus;
    }
}
