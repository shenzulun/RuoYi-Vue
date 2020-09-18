/**
 * File Name: MybatisDataAuthInterceptor.java
 * Date: 2020-09-14 15:49:14
 */
package com.hyrcb.hydp.common.core.dataAuth;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.hyrcb.hydp.common.config.StaticCode;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.system.domain.SysRole;
import com.ruoyi.project.system.domain.SysUser;
import me.belucky.easytool.util.CacheUtils;
import me.belucky.easytool.util.StringUtils;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.statement.select.Select;
import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Description: mybatis-plus数据权限
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
    /**
     	* 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     	* 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     	* 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     	* 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     	* 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";
    
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
			String mapperName = mappedStatement.getId().substring(0,mappedStatement.getId().lastIndexOf("."));
			Map<String, DataAuth> mapperMap = CacheUtils.getCache(StaticCode.DATA_AUTH_MAP_MAPPERNAME);
			if(mapperMap == null || !mapperMap.containsKey(mapperName) || SecurityUtils.getAuthentication() == null) {
				return invocation.proceed();
			}
			Map<String, DataAuth> authMap = CacheUtils.getCache(StaticCode.DATA_AUTH_MAP);
			
			SysUser sysUser = SecurityUtils.getLoginUser().getUser();
			List<SysRole> roles = sysUser.getRoles();
			if(roles != null && roles.size() > 0) {
				StringBuilder sqlString = new StringBuilder();
				for(SysRole role : roles) {
					Long roleId = role.getRoleId();
					String key = mapperName + "-" + roleId;
					DataAuth dataAuth = authMap.get(key);
					if(dataAuth != null) {
						String deptAlias = dataAuth.getDeptAlias();
						if(StringUtils.isNotNull(deptAlias)) {
							deptAlias = deptAlias + ".";
						}else {
							deptAlias = "";
						}
						switch(dataAuth.getDataScope()) {
							case DATA_SCOPE_ALL:{
								sqlString = new StringBuilder();
							}break;
							case DATA_SCOPE_CUSTOM:{
								sqlString.append(" OR " + deptAlias + "dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = " + roleId + " ) ");
							}break;
							case DATA_SCOPE_DEPT:{
								sqlString.append(" OR " + deptAlias + "dept_id = " + sysUser.getDeptId() + " ");
							}break;
							case DATA_SCOPE_DEPT_AND_CHILD:{
								sqlString.append(" OR ")
										.append(deptAlias)
										.append("dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = ")
										.append(sysUser.getDeptId())
										.append(" or find_in_set( ")
										.append(sysUser.getDeptId())
										.append(" , ancestors ) )");
							}break;
							case DATA_SCOPE_SELF:{
								sqlString.append(" OR create_user = '")
										 .append(sysUser.getUserName())
										 .append("'");
							}break;
						}
					}
				}
				String sqlAdd = sqlString.toString();
				if(StringUtils.isNotBlank(sqlAdd)) {
					sqlAdd = sqlAdd.substring(4);
					log.info("sql hack: {}", sqlAdd);
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
					Expression whereExpression = CCJSqlParserUtil.parseCondExpression(sqlAdd);
					selectBody.setWhere(new AndExpression(oldExpression,
											new Parenthesis(whereExpression)));
					/**
					 * 修改sql
					 */
					String newSql = selectBody.toString();
					log.info("==> AfterSQL: " + newSql);
					statementHandler.setValue("delegate.boundSql.sql", newSql);
				}
			}
			return invocation.proceed();
		}
		return invocation.proceed();
	}		
	
}
