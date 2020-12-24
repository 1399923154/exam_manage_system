package com.exam_manage_system.controller;

import com.exam_manage_system.entity.Department_dictionary;
import com.exam_manage_system.entity.User_info;
import com.exam_manage_system.service.S_User_Info;
import com.exam_manage_system.service.S_department_dictionary;
import com.exam_manage_system.service.S_test_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class C_Ajax {
    @Autowired
    private S_User_Info s_user_info;
    @Autowired
    private S_department_dictionary s_department_dictionary;
    @Autowired
    private S_test_info s_test_info;

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

    //此ajax用于返回 院系/专业字典列表（最终是为了实现院系专业二级联动）
    @RequestMapping(value = "get_department_list")
    @ResponseBody
    public List<Department_dictionary> getDeaprtmentList(HttpServletRequest request) {
        String dictionary_code = request.getParameter("dictionary_code");
        return s_department_dictionary.getFieldList(dictionary_code);
    }


    @RequestMapping("insert_into_regist_inform")
    @ResponseBody
    public int insert_into_regist_inform(HttpServletRequest request, Model model, HttpSession session){
        String regist_name=request.getParameter("regist_name");
        String type=request.getParameter("type");
        String exam_type=request.getParameter("exam_type");

        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        String regist_time=df.format(new Date());

        /*生成八位随机数*/
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            rs.append(r.nextInt(10));
        }

        String order_no=rs.toString();
        User_info user_info= (User_info) session.getAttribute("user");


        //插入
        int flag=this.s_test_info.insert_into_regist_inform(user_info.getIdentity_card(),type,exam_type,order_no,regist_name,regist_time);
        if (flag==1){
            System.out.println("报名成功，代缴费，科目为"+type+":"+exam_type);
        }
        return flag;
    }

}
