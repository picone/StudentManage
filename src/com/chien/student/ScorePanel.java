package com.chien.student;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.slf4j.LoggerFactory;

import com.chien.dao.CourseDAO;
import com.chien.dao.ScoreDAO;
import com.chien.dao.StudentDAO;

public class ScorePanel extends JPanel{
	private static final long serialVersionUID = 298929672185156154L;
	
	private JTable table;
	private JComboBox<String> group_case,course_case,scale_case;
	private JButton search,create,reflash,print;
	private JTextField keyword;
	
	private ScoreDAO db_score;
	private StudentDAO db_student;
	private CourseDAO db_course;
	
	public ScorePanel(){
		super(new BorderLayout());
		db_score=new ScoreDAO();
		db_student=new StudentDAO();
		db_course=new CourseDAO();
		//添加表格
		table=new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		JScrollPane sp=new JScrollPane();
		sp.setViewportView(table);
		add(BorderLayout.WEST,sp);
		//添加右侧按钮及选择列表
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
		course_case=new JComboBox<>();
		course_case.addItem("全部");
		course_case.setMaximumSize(new Dimension(5,5));
		try{
			cursor=db_course.getAll();
			while(cursor.next()){
				course_case.addItem(cursor.getString(2));
			}
		}catch(SQLException e){
			LoggerFactory.getLogger(ScorePanel.class).error(e.toString());
		}
		course_case.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
			}
		});
		p.add(course_case);
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
		JPanel box=new JPanel();
		keyword=new JTextField(9);
		search=new JButton("搜索");
		box.add(keyword);
		box.add(search);
		p.add(box);
		create=new JButton("录入");
		p.add(create);
		reflash=new JButton("刷新");
		p.add(reflash);
		print=new JButton("打印");
		p.add(print);
		add(BorderLayout.EAST,p);
		loadData();
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				((TableRowSorter<?>)table.getRowSorter()).setRowFilter(RowFilter.regexFilter(keyword.getText()));
			}
		});
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
					LoggerFactory.getLogger(ScorePanel.class).error(e1.toString());
				}
			}
		});
	}
	
	private void loadData(){
		try{
			String scale=(String)scale_case.getSelectedItem();
			String type=(String)group_case.getSelectedItem();
			String course=(String)course_case.getSelectedItem();
			ResultSet cursor=null;
			String column[]=null;
			if(type.equals("按学生查看")){
				cursor=db_score.getScoreGroupStudentId(scale,course);
				column=new String[]{"学号","姓名","学院","专业","班级","课程","分数","不及格率"};
			}else if(type.equals("按学院查看")){
				cursor=db_score.getScoreGroupAcademyId(scale,course) ;
				column=new String[]{"学院","课程","分数","不及格率"};
			}else if(type.equals("按专业查看")){
				cursor=db_score.getScoreGroupMajorId(scale,course);
				column=new String[]{"学院","专业","课程","分数","不及格率"};
			}else if(type.equals("按班级查看")){
				cursor=db_score.getScoreGroupClassId(scale,course);
				column=new String[]{"学院","专业","班级","课程","分数","不及格率"};
			}
			if(cursor!=null){
				DefaultTableModel tm=new DefaultTableModel(column,0);
				while(cursor.next()){
					Object[] row=new Object[column.length];
					for(int i=0;i<column.length-2;i++){
						row[i]=cursor.getString(i+1);
					}
					row[column.length-2]=cursor.getFloat(column.length-1);
					row[column.length-1]=cursor.getFloat(column.length);
					tm.addRow(row);
				}
				table.setModel(tm);
				//设置表头排序
				TableRowSorter<DefaultTableModel> sorter=new TableRowSorter<>(tm);
				OrderNumberComparator comparator=new OrderNumberComparator(); 
				sorter.setComparator(column.length-2,comparator);
				sorter.setComparator(column.length-1,comparator);
				table.setRowSorter(sorter);
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(ScorePanel.class).error(e.toString());
		}
		System.gc();
	}
	
	private class OrderNumberComparator implements Comparator<Object>{
		@Override
		public int compare(Object o1, Object o2) {
			// TODO 自动生成的方法存根
			return (int)((float)o1-(float)o2);
		}
	}
}