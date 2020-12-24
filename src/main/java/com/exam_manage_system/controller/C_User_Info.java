package com.exam_manage_system.controller;

import com.exam_manage_system.entity.User_info;
import com.exam_manage_system.service.S_User_Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class C_User_Info {
    @Autowired
    private S_User_Info s_user_info;

    //索引到首页（门户网站）
    @RequestMapping("index")
    public String to_index(HttpServletRequest request, Model model, HttpSession session){
        if(session.getAttribute("user")!=null){
            //从session中获取用户名并传递文字信息
            //去获取session中的信息，判断用户类型等，
            User_info user_info= (User_info) session.getAttribute("user");


            model.addAttribute("message","已登录！");
            model.addAttribute("user_name",user_info.getUser_name());
            return "index";
        }else {
            //传递文字信息
            model.addAttribute("message","未登录！");
           /* return "redirect:index";*/
            return "index";
        }

    }

    //跳转到个人中心
    @RequestMapping("to_personal_center")
        public String to_personal_center(HttpServletRequest request, Model model, HttpSession session){
        if(session.getAttribute("user")!=null){
            System.out.println("session中有账户信息（已登录）");
            //去获取session中的信息，判断用户类型等，
            User_info user_info= (User_info) session.getAttribute("user");
            //就算session中有值也需要去时刻更新session
             //更新session
            User_info user_info_new=this.s_user_info.check_login(user_info.getUser_id(),user_info.getUser_pwd(),user_info.getUser_type());
            session.removeAttribute("user");
            session.setAttribute("user",user_info_new);
            /*User_info user_info2= (User_info) session.getAttribute("user");
            System.out.println(user_info2.getCollege_name());*/

            if (user_info_new.getIdentity_card().equals("")){
                model.addAttribute("message1","为了不影响报名，请先绑定基础信息！");
            }

            String type="";
            if (user_info.getUser_type()==0){
                type="学生";
                System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                model.addAttribute("type",type);
                //跳转到个人中心
                return "personal_center";
            }else if (user_info.getUser_type()==1){
                type="老师";
                System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                model.addAttribute("type",type);
                //跳转到个人中心
                return "personal_center";
            }else {
                type="管理员";
                System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                model.addAttribute("type",type);
                //跳转到个人中心
                return "personal_center";
            }
        }else {
            //跳转到登录
            model.addAttribute("message","请先登录！");
            return "login";
        }

        }


    //索引到登录页面
    @RequestMapping("to_login")
    public String to_Login( Model model){
        model.addAttribute("message","请登录");
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

            //就算session中有值也需要去时刻更新session
            //更新session
            User_info user_info_new=this.s_user_info.check_login(user_info.getUser_id(),user_info.getUser_pwd(),user_info.getUser_type());
            session.removeAttribute("user");
            session.setAttribute("user",user_info_new);



            //跳转到首页
            return "redirect:index";

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
                return "redirect:to_login";
            }else {
                //将信息存入session
                session.setAttribute("user",user_info);
/*                if (user_info.getUser_type()==0){
                    type="学生";
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    model.addAttribute("type",type);
                    return "index";
                }else if (user_info.getUser_type()==1){
                    type="老师";
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    model.addAttribute("type",type);
                    return "index";
                }else {
                    type="管理员";
                    System.out.println("验证成功，欢迎您：|"+type+"| "+user_info.getUser_name());
                    model.addAttribute("type",type);
                    return "index";
                }*/
                //顺便去看一下关键信息有没有绑定，如果没有弹出提示框


                return "redirect:index";
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

    //(修改)绑定信息
    @RequestMapping("bind_information")
    private String bind_information(HttpServletRequest request, Model model,HttpSession session){
        String college_name=request.getParameter("college_name");
        String department_name=request.getParameter("department_name");
        String profession_name=request.getParameter("profession_name");
        String identity_code=request.getParameter("identity_card");
        //System.out.println(college_name+","+department_name+","+profession_name+","+identity_code);
        //往数据库user_info表中更新信息(其实就是添加了绑定好的信息)
        User_info user_info= (User_info) session.getAttribute("user");
        int flag=this.s_user_info.bind_infomation(user_info.getUser_id(),college_name,department_name,profession_name,identity_code);

        //session已经在上面更新了
        return "redirect:to_personal_center";
    }

    //跳转到考试报名页面
    @RequestMapping("to_test_register")
    private String to_test_register(){
        return "test_register";
    }
}
