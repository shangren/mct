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
	}

	@Override
	public void after(Object obj, Method method, Object[] args, Object result) {
		ConnectionHolder.commit();
	}

	@Override
	public void doThrow(Object obj, Method method, Object[] args, Throwable e) {
		ConnectionHolder.rollback();
		logger.error("方法 method=" + method.getName() + " 异常-->>",e);
	}

	@Override
	public void doFinnally(Object obj, Method method, Object[] args) {
		ConnectionHolder.closeAndRemove();
		logger.info("方法 method=" + method.getName() + " cost=" + (System.currentTimeMillis() - beginTime) + " .ms");
	}

}
