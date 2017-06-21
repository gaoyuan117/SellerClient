package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/9.
 */

public class MyInviteEntity {


    /**
     * invite : {"id":67,"createdTime":"2017-06-17 19:50:04","creator":null,"isLocked":false,"lastModifiedTime":"2017-06-17 19:51:05","lastModifier":null,"timeStamp":"1497700204000","userId":7237,"title":"拉闸","inviteMoney":0.5,"totalMoney":4.5,"otherMoney":4.5,"inviteUsers":null,"userNum":2,"inviteAddress":"济南","longitud":"116.29536","latitude":"39.967382","status":0,"invateTime":"2017-06-18 23:57:00","applyTime":"5:30","startTime":null,"drawTime":null,"nickName":"嘻嘻","avatar":null,"phone":"18572651299","ids":null}
     * activeStatus : 邀请成功
     */

    private InviteBean invite;
    private String activeStatus;

    public InviteBean getInvite() {
        return invite;
    }

    public void setInvite(InviteBean invite) {
        this.invite = invite;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public static class InviteBean {
        /**
         * id : 67
         * createdTime : 2017-06-17 19:50:04
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-06-17 19:51:05
         * lastModifier : null
         * timeStamp : 1497700204000
         * userId : 7237
         * title : 拉闸
         * inviteMoney : 0.5
         * totalMoney : 4.5
         * otherMoney : 4.5
         * inviteUsers : null
         * userNum : 2
         * inviteAddress : 济南
         * longitud : 116.29536
         * latitude : 39.967382
         * status : 0
         * invateTime : 2017-06-18 23:57:00
         * applyTime : 5:30
         * startTime : null
         * drawTime : null
         * nickName : 嘻嘻
         * avatar : null
         * phone : 18572651299
         * ids : null
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
        private double inviteMoney;
        private double totalMoney;
        private double otherMoney;
        private Object inviteUsers;
        private int userNum;
        private String inviteAddress;
        private String longitud;
        private String latitude;
        private int status;
        private String invateTime;
        private String applyTime;
        private Object startTime;
        private Object drawTime;
        private String nickName;
        private Object avatar;
        private String phone;
        private Object ids;

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

        public double getInviteMoney() {
            return inviteMoney;
        }

        public void setInviteMoney(double inviteMoney) {
            this.inviteMoney = inviteMoney;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public double getOtherMoney() {
            return otherMoney;
        }

        public void setOtherMoney(double otherMoney) {
            this.otherMoney = otherMoney;
        }

        public Object getInviteUsers() {
            return inviteUsers;
        }

        public void setInviteUsers(Object inviteUsers) {
            this.inviteUsers = inviteUsers;
        }

        public int getUserNum() {
            return userNum;
        }

        public void setUserNum(int userNum) {
            this.userNum = userNum;
        }

        public String getInviteAddress() {
            return inviteAddress;
        }

        public void setInviteAddress(String inviteAddress) {
            this.inviteAddress = inviteAddress;
        }

        public String getLongitud() {
            return longitud;
        }

        public void setLongitud(String longitud) {
            this.longitud = longitud;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getInvateTime() {
            return invateTime;
        }

        public void setInvateTime(String invateTime) {
            this.invateTime = invateTime;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public Object getDrawTime() {
            return drawTime;
        }

        public void setDrawTime(Object drawTime) {
            this.drawTime = drawTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getIds() {
            return ids;
        }

        public void setIds(Object ids) {
            this.ids = ids;
        }
    }
}
