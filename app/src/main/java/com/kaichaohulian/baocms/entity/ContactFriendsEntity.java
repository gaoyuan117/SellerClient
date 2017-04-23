package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * 通讯录
 * Created by ljl on 2016/12/19 0019.
 */

public class ContactFriendsEntity implements Serializable {

    private int id;
    private String createdTime;
    private String username;
    private String phoneNumber;
    private String avatar;
    private String imNumber;
    private String thermalSignatrue;
    private String header; // 首字母
    private boolean isSelect=false;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImNumber() {
        return imNumber;
    }

    public void setImNumber(String imNumber) {
        this.imNumber = imNumber;
    }

    public String getThermalSignatrue() {
        return thermalSignatrue;
    }

    public void setThermalSignatrue(String thermalSignatrue) {
        this.thermalSignatrue = thermalSignatrue;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "ContactFriendsEntity{" +
                "id=" + id +
                ", createdTime='" + createdTime + '\'' +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", avatar='" + avatar + '\'' +
                ", imNumber='" + imNumber + '\'' +
                ", thermalSignatrue='" + thermalSignatrue + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}
