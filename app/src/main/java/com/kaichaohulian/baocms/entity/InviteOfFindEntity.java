package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/11.
 */

public class InviteOfFindEntity {

    /**
     * id : 1
     * userId : 7237
     * nickName :  哈哈
     * avatar : http://oez2a4f3v.bkt.clouddn.com/FkhUiD0hB_wHMInB6bGgWc5pGdKy
     * phoneNumber : null
     * title : 日狗大队
     * inviteMoney : 2
     * inviteUsers : null
     * userNum : 10
     * inviteAddress : 中国北京市海淀区黑泉路
     * longitud : 116.379572
     * latitude : 40.039063
     * status : 0
     * invateTime : 2017-05-10 23:10:00
     * applyTime : 12å°æ¶
     * createdTime : 2017-05-10 23:06:17
     * userApplyStatus : 0
     */

    private int id;
    private int userId;
    private String nickName;
    private String avatar;
    private Object phoneNumber;
    private String title;
    private int inviteMoney;
    private Object inviteUsers;
    private int userNum;
    private String inviteAddress;
    private String longitud;
    private String latitude;
    private int status;
    private String invateTime;
    private String applyTime;
    private String createdTime;
    private int userApplyStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getUserApplyStatus() {
        return userApplyStatus;
    }

    public void setUserApplyStatus(int userApplyStatus) {
        this.userApplyStatus = userApplyStatus;
    }
}
