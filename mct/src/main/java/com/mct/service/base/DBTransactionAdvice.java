package com.mct.service.base;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.mct.dao.base.ConnectionHolder;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 数据库事务advice
 * @time 2016年6月24日下午12:35:56
 */
public class DBTransactionAdvice implements Advice{

	private static final Logger logger = Logger.getLogger(DBTransactionAdvice.class);

	//before的调用时间
	private long beginTime = 0L;
	
	@Override
	public void before(Object obj, Method method, Object[] args) {
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			beginTime = System.currentTimeMillis();
		}
		String methodKey = method.getDeclaringClass().getName() + "@method=" + method.getName();
		logger.debug("methodKey : " + methodKey);
		ConnectionHolder.ProxyMethodHolder.push(methodKey);
	}

	@Override
	public void after(Object obj, Method method, Object[] args, Object result) {
		String methodKey = ConnectionHolder.ProxyMethodHolder.pop();
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			ConnectionHolder.commit();
			logger.info("methodKey : "+ methodKey +" commited!");
		}
		
	}

	@Override
	public void doThrow(Object obj, Method method, Object[] args, Throwable e) {
		String methodKey = ConnectionHolder.ProxyMethodHolder.pop();
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			ConnectionHolder.rollback();
			logger.error("methodKey : " + methodKey + " rollback!");
		}else{
			logger.error("methodKey : " + methodKey + "throws Excepiton!");
		}
	}

	@Override
	public void doFinnally(Object obj, Method method, Object[] args) {
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			ConnectionHolder.closeAndRemove();
			logger.info("方法 method=" + method.getName() + " cost=" + (System.currentTimeMillis() - beginTime) + " .ms");
		}
	}

}
