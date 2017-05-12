package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/10.
 */

public class InviteDetailEntity {

    /**
     * invite : {"id":1,"createdTime":"2017-05-10 23:06:17","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-11 22:36:58","lastModifier":null,"timeStamp":"1494428777000","userId":7237,"title":"日狗大队","inviteMoney":2,"inviteUsers":"","userNum":10,"inviteAddress":"中国北京市海淀区黑泉路","longitud":"116.379572","latitude":"40.039063","status":0,"invateTime":"2017-05-10 23:10:00","applyTime":"12å°\u008fæ\u0097¶","nickName":"嘻嘻","avatar":"http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__","phone":null,"ids":null}
     * list : []
     * activeStatus : 进行中
     */

    private InviteBean invite;
    private String activeStatus;
    private List<?> list;

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

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public static class InviteBean {
        /**
         * id : 1
         * createdTime : 2017-05-10 23:06:17
         * creator : null
         * isLocked : false
         * lastModifiedTime : 2017-05-11 22:36:58
         * lastModifier : null
         * timeStamp : 1494428777000
         * userId : 7237
         * title : 日狗大队
         * inviteMoney : 2
         * inviteUsers :
         * userNum : 10
         * inviteAddress : 中国北京市海淀区黑泉路
         * longitud : 116.379572
         * latitude : 40.039063
         * status : 0
         * invateTime : 2017-05-10 23:10:00
         * applyTime : 12å°æ¶
         * nickName : 嘻嘻
         * avatar : http://oez2a4f3v.bkt.clouddn.com/FqHspSYV-U-SN86Hv3Xnk8KV6z__
         * phone : null
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
        private String inviteUsers;
        private int userNum;
        private String inviteAddress;
        private String longitud;
        private String latitude;
        private int status;
        private String invateTime;
        private String applyTime;
        private String nickName;
        private String avatar;
        private Object phone;
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

        public String getInviteUsers() {
            return inviteUsers;
        }

        public void setInviteUsers(String inviteUsers) {
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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
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
