package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/10.
 */

public class InviteDetailEntity {


    /**
     * invite : {"id":98,"createdTime":"2017-06-20 22:49:18","creator":null,"isLocked":false,"lastModifiedTime":"2017-06-22 23:44:59","lastModifier":null,"timeStamp":"1497970158000","userId":7257,"title":"拉闸","inviteMoney":null,"realPay":null,"totalMoney":3.6,"otherMoney":3.6,"inviteUsers":"jamlee,哈哈镜,","userNum":2,"joinNum":0,"inviteAddress":"济南","longitud":"116.379541","latitude":"40.038989","status":4,"invateTime":"2017-06-21 22:49:00","applyTime":"0小时50分","startTime":null,"drawTime":null,"rateMoney":"0.4","nickName":"拉闸？","avatar":"http://oez2a4f3v.bkt.clouddn.com/FmZvzYHT_F6GsDGLP1iITN2_liU8","phone":null,"ids":null}
     * list : [{"user_id":7256,"account":null,"password":"71660d54439ad58ada6caa35637a35ab","avator":"http://oez2a4f3v.bkt.clouddn.com/FnZ5TbZIVZ7PPfsuU4LHm6izTGsL","username":"jamlee","balance":98.38,"createdTime":"2017-06-18 15:15:49","lastTime":"2017-06-21 22:37:39","phoneNumber":"15626405387","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":0,"spreadMan":0,"addPay":5,"lockStatus":0,"inviteStatus":0,"inviteId":98,"inviteGet":null},{"user_id":7262,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"images/default.jpg","username":"哈哈镜","balance":20064.4,"createdTime":"2017-06-19 09:58:28","lastTime":"2017-06-22 13:22:07","phoneNumber":"18254215546","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":0,"spreadMan":0,"addPay":0.1,"lockStatus":0,"inviteStatus":0,"inviteId":98,"inviteGet":null}]
     * activeStatus : null
     */

    public InviteBean invite;
    public Object activeStatus;
    public List<ListBean> list;

    public static class InviteBean {
        /**
         * id : 98
         * createdTime : 2017-06-20 22:49:18
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-06-22 23:44:59
         * lastModifier : null
         * timeStamp : 1497970158000
         * userId : 7257
         * title : 拉闸
         * inviteMoney : null
         * realPay : null
         * totalMoney : 3.6
         * otherMoney : 3.6
         * inviteUsers : jamlee,哈哈镜,
         * userNum : 2
         * joinNum : 0
         * inviteAddress : 济南
         * longitud : 116.379541
         * latitude : 40.038989
         * status : 4
         * invateTime : 2017-06-21 22:49:00
         * applyTime : 0小时50分
         * startTime : null
         * drawTime : null
         * rateMoney : 0.4
         * nickName : 拉闸？
         * avatar : http://oez2a4f3v.bkt.clouddn.com/FmZvzYHT_F6GsDGLP1iITN2_liU8
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
        public Object inviteMoney;
        public Object realPay;
        public double totalMoney;
        public double otherMoney;
        public String inviteUsers;
        public int userNum;
        public int joinNum;
        public String inviteAddress;
        public String longitud;
        public String latitude;
        public int status;
        public String invateTime;
        public String applyTime;
        public Object startTime;
        public Object drawTime;
        public String rateMoney;
        public String nickName;
        public String avatar;
        public Object phone;
        public Object ids;
    }

    public static class ListBean {
        /**
         * user_id : 7256
         * account : null
         * password : 71660d54439ad58ada6caa35637a35ab
         * avator : http://oez2a4f3v.bkt.clouddn.com/FnZ5TbZIVZ7PPfsuU4LHm6izTGsL
         * username : jamlee
         * balance : 98.38
         * createdTime : 2017-06-18 15:15:49
         * lastTime : 2017-06-21 22:37:39
         * phoneNumber : 15626405387
         * email : null
         * bshop : 0
         * audit : 1
         * regip : null
         * invite6 : 0
         * customerServiceStatus : 0
         * spreadMan : 0
         * addPay : 5.0
         * lockStatus : 0
         * inviteStatus : 0
         * inviteId : 98
         * inviteGet : null
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
        public int inviteId;
        public Object inviteGet;
    }
}
