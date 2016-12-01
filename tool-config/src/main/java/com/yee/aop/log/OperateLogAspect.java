package com.yee.aop.log;

import com.yee.authority.dao.ConfigDao;
import com.yee.authority.vo.OperateLogVo;
import com.yee.authority.vo.UserVo;
import com.yee.util.AppUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * AOP注解操作日志
 *
 */
@Aspect //该注解标示该类为切面类 
@Component //注入依赖
public class OperateLogAspect{
    
	@After("within(com.mis..*||org.mis_mcs..*) && @annotation(trv)") 
    public void operateLog(JoinPoint jp, OperateLog trv) throws IOException, ServletException{  
		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		UserVo loginUser = (UserVo)request.getSession().getAttribute("user");
		if(loginUser!=null){
			String remark=trv.remark();//功能操作描述
			String cateGory=trv.cateGory();//功能
			String operateType=trv.operateType();//操作类型
			String cateGoryType=trv.cateGoryType();//功能类型
			String ip = request.getRemoteAddr();//IP地址
			OperateLogVo vo=new OperateLogVo();
			vo.setOperateBy(String.valueOf(loginUser.getId()));
			vo.setOperateByName(loginUser.getUserName());
			vo.setOperateDept(loginUser.getDeptId());
			vo.setOperateDeptName(loginUser.getDeptName());
			vo.setOperateCategory(cateGory);
			vo.setOperateContent(remark);
			vo.setOperateType(operateType);
			vo.setOperateCategoryType(cateGoryType);
			vo.setIp(ip);
			((ConfigDao) AppUtil.getBean("configDao")).saveOperateLogInfo(vo);
		}
	}  
}
