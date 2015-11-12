package com.chien.student;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.chien.dao.UserDAO;

public class LoginForm extends JFrame{

	private static final long serialVersionUID = 6821574138661678597L;
	
	private JTextField username;
	private JPasswordField password;
	private JButton login,exit;
	
	private UserDAO db_user;
	
	public LoginForm(){
		db_user=new UserDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(4,1));
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(200,120);
		setTitle("用户登录");
		//标题
		JPanel p=new JPanel();
		JLabel l=new JLabel("学生信息管理系统");
		l.setFont(new Font(Font.SERIF,Font.BOLD,20));
		p.add(l);
		add(p);
		//用户名
		Box box=Box.createHorizontalBox();
		box.add(new JLabel("用户:"));
		box.add(Box.createHorizontalStrut(6));
		username=new JTextField();
		box.add(username);
		add(box);
		//密码
		box=Box.createHorizontalBox();
		box.add(new JLabel("密码:"));
		box.add(Box.createHorizontalStrut(6));
		password=new JPasswordField();
		box.add(password);
		add(box);
		//按钮
		p=new JPanel(new BorderLayout());
		login=new JButton("登录");
		p.add(BorderLayout.WEST,login);
		exit=new JButton("退出");
		p.add(BorderLayout.EAST,exit);
		add(p);
		//登录按钮监听器
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(db_user.login(username.getText(),new String(password.getPassword()))){
					
				}else{
					JOptionPane.showMessageDialog(null,"用户名或密码错误");
					password.setText("");
				}
			}
		});
		//退出监听器
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);
			}
		});
	}
}