package com.exam_manage_system.service;

import com.exam_manage_system.entity.Order_info;
import com.exam_manage_system.mapper.Order_infoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Order_info {
    @Autowired
    Order_infoMapper order_infoMapper;

    public Order_info get_order_info(String order_no) {
        return order_infoMapper.get_order_info(order_no);
    }
}
