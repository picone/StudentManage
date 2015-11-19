package com.chien.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chien.student.Main;

/**
 * @author chien
 *数据库基础类
 */
public class BaseDAO {
	protected DBHelper DB;
	protected Connection conn;
	protected Statement stat;
	protected String table_name;
	
	public BaseDAO(String table_name){
		DB=Main.getDB();
		conn=DB.getConnection();
		stat=DB.getStat();
		this.table_name=table_name;
	}
	
	/**
	 * 获取表中所有记录
	 * @return ResultSet 记录集
	 * @throws SQLException
	 */
	public ResultSet getAll() throws SQLException{
		return stat.executeQuery("SELECT * FROM "+table_name);
	}
}