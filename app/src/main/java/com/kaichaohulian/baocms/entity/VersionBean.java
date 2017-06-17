package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/6/13.
 */

public class VersionBean {

    /**
     * code : 0
     * errorDescription : 获取版本号成功
     * dataObject : {"id":1,"androidVersion":"1.0","androidPath":"C:\\Users\\jamlee\\Desktop\\aaa\\SellerNet\\src\\main\\webapp\\apk","iosVersion":"1.0","iosPath":"123123"}
     */


    /**
     * id : 1
     * androidVersion : 1.0
     * androidPath : C:\Users\jamlee\Desktop\aaa\SellerNet\src\main\webapp\apk
     * iosVersion : 1.0
     * iosPath : 123123
     */

    private int id;
    private String androidVersion;
    private String androidPath;
    private String iosVersion;
    private String iosPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getAndroidPath() {
        return androidPath;
    }

    public void setAndroidPath(String androidPath) {
        this.androidPath = androidPath;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getIosPath() {
        return iosPath;
    }

    public void setIosPath(String iosPath) {
        this.iosPath = iosPath;
    }
}
