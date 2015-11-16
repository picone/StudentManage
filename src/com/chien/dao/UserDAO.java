package com.chien.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chien.model.MD5;

public class UserDAO extends BaseDAO{
	
	public UserDAO(){
		super("user");
	}
	
	/**
	 * 判断登录用户密码正确否
	 * @param string username 用户名
	 * @param string password 密码
	 * @return number 用户ID
	 */
	public int login(String username,String password){
		try{
			PreparedStatement stmt=conn.prepareStatement("SELECT password,uid FROM user WHERE username=? LIMIT 1");
			stmt.setString(1,username);
			ResultSet data=stmt.executeQuery();
			if(data.next()){
				if(new MD5().md5(password).equals(data.getString(1))){
					return data.getInt(2);
				}
			}
		}catch(SQLException|NoSuchAlgorithmException e){
			
		}
		return 0;
	}
	
	public ResultSet getUserInfo(int uid){
		try{
			PreparedStatement stmt=conn.prepareStatement("SELECT username,auth FROM user WHERE uid=? LIMIT 1");
			stmt.setInt(1,uid);
			return stmt.executeQuery();
		}catch(SQLException e){
			
		}
		return null;
	}
	
	public int getAuth(int uid){
		try{
			PreparedStatement stmt=conn.prepareStatement("SELECT auth FROM user WHERE uid=? LIMIT 1");
			stmt.setInt(1,uid);
			ResultSet data=stmt.executeQuery();
			if(data.next()){
				return data.getInt(1);
			}
		}catch(SQLException e){
			
		}
		return 0;
	}
	
	public boolean insert(String name,String password,int auth) throws SQLException, NoSuchAlgorithmException{
		password=new MD5().md5(password);
		return stat.execute("INSERT INTO user (username,password,auth) VALUES ('"+name+"','"+password+"',"+auth+")");
	}
	
	public boolean update(String uid,String name,int auth) throws SQLException{
		return stat.execute("UPDATE user SET username='"+name+"',auth="+auth+" WHERE uid="+uid);
	}
	
	public boolean delete(String uid) throws SQLException{
		return stat.execute("DELETE FROM user WHERE uid="+uid);
	}
}