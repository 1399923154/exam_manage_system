package com.exam_manage_system.entity;

import java.util.Date;

public class Exam_info {
    private int exam_id;
    private String exam_name;
    private String type;
    private String exam_type;
    private int exam_state;
     private Date exam_time;
    private Date exam_regist_start;
    private Date exam_regist_end;
    private Date admission_ticket_printing_time;

    public int getExam_id() {
        return exam_id;
    }

    public void setExam_id(int exam_id) {
        this.exam_id = exam_id;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
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

    public int getExam_state() {
        return exam_state;
    }

    public void setExam_state(int exam_state) {
        this.exam_state = exam_state;
    }

    public Date getExam_time() {
        return exam_time;
    }

    public void setExam_time(Date exam_time) {
        this.exam_time = exam_time;
    }

    public Date getExam_regist_start() {
        return exam_regist_start;
    }

    public void setExam_regist_start(Date exam_regist_start) {
        this.exam_regist_start = exam_regist_start;
    }

    public Date getExam_regist_end() {
        return exam_regist_end;
    }

    public void setExam_regist_end(Date exam_regist_end) {
        this.exam_regist_end = exam_regist_end;
    }

    public Date getAdmission_ticket_printing_time() {
        return admission_ticket_printing_time;
    }

    public void setAdmission_ticket_printing_time(Date admission_ticket_printing_time) {
        this.admission_ticket_printing_time = admission_ticket_printing_time;
    }
}
