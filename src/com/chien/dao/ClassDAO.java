package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

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
			LoggerFactory.getLogger(ClassDAO.class).error(e.toString());
		}
		return result;
	}
	
	public ResultSet getAll(String major) throws SQLException{
		return stat.executeQuery("SELECT class_name FROM class_view WHERE major_name='"+major+"'");
	}
	
	public boolean delete(String name) throws SQLException{
		return stat.execute("DELETE FROM class WHERE name='"+name+"'");
	}
	
	public boolean insert(int major_id,String name) throws SQLException{
		return stat.execute("INSERT INTO class (major_id,name) VALUES ("+major_id+",'"+name+"')");
	}
	
	public boolean update(String before,String after) throws SQLException{
		return stat.execute("UPDATE class SET name='"+after+"' WHERE name='"+before+"'");
	}
}