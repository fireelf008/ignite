package com.test.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtils {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T>T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }
}
