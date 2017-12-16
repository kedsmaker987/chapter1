package org.smart4j.chapter1.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.PropsUtil;

/**
 * 数据库操作
 * @author Administrator
 *
 */
public final class DatabaseHelper {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static final QueryRunner QUERY_RUNNER;
	
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	
	private static final BasicDataSource DATA_SOURCE;
	
	private DatabaseHelper(){
		
	}
	
	/*
	 * 初始化配置文件
	 */
	static{
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		QUERY_RUNNER = new QueryRunner();
		Properties conf = PropsUtil.loadProps("config.properties");
		String dirver  = conf.getProperty("jdbc.driver");
		String url  = conf.getProperty("jdbc.url");
		String username = conf.getProperty("jdbc.username");
		String password = conf.getProperty("jdbc.password");
		
		/**
		 * 数据池
		 */
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(dirver);
		DATA_SOURCE.setUrl(url);
		DATA_SOURCE.setUsername(username);
		DATA_SOURCE.setPassword(password);
	}
	
	/**
	 * 获取链接
	 * @return
	 */
	public static Connection getConnection(){
		Connection conn = CONNECTION_HOLDER.get();
		if(conn == null){
			try{
				conn = DATA_SOURCE.getConnection();
			}catch(Exception e){
				logger.error("get connection failure ",e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}
	
	/**
	 * 查询实体类list
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object...params){
		List<T> entityList;
		try{
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql,new BeanListHandler<T>(entityClass),params);
		}catch(Exception e){
			logger.error("query entity list failure",e);
			throw new RuntimeException(e);
		}
		return entityList;
	}
	
	/**
	 * 查询实体
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <T> T queryEntity(Class<T> entityClass,String sql,Object...params){
		T entity;
		try{
			Connection conn= getConnection();
			entity = QUERY_RUNNER.query(conn, sql,new BeanHandler<T>(entityClass),params);
		}catch(Exception e){
			logger.error("query entity failure",e);
			throw new RuntimeException(e);
		}
		return entity;
	}
	
	/**
	 * 复杂sql查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public static List<Map<String,Object>> executeQuery(String sql,Object...params){
		List<Map<String,Object>> result;
		
		try{
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn, sql,new MapListHandler(),params);
		}catch(Exception e){
			logger.error("execute query failure",e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	
	/**
	 * 更新语句 update insert delete
	 * @param sql
	 * @param param
	 * @return
	 */
	public static int executeUpdate(String sql,Object...param){
		int rows = 0;
		try{
			Connection conn = getConnection();
			rows = QUERY_RUNNER.update(conn,sql,param);
		}catch(Exception e){
			logger.error("execute update failure ",e);
		}
		
		return rows;
	}
	
	
	/**
	 * insert 
	 * @param entityClass
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
		if(CollectionUtil.isEmpty(fieldMap)){
			logger.error("can not insert entity: fieldMap is empty");
			return false;
		}
		
		String sql = "insert into "+getTabName(entityClass);
		StringBuffer columns = new StringBuffer("(");
		StringBuffer values = new StringBuffer("(");
		
		for(String fieldName: fieldMap.keySet()){
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		
		// 最后一个 , 替代
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql +=columns+" values "+values;
		
		Object[] params = fieldMap.values().toArray();
		
		return executeUpdate(sql, params) == 1;
	}
	
	/**
	 * update
	 * @param entityClass
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
		if(CollectionUtil.isEmpty(fieldMap)){
			 logger.error("can not update entity : fieldMap is empty");
			 return false;
		}
		
		String sql = "update "+ getTabName(entityClass) +" set ";
		StringBuilder columns = new StringBuilder();
		for(String fieldName:fieldMap.keySet()){
			columns.append(fieldName).append("=?, ");
		}
		
		sql +=columns.substring(0,columns.lastIndexOf(", "))+" where id = ?";
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		
		Object[] params = paramList.toArray();
		return executeUpdate(sql, params) == 1;
	}
	
	
	/**
	 * delete
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass,long id){
		String sql = "delete from "+ getTabName(entityClass)+ " where id = ?";
		return executeUpdate(sql, id) == 1;
	}
	
	/**
	 * 执行 sql 文件
	 * @param filePath
	 */
	public static void executeSqlFile(String filePath){
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try{
			String sql;
			while((sql=reader.readLine())!=null){
				executeUpdate(sql);
			}
		}catch(Exception e){
			logger.error("execute sql file failure",e);
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	private static String getTabName(Class<?> entityClass){
		return entityClass.getSimpleName();
	}
	
	
}
