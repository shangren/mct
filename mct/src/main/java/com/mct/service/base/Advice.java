package com.mct.service.base;

import java.lang.reflect.Method;


/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 
 * @time 2016年6月24日下午12:49:54
 */
public interface Advice{ 

	void before(Object obj, Method method, Object[] args);
	
	void after(Object obj, Method method, Object[] args, Object result);
	
	void doThrow(Object obj, Method method, Object[] args, Throwable e);
	
	void doFinnally(Object obj, Method method, Object[] args);
	
}
