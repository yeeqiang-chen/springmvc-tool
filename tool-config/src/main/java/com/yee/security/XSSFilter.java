package com.yee.security;

import org.apache.commons.lang.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * XSS filter
 */

public final class XSSFilter implements Filter {

	private static String[] excludePaths={
	};

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String requestUrl = request.getRequestURI();
		if (!excludeUrl(requestUrl)) {
			chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), servletResponse);
		}else{
			chain.doFilter(request, servletResponse);
		}
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//Weblogic 10.3.0 not support cookie-http-only
		response.addHeader("SET-COOKIE", "JSESSIONID=" + request.getSession().getId() + ";Path=/;HttpOnly");
	}

	public void destroy() {

	}

	public void init(FilterConfig config) throws ServletException {
		String excludePath = config.getInitParameter("excludePaths");
		if (StringUtils.isNotBlank(excludePath)) {
			excludePaths = excludePath.split(",");
		}
	}

	private boolean excludeUrl(String url) {
		if (excludePaths != null && excludePaths.length > 0) {
			for (String path : excludePaths) {
				if (url.indexOf(path)!=-1) {
					return true;
				}
			}
		}
		return false;
	}
}


