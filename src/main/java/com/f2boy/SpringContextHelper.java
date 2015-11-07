package com.f2boy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取SpringContext，以访问容器中定义的其他bean。需要spring-context jar包的支持
 *
 * @Copyright :aiya ©2012 -2020
 * @date :2014年11月3日
 * @author :wangws
 * @version :3.4.0
 * @description :
 *
 */
public class SpringContextHelper implements ApplicationContextAware {

    private static final Object lockObj = new Object();

    // Spring应用上下文环境
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        synchronized (lockObj) {
            context = applicationContext;
        }
    }

    /** 获取Spring应用上下文环境 */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

}
