package com.yee.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

public class SecurityThreadLocalBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  ThreadLocal<HttpServletRequest> threadLocalReqest = new ThreadLocal<HttpServletRequest>();
	private  ThreadLocal<HttpServletResponse> threadLocalResponse = new ThreadLocal<HttpServletResponse>();
	private static SecurityThreadLocalBean bean;
	
	public static  SecurityThreadLocalBean getInstance(){
		if(bean==null)
			bean=new SecurityThreadLocalBean();
		
		return bean;
	}
	public HttpServletRequest getRequest(){
		return threadLocalReqest.get();
	}
	public void setRequest(HttpServletRequest request){
		threadLocalReqest.set(request);
	}
	
	public void cleanRequest(){
		threadLocalReqest.remove();
	}
	
	public HttpServletResponse getResponse(){
		return threadLocalResponse.get();
	}
	public void setResponse(HttpServletResponse response){
		threadLocalResponse.set(response);
	}
	
	public void cleanResponse(){
		threadLocalResponse.remove();
	}
	
	public void clean(){
		this.cleanRequest();
		this.cleanResponse();
	}
}
