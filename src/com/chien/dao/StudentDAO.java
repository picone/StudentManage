package com.chien.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO extends BaseDAO{
	public StudentDAO(){
		super("student");
	}
	
	public ResultSet getAll() throws SQLException{
		return stat.executeQuery("SELECT student_id,academy_name,major_name,class_name,student_name,scale FROM student_view");
	}
	
	public boolean delete(String student_id){
		try {
			return stat.execute("DELETE FROM student WHERE student_id="+student_id);
		} catch (SQLException e) {
			
		}
		return false;
	}
	
	public boolean insert(long id,String name,int c,int scale){
		try{
			PreparedStatement stat=conn.prepareStatement("REPLACE INTO student (student_id,name,class_id,scale) VALUES (?,?,?,?)");
			stat.setLong(1,id);
			stat.setString(2,name);
			stat.setInt(3,c);
			stat.setInt(4,scale);
			return stat.execute();
		}catch(SQLException e){
			System.out.println(e.toString());
		}
		return false;
	}
	
	public ResultSet getScale() throws SQLException{
		return stat.executeQuery("SELECT DISTINCT scale FROM student");
	}
	
	public String getName(String student_id){
		try{
			ResultSet cursor=stat.executeQuery("SELECT name FROM student WHERE student_id="+student_id);
			if(cursor.next()){
				return cursor.getString(1);
			}
		}catch(SQLException e){
			
		}
		return null;
	}
}