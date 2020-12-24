package com.exam_manage_system.controller;

import com.exam_manage_system.entity.Cet_grade;
import com.exam_manage_system.entity.Order_info;
import com.exam_manage_system.entity.Registration_information;
import com.exam_manage_system.entity.User_info;
import com.exam_manage_system.service.S_Order_info;
import com.exam_manage_system.service.S_test_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class C_test  {
    @Autowired
    private S_test_info s_test_info;
    @Autowired
    private S_Order_info s_order_info;

    //跳转到cet考试介绍详细页面
    @RequestMapping("to_cet1")
    public String to_cet1(){
        return "cet1";
    }
    @RequestMapping("to_cet2")
    public String to_cet2(){
        return "cet2";
    }
    @RequestMapping("to_cet3")
    public String to_cet3(){
        return "cet3";
    }
    @RequestMapping("to_ncre1")
    public String to_cet4(){
        return "ncre1";
    }
    @RequestMapping("to_ncre2")
    public String to_ncre2(){
        return "ncre2";
    }
    @RequestMapping("to_ncre3")
    public String to_ncre3(){
        return "ncre3";
    }

    //点击在线报名
    @RequestMapping("online_rigistration")
    public String online_rigistration(HttpServletRequest request, Model model, HttpSession session){
        //验证是否已经登录
        if(session.getAttribute("user")!=null){
            //如果已经登录了，去验证信息是否完整
            //先去取session中的数据然后判断信息是否完整，不完整则跳转到个人中心去绑定信息.
            User_info user_info= (User_info) session.getAttribute("user");
            if (user_info.getIdentity_card().equals("")){
                System.out.println("基本信息未绑定，已前往绑定!");
                //跳转到个人信息页面去完善信息,并且弹出提示框
                return "redirect:to_personal_center";
            }else {
                System.out.println("基本信息已绑定！");
                //去报名信息确认页面

                //通过导航栏传递的数据去数据库查询相应的考试信息
                String type=request.getParameter("test_name");
                Registration_information registration_information=s_test_info.get_regist_info(user_info.getIdentity_card(),type);
                if (registration_information==null){
                    //说明未报名此科目
                    model.addAttribute("regist_state",0);
                    System.out.println("未报名"+type);
                }else {
                    //说明报名了此科目
                    System.out.println("已报名"+type);
                    model.addAttribute("regist_state",1);
                }

                model.addAttribute("test_name",type);
                return "confirm_information";
            }

        }else {
            //用户未登录  //跳转到登录页面
            return "redirect:to_login";
        }
    }

    //点击开始报名,进入报名协议页面
    @RequestMapping("to_DetailsAgreement")
    public String to_DetailsAgreement(HttpServletRequest request, Model model, HttpSession session){
        model.addAttribute("test_name",request.getParameter("test_name"));
        //跳到报名协议页面
        return "DetailsAgreement";
    }

    //点击同意协议后进入报名信息采集页面
    @RequestMapping("to_DetailsRegister")
    public String to_DetailsConfirm(HttpServletRequest request, Model model, HttpSession session){
        String test_name=request.getParameter("test_name");
        model.addAttribute("test_name",test_name);

        User_info user_info= (User_info) session.getAttribute("user");

        //qualify=0（资格只有四级笔试和口试）；1（四级口试，笔试，六级口试笔试）
        int qualify;
        if (test_name.toString().equals("cet")){
            //通过身份证号和00(表示四级笔试)去cet成绩表去查询成绩，验证考试资格
            System.out.println("验证cet");
            Cet_grade maxcet_grade=s_test_info.get_cet_grade(user_info.getIdentity_card(),"00");
            if (maxcet_grade==null){

            }else {
                //查询到了成绩，去判断cet4最高成绩是否<425
                if (maxcet_grade.getGrade()<425){
                    System.out.println("cet资格只有四级笔试和口试");
                    qualify=0;
                    model.addAttribute("qualify",qualify);
                }else if (maxcet_grade.getGrade()>=425){
                    qualify=1;
                    System.out.println("cet资格有四级口试，笔试，六级口试笔试");
                    model.addAttribute("qualify",qualify);
                }

            }
        }else if (test_name.toString().equals("ncre")){
            //通过身份证号ncre成绩表去查询成绩，验证考试资格
            System.out.println("验证ncre");
        }

        //报名信息采集页面
        return "DetailsRegister";
    }

    @RequestMapping("to_subject_choose")
    public String to_subject_choose(HttpServletRequest request, Model model, HttpSession session){
        String test_name=request.getParameter("test_name");
        String qualify=request.getParameter("qualify");

        //根据不同的test_name去条往不同的 科目选择页面
        if (test_name.toString().equals("cet")){
            //跳往四六级科目报名页面
            model.addAttribute("qualify",qualify);
            return "cet_subject_choose";

        }else {
            //跳往计算机等级考试页面
            return "ncre_subject_choose";
        }
    }

    //跳转报名信息页面(信息总汇)
    @RequestMapping("to_regist_info")
    public String to_regist_info(HttpServletRequest request, Model model, HttpSession session){
        String type=request.getParameter("type");

        User_info user_info= (User_info) session.getAttribute("user");
        //先判断registration_information中是否有信息（是否有报名）
        Registration_information registration_information=s_test_info.get_regist_info(user_info.getIdentity_card(),type);
        if (registration_information==null){
            //说明未报名此科目， 放回并且提示还未报名
            return "test_register";
        }else {
            //说明报名了此科目
            if (type.toString().equals("cet")){
                //说明是cet科目选择过来的
                //查询registration_information表，去获取上个页面所保存的信息，用身份证号和type查询
                List<Registration_information> registration_informationList=s_test_info.get_regist_info_list(user_info.getIdentity_card(),type);
                //去查询支付状态
                String order_no=registration_informationList.get(0).getOrder_no();
                Order_info order_info=s_order_info.get_order_info(order_no);

                int order_state=0;
                if (order_info.getOrder_state()==order_state){
                    //表示未支付
                    System.out.println("未支付");
                }else {
                    order_state=1;
                    System.out.println("已支付");
                }

                model.addAttribute("order_state",order_state);
                model.addAttribute("registration_informationList",registration_informationList);

            }else {
                //说明是ncre科目选择页面过来的
                }

            //qualify=0（资格只有四级笔试和口试）；1（四级口试，笔试，六级口试笔试）
            int qualify;
            if (type.toString().equals("cet")){
                //通过身份证号和00(表示四级笔试)去cet成绩表去查询成绩，验证考试资格
                System.out.println("验证cet");
                Cet_grade maxcet_grade=s_test_info.get_cet_grade(user_info.getIdentity_card(),"00");
                if (maxcet_grade==null){

                }else {
                    //查询到了成绩，去判断cet4最高成绩是否<425
                    if (maxcet_grade.getGrade()<425){
                        System.out.println("cet资格只有四级笔试和口试");
                        qualify=0;
                        model.addAttribute("qualify",qualify);
                    }else if (maxcet_grade.getGrade()>=425){
                        qualify=1;
                        System.out.println("cet资格有四级口试，笔试，六级口试笔试");
                        model.addAttribute("qualify",qualify);
                    }

                }
            }else if (type.toString().equals("ncre")){
                //通过身份证号ncre成绩表去查询成绩，验证考试资格
                System.out.println("验证ncre");
            }

            model.addAttribute("type",type);
            return "regist_info";
        }

    }

}
