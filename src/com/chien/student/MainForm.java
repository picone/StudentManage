package com.chien.student;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import com.chien.dao.UserDAO;

public class MainForm extends JFrame{
	
	private static final long serialVersionUID = -3525935019398413594L;
	
	private int auth;
	//数据库模型
	private UserDAO db_user;
	
	public MainForm(int uid,String username){
		//加载操作者信息
		db_user=new UserDAO();
		auth=db_user.getAuth(uid);
		//初始化界面
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("学生成绩管理系统");
		
		add(new JLabel("你好"+username+",欢迎使用学生成绩管理系统"),BorderLayout.NORTH);
		JTabbedPane tb=new JTabbedPane();
		if((auth&1)==1)tb.addTab("成绩",new ScorePanel());
		if((auth&2)==2)tb.addTab("学生",new StudentPanel());
		if((auth&4)==4)tb.addTab("班级",new ClassPanel());
		if((auth&8)==8)tb.addTab("课程",new CoursePanel());
		if((auth&16)==16)tb.addTab("权限",new AuthPanel());
		add(tb,BorderLayout.CENTER);
		setJMenuBar(new MainMenuBar());
		pack();
		setLocationRelativeTo(null);
	}
}