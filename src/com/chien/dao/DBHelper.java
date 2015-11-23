package com.chien.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.LoggerFactory;

/**
 * 数据库连接类
 */
public class DBHelper {
	private static String url;//数据库地址
	private static String type="com.mysql.jdbc.Driver";//使用的数据库类型
	private static String username;//数据库用户名
	private static String password;//数据库密码
	
	protected Connection conn;
	protected Statement stat;
	
	/**
	 * 构造函数,连接数据库
	 * @throws SQLException 
	 */
	public DBHelper() throws SQLException{
		try {
			Properties setting=new Properties();
			InputStream file=Object.class.getResourceAsStream("/database.properties");
			setting.load(file);
			url="jdbc:mysql://"+setting.getProperty("ip").trim()+":"+setting.getProperty("port").trim()+"/"+setting.getProperty("database").trim()+"?useUnicode=true&characterEncoding=utf8";
			username=setting.getProperty("username").trim();
			password=setting.getProperty("password").trim();
			file.close();
			Class.forName(type);
		} catch (ClassNotFoundException | IOException e) {
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