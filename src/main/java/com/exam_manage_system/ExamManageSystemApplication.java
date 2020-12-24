package com.exam_manage_system;

import com.exam_manage_system.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExamManageSystemApplication {

    public static void main(String[] args) {
        /*SpringApplication.run(ExamManageSystemApplication.class, args);*/
        ApplicationContext applicationContext =
                SpringApplication.run(ExamManageSystemApplication.class, args);
        SpringUtil.setApplicationContext(applicationContext);
       // SpringUtil.printBean();
    }

}
