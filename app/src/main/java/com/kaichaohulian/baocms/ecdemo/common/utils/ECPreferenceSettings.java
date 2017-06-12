/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.kaichaohulian.baocms.ecdemo.common.utils;

/**
 * @author Jorstin Chan@容联•云通讯
 * @date 2014-12-10
 * @version 4.0
 */
public enum ECPreferenceSettings {

    /**
     * Whether is the first use of the application
     *
     */
    SETTINGS_FIRST_USE("com.kaichaohulian.baocms_first_use" , Boolean.TRUE),
    /**坚持云通讯登陆账号*/
    SETTINGS_YUNTONGXUN_ACCOUNT("com.kaichaohulian.baocms_yun_account" , ""),
    /**检查是否需要自动登录*/
    SETTINGS_REGIST_AUTO("com.kaichaohulian.baocms_account" , ""),
    /**是否使用回车键发送消息*/
    SETTINGS_ENABLE_ENTER_KEY("com.kaichaohulian.baocms_sendmessage_by_enterkey" , Boolean.TRUE),
    /**聊天键盘的高度*/
    SETTINGS_KEYBORD_HEIGHT("com.kaichaohulian.baocms_keybord_height" , 0),
    /**新消息声音*/
    SETTINGS_NEW_MSG_SOUND("com.kaichaohulian.baocms_new_msg_sound" , true),
    /**新消息震动*/
    SETTINGS_NEW_MSG_SHAKE("com.kaichaohulian.baocms_new_msg_shake" , true),
    SETTING_CHATTING_CONTACTID("com.kaichaohulian.baocms_chatting_contactid" , ""),
    /**图片缓存路径*/
    SETTINGS_CROPIMAGE_OUTPUTPATH("com.kaichaohulian.baocms_CropImage_OutputPath" , ""),





//    SETTINGS_APPKEY("com.kaichaohulian.baocms_appkey" , "8a216da858ce0b3c0158d8b1e2a00840"),
//   SETTINGS_TOKEN("com.kaichaohulian.baocms_token" , "0c54dbad19d314dd37fb4b4872a41529"),

    SETTINGS_APPKEY("com.kaichaohulian.baocms_appkey" , "8aaf07085c62aa66015c85afe6cb0d58"),
    SETTINGS_TOKEN("com.kaichaohulian.baocms_token" , "51d011f971861cf172eeffdbd4770d66"),





    SETTINGS_ABSOLUTELY_EXIT("com.kaichaohulian.baocms_absolutely_exit" , Boolean.FALSE),
    SETTINGS_FULLY_EXIT("com.kaichaohulian.baocms_fully_exit" , Boolean.FALSE),
    SETTINGS_PREVIEW_SELECTED("com.kaichaohulian.baocms_preview_selected" , Boolean.FALSE),
    SETTINGS_OFFLINE_MESSAGE_VERSION("com.kaichaohulian.baocms_offline_version" , 0),
    /**设置是否是匿名聊天*/
    SETTINGS_SHOW_CHATTING_NAME("com.kaichaohulian.baocms_show_chat_name" , false),
    
    SETTINGS_CUSTOM_APPKEY("com.kaichaohulian.baocms_custom_appkey" , ""),
    SETTINGS_CUSTOM_TOKEN("com.kaichaohulian.baocms_custom_token" , ""),
    SETTINGS_SERVER_CUSTOM("com.kaichaohulian.baocms_setserver" , false),
    SETTINGS_NOTICE_CUSTOM("com.kaichaohulian.baocms_notice" , Boolean.FALSE),
    SETTINGS_RATIO_CUSTOM("com.kaichaohulian.baocms_ratio" , ""),
    SETTINGS_AT("com.kaichaohulian.baocms_at_self" , ""), ;


    private final String mId;
    private final Object mDefaultValue;

    /**
     * Constructor of <code>CCPPreferenceSettings</code>.
     * @param id
     *            The unique identifier of the setting
     * @param defaultValue
     *            The default value of the setting
     */
    private ECPreferenceSettings(String id, Object defaultValue) {
        this.mId = id;
        this.mDefaultValue = defaultValue;
    }

    /**
     * Method that returns the unique identifier of the setting.
     * @return the mId
     */
    public String getId() {
        return this.mId;
    }

    /**
     * Method that returns the default value of the setting.
     *
     * @return Object The default value of the setting
     */
    public Object getDefaultValue() {
        return this.mDefaultValue;
    }

    /**
     * Method that returns an instance of {@link ECPreferenceSettings} from
     * its. unique identifier
     *
     * @param id
     *            The unique identifier
     * @return CCPPreferenceSettings The navigation sort mode
     */
    public static ECPreferenceSettings fromId(String id) {
        ECPreferenceSettings[] values = values();
        int cc = values.length;
        for (int i = 0; i < cc; i++) {
            if (values[i].mId == id) {
                return values[i];
            }
        }
        return null;
    }
}
