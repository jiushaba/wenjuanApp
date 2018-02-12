package com.jianshushe.wenjuan.model;

public class Submit {

    private int id;
    private String problem_id;
    private int problem_type;
    private String choose;
    private String problem_name;
    private String submit_address;
    private String x;
    private String y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }

    public int getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(int problem_type) {
        this.problem_type = problem_type;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public String getSubmit_address() {
        return submit_address;
    }

    public void setSubmit_address(String submit_address) {
        this.submit_address = submit_address;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
