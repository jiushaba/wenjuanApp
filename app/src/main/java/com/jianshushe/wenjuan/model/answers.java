package com.jianshushe.wenjuan.model;

import org.litepal.crud.DataSupport;

/**
 * 项目名：WenJuan
 * 包名 ： com.jianshushe.wenjuan.model
 * 文件名：answers
 * 创建者：jiushaba
 * 创建时间：2018/1/9 0009上午 11:30
 * 描述： TODO
 */
public class answers extends DataSupport {
    private int questionID;
    private String answer;
    private String formId;


    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
