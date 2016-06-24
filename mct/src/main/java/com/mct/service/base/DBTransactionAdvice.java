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
	private long beginTime = System.currentTimeMillis();
	
	@Override
	public void before(Object obj, Method method, Object[] args) {
		String methodKey = method.getDeclaringClass().getName() + "@method=" + method.getName();
		ConnectionHolder.ProxyMethodHolder.push(methodKey);
	}

	@Override
	public void after(Object obj, Method method, Object[] args, Object result) {
		String methodKey = ConnectionHolder.ProxyMethodHolder.pop();
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			ConnectionHolder.commit();
			logger.info("methodKey : "+ methodKey +"is commited!");
		}
		
	}

	@Override
	public void doThrow(Object obj, Method method, Object[] args, Throwable e) {
		String methodKey = ConnectionHolder.ProxyMethodHolder.pop();
		if(ConnectionHolder.ProxyMethodHolder.isEmpty()){
			ConnectionHolder.rollback();
			logger.info("methodKey : " + methodKey + " rollback!");
		}else{
			logger.info("methodKey : " + methodKey + "throws Excepiton!");
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
