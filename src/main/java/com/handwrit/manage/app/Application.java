package com.handwrit.manage.app;

import com.handwrit.manage.utils.util.SpringBeanUtil;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(value = {"com.handwrit.manage.controller",
        "com.handwrit.manage.service",
        "com.handwrit.manage.config"})
@MapperScan("com.handwrit.manage.mapper")
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        SpringBeanUtil.initContext(context);
        LoggerFactory.getLogger(Application.class).info("==============================================RUN SUCCESS===========================================");    }

}
