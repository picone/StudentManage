package com.chien.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chien.model.MD5;

public class UserDAO extends BaseDAO{
	
	public UserDAO(){
		super();
	}
	
	/**
	 * 判断登录用户密码正确否
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean login(String username,String password){
		try{
			PreparedStatement stmt=conn.prepareStatement("SELECT password FROM user WHERE username=? LIMIT 1");
			stmt.setString(1,username);
			ResultSet data=stmt.executeQuery();
			if(data.next()){
				return new MD5().md5(password).equals(data.getString(1));
			}
		}catch(SQLException|NoSuchAlgorithmException e){
			System.out.println(e.toString());
		}
		return false;
	}
}