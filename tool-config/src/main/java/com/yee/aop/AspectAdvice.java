package com.yee.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.authority.dao.UserDao;
import com.authority.vo.OperateLogVo;
import com.authority.vo.UserVo;
import com.security.MethodLog;
import com.security.SecurityThreadLocalBean;


@SuppressWarnings("all")
public class AspectAdvice {

	private UserDao userMapper;
	
	
	public UserDao getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserDao userMapper) {
		this.userMapper = userMapper;
	}

	public void doAfter(JoinPoint jp,String result) throws Exception{
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserVo user = (com.authority.vo.UserVo) request.getSession().getAttribute("user");
		//保存用户操作日志
		MethodLog ml=getMethodRemark(jp);
		OperateLogVo vo=new OperateLogVo();
		//vo.setOperateBy(user.getId());
		vo.setOperateCategory(ml.cateGory());
		vo.setOperateContent(ml.remark());
		vo.setOperateType(ml.operateType());
		//this.operateLogDao.saveOperateLog(vo);
		SecurityThreadLocalBean.getInstance().clean();
	}

	public static MethodLog getMethodRemark(JoinPoint joinPoint) throws Exception{
		String targetName=joinPoint.getTarget().getClass().getName();
		String methodName=joinPoint.getSignature().getName();
		Object[] arguments=joinPoint.getArgs();
		Class targetClass=Class.forName(targetName);
		Method[] method =targetClass.getMethods();
//		String methode="";
		for (Method m : method) {
			if(m.getName().equals(methodName)){
				Class[] tmpCs=m.getParameterTypes();
				if(tmpCs.length==arguments.length){
					MethodLog mechodCache =m.getAnnotation(MethodLog.class);
					return mechodCache;
//					methode=mechodCache.remark();
//					break;
				}
			}
		}
		return null;
		
	}
}
