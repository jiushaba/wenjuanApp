package com.jianshushe.wenjuan.model;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.model
 * 文件名：Form
 * 创建者：jiushaba
 * 创建时间：2018/1/9 0009上午 11:29
 * 描述： TODO
 */
public class Form extends DataSupport{
    private String key;
    private String surveyID;
    private String startdate;
    private String enddate;
    private String spotName;

    public List<answers> list = new ArrayList<>();

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }
}
