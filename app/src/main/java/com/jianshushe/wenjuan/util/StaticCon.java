package com.jianshushe.wenjuan.util;

import android.content.Context;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.util
 * 文件名：StaticCon
 * 创建者：jiushaba
 * 创建时间：2018/1/5 0005下午 12:01
 * 描述： TODO
 */
public class StaticCon {


    public static final String BASE_URL = "http://192.168.1.104:9900";
    public static final String BASE_URL_SUB = "http://192.168.1.104:9900";


    public static String url(Context context) {
        String ip = ShareUtils.getString(context, "ip", "");
        String port = ShareUtils.getString(context, "port", "");
        return "http://" +"192.168.1.104" + ":" + "9900";
    }
}
