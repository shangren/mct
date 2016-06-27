package com.mct.service.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 服务调用拦截器
 * @time 2016年6月24日下午12:21:29
 */
public class ServiceInterceptor implements MethodInterceptor {
	
	private static final List<Advice> adviceList = new ArrayList<Advice>();
	static{
		adviceList.add(new DBTransactionAdvice());
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy){
		Object result = null;
		try{
			doBefore(obj, method, args);
			result = proxy.invokeSuper(obj, args);
			doAfter(obj, method, args, result);
		}catch(Throwable e){
			e.printStackTrace();
			doThrow(obj, method, args, e);
		}finally{
			doFinnally(obj, method, args);
		}
		return result;
	}

	private void doFinnally(Object obj, Method method, Object[] args) {
		
		for(Advice advice : adviceList){
			advice.doFinnally(obj, method, args);
		}
	}

	private void doThrow(Object obj, Method method, Object[] args,
			Throwable e) {
		for(Advice advice : adviceList){
			advice.doThrow(obj, method, args, e);
		}
		
	}

	private void doAfter(Object obj, Method method, Object[] args,
			Object result) {
		
		for(Advice advice : adviceList){
			advice.after(obj, method, args, result);
		}
	}

	private void doBefore(Object obj, Method method, Object[] args) {
		for(Advice advice : adviceList){
			advice.before(obj, method, args);
		}
	}

}
