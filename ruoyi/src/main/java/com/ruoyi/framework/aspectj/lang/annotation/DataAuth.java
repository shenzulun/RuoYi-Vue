/**
 * File Name: DataAuth.java
 * Date: 2020-09-14 16:05:16
 */
package com.ruoyi.framework.aspectj.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 数据权限
 * @author shenzulun
 * @date 2020-09-14
 * @version 1.0
 */
@Documented
@Target(value={ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataAuth {

	public String method() default "whereSql";
	
	public String tableAlias() default "";
	
}
