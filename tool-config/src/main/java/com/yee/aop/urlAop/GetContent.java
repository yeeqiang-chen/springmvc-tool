package com.aop.urlAop;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * @author WB028
 * 通过过滤器将request、response放入ThreadLocal
 *
 */
public class GetContent implements Filter{
	    @Override 
	    public void destroy() {  
	    	
	    }  
	    @Override  
	    public void doFilter(ServletRequest request, ServletResponse response,  
	            FilterChain chain) throws IOException, ServletException {  
	        SysContent.setRequest((HttpServletRequest) request);  
	        SysContent.setResponse((HttpServletResponse) response);  
	        chain.doFilter(request, response);  
	    }  
	    @Override  
	    public void init(FilterConfig arg0) throws ServletException {
	    	
	    }
		
}
