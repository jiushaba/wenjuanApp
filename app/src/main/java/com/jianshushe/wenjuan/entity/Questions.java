package com.jianshushe.wenjuan.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity
 * 文件名：Questions
 * 创建者：jiushaba
 * 创建时间：2018/1/3 0003下午 7:07
 * 描述： TODO
 */
public class Questions implements Serializable {
    private String name;
    private int type;
    private int must;
    private String des;
    private String subName;
    private int questionId;
    public List<Type> typeList = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMust() {
        return must;
    }

    public void setMust(int must) {
        this.must = must;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
