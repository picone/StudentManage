package com.chien.student;

import java.sql.SQLException;

import com.chien.dao.DBHelper;

public class Main {
	private static DBHelper DB;
	
	public static void main(String args[]){
		try{
			DB=new DBHelper();
			new LoginForm().setVisible(true);
		}catch(SQLException e){
			System.out.println("数据库连接失败");
		}
	}
	
	public static DBHelper getDB(){
		return DB;
	}
}