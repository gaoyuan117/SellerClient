package com.kaichaohulian.baocms.entity;

/**
 * Created by liuyu on 2016/12/22.
 */

public class ReceivedRedBagListEntity {

    private String userName;
    private String avatar;
    private String createdTime;
    private double useracount;
    private boolean best;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public double getUseracount() {
        return useracount;
    }

    public void setUseracount(double useracount) {
        this.useracount = useracount;
    }

    public boolean isBest() {
        return best;
    }

    public void setBest(boolean best) {
        this.best = best;
    }
}
