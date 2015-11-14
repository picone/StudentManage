package com.chien.student;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
		setSize(520,300);
		setTitle("学生成绩管理系统");
		JTabbedPane tb=new JTabbedPane();
		JPanel p;
		tb.addTab("成绩查询",new ScorePanel());
		tb.addTab("学生管理",new StudentPanel());
		tb.addTab("班级管理",new JPanel());
		tb.addTab("课程管理",new JPanel());
		tb.addTab("权限管理",new JPanel());
		tb.setSelectedIndex(0);
		add(BorderLayout.CENTER,tb);
		setJMenuBar(new MainMenuBar());
		pack();
		setLocationRelativeTo(null);
	}
}