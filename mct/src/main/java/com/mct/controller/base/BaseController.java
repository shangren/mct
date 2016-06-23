package com.mct.controller.base;

import java.util.Map;

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
	
}
