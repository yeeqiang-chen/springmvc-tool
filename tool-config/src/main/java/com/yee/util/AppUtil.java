/*
FileName: AppUtil

Function Description:

Author: yiqiang-Chen(004205)
Date: 2016-11-23 23:08
Version: V1.0
Copyright © Spring Airlines;Spring Travel.All rights reserved.
*/

package com.yee.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description:
 * @author: chenyiqiang
 * @date: 2016-11-23 23:08
 */
public class AppUtil implements ApplicationContextAware {
    public static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        if(AppUtil.ac==null){
            AppUtil.ac=arg0;
        }
    }
    /**
     * 返回ApplicationContext实例<br>
     * @return ApplicationContext实例<br>
     */
    public static ApplicationContext getApplicationContext(){
        return AppUtil.ac;
    }

    /**
     * 返回ApplicationContext容器维护的bean实例<br>
     * @param beanName bean的标识<br>
     * @return 需要获取的bean实例<br>
     */
    public static Object getBean(String beanName){
        return AppUtil.ac.getBean(beanName);
    }
}
