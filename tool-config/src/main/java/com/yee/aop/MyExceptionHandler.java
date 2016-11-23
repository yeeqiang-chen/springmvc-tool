package com.yee.aop;

import com.yee.aop.exception.BussinessException;
import com.yee.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class MyExceptionHandler implements HandlerExceptionResolver {
	
	private Logger logger=Logger.getLogger(MyExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception exp) {
		String message = "";
		
		if (exp instanceof AccessDeniedException) {
			message = getMessage("exception.AccessDeniedException");
		} else if (exp instanceof PermissionDeniedException) {
			message = getMessage("exception.PermissionDeniedException");
		} else if (exp instanceof NullPointerException) {
			message = getMessage("exception.NullPointerException");
		} else if(exp instanceof BussinessException){
			message = ((BussinessException) exp).getExceptionMessage();
		} else if(exp instanceof NoSuchMethodException){
			message = "NoSuchMethodException";
		}else if(exp instanceof SecurityException){
			message = "SecurityException";
		}else if(exp instanceof ClassNotFoundException){
			message = "ClassNotFoundException";
		}else if(exp instanceof IllegalAccessException){
			message = "IllegalAccessException";
		}else if(exp instanceof IllegalArgumentException){
			message = "IllegalArgumentException";
		}else if(exp instanceof InvocationTargetException){
			message = "InvocationTargetException";
		}else if(exp instanceof AuthenticationServiceException){
			message=((AuthenticationServiceException) exp).getMessage();
		}else {
		   String msg = exp.getMessage();
		  if(StringUtils.isNotEmpty(msg)){
			  message=msg;
		  }else{
			message = getMessage("exception.syserror");
		  }
		}
		
		//获取用户信息异常时，登出
		if("nologin".equals(message)){
			logger.error("对不起，您的用户已过期！", exp);
			request.getSession().invalidate();
			ModelAndView mav = new ModelAndView("../../login");
			return mav;
		}
		
		logger.error(message, exp);
		
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equals("XMLHttpRequest")) {
			// 此请求为ajax请求
			try {
				PrintWriter writer = response.getWriter();
				writer.write(message);
			} catch (IOException e) {

			}
			return null;
		} else {
			request.setAttribute("errorMsg", message);
			ModelAndView mav = new ModelAndView("common/error");
			mav.addObject("errorMsg", message);
			return mav;
		}
	}

	/**
	 * 获取国际化文字(不带参数)
	 * 
	 * @author chenyiqiang
	 * @param code
	 * 
	 */
	public String getMessage(String code) {
		String message = "";
		MessageSource messageSource = (MessageSource) AppUtil
				.getBean("messageSource");
		String[] args = null;
		message = messageSource.getMessage(code, args, Locale.CHINA);
		return message;
	}
}
