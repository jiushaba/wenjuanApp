package com.jianshushe.wenjuan.entity.ChartEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.entity.ChartEntity
 * 文件名：Questions
 * 创建者：jiushaba
 * 创建时间：2018/1/4 0004上午 11:42
 * 描述： TODO
 */
public class Questions implements Serializable {

    private String questionName;
    private int type;
    public List<Answers> list=new ArrayList<>();
    public List<Type2> type2List = new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
