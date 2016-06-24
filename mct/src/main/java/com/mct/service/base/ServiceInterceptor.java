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
			doBefore(proxy, method, args);
			result = proxy.invoke(obj, args);
			doAfter(proxy, method, args, result);
		}catch(Throwable e){
			e.printStackTrace();
			doThrow(proxy, method, args, e);
		}finally{
			doFinnally(proxy, method, args);
		}
		return result;
	}

	private void doFinnally(MethodProxy proxy, Method method, Object[] args) {
		
		for(Advice advice : adviceList){
			advice.doFinnally(proxy, method, args);
		}
	}

	private void doThrow(MethodProxy proxy, Method method, Object[] args,
			Throwable e) {
		for(Advice advice : adviceList){
			advice.doThrow(proxy, method, args, e);
		}
		
	}

	private void doAfter(MethodProxy proxy, Method method, Object[] args,
			Object result) {
		
		for(Advice advice : adviceList){
			advice.after(proxy, method, args, result);
		}
	}

	private void doBefore(MethodProxy proxy, Method method, Object[] args) {
		for(Advice advice : adviceList){
			advice.before(proxy, method, args);
		}
	}

}
