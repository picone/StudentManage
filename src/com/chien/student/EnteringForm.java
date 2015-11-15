package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chien.dao.CourseDAO;
import com.chien.dao.ScoreDAO;
import com.chien.dao.StudentDAO;

public class EnteringForm extends JFrame{
	private static final long serialVersionUID = 4089283883665440537L;

	private boolean has_input=false;
	private int course_id=0;
	
	private JComboBox<String> course;
	private JTextField student_id,score;
	private JLabel student_name;
	private JButton create;
	
	private CourseDAO db_course;
	private ScoreDAO db_score;
	private StudentDAO db_student;
	
	public EnteringForm(){
		db_course=new CourseDAO();
		db_score=new ScoreDAO();
		db_student=new StudentDAO();
		//初始化界面
		setTitle("成绩录入");
		
		course=new JComboBox<>();
		try {
			ResultSet cursor=db_course.getAll();
			while(cursor.next()){
				course.addItem(cursor.getString(2));
			}
		} catch (SQLException e) {
			
		}
		course.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				course_id=db_course.getId((String) course.getSelectedItem());
			}
		});
		course_id=db_course.getId((String) course.getSelectedItem());
		add(course,BorderLayout.NORTH);
		
		JPanel p=new JPanel();
		p.add(new JLabel("学号"));
		student_id=new JTextField(9);
		student_id.setDocument(new NumberLimitedDoc(10));
		p.add(student_id);
		p.add(new JLabel("分数"));
		score=new JTextField(3);
		score.setDocument(new NumberLimitedDoc(3));
		score.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				String name=db_student.getName(student_id.getText());
				if(name==null||name.equals("")){
					student_name.setText("不存在");
					has_input=false;
				}else{
					student_name.setText(name);
					has_input=true;
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		p.add(score);
		student_name=new JLabel("请输入");
		p.add(student_name);
		add(p,BorderLayout.CENTER);
		
		create=new JButton("录入");
		add(create,BorderLayout.SOUTH);
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(has_input){
					db_score.insert(Long.parseLong(student_id.getText()),course_id,Integer.parseInt(score.getText()));
					student_id.setText("");
					score.setText("");
					has_input=false;
					student_name.setText("");
				}else{
					JOptionPane.showMessageDialog(null,"请输入正确的学号");
				}
			}
		});
		getRootPane().setDefaultButton(create);
		
		pack();
		setLocationRelativeTo(null);
	}
}