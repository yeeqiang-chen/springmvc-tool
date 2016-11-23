package com.aop.urlAop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aop.exception.BussinessException;
import com.aop.exception.BussinessExceptionBean;
import com.authority.dao.ConfigDao;
import com.authority.vo.UserVo;
import com.util.AppUtil;


/**
 * AOP注解权限控制
 *
 */
@Aspect //该注解标示该类为切面类 
@Component //注入依赖

public class SecurityAspect{
    
	@Before("within(com.mis..*||org.mis_mcs..*) && @annotation(sv)") 
    public void resourceValid(JoinPoint jp, SecurityValid sv) throws IOException, ServletException{  
		String sourceCode=sv.resourceCode();//获取参数
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = SysContent.getResponse(); 
        //获取登陆者信息
		UserVo loginUser = (UserVo)request.getSession().getAttribute("user");
		//如果登陆者信息为空，提示session过期信息
		if(loginUser==null){
			 response.setHeader("sessionstatus", "timeout");
			 throw new BussinessException(new BussinessExceptionBean("exception.nologinUser"),new Throwable());
		}else{
			//查询用户是否有该权限
			int count=((ConfigDao)AppUtil.getBean("configDao")).countUserAuthOfResoure(loginUser.getId(), sourceCode);
			if(count<=0){
				 throw new BussinessException(new BussinessExceptionBean("exception.OperateDeniedExceptiob"),new Throwable());
			}
		}
	}
}