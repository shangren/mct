package com.mct.service.base;

import org.apache.ibatis.session.SqlSession;

import com.mct.dao.base.ConnectionHolder;

public class BaseService {

	
	/**
	 * 获取sqlSession
	 * @return
	 */
	public SqlSession getSqlSession(boolean autoCommit){
		return ConnectionHolder.getSqlSession(ConnectionHolder.DBType.MYSQL, autoCommit);
	}
	
	
	/**
	 * 获取sqlMapper
	 * @param clazz
	 * @return
	 */
	public <T> T getMapper(Class<T> clazz, boolean autoCommit){
		return getSqlSession(autoCommit).getMapper(clazz);
	}
}
