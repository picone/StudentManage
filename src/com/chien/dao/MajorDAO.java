package com.chien.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

/**
 * @author chien
 * 专业表操作类
 */
public class MajorDAO extends BaseDAO{
	public MajorDAO(){
		super("major");
	}
	
	/**
	 * @param name 专业的名字
	 * @return 专业的ID
	 */
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
	
	/**
	 * @param academy 学院名字
	 * @return 专业的记录集
	 * @throws SQLException
	 */
	public ResultSet getAll(String academy) throws SQLException{
		return stat.executeQuery("SELECT major_name FROM major_view WHERE academy_name='"+academy+"'");
	}
	
	/**
	 * @param name 专业的名字
	 * @return 删除的结果
	 * @throws SQLException
	 */
	public boolean delete(String name) throws SQLException{
		return stat.execute("DELETE FROM major WHERE name='"+name+"'");
	}
	
	/**
	 * @param academy_id 父学院的ID
	 * @param name 专业的名字
	 * @return 插入的结果
	 * @throws SQLException
	 */
	public boolean insert(int academy_id,String name) throws SQLException{
		return stat.execute("INSERT INTO major (academy_id,name) VALUES ("+academy_id+",'"+name+"')");
	}
	
	/**
	 * @param before 改名前专业的名字
	 * @param after 改名后专业的名字
	 * @return 改名的结果
	 * @throws SQLException
	 */
	public boolean update(String before,String after) throws SQLException{
		return stat.execute("UPDATE major SET name='"+after+"' WHERE name='"+before+"'");
	}
}