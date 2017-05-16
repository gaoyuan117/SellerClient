package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/16.
 */

public class AdvertDetailEntity {

    /**
     * advert : {"id":26,"createdTime":"2017-05-16 22:36:34","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-16 22:37:37","lastModifier":null,"timeStamp":"1494945394000","userId":7237,"title":"啊啊啊啊啊","context":"嘻嘻嘻嘻嘻","type":1,"receive":"7236,7237,","receiveGroup":null,"image":"FnFgZIbfQbU9pOG6T-5sk9w6KAdv,","redMoney":2,"hasGetMoney":0,"pay":4,"readNum":1,"userName":"嘻嘻","phoneNumber":null,"avter":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","readStatus":null,"otherUser":null,"hasGet":null}
     * redMoney : 1.85
     */

    public AdvertBean advert;
    public String redMoney;

    public static class AdvertBean {
        /**
         * id : 26
         * createdTime : 2017-05-16 22:36:34
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-16 22:37:37
         * lastModifier : null
         * timeStamp : 1494945394000
         * userId : 7237
         * title : 啊啊啊啊啊
         * context : 嘻嘻嘻嘻嘻
         * type : 1
         * receive : 7236,7237,
         * receiveGroup : null
         * image : FnFgZIbfQbU9pOG6T-5sk9w6KAdv,
         * redMoney : 2.0
         * hasGetMoney : 0.0
         * pay : 4.0
         * readNum : 1
         * userName : 嘻嘻
         * phoneNumber : null
         * avter : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
         * readStatus : null
         * otherUser : null
         * hasGet : null
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
        public String context;
        public int type;
        public String receive;
        public Object receiveGroup;
        public String image;
        public double redMoney;
        public double hasGetMoney;
        public double pay;
        public int readNum;
        public String userName;
        public Object phoneNumber;
        public String avter;
        public Object readStatus;
        public Object otherUser;
        public Object hasGet;
    }
}
