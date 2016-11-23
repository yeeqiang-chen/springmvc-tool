package com.yee.aop.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * aop自定义注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface  OperateLog {
	String remark() default "";//功能操作描述
	String cateGory() default "";//功能描述
	String cateGoryType() default "";//功能类型-唯一标识某一功能
	String operateType() default "";//操作类型  0为进入页面 1为保存 2为删除
}
