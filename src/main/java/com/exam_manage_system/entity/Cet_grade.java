package com.exam_manage_system.entity;

import java.util.Date;

public class Cet_grade {
    private int cet_grade_id;
    private String identity_card;
    private String type;
    private String exam_type;
    private int grade;
    private Date exam_time;

    public Date getExam_time() {
        return exam_time;
    }

    public void setExam_time(Date exam_time) {
        this.exam_time = exam_time;
    }

    public int getCet_grade_id() {
        return cet_grade_id;
    }

    public void setCet_grade_id(int cet_grade_id) {
        this.cet_grade_id = cet_grade_id;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExam_type() {
        return exam_type;
    }

    public void setExam_type(String exam_type) {
        this.exam_type = exam_type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
