package com.handwrit.manage.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by 甘银涛 on 2019/5/2 21:18
 */
public class SpringBeanUtil {

    private static final Logger log = LoggerFactory.getLogger(SpringBeanUtil.class);
    private static ApplicationContext context = null;
    public static void initContext(ApplicationContext ctx){
        if (ctx == null){
            log.warn("SpringBeanUtils init Fail,Because the ApplicationContext is null.");
            return;
        }
        log.info("SpringBeanUtils init Success");
        context = ctx;
    }

    public static <T> T getBean(Class<T> cls){
        return context == null ? null : context.getBean(cls);
    }

    public static Object getBean(String name){
        return context == null ? null : context.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> cls){
        return context == null ? null : context.getBean(name, cls);
    }
}
