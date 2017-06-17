package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/6/17.
 */

public class NewInfoBean {

    /**
     * id : 5
     * createdTime : 2017-06-17 17:01:25
     * creator : null
     * isLocked : false
     * lastModifiedTime : 2017-06-17 17:01:25
     * lastModifier : null
     * timeStamp : 1497690085790
     * userId : 7236
     * inviteStatus : 0
     * advertStatus : 1
     * nearStatus : 0
     * firendAppyStatus : 0
     */

    private int id;
    private String createdTime;
    private Object creator;
    private boolean isLocked;
    private String lastModifiedTime;
    private Object lastModifier;
    private String timeStamp;
    private int userId;
    private int inviteStatus;
    private int advertStatus;
    private int nearStatus;
    private int firendAppyStatus;

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

    public int getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(int inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public int getAdvertStatus() {
        return advertStatus;
    }

    public void setAdvertStatus(int advertStatus) {
        this.advertStatus = advertStatus;
    }

    public int getNearStatus() {
        return nearStatus;
    }

    public void setNearStatus(int nearStatus) {
        this.nearStatus = nearStatus;
    }

    public int getFirendAppyStatus() {
        return firendAppyStatus;
    }

    public void setFirendAppyStatus(int firendAppyStatus) {
        this.firendAppyStatus = firendAppyStatus;
    }
}
