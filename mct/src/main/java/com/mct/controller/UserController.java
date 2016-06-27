package com.mct.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

	private UserService userService = ServiceFactory.getInstance(UserService.class);
	
	/**
	 * 用户注册
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/reg")
	@ResponseBody
	public Map<String, Object> registUser(HttpServletRequest request, ModelMap model){
		userService.add(model);
		return this.getSuccView();
	}
	
	
}
