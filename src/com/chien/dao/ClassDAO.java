package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassDAO extends BaseDAO{
	public ClassDAO(){
		super("class");
	}
	
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT class_id FROM class WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			
		}
		return result;
	}
}