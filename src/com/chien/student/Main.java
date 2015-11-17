package com.chien.student;

import java.sql.SQLException;

import org.slf4j.LoggerFactory;

import com.chien.dao.DBHelper;

public class Main {
	private static DBHelper DB;
	
	public static void main(String args[]){
		try{
			DB=new DBHelper();
			new LoginForm().setVisible(true);
		}catch(SQLException e){
			LoggerFactory.getLogger(Main.class).error("数据库链接失败");
			e.printStackTrace();
		}
	}
	
	public static DBHelper getDB(){
		return DB;
	}
}