package com.mct.dao.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mct.util.MyUtils;

/**
 * 
 * @author yangchao.wang
 *
 * @email  yangchao.wang@flaginfo.com.cn
 * @Desc  
 * 数据库操作基类
 * @time 2016年6月24日上午9:01:25
 */
public class ConnectionHolder {

	
	/**
	 * 静态初始化所有sqlSessionFactory
	 */
	private static final ConcurrentMap<DBType, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<DBType, SqlSessionFactory>();
	
	/**
	 * 静态初始化本地资源
	 */
	private static final ThreadLocal<ConcurrentMap<DBType, SqlSession>> localSqlSession = new ThreadLocal<ConcurrentMap<DBType, SqlSession>>();
	
	static{
		Reader mybatisConfigReader;
		try {
			mybatisConfigReader = new FileReader("mybatis.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("mybatis.xml 文件没有找到!");
		}
		
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfigReader,DBType.MYSQL.toString());
		sqlSessionFactoryMap.put(DBType.MYSQL, sqlSessionFactory);
	}
	
	
	
	/**
	 * 根据数据库类型获取sqlSession
	 * @param type
	 * @return
	 */
	public static  SqlSession getSqlSession(DBType type){
		
			return getSqlSession(type, true);
	}

	
	
	
	
	
	/**
	 * 获取sqlSession
	 * @param type
	 * @param autoCommit
	 * @return
	 */
	@SuppressWarnings("resource")
	public static  SqlSession getSqlSession(DBType type, boolean autoCommit) {
		SqlSession sqlSession = localSqlSession.get() == null ? null : localSqlSession.get().get(type);
		if(MyUtils.isEmpty(sqlSession)){
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(type);
			if(MyUtils.isEmpty(sqlSessionFactory)){
				throw new RuntimeException("没有找到"+ type +"类型的数据源！");
			}
			sqlSession = sqlSessionFactory.openSession(autoCommit);
			if(localSqlSession.get() == null){
				ConcurrentMap<DBType, SqlSession> m = new ConcurrentHashMap<DBType, SqlSession>();
				m.put(type, sqlSession);
				localSqlSession.set(m);
			}else{
				ConcurrentMap<DBType, SqlSession> m = localSqlSession.get();
				m.put(type, sqlSession);
			}
		}
		return sqlSession;
	}



	/**
	 * 关闭当前线程的数据库链接
	 */
	public static void closeAndRemove(){
		Map<DBType, SqlSession> m = localSqlSession.get();
		if(!MyUtils.isEmpty(m)){
			for(Map.Entry<DBType, SqlSession> entry : m.entrySet()){
				entry.getValue().close();
			}
			localSqlSession.remove();
		}
	}

	
	/**
	 * 提交
	 */
	public static  void commit(){
		Map<DBType, SqlSession> m = localSqlSession.get();
		if(!MyUtils.isEmpty(m)){
			for(Map.Entry<DBType, SqlSession> entry : m.entrySet()){
				entry.getValue().commit();
			}
		}
	}

	
	/**
	 * rollback
	 */
	public static  void rollback(){
		Map<DBType, SqlSession> m = localSqlSession.get();
		if(!MyUtils.isEmpty(m)){
			for(Map.Entry<DBType, SqlSession> entry : m.entrySet()){
				entry.getValue().rollback(true);
			}
		}
	}


	/**
	 * 
	 * @author yangchao.wang
	 *
	 * @email  yangchao.wang@flaginfo.com.cn
	 * @Desc  
	 * 数据库类型
	 * @time 2016年6月24日上午9:00:04
	 */
	public static enum DBType{
		MYSQL, ORACLE, SQLSERVER
	}
}
