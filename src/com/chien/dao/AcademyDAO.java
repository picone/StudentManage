package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

public class AcademyDAO extends BaseDAO{
	public AcademyDAO(){
		super("academy");
	}
	
	/**
	 * 获取学院的ID
	 * @param name
	 * @return
	 */
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT academy_id FROM academy WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(AcademyDAO.class).error(e.toString());
		}
		return result;
	}
	
	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(String name) throws SQLException{
		return stat.execute("DELETE FROM academy WHERE name='"+name+"'");
	}
	
	public boolean insert(String name) throws SQLException{
		return stat.execute("INSERT INTO academy (name) VALUES ('"+name+"')");
	}
	
	public boolean update(String before,String after) throws SQLException{
		return stat.execute("UPDATE academy SET name='"+after+"' WHERE name='"+before+"'");
	}
}