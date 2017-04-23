package com.kaichaohulian.baocms.entity;

/**
 * Created by kenton on 2017/2/8.
 */

public class ItemMenu {
    public String text;
    public int id;

    public ItemMenu(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return text;
    }
}
