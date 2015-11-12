package com.chien.dao;

import java.sql.Connection;
import java.sql.Statement;

import com.chien.student.Main;

public class BaseDAO {
	protected DBHelper DB;
	protected Connection conn;
	protected Statement stat;
	
	public BaseDAO(){
		DB=Main.getDB();
		conn=DB.getConnection();
		stat=DB.getStat();
	}
}