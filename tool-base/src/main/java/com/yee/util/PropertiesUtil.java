/*
FileName: PropertiesUtil

Function Description:

Author: yiqiang-Chen(004205)
Date: 2016-11-22 19:30
Version: V1.0
Copyright © Spring Airlines;Spring Travel.All rights reserved.
*/

package com.yee.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @Description: 属性文件操作工具类
 * @author: chenyiqiang(004205)
 * @date: 2016-11-22 19:30
 */
public class PropertiesUtil {
    private static PropertiesUtil oInstance = new PropertiesUtil();
    private static Properties oProperties;
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
    private static String propertiesName = "system.properties";
    private PropertiesUtil() {

    }

    public static void setPropertiesName(String propertiesName) {
        propertiesName = null;
        oProperties = null;
        if (StringUtils.isNotEmpty(propertiesName)) {
            propertiesName = propertiesName;
        } else {
            propertiesName = "system.properties";
        }
    }

    protected void loadProperties() {
        try {
            oProperties = new Properties();
            ClassLoader e = Thread.currentThread().getContextClassLoader();
            if (e == null) {
                e = oInstance.getClass().getClassLoader();
            }
            InputStream is = e.getResourceAsStream(propertiesName);
            if (is != null) {
                oProperties.load(is);
                is.close();
            } else {
                logger.error("SysPropertiesUtil can not load property files!");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (oProperties == null) {
            oInstance.loadProperties();
        }
        return oProperties.getProperty(key);
    }

    public static int getInt(String sPropertyName, int iDefaultValue) {
        try {
            String e = getProperty(sPropertyName);
            return Integer.parseInt(e);
        } catch (Exception var3) {
            return iDefaultValue;
        }
    }

    public static String getString(String sPropertyName, String sDefaultValue) {
        try {
            return getProperty(sPropertyName);
        } catch (Exception var3) {
            return sDefaultValue;
        }
    }

    public static HashMap getProperties(String keyGroup) {
        HashMap hashmap = new HashMap();
        if(oProperties == null) {
            oInstance.loadProperties();
        }

        Enumeration enumeration = oProperties.keys();

        while(enumeration.hasMoreElements()) {
            String tempKey = (String)enumeration.nextElement();
            if(tempKey.startsWith(keyGroup)) {
                hashmap.put(tempKey, oProperties.get(tempKey));
            }
        }

        return hashmap;
    }

    public static boolean getBoolean(String key, boolean bDefaultValue) {
        try {
            String e = getProperty(key);
            return e != null ? e.equalsIgnoreCase("true") || e.equalsIgnoreCase("t") : bDefaultValue;
        } catch (Exception var3) {
            return bDefaultValue;
        }
    }

    public static void main(String[] args) {
        setPropertiesName("file.properties");
        System.out.println(propertiesName);
        String property1 = getProperty("ksms.file.path");
        System.out.println("property1 = " + property1);
    }
}
