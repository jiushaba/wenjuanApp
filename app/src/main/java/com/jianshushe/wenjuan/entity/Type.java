package com.jianshushe.wenjuan.entity;

import java.io.Serializable;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity
 * 文件名：Type
 * 创建者：jiushaba
 * 创建时间：2018/1/3 0003下午 7:08
 * 描述： TODO
 */
public class Type implements Serializable {
    private int optionId;
    private String name;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
