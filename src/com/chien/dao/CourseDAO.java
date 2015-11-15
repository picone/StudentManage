package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAO extends BaseDAO{
	public CourseDAO(){
		super("course");
	}
	
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT course_id FROM course WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			
		}
		return result;
	}
	
	public boolean insert(int course_id,String name){
		try{
			if(course_id==0){
				return stat.execute("INSERT INTO course (name) VALUES ('"+name+"')");
			}else{
				stat.execute("UPDATE course SET name='"+name+"' WHERE course_id="+course_id);
			}
		}catch(Exception e){
			
		}
		return false;
	}
}