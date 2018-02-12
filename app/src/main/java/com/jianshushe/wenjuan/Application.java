package com.jianshushe.wenjuan;

import com.jianshushe.wenjuan.util.AssetCopyer;

import org.litepal.LitePalApplication;

import java.io.IOException;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan
 * 文件名：Application
 * 创建者：jiushaba
 * 创建时间：2018/1/5 0005上午 12:15
 * 描述： TODO
 */
public class Application extends LitePalApplication {

    AssetCopyer assetCopyer;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            assetCopyer = new AssetCopyer(this);
            assetCopyer.copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

