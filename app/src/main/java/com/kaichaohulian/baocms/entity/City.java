package com.kaichaohulian.baocms.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyu on 2016/12/21.
 */

public class City extends Area {
    private String provinceId;
    private List<County> counties = new ArrayList<County>();

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }
}
