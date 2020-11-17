package com.exam_manage_system.controller;

import com.exam_manage_system.entity.User_info;
import com.exam_manage_system.service.S_User_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Controller
public class C_User_Info {
    @Autowired
    private S_User_Info s_user_info;

    //索引到登录页面
    @RequestMapping("login")
    public String to_Login(){
        return "login";
    }

    //验证登录
    @RequestMapping("check_login")
    public String check_Login(HttpServletRequest request, Model model, HttpSession session){
        //先去session中判断是否有值
        if(session.getAttribute("user")!=null){
            System.out.println("session中有账户信息（已登录）");
            //去获取session中的信息，判断用户类型，然后跳转到相应的界面
            User_info user_info= (User_info) session.getAttribute("user");
            //根据session中的信息跳转到不同页面
            if (user_info.getUser_type()==0){
                return "student_index";
            }else if (user_info.getUser_type()==1){
                return "teacher_index";
            }else {
                return "admin_index";
            }

        }else {
            System.out.println("用户还未登录，进行登录验证");
            //获取登录页面传来的值
            int user_id=Integer.parseInt(request.getParameter("user_id"));
            String user_pwd=request.getParameter("user_pwd");
            //用户类型：0表示学生 1表示老师 2表示管理员
            int user_type=Integer.parseInt(request.getParameter("user_type"));
            String type="";

            //新建用户对象，调用service层的check_login方法然后去数据库进行判断
            User_info user_info=this.s_user_info.check_login(user_id,user_pwd,user_type);
            if (user_info==null){
                System.out.println("登录失败，请检查用户名，密码以及职称");
                return "redirect:login";
            }else {
                //将信息存入session
                session.setAttribute("user",user_info);

                if (user_info.getUser_type()==0){
                    type="学生";
                    System.out.println("你好学生");
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    return "student_index";
                }else if (user_info.getUser_type()==1){
                    type="老师";
                    System.out.println("你好老师");
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    return "teacher_index";
                }else {
                    type="管理员";
                    System.out.println("你好管理员");
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    return "admin_index";
                }
            }
        }


        /*//设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // new Date()为获取当前系统时间
        System.out.println(df.format(new Date()));

*/
    }
    //退出登录
    @RequestMapping("layout")
    public String layout(HttpSession session) throws Exception {
        session.removeAttribute("user");
        System.out.println("session数据已清除");
        return "login";
    }

    /*注册功能实现*/

    //索引到注册页面
    @RequestMapping("sign_in")
    public String to_signin(Model model){
        model.addAttribute("message","*");
        return "sign_in";

    }

    //注册事务
    @RequestMapping("signin_affair")
    public String signin_affair(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws MessagingException {
        //获取注册页面传来的信息
        int user_id=Integer.parseInt(request.getParameter("user_id"));
        String user_name=request.getParameter("user_name");
        String user_pwd=request.getParameter("user_pwd");
        String email=request.getParameter("email");
        String user_type=request.getParameter("user_type");

        User_info user_info=new User_info();
        //检查注册信息是否和数据库中的重复
        if (this.s_user_info.id_to_find_user_info(user_id)!=null){
            //此时id重复
            model.addAttribute("message","此id已经被注册过，请重新输入！");
            return "sign_in";
        }else if (this.s_user_info.email_to_find_user_info(email)!=null){
            //此时email重复
            model.addAttribute("message","此email已经被注册过，请重新输入！");
            return "sign_in";
        }else {
            //说明email和id都可以用，此时进行邮箱验证码验证！
  /*


            //携带参数+重定向到邮箱验证(此种携带参数的方式，可在定向请求所对应的页面直接用jstl取值，不需要在下一个请求获取参数再转发到前端)
            /*redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("user_id", user_id);
            return "redirect:email_check";*/
            model.addAttribute("email", email);
            model.addAttribute("user_id", user_id);
            model.addAttribute("user_name", user_name);
            model.addAttribute("user_pwd", user_pwd);
            model.addAttribute("user_type", user_type);
            return "email_check";
        }
    }


}
