package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class NearbyBean {

    /**
     * userId : 7240
     * username : 高原2号
     * thermalSignatrue : null
     * distance : 173 米以内
     * avatar : http://oez2a4f3v.bkt.clouddn.com/Fisv14RRoy-FaAcXO7KCY1UIdSZP
     * districtName : 山东省 济宁市 兖州市
     * sex : 男
     * industry : null
     * isFriend : 0
     * phone : 18657217617
     */

    private int userId;
    private String username;
    private Object thermalSignatrue;
    private String distance;
    private String avatar;
    private String districtName;
    private String sex;
    private Object industry;
    private int isFriend;
    private String phone;

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

    public Object getThermalSignatrue() {
        return thermalSignatrue;
    }

    public void setThermalSignatrue(Object thermalSignatrue) {
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(Object industry) {
        this.industry = industry;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
