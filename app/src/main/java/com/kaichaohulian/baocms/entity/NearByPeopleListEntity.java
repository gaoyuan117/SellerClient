package com.kaichaohulian.baocms.entity;

/**
 * Created by liuyu on 2016/12/20.
 */

public class NearByPeopleListEntity {
    private  String name;
    private  String distance;
    private  String headIconURL;
    private  String mySign;
    private  String userId;
    private String industry;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getHeadIconURL() {
        return headIconURL;
    }

    public void setHeadIconURL(String headIconURL) {
        this.headIconURL = headIconURL;
    }

    public String getMySign() {
        return mySign;
    }

    public void setMySign(String mySign) {
        this.mySign = mySign;
    }
}
