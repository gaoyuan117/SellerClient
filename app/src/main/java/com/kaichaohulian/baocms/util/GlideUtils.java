package com.kaichaohulian.baocms.util;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.http.Url;

import java.net.URL;

/**
 * Created by admin on 2017/3/30.
 */

public class GlideUtils {

    /**
     * 加载普通图片
     * @param imgPath
     * @param imageView
     */
    public static void glideImg(String imgPath, ImageView imageView) {
        Glide.with(MyApplication.getInstance())
                .load("http://www.52yeli.com/" + imgPath)
                .error(R.mipmap.default_image)
                .crossFade()
                .into(imageView);
    }

    /**
     * 加载头像
     * @param imgPath
     * @param imageView
     */
    public static void glideAvatar(String imgPath, ImageView imageView) {
        Glide.with(MyApplication.getInstance())
                .load("http://www.52yeli.com/" + imgPath)
                .error(R.mipmap.default_useravatar)
                .crossFade()
                .into(imageView);
    }

    public static void glideQiNiuAvatar(String imgPath, ImageView imageView) {
        Glide.with(MyApplication.getInstance())
                .load("http://oez2a4f3v.bkt.clouddn.com/" + imgPath)
                .error(R.mipmap.default_useravatar)
                .crossFade()
                .into(imageView);
    }

    public static void glideQiNiuImg(String imgPath, ImageView imageView) {
        Glide.with(MyApplication.getInstance())
                .load("http://oez2a4f3v.bkt.clouddn.com/" + imgPath)
                .error(R.mipmap.default_image)
                .crossFade()
                .into(imageView);
    }

}
