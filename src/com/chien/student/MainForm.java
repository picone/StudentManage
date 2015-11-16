package com.chien.student;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.chien.dao.UserDAO;

public class MainForm extends JFrame{
	
	private static final long serialVersionUID = -3525935019398413594L;
	
	//操作者信息
	private int uid=0;
	private String username;
	private int auth;
	//数据库模型
	private UserDAO db_user;
	
	public MainForm(int uid,String username){
		//加载操作者信息
		this.uid=uid;
		this.username=username;
		db_user=new UserDAO();
		auth=db_user.getAuth(uid);
		//初始化界面
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("学生成绩管理系统");
		JTabbedPane tb=new JTabbedPane();
		tb.addTab("成绩",new ScorePanel());
		tb.addTab("学生",new StudentPanel());
		tb.addTab("班级",new ClassPanel());
		tb.addTab("课程",new CoursePanel());
		tb.addTab("权限",new AuthPanel());
		tb.setSelectedIndex(4);
		add(BorderLayout.CENTER,tb);
		setJMenuBar(new MainMenuBar());
		pack();
		setLocationRelativeTo(null);
	}
}