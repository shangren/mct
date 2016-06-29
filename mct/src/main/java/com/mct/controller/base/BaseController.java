package com.mct.controller.base;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mct.util.MyUtils;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 控制器基类
 * @time 2016年6月23日下午4:30:57
 */
public class BaseController {

	public static final String SUCC_RETURN_CODE = "200";
	public static final String ERROR_RETURN_CODE = "499";
	
	
	
	/**
	 * 获取成功返回码
	 * @return
	 */
	public Map<String, Object> getSuccView(){
		Map<String, Object> result = MyUtils.newMap();
		result.put("returnCode",SUCC_RETURN_CODE);
		return result;
	}
	
	
	/**
	 * 获取成功返回码
	 * @return
	 */
	public Map<String, Object> getErrorView(){
		Map<String, Object> result = MyUtils.newMap();
		result.put("returnCode",ERROR_RETURN_CODE);
		return result;
	}
	/**
	 * 取出request 中的参数转化为map
	 * @param request
	 * @return
	 */
	public Map<String, Object> bindParam2Map(HttpServletRequest request){
		Map<String, Object>  result = MyUtils.newMap();
		Enumeration<?> e = request.getParameterNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String val = request.getParameter(key);
			if(!MyUtils.isEmpty(val)){
				result.put(key, val);
			}
			continue;
		}
		
		return result;
	}
	
}
