package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/12.
 */

public class AblumEntity {

    /**
     * backAvatar : null
     * avatar : null
     * nikeName : 张三
     * experiences : [{"id":3,"createdTime":"2016-12-25 12:49:46","creator":1,"isLocked":false,"lastModifiedTime":"2016-12-25 12:49:46","lastModifier":1,"nickName":"张三","avator":null,"content":null,"reminds":null,"districtId":null,"images":"1.jpg,2.jpg,3.jpg","evaluates":[],"islikes":[]},{"id":2,"createdTime":"2016-12-25 12:47:36","creator":1,"isLocked":false,"lastModifiedTime":"2016-12-25 12:47:36","lastModifier":1,"nickName":"张三","avator":null,"content":null,"reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]},{"id":1,"createdTime":"2016-12-25 12:42:57","creator":1,"isLocked":false,"lastModifiedTime":"2016-12-25 12:42:57","lastModifier":1,"nickName":"张三","avator":null,"content":null,"reminds":null,"districtId":null,"images":null,"evaluates":[],"islikes":[]}]
     */

    public Object backAvatar;
    public Object avatar;
    public String nikeName;
    public List<ExperiencesBean> experiences;

    public static class ExperiencesBean {
        /**
         * id : 3
         * createdTime : 2016-12-25 12:49:46
         * creator : 1
         * isLocked : false
         * lastModifiedTime : 2016-12-25 12:49:46
         * lastModifier : 1
         * nickName : 张三
         * avator : null
         * content : null
         * reminds : null
         * districtId : null
         * images : 1.jpg,2.jpg,3.jpg
         * evaluates : []
         * islikes : []
         */

        public int id;
        public String createdTime;
        public int creator;
        public boolean isLocked;
        public String lastModifiedTime;
        public int lastModifier;
        public String nickName;
        public Object avator;
        public Object content;
        public Object reminds;
        public Object districtId;
        public String images;
        public List<?> evaluates;
        public List<?> islikes;
    }
}
