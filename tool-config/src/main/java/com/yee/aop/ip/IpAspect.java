package com.yee.aop.ip;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class IpAspect {

	// Service层切点
	@Pointcut("@annotation(com.aop.ip.IpAop)")
	public void serviceAspect() {
	}

	/**
	 * 前置通知 用于拦截service层,判断IP是否在IP白名单中 并记录调用方访问记录
	 *
	 * @param joinPoint
	 *            切点
	 */
	@Before("serviceAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		//请求的IP
        String ip = request.getRemoteAddr();

	}

}
