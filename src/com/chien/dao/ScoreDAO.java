package com.chien.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;

/**
 * @author chien
 *分数表操作类
 */
public class ScoreDAO extends BaseDAO{
	public ScoreDAO(){
		super("score");
	}
	
	/**
	 * 根据学生获取
	 * @param scale 年级
	 * @param course_name 课程名
	 * @return 成绩记录集
	 * @throws SQLException
	 */
	public ResultSet getScoreGroupStudentId(String scale,String course_name) throws SQLException{
		if(course_name.equals("全部")){
			return stat.executeQuery("SELECT score_view.student_id,student_name,academy_name,major_name,class_name,GROUP_CONCAT(course_name),AVG(score) AS score,(1-pass/count(score_view.student_id))*100 FROM score_view JOIN (SELECT count(student_id) AS pass,student_id FROM score_view WHERE score>=60 AND scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY student_id) AS pass ON pass.student_id=score_view.student_id WHERE scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY score_view.student_id ORDER BY score DESC");			
		}else{
			return stat.executeQuery("SELECT score_view.student_id,student_name,academy_name,major_name,class_name,course_name,score,IF(score>=60,0,100) FROM score_view WHERE scale="+scale+" AND course_name='"+course_name+"' GROUP BY student_id");
		}
	}
	
	/**
	 * 根据学院获取
	 * @param scale 年级
	 * @param course_name 课程名
	 * @return 成绩记录集
	 * @throws SQLException
	 */
	public ResultSet getScoreGroupAcademyId(String scale,String course_name) throws SQLException{
		return stat.executeQuery("SELECT academy_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,(1-pass/COUNT(student_id))*100 FROM score_view JOIN (SELECT COUNT(student_id) AS pass,academy_id FROM score_view WHERE score>=60 AND scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY academy_id) AS pass ON pass.academy_id=score_view.academy_id WHERE scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY score_view.academy_id ORDER BY score DESC");
	}
	
	/**
	 * 根据专业获取
	 * @param scale 年级
	 * @param course_name 课程名
	 * @return 成绩记录集
	 * @throws SQLException
	 */
	public ResultSet getScoreGroupMajorId(String scale,String course_name) throws SQLException{
		return stat.executeQuery("SELECT academy_name,major_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,(1-pass/count(student_id))*100 FROM score_view JOIN (SELECT count(student_id) AS pass,major_id FROM score_view WHERE score>=60 AND scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY major_id) AS pass ON pass.major_id=score_view.major_id WHERE scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY score_view.major_id ORDER BY score DESC");
	}
	
	/**
	 * 根据班级获取
	 * @param scale 年级
	 * @param course_name 课程名
	 * @return 成绩记录集
	 * @throws SQLException
	 */
	public ResultSet getScoreGroupClassId(String scale,String course_name) throws SQLException{
		return stat.executeQuery("SELECT academy_name,major_name,class_name,GROUP_CONCAT(DISTINCT course_name),AVG(score) AS score,(1-pass/count(student_id))*100 FROM score_view JOIN (SELECT COUNT(student_id) AS pass,class_id FROM score_view WHERE score>=60 AND scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY class_id) AS pass ON pass.class_id=score_view.class_id WHERE scale="+scale+(course_name.equals("全部")?"":" AND course_name='"+course_name+"'")+" GROUP BY score_view.class_id ORDER BY score DESC");
	}
	
	/**
	 * @param student_id 学生的ID
	 * @param course_id 课程的ID
	 * @param score 分数
	 * @return 插入的结果
	 */
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