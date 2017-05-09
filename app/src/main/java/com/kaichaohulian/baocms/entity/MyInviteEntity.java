package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/9.
 */

public class MyInviteEntity {

    /**
     * invite : {"id":1,"createdTime":"2017-05-08 18:44:24","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-09 00:17:09","lastModifier":null,"timeStamp":"1494240264000","userId":1,"title":"2123","inviteMoney":211,"inviteUsers":null,"userNum":null,"inviteAddress":"123123","longitud":"12","latitude":"12","status":1,"invateTime":"2017-05-08 18:44:20","applyTime":"12322","nickName":null,"avatar":null,"ids":null}
     * activeStatus : 进行中
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
         * id : 1
         * createdTime : 2017-05-08 18:44:24
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-09 00:17:09
         * lastModifier : null
         * timeStamp : 1494240264000
         * userId : 1
         * title : 2123
         * inviteMoney : 211
         * inviteUsers : null
         * userNum : null
         * inviteAddress : 123123
         * longitud : 12
         * latitude : 12
         * status : 1
         * invateTime : 2017-05-08 18:44:20
         * applyTime : 12322
         * nickName : null
         * avatar : null
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
        private int inviteMoney;
        private Object inviteUsers;
        private Object userNum;
        private String inviteAddress;
        private String longitud;
        private String latitude;
        private int status;
        private String invateTime;
        private String applyTime;
        private Object nickName;
        private Object avatar;
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

        public int getInviteMoney() {
            return inviteMoney;
        }

        public void setInviteMoney(int inviteMoney) {
            this.inviteMoney = inviteMoney;
        }

        public Object getInviteUsers() {
            return inviteUsers;
        }

        public void setInviteUsers(Object inviteUsers) {
            this.inviteUsers = inviteUsers;
        }

        public Object getUserNum() {
            return userNum;
        }

        public void setUserNum(Object userNum) {
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

        public Object getNickName() {
            return nickName;
        }

        public void setNickName(Object nickName) {
            this.nickName = nickName;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getIds() {
            return ids;
        }

        public void setIds(Object ids) {
            this.ids = ids;
        }
    }
}
