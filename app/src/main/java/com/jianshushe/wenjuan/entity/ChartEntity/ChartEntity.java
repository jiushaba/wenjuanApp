package com.jianshushe.wenjuan.entity.ChartEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity.ChartEntity
 * 文件名：ChartEntity
 * 创建者：jiushaba
 * 创建时间：2018/1/4 0004上午 11:42
 * 描述： TODO
 */
public class ChartEntity implements Serializable {

    private String name;
    public List<Questions> list = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
