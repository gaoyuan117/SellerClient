package com.kaichaohulian.baocms.entity;

/**
 * Created by kenton on 2017/2/14.
 */

public class PickUpBottleBean {
    int id;
    String createdTime;
    int creator;
    boolean isLocked;
    String lastModifiedTime;
    String lastModifier;
    String timeStamp;
    int toUser;
    int content;
    String username;
    String thermalSignarue;
    String sex;

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

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThermalSignarue() {
        return thermalSignarue;
    }

    public void setThermalSignarue(String thermalSignarue) {
        this.thermalSignarue = thermalSignarue;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "PickUpBottleBean{" +
                "id=" + id +
                ", createdTime='" + createdTime + '\'' +
                ", creator=" + creator +
                ", isLocked=" + isLocked +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                ", lastModifier='" + lastModifier + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", toUser=" + toUser +
                ", content=" + content +
                ", username='" + username + '\'' +
                ", thermalSignarue='" + thermalSignarue + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
