package com.mct.dao;

import com.mct.model.UserInfoModel;

public interface UserMapper {

	public void insert(UserInfoModel model);

	
	public UserInfoModel get(UserInfoModel userInfoModel);


	public void del(UserInfoModel userInfoModel);


	public void update(UserInfoModel userInfoModel);
	
}
