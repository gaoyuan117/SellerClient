package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/20.
 */

public class CityEntity implements Serializable {

    private int cityId;
    private int areaId;
    private String name;
    private String areaName;
    private String pinyin;
    private boolean isOpen;
    private String lng;
    private String lat;
    private String theme;
    private int orderby;
    private String firstLetter;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getOrderby() {
        return orderby;
    }

    public void setOrderby(int orderby) {
        this.orderby = orderby;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "cityId=" + cityId +
                ", areaId=" + areaId +
                ", name='" + name + '\'' +
                ", areaName='" + areaName + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", isOpen=" + isOpen +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", theme='" + theme + '\'' +
                ", orderby=" + orderby +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }
}
