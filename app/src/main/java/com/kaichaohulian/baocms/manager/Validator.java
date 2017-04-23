package com.kaichaohulian.baocms.manager;

import android.text.TextUtils;

import com.kaichaohulian.baocms.constant.IllegalTips;

public class Validator {

    /**
     * 判断token是否非法
     *
     * @param errorDesc
     * @return
     */
    public static boolean isTokenIllegal(String errorDesc) {
        if (TextUtils.isEmpty(errorDesc)) {
            return false;
        }

        if (IllegalTips.TOKEN_ILLEGAL.equals(errorDesc) || IllegalTips.TOKEN_NULL.equals(errorDesc) || IllegalTips.TOKEN_WEB.equals(errorDesc)) {
            return true;
        }
        return false;
    }

}
