package com.chien.student;

import com.chien.dao.DBHelper;

public class Main {
	private static DBHelper DB;
	
	public static void main(String args[]){
		DB=new DBHelper();
		new LoginForm().setVisible(true);
	}
	
	public static DBHelper getDB(){
		return DB;
	}
}