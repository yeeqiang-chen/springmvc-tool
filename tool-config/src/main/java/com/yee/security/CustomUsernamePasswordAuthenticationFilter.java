package com.yee.security;

import com.airspring.common.security.Constant;
import com.airspring.common.security.ISecurityCheck;
import com.airspring.common.security.SecurityData;
import com.yee.authority.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @author
 * 登录页面验证方法，判断用户登录
 *
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	public static final String USERNAME = "j_username";
	public static final String PASSWORD = "j_password";
	public static final String VERIFYCODE = "j_verifyCode";
	private Logger logger=Logger.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private ISecurityCheck iSecurityCheck;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!"POST".equals(request.getMethod())) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error("不支持编码:"+e1.getMessage());
		}
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String remember=request.getParameter("j_remember");
		String checkVerifyCode=ChunQiuTool.obj2String(request.getSession().getAttribute("verifyCode"));
		String verifyCode = request.getParameter(VERIFYCODE);
		//使session失效
		request.getSession().invalidate();
		if("yes".equals(remember)){
			Cookie cookie=new Cookie(GlobalConstants.sysName,username);
			cookie.setMaxAge(60*60*24*30);
			cookie.setPath("/");
			response.addCookie(cookie);
			response.addHeader("SET-COOKIE", "JSESSIONID=" + username + ";Path=/;HttpOnly");
		}
		
		if (StringUtils.isEmpty(username)){
			request.getSession().setAttribute("loginError", "用户名不能为空！");
			throw new AuthenticationServiceException("用户名不能为空！"); 
		}
		if (StringUtils.isEmpty(password)){
			request.getSession().setAttribute("loginError", "密码不能为空！");
			throw new AuthenticationServiceException("密码不能为空！"); 
		}
		if(verifyCode ==null || !checkVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())){
			request.getSession().setAttribute("loginError", "验证码不正确");//验证码不正确
			request.getSession().setAttribute("userAccount", username);
			throw new AuthenticationServiceException("验证码不正确"); //验证码不正确
		}
		
		//安全组件添加
		SecurityData inputData=new SecurityData();
		SecurityData outputData=new SecurityData();
		inputData.setBusiScenCode(Constant.SCENE_LOGIN_TIME);
		inputData.setUid(username);
		outputData=iSecurityCheck.doSecurity(inputData);
		//返回码： 0000：通过。0101：登录错误次数验证超过限制。9900：参数缺失。9901：未知错误。
		if(Constant.RESULT_LOGIN_OVERFLOW.equals(outputData.getResultCode())){
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("loginError", "登陆失败次数超限了");
			logger.error("登陆失败次数超限了");
			//登录错误次数验证超过限制则禁用user表，
			this.userDao.updateUserStatus(username);
			throw new AuthenticationServiceException("登陆失败次数超限了");
		}
		
		//验证用户账号与密码是否对应
		username = username.trim();
		UserVo userInfo = null;
		try {
			//获取用户信息
			userInfo =userDao.getUserByUsername(username);
			if(userInfo != null){
				//设置用户角色信息
				userInfo.setRoleList(userDao.getUserRolesByUserAccount(username));
				if(userInfo.getRoleList()!=null && !userInfo.getRoleList().isEmpty()){
					String [] str=(String [])userInfo.getRoleList().toArray(new String[0]);
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("role", str);
					userInfo.setResources(userDao.getUserResources(map));
				}
			}
		} catch (Exception e) {
			logger.error("获取用户信息异常！");
		}
		if(userInfo == null) {
			request.getSession().setAttribute("loginError", "用户名错误!");
			request.getSession().setAttribute("userAccount", username);
			throw new AuthenticationServiceException("用户名错误!"); 
		}else if(!password.equals(userInfo.getPwd().toUpperCase())){
			request.getSession().setAttribute("loginError", "密码错误!");
			request.getSession().setAttribute("userAccount", username);
			//记录登陆失败
			writeWrongNum(inputData,outputData);
			throw new AuthenticationServiceException("密码错误!"); 
		}else if(!StringUtils.equals(GlobalConstants.ZERO, userInfo.getUserFlag())){
			request.getSession().setAttribute("loginError", "用户未激活!");
			request.getSession().setAttribute("userAccount", username);
			throw new AuthenticationServiceException("用户未激活!"); 
		}else if(userInfo.getRoleList()==null || userInfo.getRoleList().size()==0 ){
			request.getSession().setAttribute("loginError", "用户未授权!");
			request.getSession().setAttribute("userAccount", username);
			throw new AuthenticationServiceException("用户未授权!"); 
		}else{
			request.getSession().setAttribute("loginError", "");
			request.getSession().setAttribute("userAccount", username);
		}

		//UsernamePasswordAuthenticationToken实现 Authentication
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		
		// 允许子类设置详细属性
        setDetails(request, authRequest);
		request.getSession().setAttribute("user", userInfo);
		
		this.setUserPermissionToSession(request, userInfo);
		//设置语言为中文
		Locale locale = new Locale("zh", "CN");
		request.getSession().setAttribute(
						SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
						locale);
        // 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public void setUserPermissionToSession(HttpServletRequest request,UserVo userDto) {
		if (userDto != null) {
			List<String> myPermissionUrls = new ArrayList<String>();
			for (ResourceVo resource : userDto.getResources()) {
				addPermissionUrl(resource, myPermissionUrls);
			}
		}
	}
	
	private void addPermissionUrl(ResourceVo resource, List<String> list) {
		if ("1".equals(resource.getResourceType()) && StringUtils.isNotBlank(resource.getUrlPattern()) 
				&& !"root".equals(resource.getUrlPattern())) {
			list.add(resource.getUrlPattern());
		}
	}
	
	/**
     * 安全组件记录错误的记录
     */
    public void writeWrongNum(SecurityData inputData,SecurityData outputData){
    	Map<String,Object> map=new HashMap<String, Object>();
		inputData.setBusiScenCode(Constant.SCENE_LOGIN_AFTER);
		map.put(Constant.KEY_YESORNO, Constant.VALUE_FAIL);
		inputData.setOprationDatas(map);
		outputData=iSecurityCheck.doSecurity(inputData);
    }
    
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : RSAUtils
				.decryptStringByJs(obj.toString());
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : MD5.getMD5Encode(RSAUtils
				.decryptStringByJs(obj.toString()));
	}
}
