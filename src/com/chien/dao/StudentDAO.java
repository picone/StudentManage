package com.chien.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO extends BaseDAO{
	public StudentDAO(){
		super("student");
	}
	
	public ResultSet getAll() throws SQLException{
		return stat.executeQuery("SELECT * FROM student_info");
	}
	
	public boolean delete(String student_id){
		try {
			return stat.execute("DELETE FROM student WHERE student_id="+student_id);
		} catch (SQLException e) {
			
		}
		return false;
	}
	
	public boolean insert(long id,String name,int academy,int major,int c,int scale){
		try{
			PreparedStatement stat=conn.prepareStatement("REPLACE INTO student (student_id,name,academy_id,major_id,class_id,scale) VALUES (?,?,?,?,?,?)");
			stat.setLong(1,id);
			stat.setString(2,name);
			stat.setInt(3,academy);
			stat.setInt(4,major);
			stat.setInt(5,c);
			stat.setInt(6,scale);
			return stat.execute();
		}catch(SQLException e){
			
		}
		return false;
	}
}