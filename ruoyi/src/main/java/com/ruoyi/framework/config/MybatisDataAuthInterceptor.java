/**
 * File Name: MybatisDataAuthInterceptor.java
 * Date: 2020-09-14 15:49:14
 */
package com.ruoyi.framework.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.ruoyi.framework.aspectj.lang.annotation.DataAuth;
import me.belucky.easytool.util.StringUtils;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.statement.select.Select;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Map;

/**
 * Description: 
 * @author shenzulun
 * @date 2020-09-14
 * @version 1.0
 */
@Component
@Intercepts({
	@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
	@Signature(method = "query", type = Executor.class,args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisDataAuthInterceptor implements Interceptor,ApplicationContextAware{
    private static final Logger log = LoggerFactory.getLogger(MybatisDataAuthInterceptor.class);
    
    private ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object obj = invocation.getTarget();
		if(obj instanceof StatementHandler) {
			StatementHandler handler = (StatementHandler) invocation.getTarget();
			MetaObject statementHandler = SystemMetaObject.forObject(obj);
	        MappedStatement mappedStatement = (MappedStatement) statementHandler.getValue("delegate.mappedStatement");
	        // 只对查询sql拦截
			if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
				return invocation.proceed();
			}
			Class<?> classType = Class.forName(mappedStatement.getId().substring(0,mappedStatement.getId().lastIndexOf(".")));
			String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length()); 
			
			if("com.hyrcb.hydp.modules.pbc.mapper.PbcArticleMapper.selectList".equals(mappedStatement.getId())) {
				BoundSql boundSql = handler.getBoundSql();
	            String oldSQL = boundSql.getSql();
	            log.info("==> BeforeSQL: " + oldSQL);
	            CCJSqlParserManager parserManager = new CCJSqlParserManager();
				Select select = (Select) parserManager.parse(new StringReader(boundSql.getSql()));
				PlainSelect selectBody = (PlainSelect) select.getSelectBody();
				Expression oldExpression = selectBody.getWhere();
				if(oldExpression == null) {
					oldExpression = CCJSqlParserUtil.parseCondExpression("1=1");
				}
				Expression whereExpression = CCJSqlParserUtil.parseCondExpression("dept_id='黄岩农商银行'");
				selectBody.setWhere(new AndExpression(oldExpression,
										new Parenthesis(whereExpression)));
				/**
				 * 修改sql
				 */
				String newSql = selectBody.toString();
				log.info("==> AfterSQL: " + newSql);
//				statementHandler.setValue("delegate.boundSql.sql", newSql);
//				msObject.setValue("sqlSource.boundSql.sql", selectBody.toString());
	            
			}
			return invocation.proceed();
		}
		
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		// 只对查询sql拦截
		if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
			return invocation.proceed();
		}
		Class<?> classType = Class.forName(mappedStatement.getId().substring(0,mappedStatement.getId().lastIndexOf(".")));
		String mName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1, mappedStatement.getId().length()); 
		for (Method method : classType.getDeclaredMethods()) {
			if (method.isAnnotationPresent(DataAuth.class) && mName.equals(method.getName())) {
				// 查找标识了该注解的实现类
				Map<String, Object> beanMap = context.getBeansWithAnnotation(DataAuth.class);
				if ((beanMap != null) && (beanMap.entrySet().size() > 0)) {
					for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
						DataAuth action = method.getAnnotation(DataAuth.class);
						if (StringUtils.isNull(action.method())) {
							break;
						}
						try {
							Method md = entry.getValue().getClass().getMethod(action.method(),new Class[] { String.class });
							String whereSql = (String) md.invoke(context.getBean(entry.getValue().getClass()),new Object[] { action.tableAlias() });
							if (!StringUtils.isNull(whereSql) && !"null".equalsIgnoreCase(whereSql)) {
								Object parameter = null;
								if (invocation.getArgs().length > 1) {
									parameter = invocation.getArgs()[1];
								}
								BoundSql boundSql = mappedStatement.getBoundSql(parameter);
								MappedStatement newStatement = newMappedStatement(mappedStatement,new BoundSqlSqlSource(boundSql));
								MetaObject msObject = MetaObject.forObject(newStatement,
														new DefaultObjectFactory(),
														new DefaultObjectWrapperFactory(),
														new DefaultReflectorFactory());
								/**
								 * 通过JSqlParser解析 原有sql,追加sql条件
								 */
								CCJSqlParserManager parserManager = new CCJSqlParserManager();
								Select select = (Select) parserManager.parse(new StringReader(boundSql.getSql()));
								PlainSelect selectBody = (PlainSelect) select.getSelectBody();
								Expression whereExpression = CCJSqlParserUtil.parseCondExpression(whereSql);
								selectBody.setWhere(new AndExpression(selectBody.getWhere(),
														new Parenthesis(whereExpression)));
								/**
								 * 修改sql
								 */
								msObject.setValue("sqlSource.boundSql.sql", selectBody.toString());
								invocation.getArgs()[0] = newStatement;
								log.info("Interceptor sql:" + selectBody.toString());
							}
						}catch(Exception e) {
							log.error("", e);
						}
					}
				}
			}
		}
		return invocation.proceed();
	}
	
	private MappedStatement newMappedStatement(MappedStatement ms,SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if ((ms.getKeyProperties() != null) && (ms.getKeyProperties().length != 0)) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1,keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());	
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}
			
	
	class BoundSqlSqlSource implements SqlSource {
		private BoundSql boundSql;
		
		public BoundSqlSqlSource(BoundSql boundSql) {
			super();
			this.boundSql = boundSql;
		}

		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

}
