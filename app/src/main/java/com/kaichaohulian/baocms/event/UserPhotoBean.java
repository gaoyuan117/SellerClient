package com.kaichaohulian.baocms.event;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/6.
 */

public class UserPhotoBean {

    /**
     * backAvatar : null
     * avatar : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
     * nikeName : 嘻嘻
     * experiences : [{"id":2,"createdTime":"2017-04-28 11:23:10","creator":7237,"isLocked":false,"lastModifiedTime":"2017-04-28 11:23:10","lastModifier":7237,"timeStamp":"1493349790000","nickName":"嘻嘻","avator":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","content":"啊？","reminds":null,"districtId":null,"images":"[\"http:\\/\\/oez2a4f3v.bkt.clouddn.com\\/FlQAVuUGaTPZFLonGsQTF2hXriws\"]","evaluates":[],"islikes":[]}]
     */

    private Object backAvatar;
    private String avatar;
    private String nikeName;
    private List<ExperiencesBean> experiences;

    public Object getBackAvatar() {
        return backAvatar;
    }

    public void setBackAvatar(Object backAvatar) {
        this.backAvatar = backAvatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public List<ExperiencesBean> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperiencesBean> experiences) {
        this.experiences = experiences;
    }

    public static class ExperiencesBean {
        /**
         * id : 2
         * createdTime : 2017-04-28 11:23:10
         * creator : 7237
         * isLocked : false
         * lastModifiedTime : 2017-04-28 11:23:10
         * lastModifier : 7237
         * timeStamp : 1493349790000
         * nickName : 嘻嘻
         * avator : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
         * content : 啊？
         * reminds : null
         * districtId : null
         * images : ["http:\/\/oez2a4f3v.bkt.clouddn.com\/FlQAVuUGaTPZFLonGsQTF2hXriws"]
         * evaluates : []
         * islikes : []
         */

        private int id;
        private String createdTime;
        private int creator;
        private boolean isLocked;
        private String lastModifiedTime;
        private int lastModifier;
        private String timeStamp;
        private String nickName;
        private String avator;
        private String content;
        private Object reminds;
        private Object districtId;
        private String images;
        private List<?> evaluates;
        private List<?> islikes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public int getCreator() {
            return creator;
        }

        public void setCreator(int creator) {
            this.creator = creator;
        }

        public boolean isIsLocked() {
            return isLocked;
        }

        public void setIsLocked(boolean isLocked) {
            this.isLocked = isLocked;
        }

        public String getLastModifiedTime() {
            return lastModifiedTime;
        }

        public void setLastModifiedTime(String lastModifiedTime) {
            this.lastModifiedTime = lastModifiedTime;
        }

        public int getLastModifier() {
            return lastModifier;
        }

        public void setLastModifier(int lastModifier) {
            this.lastModifier = lastModifier;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getReminds() {
            return reminds;
        }

        public void setReminds(Object reminds) {
            this.reminds = reminds;
        }

        public Object getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Object districtId) {
            this.districtId = districtId;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public List<?> getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(List<?> evaluates) {
            this.evaluates = evaluates;
        }

        public List<?> getIslikes() {
            return islikes;
        }

        public void setIslikes(List<?> islikes) {
            this.islikes = islikes;
        }
    }
}
