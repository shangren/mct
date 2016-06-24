package com.mct.service.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 
 * @time 2016年6月24日上午11:24:25
 */
public class BaseService {

private static Map<String,Object> beanMap = new HashMap<String,Object>();
	
	public static <T> T getInstance(Class<?> beanClazz){
		 T t = (T)beanMap.get(beanClazz.getName());
		 if(t==null){
			 Enhancer en = new Enhancer();
			 en.setSuperclass(beanClazz);
			 en.setCallback(new ServiceInterceptor()); //DBTransactionInterceptor
			 en.setCallbackFilter(new CallbackFilter() {
				@Override
				public int accept(Method arg0) {
					return 0;
				}
			});
			 t = (T)en.create();
			 beanMap.put(beanClazz.getName(),t);
		 }
		 return t;
	}
	
	
	/**
	 * className去实例化对象
	 * @param clazzName
	 * @return
	 */
	public static <T> T getInstance(String clazzName){
		try {
			return getInstance(Class.forName(clazzName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
