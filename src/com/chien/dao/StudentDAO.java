package com.chien.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

/**
 * @author chien
 *学生表操作类
 */
public class StudentDAO extends BaseDAO{
	public StudentDAO(){
		super("student");
	}
	
	public ResultSet getAll() throws SQLException{
		return stat.executeQuery("SELECT student_id,academy_name,major_name,class_name,student_name,scale FROM student_view");
	}
	
	/**
	 * @param student_id 学生的ID
	 * @return 删除的结果
	 */
	public boolean delete(String student_id){
		try {
			return stat.execute("DELETE FROM student WHERE student_id="+student_id);
		} catch (SQLException e) {
			LoggerFactory.getLogger(StudentDAO.class).error(e.toString());
		}
		return false;
	}
	
	/**
	 * @param id 学生ID
	 * @param name 学生姓名
	 * @param c 学生班级ID
	 * @param scale 学生的年级
	 * @return 插入的结果
	 */
	public boolean insert(long id,String name,int c,int scale){
		try{
			PreparedStatement stat=conn.prepareStatement("REPLACE INTO student (student_id,name,class_id,scale) VALUES (?,?,?,?)");
			stat.setLong(1,id);
			stat.setString(2,name);
			stat.setInt(3,c);
			stat.setInt(4,scale);
			return stat.execute();
		}catch(SQLException e){
			LoggerFactory.getLogger(StudentDAO.class).error(e.toString());
		}
		return false;
	}
	
	/**
	 * @return 获取所有学生的年级
	 * @throws SQLException
	 */
	public ResultSet getScale() throws SQLException{
		return stat.executeQuery("SELECT DISTINCT scale FROM student");
	}
	
	/**
	 * @param student_id 学生的ID 
	 * @return 学生的名字
	 */
	public String getName(String student_id){
		try{
			ResultSet cursor=stat.executeQuery("SELECT name FROM student WHERE student_id="+student_id);
			if(cursor.next()){
				return cursor.getString(1);
			}
		}catch(SQLException e){
			LoggerFactory.getLogger(StudentDAO.class).error(e.toString());
		}
		return null;
	}
}