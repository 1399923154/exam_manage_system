package com.exam_manage_system.controller;

import com.exam_manage_system.service.S_User_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class C_Ajax {
    @Autowired
    private S_User_Info s_user_info;


    @RequestMapping(value = "send_check_no", method = RequestMethod.POST)
    //让其返回的是json数据而不是模板
    @ResponseBody
    public String send_check_no(HttpServletRequest request) throws MessagingException {
        String email=request.getParameter("email");
        //发送邮件并获取邮件的验证码
        SendTextMail sendTextMail=new SendTextMail();
        String check_code=sendTextMail.SendMail("1399923154@qq.com",email);
        return check_code;
    }

    @RequestMapping(value = "insert_into_user_info", method = RequestMethod.POST)
    @ResponseBody
    public int insert_into_user_info(HttpServletRequest request){
        int user_id=Integer.parseInt(request.getParameter("user_id"));
        String user_name=request.getParameter("user_name");
        String user_pwd=request.getParameter("user_pwd");

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        String join_time=df.format(new Date());

        String user_type=request.getParameter("user_type");
        String email=request.getParameter("email");


        //插入
        int flag=this.s_user_info.insert_into_user_info(user_id,user_name,user_pwd,join_time,user_type,email);

        return flag;
    }
}
