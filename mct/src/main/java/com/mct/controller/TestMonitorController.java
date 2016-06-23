package com.mct.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mct.controller.base.BaseController;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 服务监控器
 * @time 2016年6月23日下午4:32:37
 */
@Controller
@RequestMapping("/monitor")
public class TestMonitorController extends BaseController{
	
	
	/**
	 * 监控测试
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/test")
	@ResponseBody
	public Map<String, Object> monitorTest(HttpServletRequest request){
		return this.getSuccView();
	} 

}
