package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.slf4j.LoggerFactory;

import com.chien.dao.AcademyDAO;
import com.chien.dao.ClassDAO;
import com.chien.dao.MajorDAO;
import com.chien.dao.StudentDAO;

public class StudentPanel extends JPanel{
	
	private static final long serialVersionUID = 5147826233396792277L;
	
	private JTable student;
	private DefaultTableModel student_model;
	private JButton search,create,delete,reflash,print;
	private JTextField keyword;
	
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
		student.setAutoCreateRowSorter(true);
		loadData();
		loadColumn();
		//添加表格
		JScrollPane sp=new JScrollPane();
		sp.setViewportView(student);
		add(BorderLayout.WEST,sp);
		//添加右侧按钮
		JPanel box=new JPanel(new VFlowLayout());
		JPanel p=new JPanel();
		keyword=new JTextField(7);
		search=new JButton("搜索");
		p.add(keyword);
		p.add(search);
		box.add(p);
		create=new JButton("添加");
		box.add(create);
		delete=new JButton("删除");
		box.add(delete);
		reflash=new JButton("刷新");
		box.add(reflash);
		print=new JButton("打印");
		box.add(print);
		add(BorderLayout.EAST,box);
		
		search.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				((TableRowSorter<?>)student.getRowSorter()).setRowFilter(RowFilter.regexFilter(keyword.getText()));
			}
		});
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				student_model.addRow(new String[]{"","","","","",String.valueOf(calendar.get(Calendar.YEAR))});
				student.changeSelection(student.getRowCount()-1, 0, false, false);
			}
		});
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int row=student.getSelectedRow();
				if(row>=0&&JOptionPane.showConfirmDialog(null,"你确定要删除学生"+student.getValueAt(row,4)+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
					db_student.delete((String) student.getValueAt(row,0));
					loadData();
					JOptionPane.showMessageDialog(null,"删除成功!");
				}
			}
		});
		reflash.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				loadData();
				loadColumn();
			}
		});
		print.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				try {
					student.print();
				} catch (PrinterException ex){
					// TODO 自动生成的 catch 块
					LoggerFactory.getLogger(StudentPanel.class).error(ex.toString());
				}
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
			LoggerFactory.getLogger(StudentPanel.class).error(e.toString());
		}
		student_model.addTableModelListener(new MyTableModelListener());
		student.setModel(student_model);
	}
	
	private void loadColumn(){
		TableColumnModel cm=student.getColumnModel();
		final JComboBox<String> cb_academy=new JComboBox<>();
		try {
			ResultSet data=db_academy.getAll();
			while(data.next()){
				cb_academy.addItem(data.getString(2));
			}
		}catch(SQLException e){
			LoggerFactory.getLogger(StudentPanel.class).error(e.toString());
		}
		cm.getColumn(1).setCellEditor(new DefaultCellEditor(cb_academy));
		cb_academy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				final JComboBox<String> cb_major=new JComboBox<>();
				try{
					ResultSet data=db_major.getAll((String)cb_academy.getSelectedItem());
					while(data.next()){
						cb_major.addItem(data.getString(1));
					}
				}catch(SQLException ex){
					LoggerFactory.getLogger(StudentPanel.class).error(e.toString());
				}
				cm.getColumn(2).setCellEditor(new DefaultCellEditor(cb_major));
				cb_major.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						JComboBox<String> cb_class=new JComboBox<>();
						try{
							ResultSet data=db_class.getAll((String)cb_major.getSelectedItem());
							while(data.next()){
								cb_class.addItem(data.getString(1));
							}
						}catch(SQLException ex){
							LoggerFactory.getLogger(StudentPanel.class).error(e.toString());
						}
						cm.getColumn(3).setCellEditor(new DefaultCellEditor(cb_class));
					}
				});
			}
		});
	}
	
	private class MyTableModelListener implements TableModelListener{
		@Override
		public void tableChanged(TableModelEvent e) {
			// TODO 自动生成的方法存根
			int col=e.getColumn(),row=e.getFirstRow();
			if((col==0||col==4||col==5)&&row>0&&row<student.getRowCount()){
				boolean is_finish=true;
				for(int i=0;i<6;i++){
					if(student_model.getValueAt(e.getFirstRow(),i).equals("")){
						is_finish=false;
						break;
					}
				}
				if(is_finish){//当数据输入完整时
					try{
						long student_id=Long.parseLong((String) student_model.getValueAt(e.getFirstRow(),0));
						int class_id=db_class.getId((String) student_model.getValueAt(e.getFirstRow(),3));
						int scale=Integer.parseInt((String) student_model.getValueAt(e.getFirstRow(),5));
						if(JOptionPane.showConfirmDialog(null,"你是否要更新"+student_model.getValueAt(e.getFirstRow(),4)+"的信息吗?","提示",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
							db_student.insert(student_id,(String)student_model.getValueAt(e.getFirstRow(),4),class_id,scale);
						}
					}catch(NumberFormatException ex){
						LoggerFactory.getLogger(StudentPanel.class).info(ex.toString());
						JOptionPane.showMessageDialog(null,"请输入数字");
					}catch(SQLException ex){
						LoggerFactory.getLogger(StudentPanel.class).info(ex.toString());
						JOptionPane.showMessageDialog(null,"学生已存在");
					}
				}
			}
		}
	}
}