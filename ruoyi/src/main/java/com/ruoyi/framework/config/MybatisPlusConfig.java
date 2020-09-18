/**
 * File Name: MybatisPlusConfig.java
 * Date: 2020-05-20 16:40:08
 */
package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.SqlExplainInterceptor;
import com.hyrcb.hydp.common.core.dataAuth.MybatisDataAuthInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-05-20
 * @version 1.0
 */
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件，自动识别数据库类型
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

	/**
	 * 乐观锁插件
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * 如果是对全表的删除或更新操作，就会终止该操作
	 */
	@Bean
	public SqlExplainInterceptor sqlExplainInterceptor() {
		return new SqlExplainInterceptor();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MybatisDataAuthInterceptor mybatisDataAuthInterceptor() {
		return new MybatisDataAuthInterceptor();
	}
}
