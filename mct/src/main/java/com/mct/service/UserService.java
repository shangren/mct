package com.mct.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.mct.dao.UserMapper;
import com.mct.service.base.BaseService;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 用户服务
 * @time 2016年6月24日下午5:41:10
 */
public class UserService extends BaseService{

	
	/**
	 * 新增
	 * @param reqMap
	 */
	public void  add(Map<String, ?> reqMap){
		this.getMapper(UserMapper.class, false).insert(reqMap);
	}
	
	
}
