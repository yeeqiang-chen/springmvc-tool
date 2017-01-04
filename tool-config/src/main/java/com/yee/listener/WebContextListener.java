package com.yee.listener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	private static final Logger logger = Logger.getLogger(WebContextListener.class);
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		//打印信息
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎   \r\n");
		sb.append("\r\n======================================================================\r\n");
		logger.info(sb.toString());
		return super.initWebApplicationContext(servletContext);
	}
}
