package com.yee.authority.dao;

import com.yee.authority.vo.OperateLogVo;
import org.apache.ibatis.annotations.Param;

/**
 * 基础配置操作Dao
 * 操作日志、权限控制
 * @author yee
 *
 */
public interface ConfigDao {

	/**
	 * 保存操作日志
	 * @param vo
	 * @return
	 */
	void saveOperateLogInfo(@Param("ol") OperateLogVo vo);
	
	/**
	 * 根据用户ID及resourceCode查询资源信息
	 * @param user
	 * @param code
	 * @return
	 */
	int countUserAuthOfResoure(@Param("user") String user,@Param("code") String code);
}
