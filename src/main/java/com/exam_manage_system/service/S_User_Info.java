package com.exam_manage_system.service;

import com.exam_manage_system.entity.User_info;
import com.exam_manage_system.mapper.User_InfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class S_User_Info {
 @Autowired
   private User_InfoMapper user_infoMapper;

    //验证登录
    public User_info check_login(int user_id, String user_pwd, int user_type) {
    User_info user_info=this.user_infoMapper.check_login(user_id,user_pwd,user_type);
    return user_info;
    }

    //注册验证
     //1.用user_id去查找用户信息
   public User_info id_to_find_user_info(int user_id){
    User_info user_info=this.user_infoMapper.id_to_find_user_info(user_id);
    return user_info;
   }
     //2.用user_id去查找用户信息
   public User_info email_to_find_user_info(String email){
     User_info user_info=this.user_infoMapper.email_to_find_user_info(email);
     return user_info;
 }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int insert_into_user_info(int user_id, String user_name, String user_pwd, String join_time, String user_type, String email) {
        int flag=0;
        flag=this.user_infoMapper.insert_into_user_info(user_id,user_name,user_pwd,join_time,user_type,email);
        return flag;
    }
}
