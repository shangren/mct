package com.mct.dao;

import java.util.List;

import com.mct.model.UserInfoModel;

public interface UserMapper {

	public void insert(UserInfoModel model);

	
	public List<UserInfoModel> get(UserInfoModel userInfoModel);


	public void del(UserInfoModel userInfoModel);


	public void update(UserInfoModel userInfoModel);
	
}
