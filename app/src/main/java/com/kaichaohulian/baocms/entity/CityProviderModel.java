package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * 类描述：省市级联
 * 作者：mac on 16-5-30 23:45
 * 邮箱：eyetony@sina.cn
 * 修改人：
 * 修改时间：on 16-5-30 23:45
 * 修改备注：
 */
public class CityProviderModel implements Serializable {
    private String id;//": "110000",
    private String name;//": "北京",
    private String fullname;//": "北京市",
    private String pinyin;//,
    private String location;
    private String cidx; //子集所在索引

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCidx() {
        return cidx;
    }

    public void setCidx(String cidx) {
        this.cidx = cidx;
    }
}
