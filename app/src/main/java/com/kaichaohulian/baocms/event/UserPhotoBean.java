package com.kaichaohulian.baocms.event;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/6.
 */

public class UserPhotoBean {

    /**
     * code : 0
     * errorDescription : 返回朋友圈相册信息成功
     * dataObject : ["[\"http:\\/\\/oez2a4f3v.bkt.clouddn.com\\/Fu8arndkMRr3svz8GT-XAicS_Lo4\",\"http:\\/\\/oez2a4f3v.bkt.clouddn.com\\/Fr6Ml60kx8GxMiFdCmfVo39jSNVQ\"]","[\"http:\\/\\/oez2a4f3v.bkt.clouddn.com\\/FlQAVuUGaTPZFLonGsQTF2hXriws\"]"]
     */

    private int code;
    private String errorDescription;
    private List<String> dataObject;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<String> getDataObject() {
        return dataObject;
    }

    public void setDataObject(List<String> dataObject) {
        this.dataObject = dataObject;
    }
}
