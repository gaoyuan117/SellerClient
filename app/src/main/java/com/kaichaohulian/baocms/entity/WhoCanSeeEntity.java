package com.kaichaohulian.baocms.entity;

/**
 * Created by LMS on 2017/2/18.
 */

public class WhoCanSeeEntity {

    private String key;
    private String value;
    private boolean isSelect;

    public WhoCanSeeEntity(String key, String value, boolean isSelect) {
        this.key = key;
        this.value = value;
        this.isSelect = isSelect;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
