package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/20.
 */

public class BusinessAllEntity implements Serializable {

    private int businessId;

    private String businessName;

    private int areaId;

    private int orderby;

    private boolean isHot;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    @Override
    public String toString() {
        return "BusinessAllEntity{" +
                "businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", areaId=" + areaId +
                ", orderby=" + orderby +
                ", isHot=" + isHot +
                '}';
    }
}
