package com.yee.authority;

import com.yee.authority.dao.UserDao;
import com.yee.authority.vo.UserVo;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	private static Logger LOGGER=Logger.getLogger(CustomUserDetailsService.class);
	@Autowired
	private UserDao userDao;
	/**
	 * 根据登陆的用户名加载用户的所有角色权限
	 * @param
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserDetails loadUserByUsername (String userAccount) throws UsernameNotFoundException{
		UserVo userInfo = new UserVo();
		try {
			
			userInfo=userDao.getUserByUsername(userAccount);
			if(userInfo != null){
				userInfo.setRoleList(userDao.getUserRolesByUserAccount(userAccount));
			}
			
			if(userInfo!=null && userInfo.getRoleList()!=null && !userInfo.getRoleList().isEmpty()){
				
				String [] str=(String [])userInfo.getRoleList().toArray(new String[0]);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("role", str);
				userInfo.setResources(userDao.getUserResources(map));
			}
			
		} catch (Exception e) {
			LOGGER.error("数据库不存在该用户:"+userAccount);
		}
		String password = userInfo.getPwd().toUpperCase();
		if(userInfo.getPwd()==null||"".equals(userInfo.getPwd()))
		{
			throw new UsernameNotFoundException("Can't find the user "+userAccount);
		}
		Set<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(userInfo.getRoleList());
		UserDetails userDetails = new User(userAccount, password, true, true, true, true,
				grantedAuths);
		MDC.put("username", userDetails.getUsername());
		return userDetails;
	}
	private Set<GrantedAuthority> obtionGrantedAuthorities(List<String> roleNameList) {
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		for (String roleName : roleNameList) {
			set.add(new SimpleGrantedAuthority(roleName));
		}	
		return set;
	}
}
