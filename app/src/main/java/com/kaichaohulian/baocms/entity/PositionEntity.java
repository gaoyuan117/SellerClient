package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/6/22.
 */

public class PositionEntity {


    /**
     * id : 14
     * createdTime : 2017-06-24 15:39:34
     * creator : null
     * isLocked : false
     * lastModifiedTime : 2017-06-24 15:39:34
     * lastModifier : null
     * timeStamp : 1498289974474
     * name : 计算机/互联网/通信
     * remark : IT
     * sex : 0
     * type : 3
     * ser : 0
     */

    private int id;
    private String createdTime;
    private Object creator;
    private boolean isLocked;
    private String lastModifiedTime;
    private Object lastModifier;
    private String timeStamp;
    private String name;
    private String remark;
    private int sex;
    private int type;
    private int ser;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSer() {
        return ser;
    }

    public void setSer(int ser) {
        this.ser = ser;
    }
}
