package com.mct.service;

import java.util.List;

import com.mct.dao.UserMapper;
import com.mct.model.UserInfoModel;
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
	public void  add(UserInfoModel user){
		this.getMapper(UserMapper.class, false).insert(user);
	}

	/**
	 * 查询
	 * @param userInfoModel
	 * @return
	 */
	public List<UserInfoModel> get(UserInfoModel userInfoModel) {
		return this.getMapper(UserMapper.class, false).get(userInfoModel);
	}
	
	/**
	 * 删除
	 * @param userInfoModel
	 * @return
	 */
	public void delete(UserInfoModel userInfoModel) {
		this.getMapper(UserMapper.class, false).del(userInfoModel);
	}
	
	
	/**
	 * 修改
	 * @param userInfoModel
	 * @return
	 */
	public void modify(UserInfoModel userInfoModel) {
		this.getMapper(UserMapper.class, false).update(userInfoModel);
	}
	
	
	public void paginationSearch(UserInfoModel userInfoModel){
		this.getMapper(UserMapper.class,false).get(userInfoModel);
	}
}
