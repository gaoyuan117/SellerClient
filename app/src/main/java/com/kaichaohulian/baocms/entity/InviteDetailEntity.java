package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/10.
 */

public class InviteDetailEntity {

    /**
     * invite : {"id":1,"createdTime":"2017-05-10 23:06:17","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-12 21:51:46","lastModifier":null,"timeStamp":"1494428777000","userId":7237,"title":"日狗大队","inviteMoney":2,"inviteUsers":"null, 哈哈,","userNum":10,"inviteAddress":"中国北京市海淀区黑泉路","longitud":"116.379572","latitude":"40.039063","status":1,"invateTime":"2017-05-10 23:10:00","applyTime":"12å°\u008fæ\u0097¶","nickName":"嘻嘻","avatar":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","phone":null,"ids":null}
     * list : [{"user_id":0,"account":null,"password":null,"avator":null,"username":null,"balance":null,"createdTime":null,"lastTime":null,"phoneNumber":null,"email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":null,"spreadMan":0,"addPay":null,"inviteStatus":1},{"user_id":7236,"account":null,"password":"e10adc3949ba59abbe56e057f20f883e","avator":"http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy","username":" 哈哈","balance":3.88,"createdTime":1518550169,"lastTime":null,"phoneNumber":"14763766689","email":null,"bshop":0,"audit":1,"regip":null,"invite6":0,"customerServiceStatus":1,"spreadMan":0,"addPay":null,"inviteStatus":1}]
     * activeStatus : 已见面,活动进行中
     */

    public InviteBean invite;
    public String activeStatus;
    public List<ListBean> list;

    public static class InviteBean {
        /**
         * id : 1
         * createdTime : 2017-05-10 23:06:17
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-12 21:51:46
         * lastModifier : null
         * timeStamp : 1494428777000
         * userId : 7237
         * title : 日狗大队
         * inviteMoney : 2
         * inviteUsers : null, 哈哈,
         * userNum : 10
         * inviteAddress : 中国北京市海淀区黑泉路
         * longitud : 116.379572
         * latitude : 40.039063
         * status : 1
         * invateTime : 2017-05-10 23:10:00
         * applyTime : 12å°æ¶
         * nickName : 嘻嘻
         * avatar : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
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
        public int inviteMoney;
        public String inviteUsers;
        public int userNum;
        public String inviteAddress;
        public String longitud;
        public String latitude;
        public int status;
        public String invateTime;
        public String applyTime;
        public String nickName;
        public String avatar;
        public Object phone;
        public Object ids;
    }

    public static class ListBean {
        /**
         * user_id : 0
         * account : null
         * password : null
         * avator : null
         * username : null
         * balance : null
         * createdTime : null
         * lastTime : null
         * phoneNumber : null
         * email : null
         * bshop : 0
         * audit : 1
         * regip : null
         * invite6 : 0
         * customerServiceStatus : null
         * spreadMan : 0
         * addPay : null
         * inviteStatus : 1
         */

        public int user_id;
        public Object account;
        public Object password;
        public String avator;
        public Object username;
        public Object balance;
        public String createdTime;
        public Object lastTime;
        public Object phoneNumber;
        public Object email;
        public int bshop;
        public int audit;
        public Object regip;
        public int invite6;
        public Object customerServiceStatus;
        public int spreadMan;
        public Object addPay;
        public int inviteStatus;
    }
}
