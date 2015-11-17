package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

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
			LoggerFactory.getLogger(MajorDAO.class).error(e.toString());
		}
		return result;
	}
	
	public ResultSet getAll(String academy) throws SQLException{
		return stat.executeQuery("SELECT major_name FROM major_view WHERE academy_name='"+academy+"'");
	}
	
	public boolean delete(String name) throws SQLException{
		return stat.execute("DELETE FROM major WHERE name='"+name+"'");
	}
	
	public boolean insert(int academy_id,String name) throws SQLException{
		return stat.execute("INSERT INTO major (academy_id,name) VALUES ("+academy_id+",'"+name+"')");
	}
	
	public boolean update(String before,String after) throws SQLException{
		return stat.execute("UPDATE major SET name='"+after+"' WHERE name='"+before+"'");
	}
}