package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorDAO extends BaseDAO{
	public MajorDAO(){
		super("major");
	}
	
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT major_id FROM major WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			
		}
		return result;
	}
}