package com.kaichaohulian.baocms.entity;

/**
 * Created by HuaSao1024 on 2017/1/18.
 */

public class ShopEntity {

    private int shopId;       // 店铺id
    private int userId;       // 用户id
    private String shopName;  // 商户名称
    private String logo;       // 产品logo
    private String photo;      // 形象照
    private String cateName;   // 分类
    private String header; // 首字母

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "shopId=" + shopId +
                ", userId=" + userId +
                ", shopName='" + shopName + '\'' +
                ", logo='" + logo + '\'' +
                ", photo='" + photo + '\'' +
                ", cateName='" + cateName + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}
