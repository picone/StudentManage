package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.chien.dao.AcademyDAO;
import com.chien.dao.ClassDAO;
import com.chien.dao.MajorDAO;
import com.chien.dao.StudentDAO;

public class StudentPanel extends JPanel{
	
	private static final long serialVersionUID = 5147826233396792277L;
	
	private JTable student;
	private DefaultTableModel student_model;
	private JButton create,delete,reflash;
	
	private StudentDAO db_student;
	private AcademyDAO db_academy;
	private MajorDAO db_major;
	private ClassDAO db_class;
	
	private Calendar calendar;
	
	public StudentPanel(){
		super(new BorderLayout());
		db_student=new StudentDAO();
		db_academy=new AcademyDAO();
		db_major=new MajorDAO();
		db_class=new ClassDAO();
		calendar=Calendar.getInstance();
		
		student=new JTable();
		student.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		loadData();
		TableColumnModel cm=student.getColumnModel();
		try {
			JComboBox<String> cb=new JComboBox<>();
			ResultSet data=db_academy.getAll();
			while(data.next()){
				cb.addItem(data.getString(2));
			}
			cm.getColumn(1).setCellEditor(new DefaultCellEditor(cb));
			data=db_major.getAll();
			cb=new JComboBox<>();
			while(data.next()){
				cb.addItem(data.getString(2));
			}
			cm.getColumn(2).setCellEditor(new DefaultCellEditor(cb));
			data=db_class.getAll();
			cb=new JComboBox<>();
			while(data.next()){
				cb.addItem(data.getString(2));
			}
			cm.getColumn(3).setCellEditor(new DefaultCellEditor(cb));
		} catch (SQLException e) {
			
		}
		
		JScrollPane sp=new JScrollPane();
		sp.setViewportView(student);
		add(BorderLayout.WEST,sp);
		
		Box box=Box.createVerticalBox();
		create=new JButton("添加");
		box.add(create);
		box.add(Box.createVerticalStrut(6));
		delete=new JButton("删除");
		box.add(delete);
		box.add(Box.createVerticalStrut(6));
		reflash=new JButton("刷新");
		box.add(reflash);
		add(BorderLayout.EAST,box);
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				student_model.addRow(new String[]{"","","","","",String.valueOf(calendar.get(Calendar.YEAR))});
			}
		});
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int row=student.getSelectedRow();
				if(row>0&&JOptionPane.showConfirmDialog(null,"你确定要删除学生"+student_model.getValueAt(row,4)+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
					db_student.delete((String) student_model.getValueAt(row,0));
					student_model.removeRow(row);
					JOptionPane.showMessageDialog(null,"删除成功!");
				}
			}
		});
		reflash.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
			}
		});
	}
	
	private void loadData(){
		try {
			ResultSet cursor=db_student.getAll();
			student_model=new DefaultTableModel(new String[]{"学号","学院","专业","班级","姓名","年级"},0);
			while(cursor.next()){
				String[] row=new String[6];
				for(int i=0;i<6;i++){
					row[i]=cursor.getString(i+1);
				}
				student_model.addRow(row);
			}
		} catch (SQLException e) {
			
		}
		student_model.addTableModelListener(new MyTableModelListener());
		student.setModel(student_model);
		System.gc();
	}
	
	private class MyTableModelListener implements TableModelListener{
		@Override
		public void tableChanged(TableModelEvent e) {
			// TODO 自动生成的方法存根
			if(e.getFirstRow()>0){
				boolean is_finish=true;
				for(int i=0;i<6;i++){
					if(student_model.getValueAt(e.getFirstRow(),i).equals("")){
						is_finish=false;
						break;
					}
				}
				if(is_finish){
					try{
						long student_id=Long.parseLong((String) student_model.getValueAt(e.getFirstRow(),0));
						int academy_id=db_academy.getId((String) student_model.getValueAt(e.getFirstRow(),1));
						int major_id=db_major.getId((String) student_model.getValueAt(e.getFirstRow(),2));
						int class_id=db_class.getId((String) student_model.getValueAt(e.getFirstRow(),3));
						int scale=Integer.parseInt((String) student_model.getValueAt(e.getFirstRow(),5));
						if(JOptionPane.showConfirmDialog(null,"你是否要更新"+student_model.getValueAt(e.getFirstRow(),4)+"的信息吗?","提示",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
							db_student.insert(student_id,(String)student_model.getValueAt(e.getFirstRow(),4),academy_id, major_id, class_id,scale);
						}
					}catch(NumberFormatException ex){
						JOptionPane.showMessageDialog(null,"请输入数字");
					}
				}
			}
		}
	}
}