package com.exam_manage_system.entity;

import java.util.Date;

public class Registration_information {
    private int registration_id;
    private String identity_card;
    private String type;
    private String exam_type;
    private int state;
    private String order_no;
    private String regist_name;
    private Date regist_time;

    public String getRegist_name() {
        return regist_name;
    }

    public void setRegist_name(String regist_name) {
        this.regist_name = regist_name;
    }

    public Date getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(Date regist_time) {
        this.regist_time = regist_time;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
