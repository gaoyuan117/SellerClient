package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/2.
 */

public class AdviertisementEntity {

    /**
     * advertList : [{"id":25,"createdTime":"2017-05-12 13:50:29","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-12 13:50:29","lastModifier":null,"timeStamp":"1494568229189","userId":7236,"title":"广告2","context":"海拔","type":1,"receive":"7237,7236,","receiveGroup":null,"image":"FoPvjnyXrIRTJeRY6VOGCuIGHSeV,","redMoney":2,"hasGetMoney":2,"pay":4,"readNum":24,"userName":" 哈哈","avter":"http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy","readStatus":1},{"id":24,"createdTime":"2017-05-12 13:50:29","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-12 13:50:29","lastModifier":null,"timeStamp":"1494568229189","userId":7237,"title":"啊啊啊啊啊","context":"啊","type":1,"receive":"7226,7235,7236,7237,7240,","receiveGroup":null,"image":"Fu8arndkMRr3svz8GT-XAicS_Lo4,","redMoney":1,"hasGetMoney":1,"pay":2,"readNum":30,"userName":"嘻嘻","avter":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","readStatus":1},{"id":13,"createdTime":"2017-05-12 13:50:29","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-12 13:50:29","lastModifier":null,"timeStamp":"1494568229189","userId":7236,"title":"嗯","context":"嗯","type":1,"receive":"7237,","receiveGroup":null,"image":"Fv_B6Cg12wUGqdoyWrwCXesXNpNo,","redMoney":2,"hasGetMoney":4,"pay":4,"readNum":7,"userName":" 哈哈","avter":"http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy","readStatus":1}]
     * noReadCount : 0
     */

    public int noReadCount;
    public List<AdvertListBean> advertList;

    public static class AdvertListBean {
        /**
         * id : 25
         * createdTime : 2017-05-12 13:50:29
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-12 13:50:29
         * lastModifier : null
         * timeStamp : 1494568229189
         * userId : 7236
         * title : 广告2
         * context : 海拔
         * type : 1
         * receive : 7237,7236,
         * receiveGroup : null
         * image : FoPvjnyXrIRTJeRY6VOGCuIGHSeV,
         * redMoney : 2.0
         * hasGetMoney : 2.0
         * pay : 4.0
         * readNum : 24
         * userName :  哈哈
         * avter : http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy
         * readStatus : 1
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
        public String avter;
        public int readStatus;
    }
}
