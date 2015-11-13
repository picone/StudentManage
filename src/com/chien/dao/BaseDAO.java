package com.chien.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chien.student.Main;

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
	
	public ResultSet getAll() throws SQLException{
		return stat.executeQuery("SELECT * FROM "+table_name);
	}
}