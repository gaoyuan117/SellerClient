package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/9.
 */

public class InvitedBean {

    /**
     * id : 2
     * userId : 7235
     * nickName : 未填写
     * avatar : images/default.jpg
     * phoneNumber : null
     * title : 2123
     * inviteMoney : 211
     * inviteUsers :
     * userNum : null
     * inviteAddress : 123123
     * longitud : 12
     * latitude : 12
     * status : 1
     * invateTime : 2017-05-08 18:44:20
     * applyTime : 12322
     * createdTime : 2017-05-08 18:44:24
     * userApplyStatus : 1
     */

    private int id;
    private int userId;
    private String nickName;
    private String avatar;
    private Object phoneNumber;
    private String title;
    private int inviteMoney;
    private String inviteUsers;
    private Object userNum;
    private String inviteAddress;
    private String longitud;
    private String latitude;
    private int status;
    private String invateTime;
    private String applyTime;
    private String createdTime;
    private int userApplyStatus;
    private int ifRead;

    public int getIfRead() {
        return ifRead;
    }

    public void setIfRead(int ifRead) {
        this.ifRead = ifRead;
    }

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

    public String getInviteUsers() {
        return inviteUsers;
    }

    public void setInviteUsers(String inviteUsers) {
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
