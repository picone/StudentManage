package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.slf4j.LoggerFactory;

import com.chien.dao.ScoreDAO;
import com.chien.dao.StudentDAO;

public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 298929672185156154L;
	
	private JTable table;
	private JComboBox<String> group_case,scale_case;
	private JButton create,reflash,print;
	
	private ScoreDAO db_score;
	private StudentDAO db_student;
	
	public ScorePanel(){
		super(new BorderLayout());
		db_score=new ScoreDAO();
		db_student=new StudentDAO();
		
		table=new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane sp=new JScrollPane();
		sp.setViewportView(table);
		add(BorderLayout.WEST,sp);
		
		JPanel p=new JPanel(new VFlowLayout());
		scale_case=new JComboBox<>();
		ResultSet cursor;
		try {
			cursor = db_student.getScale();
			while(cursor.next()){
				scale_case.addItem(cursor.getString(1));
			}
		}catch (SQLException e) {
			LoggerFactory.getLogger(ScorePanel.class).error(e.toString());
		}
		scale_case.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
			}
		});
		p.add(scale_case);
		group_case=new JComboBox<>();
		group_case.addItem("按学生查看");
		group_case.addItem("按学院查看");
		group_case.addItem("按专业查看");
		group_case.addItem("按班级查看");
		group_case.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
			}}
		);
		p.add(group_case);
		create=new JButton("录入");
		p.add(create);
		reflash=new JButton("刷新");
		p.add(reflash);
		print=new JButton("打印");
		p.add(print);
		add(BorderLayout.EAST,p);
		loadData();
		
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				new EnteringForm().setVisible(true);
			}
		});
		reflash.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
			}
		});
		print.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try {
					table.print();
				} catch (PrinterException e1) {
					// TODO 自动生成的 catch 块
					LoggerFactory.getLogger(ScorePanel.class).error(e.toString());
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void loadData(){
		try{
			String scale=(String)scale_case.getSelectedItem();
			String type=(String)group_case.getSelectedItem();
			ResultSet cursor=null;
			String column[]=null;
			if(type.equals("按学生查看")){
				cursor=db_score.getScoreGroupStudentId(scale);
				column=new String[]{"学号","姓名","学院","专业","班级","课程","分数","及格率%"};
			}else if(type.equals("按学院查看")){
				cursor=db_score.getScoreGroupAcademyId(scale) ;
				column=new String[]{"学院","课程","分数","及格率"};
			}else if(type.equals("按专业查看")){
				cursor=db_score.getScoreGroupMajorId(scale);
				column=new String[]{"学院","专业","课程","分数","及格率"};
			}else if(type.equals("按班级查看")){
				cursor=db_score.getScoreGroupClassId(scale);
				column=new String[]{"学院","专业","班级","课程","分数","及格率"};
			}
			if(cursor!=null){
				DefaultTableModel tm=new DefaultTableModel(column,0);
				while(cursor.next()){
					String[] row=new String[column.length];
					for(int i=0;i<column.length;i++){
						row[i]=cursor.getString(i+1);
					}
					tm.addRow(row);
				}
				table.setModel(tm);
				table.setRowSorter(new TableRowSorter<DefaultTableModel>(tm));
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(ScorePanel.class).error(e.toString());
		}
		System.gc();
	}
}