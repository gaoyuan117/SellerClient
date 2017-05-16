package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/16.
 */

public class LableBean {

    /**
     * id : 1
     * createdTime : 2017-05-15 08:10:15
     * creator : null
     * isLocked : false
     * lastModifiedTime : 2017-05-15 08:10:15
     * lastModifier : null
     * timeStamp : 1494807015300
     * name : sb
     */

    private int id;
    private String createdTime;
    private Object creator;
    private boolean isLocked;
    private String lastModifiedTime;
    private Object lastModifier;
    private String timeStamp;
    private String name;

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
}
