package com.chien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

/**
 * 数据库连接类
 */
public class DBHelper {
	private static final String url="jdbc:mysql://127.0.0.1/student_manage";//数据库地址
	private static final String type="com.mysql.jdbc.Driver";//使用的数据库类型
	private static final String username="root";//数据库用户名
	private static final String password="86760385";//数据库密码
	
	protected Connection conn;
	protected Statement stat;
	
	/**
	 * 构造函数,连接数据库
	 * @throws SQLException 
	 */
	public DBHelper() throws SQLException{
		try {
			Class.forName(type);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			LoggerFactory.getLogger(DBHelper.class).error(e.toString());
			e.printStackTrace();
		}
		conn=DriverManager.getConnection(url,username,password);
		stat=conn.createStatement();
	}
	
	/**
	 * @return Connection 返回当前数据库连接
	 */
	public Connection getConnection(){
		return conn;
	}
	
	/**
	 * @return Statement 返回当前数据库执行对象
	 */
	public Statement getStat(){
		return stat;
	}
	
	/**
	 * 关闭链接
	 */
	public void Close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			LoggerFactory.getLogger(DBHelper.class).error(e.toString());
			e.printStackTrace();
		}
	}
}