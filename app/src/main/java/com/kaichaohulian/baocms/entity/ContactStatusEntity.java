package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Jimq on 2017/3/5
 */

public class ContactStatusEntity implements Serializable {

    private int id;

    private int uId;

    private int cUId;

    private String flag;

    private String cuImg;

    private String cuname;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public int getUId() {
        return this.uId;
    }

    public void setCUId(int cUId) {
        this.cUId = cUId;
    }

    public int getCUId() {
        return this.cUId;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return this.flag;
    }

    public void setCuImg(String cuImg) {
        this.cuImg = cuImg;
    }

    public String getCuImg() {
        return this.cuImg;
    }

    public void setCuname(String cuname) {
        this.cuname = cuname;
    }

    public String getCuname() {
        return this.cuname;
    }
}
