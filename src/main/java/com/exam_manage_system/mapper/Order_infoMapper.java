package com.exam_manage_system.mapper;

import com.exam_manage_system.entity.Order_info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Order_infoMapper {
    @Insert("INSERT INTO order_info (order_no,order_time,order_state,order_money)" +
            " VALUES (#{order_no},#{order_time},#{order_state},#{order_money})")
    int insert_into_order_info(String order_no, String order_time, int order_state, int order_money);

    @Select("select * from order_info where order_no=#{order_no}")
    Order_info get_order_info(String order_no);
}
