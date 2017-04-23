package com.kaichaohulian.baocms.qiniu;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by huashao on 2016/10/29.
 */

public class UrlSafeBase64 {
    public UrlSafeBase64() {
    }

    public static String encodeToString(String data) {
        try {
            return encodeToString(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String encodeToString(byte[] data) {
        return Base64.encodeToString(data, 10);
    }

    public static byte[] decode(String data) {
        return Base64.decode(data, 10);
    }
}
