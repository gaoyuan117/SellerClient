package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by liuyu on 2016/12/21.
 */

public class MeAddressEntity implements Serializable{

    String shouHuoname;
    String address;
    String postCode;
    String area;
    String shouHuoPhone;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    String addressId;


    public String getShouHuoname() {
        return shouHuoname;
    }

    public void setShouHuoname(String shouHuoname) {
        this.shouHuoname = shouHuoname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShouHuoPhone() {
        return shouHuoPhone;
    }

    public void setShouHuoPhone(String shouHuoPhone) {
        this.shouHuoPhone = shouHuoPhone;
    }
}
