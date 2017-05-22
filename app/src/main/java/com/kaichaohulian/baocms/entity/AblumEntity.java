package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/12.
 */

public class AblumEntity {


    /**
     * backAvatar : null
     * avatar : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
     * nikeName : 嘻嘻
     * experiences : [{"id":9,"createdTime":"2017-05-22 23:31:29","creator":7237,"isLocked":false,"lastModifiedTime":"2017-05-22 23:31:29","lastModifier":7237,"timeStamp":"1495467089000","nickName":null,"avator":null,"content":"拉个闸","reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]},{"id":8,"createdTime":"2017-05-22 22:01:40","creator":7236,"isLocked":false,"lastModifiedTime":"2017-05-22 22:01:40","lastModifier":7236,"timeStamp":"1495461700000","nickName":null,"avator":null,"content":"哈哈哈","reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]},{"id":4,"createdTime":"2017-05-07 22:42:08","creator":7237,"isLocked":false,"lastModifiedTime":"2017-05-07 22:42:08","lastModifier":7237,"timeStamp":"1494168128000","nickName":null,"avator":null,"content":"啊啊啊啊啊啊啊","reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]},{"id":2,"createdTime":"2017-04-28 11:23:10","creator":7237,"isLocked":false,"lastModifiedTime":"2017-04-28 11:23:10","lastModifier":7237,"timeStamp":"1493349790000","nickName":null,"avator":null,"content":"啊？","reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]},{"id":1,"createdTime":"2017-04-27 11:33:41","creator":7236,"isLocked":false,"lastModifiedTime":"2017-04-27 11:33:41","lastModifier":7236,"timeStamp":"1493264021000","nickName":null,"avator":null,"content":"鹅鹅鹅","reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]}]
     */

    public Object backAvatar;
    public String avatar;
    public String nikeName;
    public List<ExperiencesBean> experiences;

    public static class ExperiencesBean {
        /**
         * id : 9
         * createdTime : 2017-05-22 23:31:29
         * creator : 7237
         * isLocked : false
         * lastModifiedTime : 2017-05-22 23:31:29
         * lastModifier : 7237
         * timeStamp : 1495467089000
         * nickName : null
         * avator : null
         * content : 拉个闸
         * reminds : null
         * districtId : null
         * images : null
         * evaluates : []
         * islikes : []
         */

        public int id;
        public String createdTime;
        public int creator;
        public boolean isLocked;
        public String lastModifiedTime;
        public int lastModifier;
        public String timeStamp;
        public Object nickName;
        public Object avator;
        public String content;
        public Object reminds;
        public Object districtId;
        public Object images;
        public List<?> evaluates;
        public List<?> islikes;
    }
}
