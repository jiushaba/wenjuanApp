package com.jianshushe.wenjuan.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity
 * 文件名：Survey
 * 创建者：jiushaba
 * 创建时间：2018/1/3 0003下午 6:34
 * 描述： TODO
 */
public class Survey implements Serializable {
    private String name;
    private int surveyid;
    public List<Questions> questions = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSurveyid() {
        return surveyid;
    }

    public void setSurveyid(int surveyid) {
        this.surveyid = surveyid;
    }
}
