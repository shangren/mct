package com.mct.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mct.controller.base.BaseController;
import com.mct.service.UserService;
import com.mct.service.base.ServiceFactory;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 用户
 * @time 2016年6月16日上午10:41:13
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	private static final Logger logger = Logger.getLogger(UserController.class);
	
	private UserService userService = ServiceFactory.getInstance(UserService.class);
	
	/**
	 * 用户注册
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/reg")
	@ResponseBody
	public Map<String, Object> registUser(HttpServletRequest request){
		Map<String, Object> reqMap = this.bindParam2Map(request);
		logger.info("/user/reg; params->" + reqMap);
		try{
			userService.add(reqMap);
		}catch(Exception e){
			return this.getErrorView();
		}		
		
		return this.getSuccView();
	}
	
	
}
