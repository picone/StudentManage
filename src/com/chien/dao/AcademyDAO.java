package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademyDAO extends BaseDAO{
	public AcademyDAO(){
		super("academy");
	}
	
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT academy_id FROM academy WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			
		}
		return result;
	}
}