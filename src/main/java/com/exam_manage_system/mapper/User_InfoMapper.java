package com.exam_manage_system.mapper;

import com.exam_manage_system.entity.User_info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface User_InfoMapper {
    @Select("select * from user_info where user_id=#{user_id} and user_pwd=#{user_pwd} and user_type=#{user_type}")
    User_info check_login(int user_id, String user_pwd, int user_type);

    @Select("select * from user_info where user_id=#{user_id}")
    User_info id_to_find_user_info(int user_id);

    @Select("select * from user_info where email=#{email}")
    User_info email_to_find_user_info(String email);

    //插入信息到user_info表
    @Insert("insert into user_info(user_id, user_name,user_pwd,join_time,user_type,email) values(" +
            "#{user_id}, #{user_name}, #{user_pwd},#{join_time},#{user_type},#{email})")
    int insert_into_user_info(int user_id, String user_name, String user_pwd, String join_time, String user_type, String email);

    //修改user_info表绑定信息
    @Update("UPDATE user_info set college_name=#{college_name},department_name=#{department_name},profession_name=#{profession_name},identity_card=#{identity_code}" +
            "WHERE user_id=#{user_id}")
    int bind_infomation(int user_id, String college_name, String department_name, String profession_name, String identity_code);
}
