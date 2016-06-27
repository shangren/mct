package com.mct.dao.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

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

	private static final Logger logger = Logger.getLogger(ConnectionHolder.class);
	/**
	 * 静态初始化所有sqlSessionFactory
	 */
	private static final ConcurrentMap<DBType, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<DBType, SqlSessionFactory>();
	
	/**
	 * 静态初始化本地资源
	 */
	private static final ThreadLocal<ConcurrentMap<DBType, SqlSession>> localSqlSession = new ThreadLocal<ConcurrentMap<DBType, SqlSession>>();

	/**
	 * 代理的方法调用栈
	 */
	private static final ThreadLocal<Stack<String>> localMethodStack = new ThreadLocal<Stack<String>>();
	
	
	public static class ProxyMethodHolder{
		
		
		/**
		 * 获取该线程的方法栈
		 * @return
		 */
		public static Stack<String> get(){
			return localMethodStack.get();
		}
		
		
		/**
		 * 像方法栈中push调用的方法
		 * @param methodName
		 */
		public static void push(String methodName){
			Stack<String> s = localMethodStack.get();
			if(s == null){
				s = new Stack<String>();
			}
			s.push(methodName);
			localMethodStack.set(s);
		}
		
		/**
		 * 从栈中弹出 方法名
		 * @return
		 */
		public static String pop(){
			Stack<String> s = localMethodStack.get();
			if(s == null){
				return null;
			}
			return s.pop();
		}
		
		/**
		 * 方法栈是否为空
		 * @return
		 */
		public static boolean isEmpty(){
			Stack<String> s = localMethodStack.get();
			if(s == null){
				return true;
			}
			return (s.size() > 0); 
		}
		
		
		/**
		 * 移除线程栈
		 */
		public static void remove(){
			localMethodStack.remove();
		}
	}
	
	static{
		Reader mybatisConfigReader;
		try {
			String fileName =logger.getClass().getResource("/mybatis-config.xml").getPath();
			mybatisConfigReader = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("mybatis-config.xml 文件没有找到!");
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
			localMethodStack.remove();
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
