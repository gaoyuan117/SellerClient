package com.kaichaohulian.baocms.qiniu;


//import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSON;

/**
 * Created by huashao on 2016/10/29.
 */

public class Json {
    private Json() {
    }

    public static String encode(StringMap map) {

        return JSON.toJSONString(map.map());
    }

}
