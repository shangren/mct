package com.mct.service.base;

import org.apache.ibatis.session.SqlSession;

import com.mct.dao.base.ConnectionHolder;

public class BaseService {

	/**
	 * 获取sqlSession
	 * @return
	 */
	public SqlSession getSqlSession(){
		return ConnectionHolder.getSqlSession(ConnectionHolder.DBType.MYSQL);
	}
	
	/**
	 * 获取sqlMapper
	 * @param clazz
	 * @return
	 */
	public <T> T getMapper(Class<T> clazz){
		return getSqlSession().getMapper(clazz);
	}
}
