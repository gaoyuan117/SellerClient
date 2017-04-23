package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/20.
 */

public class NearBusinessEntity implements Serializable {

    private int userId;

    private String username;

    private String thermalSignatrue;

    private String distance;

    private String avatar;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getThermalSignatrue() {
        return thermalSignatrue;
    }

    public void setThermalSignatrue(String thermalSignatrue) {
        this.thermalSignatrue = thermalSignatrue;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
