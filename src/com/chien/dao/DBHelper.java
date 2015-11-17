package com.chien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.LoggerFactory;

public class DBHelper {
	private static final String url="jdbc:mysql://127.0.0.1/student_manage";
	private static final String type="com.mysql.jdbc.Driver";
	private static final String username="root";
	private static final String password="86760385";
	
	protected Connection conn;
	protected Statement stat;
	
	/**
	 * 构造函数,连接数据库
	 * @throws ClassNotFoundException 
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
	
	public Connection getConnection(){
		return conn;
	}
	
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