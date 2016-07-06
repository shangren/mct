package com.mct.dao.intercept;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import com.mct.util.Page;
import com.mysql.jdbc.Statement;

/**
 * 分页查询拦截器
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 
 * @time 2016年7月6日下午12:14:49
 */
@Intercepts({@Signature(
		type= StatementHandler.class,
		method="query",
		args = {Statement.class,ResultHandler.class}
		)
})
public class PaginationSearchIntercept implements Interceptor {

	private static final ThreadLocal<Page<?>> pageLocal = new ThreadLocal<Page<?>>();
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Page<?> page = null;
		if((page = pageLocal.get()) == null){
			return invocation.proceed();
		}
		String extendSql = " LIMIT " + page.getOffset() + " , " + page.getLimit();
		
		StatementHandler handler = (StatementHandler)invocation.getTarget();
		BoundSql sql = handler.getBoundSql();
		String oldSql = sql.getSql();
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return target;
	}

	@Override
	public void setProperties(Properties properties) {
		return;
	}

}
