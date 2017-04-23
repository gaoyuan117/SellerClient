package com.kaichaohulian.baocms.entity;

/**
 * Created by kenton on 2017/2/9.
 */

public class RecentlyContact {
    private String sessionId;  // 名称
    private String shopName;  // 名称
    private String logo;       // 产品logo
    private String header; // 首字母

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
