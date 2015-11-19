package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

/**
 * @author chien
 *课程表操作类
 */
public class CourseDAO extends BaseDAO{
	public CourseDAO(){
		super("course");
	}
	
	/**
	 * @param name 课程名字
	 * @return 课程的ID
	 */
	public int getId(String name){
		int result=0;
		try {
			ResultSet data=stat.executeQuery("SELECT course_id FROM course WHERE name='"+name+"'");
			if(data.next()){
				result=data.getInt(1);
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(CourseDAO.class).error(e.toString());
		}
		return result;
	}
	
	/**
	 * @param course_id 课程的ID
	 * @param name 课程的名字
	 * @return 替换的结果
	 */
	public boolean insert(int course_id,String name){
		try{
			if(course_id==0){
				return stat.execute("INSERT INTO course (name) VALUES ('"+name+"')");
			}else{
				stat.execute("UPDATE course SET name='"+name+"' WHERE course_id="+course_id);
			}
		}catch(SQLException e){
			LoggerFactory.getLogger(CourseDAO.class).error(e.toString());
		}
		return false;
	}
	
	/**
	 * @param course_id 课程的ID
	 * @return 删除的结果
	 */
	public boolean delete(int course_id){
		try{
			return stat.execute("DELETE FROM course WHERE course_id="+course_id);
		}catch(SQLException e){
			LoggerFactory.getLogger(CourseDAO.class).error(e.toString());
		}
		return false;
	}
}