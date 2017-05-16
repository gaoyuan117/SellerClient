package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/1.
 */

public class OnlineServiceEntity {


    /**
     * id : 3
     * createdTime : 2017-05-16 15:46:29
     * creator : null
     * isLocked : false
     * lastModifiedTime : 2017-05-16 19:07:50
     * lastModifier : null
     * timeStamp : 1494920789000
     * name : hha
     * phoneNumber : 18634097877
     * password : e10adc3949ba59abbe56e057f20f883e
     * avatar : http://oez2a4f3v.bkt.clouddn.com/Fs5XIh2OnL8EmufdSkYu-sKHdrEj
     * sex : 0
     * age : 37
     * address : 北京市 东城区
     * remark : null
     * status : 1
     */

    private int id;
    private String createdTime;
    private Object creator;
    private boolean isLocked;
    private String lastModifiedTime;
    private Object lastModifier;
    private String timeStamp;
    private String name;
    private String phoneNumber;
    private String password;
    private String avatar;
    private int sex;
    private int age;
    private String address;
    private Object remark;
    private int status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
