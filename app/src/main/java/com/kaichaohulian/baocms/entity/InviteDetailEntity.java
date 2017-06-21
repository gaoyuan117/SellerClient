package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/10.
 */

public class InviteDetailEntity {


    /**
     * invite : {"id":85,"createdTime":"2017-06-19 15:23:18","creator":null,"isLocked":false,"lastModifiedTime":"2017-06-19 15:28:21","lastModifier":null,"timeStamp":"1497856998000","userId":7262,"title":"足球","inviteMoney":0.3,"totalMoney":5.4,"otherMoney":5.4,"inviteUsers":"未填写,我的哈哈,","userNum":null,"inviteAddress":"立邦万达","longitud":"120.434572","latitude":"36.159983","status":0,"invateTime":"2017-06-19 15:36:00","applyTime":"0小时15分","startTime":null,"drawTime":null,"nickName":"哈哈镜","avatar":"images/default.jpg","phone":null,"ids":null}
     * list : [{"user_id":7259,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"images/default.jpg","username":"未填写","balance":0,"createdTime":"2017-06-18 20:06:19","lastTime":"2017-06-19 14:38:04","phoneNumber":"14763766689","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":0,"spreadMan":0,"addPay":0,"lockStatus":0,"inviteStatus":0},{"user_id":7265,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"images/default.jpg","username":"我的哈哈","balance":4998.85,"createdTime":"2017-06-19 10:42:51","lastTime":"2017-06-19 13:35:57","phoneNumber":"18153254126","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":0,"spreadMan":0,"addPay":0,"lockStatus":0,"inviteStatus":0}]
     * activeStatus : 进行中
     */

    public InviteBean invite;
    public String activeStatus;
    public List<ListBean> list;

    public static class InviteBean {
        /**
         * id : 85
         * createdTime : 2017-06-19 15:23:18
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-06-19 15:28:21
         * lastModifier : null
         * timeStamp : 1497856998000
         * userId : 7262
         * title : 足球
         * inviteMoney : 0.3
         * totalMoney : 5.4
         * otherMoney : 5.4
         * inviteUsers : 未填写,我的哈哈,
         * userNum : null
         * inviteAddress : 立邦万达
         * longitud : 120.434572
         * latitude : 36.159983
         * status : 0
         * invateTime : 2017-06-19 15:36:00
         * applyTime : 0小时15分
         * startTime : null
         * drawTime : null
         * nickName : 哈哈镜
         * avatar : images/default.jpg
         * phone : null
         * ids : null
         */

        public int id;
        public String createdTime;
        public Object creator;
        public boolean isLocked;
        public String lastModifiedTime;
        public Object lastModifier;
        public String timeStamp;
        public int userId;
        public String title;
        public double inviteMoney;
        public double totalMoney;
        public double otherMoney;
        public String inviteUsers;
        public Object userNum;
        public String inviteAddress;
        public String longitud;
        public String latitude;
        public int status;
        public String invateTime;
        public String applyTime;
        public Object startTime;
        public Object drawTime;
        public String nickName;
        public String avatar;
        public Object phone;
        public Object ids;
    }

    public static class ListBean {
        /**
         * user_id : 7259
         * account : null
         * password : e10adc3949ba59abbe56e057f20f883e
         * avator : images/default.jpg
         * username : 未填写
         * balance : 0.0
         * createdTime : 2017-06-18 20:06:19
         * lastTime : 2017-06-19 14:38:04
         * phoneNumber : 14763766689
         * email : null
         * bshop : 0
         * audit : 1
         * regip : null
         * invite6 : 0
         * customerServiceStatus : 0
         * spreadMan : 0
         * addPay : 0.0
         * lockStatus : 0
         * inviteStatus : 0
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
        public int inviteStatus;
    }
}
