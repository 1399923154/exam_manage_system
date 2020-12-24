package com.exam_manage_system.mapper;

import com.exam_manage_system.entity.Cet_grade;
import com.exam_manage_system.entity.Exam_info;
import com.exam_manage_system.entity.Registration_information;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Test_InfoMapper {



    @Select("select * from registration_information where identity_card=#{identity_card} and type=#{type}")
    Registration_information get_regist_info(String identity_card, String type);

    @Select("SELECT *  from cet_grade WHERE ((\n" +
            "grade=(SELECT  MAX(grade) FROM cet_grade\n" +
            " WHERE (identity_card=#{identity_card} AND exam_type=#{s}\n" +
            ")) )AND (identity_card=#{identity_card} AND exam_type=#{s}))GROUP BY grade")
    Cet_grade get_cet_grade(String identity_card, String s);

    @Insert("INSERT INTO registration_information (identity_card,type,exam_type,order_no,regist_name,regist_time)" +
            " VALUES (#{identity_card},#{type},#{exam_type},#{order_no},#{regist_name},#{regist_time})")
    int insert_into_regist_inform(String identity_card, String type, String exam_type, String order_no, String regist_name, String regist_time);

    @Select("select * from registration_information where identity_card=#{identity_card} and type=#{type}")
    List<Registration_information> get_regist_info_list(String identity_card, String type);
}
