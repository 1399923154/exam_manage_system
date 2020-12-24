package com.exam_manage_system.service;

import com.exam_manage_system.entity.Cet_grade;
import com.exam_manage_system.entity.Exam_info;
import com.exam_manage_system.entity.Registration_information;
import com.exam_manage_system.mapper.Order_infoMapper;
import com.exam_manage_system.mapper.Test_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class S_test_info {
    @Autowired
    private Test_InfoMapper test_infoMapper;
    @Autowired
    private Order_infoMapper order_infoMapper;

    public Registration_information get_regist_info(String identity_card, String type) {
        Registration_information registration_information=test_infoMapper.get_regist_info(identity_card,type);
        return registration_information;
    }

    public Cet_grade get_cet_grade(String identity_card, String s) {
        Cet_grade cet_grade=test_infoMapper.get_cet_grade(identity_card,s);
        return cet_grade;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int insert_into_regist_inform(String identity_card, String type, String exam_type, String order_no, String regist_name, String regist_time) {
        int flag=test_infoMapper.insert_into_regist_inform(identity_card,type,exam_type,order_no,regist_name,regist_time);
        //同时往order_info表中插入一条记录，但支付状态0为未支付
        int order_money=30;
        flag=order_infoMapper.insert_into_order_info(order_no,regist_time,0,order_money);
        return flag;
    }

    public List<Registration_information> get_regist_info_list(String identity_card, String type) {
        return test_infoMapper.get_regist_info_list(identity_card,type);
    }
}
