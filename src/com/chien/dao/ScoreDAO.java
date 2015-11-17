package com.chien.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

public class ScoreDAO extends BaseDAO{
	public ScoreDAO(){
		super("score");
	}
	
	public ResultSet getScoreGroupStudentId(String scale) throws SQLException{
		return stat.executeQuery("SELECT score_view.student_id,student_name,academy_name,major_name,class_name,GROUP_CONCAT(course_name),AVG(score) AS score,pass/count(score_view.student_id)*100 FROM score_view JOIN (SELECT count(student_id) AS pass,student_id FROM score_view WHERE score>=60 AND scale="+scale+" GROUP BY student_id) AS pass ON pass.student_id=score_view.student_id WHERE scale="+scale+" GROUP BY score_view.student_id ORDER BY score DESC");
	}
	
	public ResultSet getScoreGroupAcademyId(String scale) throws SQLException{
		return stat.executeQuery("SELECT academy_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,pass/COUNT(student_id)*100 FROM score_view JOIN (SELECT COUNT(student_id) AS pass,academy_id FROM score_view WHERE score>=60 AND scale="+scale+" GROUP BY academy_id) AS pass ON pass.academy_id=score_view.academy_id WHERE scale="+scale+" GROUP BY score_view.academy_id ORDER BY score DESC");
	}
	
	public ResultSet getScoreGroupMajorId(String scale) throws SQLException{
		return stat.executeQuery("SELECT academy_name,major_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,pass/count(student_id)*100 FROM score_view JOIN (SELECT count(student_id) AS pass,major_id FROM score_view WHERE score>=60 AND scale="+scale+" GROUP BY major_id) AS pass ON pass.major_id=score_view.major_id WHERE scale="+scale+" GROUP BY score_view.major_id ORDER BY score DESC");
	}
	
	public ResultSet getScoreGroupClassId(String scale) throws SQLException{
		return stat.executeQuery("SELECT academy_name,major_name,class_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,pass/count(student_id)*100 FROM score_view JOIN (SELECT COUNT(student_id) AS pass,class_id FROM score_view WHERE score>=60 AND scale="+scale+" GROUP BY class_id) AS pass ON pass.class_id=score_view.class_id WHERE scale="+scale+" GROUP BY score_view.class_id ORDER BY score DESC");
	}
	
	public boolean insert(long student_id,int course_id,int score){
		try{
			PreparedStatement stat=conn.prepareStatement("REPLACE INTO score (student_id,course_id,score) VALUES (?,?,?)");
			stat.setLong(1,student_id);
			stat.setInt(2,course_id);
			stat.setInt(3,score);
			return stat.execute();
		}catch(SQLException e){
			LoggerFactory.getLogger(ScoreDAO.class).error(e.toString());
		}
		return false;
	}
}