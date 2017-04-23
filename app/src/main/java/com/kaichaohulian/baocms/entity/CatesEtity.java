package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/20.
 */

public class CatesEtity implements Serializable {

    private int cateId;
    private String cateName;
    private int parentId;
    private int orderby;
    private boolean isHot;
    private String d1;
    private String d2;
    private String d3;
    private String title;

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CatesEtity{" +
                "cateId=" + cateId +
                ", cateName='" + cateName + '\'' +
                ", parentId=" + parentId +
                ", orderby=" + orderby +
                ", isHot=" + isHot +
                ", d1='" + d1 + '\'' +
                ", d2='" + d2 + '\'' +
                ", d3='" + d3 + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
