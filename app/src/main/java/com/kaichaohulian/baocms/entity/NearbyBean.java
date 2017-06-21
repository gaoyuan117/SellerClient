package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class NearbyBean {


    /**
     * userId : 7262
     * username : 哈哈镜
     * thermalSignatrue : null
     * distance : 603588 米以内
     * avatar : images/default.jpg
     * districtName : 山东省 青岛市 李沧区
     * sex : null
     * industry : null
     * isFriend : 0
     * phone : 18254215546
     * addPay : 0.10
     */

    private int userId;
    private String username;
    private Object thermalSignatrue;
    private String distance;
    private String avatar;
    private String districtName;
    private Object sex;
    private Object industry;
    private int isFriend;
    private String phone;
    private String addPay;

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

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
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

    public String getAddPay() {
        return addPay;
    }

    public void setAddPay(String addPay) {
        this.addPay = addPay;
    }

    @Override
    public String toString() {
        return "NearbyBean{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", thermalSignatrue=" + thermalSignatrue +
                ", distance='" + distance + '\'' +
                ", avatar='" + avatar + '\'' +
                ", districtName='" + districtName + '\'' +
                ", sex=" + sex +
                ", industry=" + industry +
                ", isFriend=" + isFriend +
                ", phone='" + phone + '\'' +
                ", addPay='" + addPay + '\'' +
                '}';
    }
}
