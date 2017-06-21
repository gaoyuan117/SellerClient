package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/9.
 */

public class InvitedBean {


    /**
     * id : 78
     * recordId : 47
     * userId : 7257
     * nickName : jamlee
     * avatar : http://oez2a4f3v.bkt.clouddn.com/FnZ5TbZIVZ7PPfsuU4LHm6izTGsL
     * phoneNumber : null
     * title : 啊啊啊啊啊啊
     * inviteMoney : 0.0
     * inviteUsers : null
     * userNum : null
     * inviteAddress : 济南
     * longitud : 116.379649
     * latitude : 40.03908
     * status : 0
     * invateTime : 2017-08-19 16:36:00
     * applyTime : 2:
     * createdTime : 2017-06-18 16:36:50
     * userApplyStatus : 1
     * ifRead : 1
     */

    private int id;
    private int recordId;
    private int userId;
    private String nickName;
    private String avatar;
    private Object phoneNumber;
    private String title;
    private double inviteMoney;
    private Object inviteUsers;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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

    public double getInviteMoney() {
        return inviteMoney;
    }

    public void setInviteMoney(double inviteMoney) {
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

    public int getIfRead() {
        return ifRead;
    }

    public void setIfRead(int ifRead) {
        this.ifRead = ifRead;
    }
}
