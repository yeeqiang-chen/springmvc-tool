/*
FileName: MessageUtil

Function Description:

Author: yiqiang-Chen(004205)
Date: 2016-11-23 23:07
Version: V1.0
Copyright © Spring Airlines;Spring Travel.All rights reserved.
*/

package com.yee.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @Description:
 * @author: chenyiqiang
 * @date: 2016-11-23 23:07
 */
public class MessageUtil {
    /**
     * 获取国际化文字(不带参数)
     * @author yuanlixin
     * @param code
     *
     */
    public static String getMessage(String code){
        String message="";
        MessageSource messageSource = (MessageSource)AppUtil.getBean("messageSource");
        String[] args = null;
        message = messageSource.getMessage(code, args, Locale.CHINA);
        return message;
    }
    /**
     * 获取国际化文字(带参数)
     * @author yuanlixin
     * @param code
     * @param args
     *
     */
    public static String getMessage(String code,String[] args){
        String message="";
        MessageSource messageSource = (MessageSource)AppUtil.getBean("messageSource");
        message = messageSource.getMessage(code, args, Locale.CHINA);
        return message;
    }
}
