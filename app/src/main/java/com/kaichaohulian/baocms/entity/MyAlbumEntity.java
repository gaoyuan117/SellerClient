package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * 相册
 * Created by ljl on 2016/12/29.
 */

public class MyAlbumEntity {

    private String createTime;
    private String  content;
    private List<String> List;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getList() {
        return List;
    }

    public void setList(java.util.List<String> list) {
        List = list;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
