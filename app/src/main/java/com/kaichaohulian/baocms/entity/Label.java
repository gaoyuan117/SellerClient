package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * Created by Adminis on 2017/1/17.
 */
public class Label implements Serializable {
    private int labelId;
    private String name = "";
    private int labelCounts;

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLabelCounts() {
        return labelCounts;
    }

    public void setLabelCounts(int labelCounts) {
        this.labelCounts = labelCounts;
    }
}
