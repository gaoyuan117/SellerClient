package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by HuaSao1024 on 2017/2/5.
 */

public class ShopInfoEntity implements Serializable {

    private String avatar;
    private String shopName;
    private String qrcode;
    private String userName;
    private String businessTime;
    private String distriwaitTime;
    private String distriDetail;
    private double price;
    private String details;
    private String area;
    private String telPhone;
    private String address;
    private String longitud;
    private String latitude;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getDistriwaitTime() {
        return distriwaitTime;
    }

    public void setDistriwaitTime(String distriwaitTime) {
        this.distriwaitTime = distriwaitTime;
    }

    public String getDistriDetail() {
        return distriDetail;
    }

    public void setDistriDetail(String distriDetail) {
        this.distriDetail = distriDetail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "ShopInfoEntity{" +
                "avatar='" + avatar + '\'' +
                ", shopName='" + shopName + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", userName='" + userName + '\'' +
                ", businessTime='" + businessTime + '\'' +
                ", distriwaitTime='" + distriwaitTime + '\'' +
                ", distriDetail='" + distriDetail + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", area='" + area + '\'' +
                ", telPhone='" + telPhone + '\'' +
                ", address='" + address + '\'' +
                ", longitud='" + longitud + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
