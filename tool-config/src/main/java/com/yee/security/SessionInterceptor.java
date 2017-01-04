package com.yee.security;

import com.yee.authority.vo.UserVo;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = Logger.getLogger(SessionInterceptor.class.getName()); 

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse rsp, Object obj, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse rsp,
			Object obj, ModelAndView mode) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		    
		    String requestUrl=request.getRequestURL().toString();
			UserVo loginUser = (UserVo)request.getSession().getAttribute("user");
			if(loginUser==null){
			    response.setHeader("sessionstatus", "timeout");
			}else{
				
			}
		   return true;
	}
}
