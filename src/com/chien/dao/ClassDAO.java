package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

/**
 * @author chien
 *班级表操作类
 */
public class ClassDAO extends BaseDAO{
	public ClassDAO(){
		super("class");
	}
	
	/**
	 * @param name 班级名字
	 * @return 班级的ID
	 */
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
	
	/**
	 * @param major 专业名字
	 * @return 对应专业的班级记录集
	 * @throws SQLException
	 */
	public ResultSet getAll(String major) throws SQLException{
		return stat.executeQuery("SELECT class_name FROM class_view WHERE major_name='"+major+"'");
	}
	
	/**
	 * @param name 删除的班级名字
	 * @return 删除的结果
	 * @throws SQLException
	 */
	public boolean delete(String name) throws SQLException{
		return stat.execute("DELETE FROM class WHERE name='"+name+"'");
	}
	
	/**
	 * @param major_id 专业ID
	 * @param name 班级名字
	 * @return 插入的结果
	 * @throws SQLException
	 */
	public boolean insert(int major_id,String name) throws SQLException{
		return stat.execute("INSERT INTO class (major_id,name) VALUES ("+major_id+",'"+name+"')");
	}
	
	/**
	 * @param before 修改前班级的名字
	 * @param after 修改后班级的名字
	 * @return 修改的结果
	 * @throws SQLException
	 */
	public boolean update(String before,String after) throws SQLException{
		return stat.execute("UPDATE class SET name='"+after+"' WHERE name='"+before+"'");
	}
}