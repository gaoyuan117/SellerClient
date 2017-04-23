package com.kaichaohulian.baocms.entity;

/**
 * Created by limengshuai on 2017/2/16.
 */

public class DialogListViewItem {

    CharSequence text;

    public DialogListViewItem(CharSequence text) {
        this.text = text;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }
}
