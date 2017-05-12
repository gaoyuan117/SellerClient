package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class HasGetAdverBean {


    private int noReadCount;
    private List<AdvertListBean> advertList;

    public int getNoReadCount() {
        return noReadCount;
    }

    public void setNoReadCount(int noReadCount) {
        this.noReadCount = noReadCount;
    }

    public List<AdvertListBean> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<AdvertListBean> advertList) {
        this.advertList = advertList;
    }

    public static class AdvertListBean {
        /**
         * id : 25
         * createdTime : 2017-05-11 22:58:03
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-11 22:58:03
         * lastModifier : null
         * timeStamp : 1494514683645
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
         * readNum : 22
         * userName :  哈哈
         * avter : http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy
         * readStatus : 1
         */

        private int id;
        private String createdTime;
        private Object creator;
        private boolean isLocked;
        private String lastModifiedTime;
        private Object lastModifier;
        private String timeStamp;
        private int userId;
        private String title;
        private String context;
        private int type;
        private String receive;
        private Object receiveGroup;
        private String image;
        private double redMoney;
        private double hasGetMoney;
        private double pay;
        private int readNum;
        private String userName;
        private String avter;
        private int readStatus;

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

        public Object getCreator() {
            return creator;
        }

        public void setCreator(Object creator) {
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

        public Object getLastModifier() {
            return lastModifier;
        }

        public void setLastModifier(Object lastModifier) {
            this.lastModifier = lastModifier;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public Object getReceiveGroup() {
            return receiveGroup;
        }

        public void setReceiveGroup(Object receiveGroup) {
            this.receiveGroup = receiveGroup;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getRedMoney() {
            return redMoney;
        }

        public void setRedMoney(double redMoney) {
            this.redMoney = redMoney;
        }

        public double getHasGetMoney() {
            return hasGetMoney;
        }

        public void setHasGetMoney(double hasGetMoney) {
            this.hasGetMoney = hasGetMoney;
        }

        public double getPay() {
            return pay;
        }

        public void setPay(double pay) {
            this.pay = pay;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvter() {
            return avter;
        }

        public void setAvter(String avter) {
            this.avter = avter;
        }

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }
    }

}
