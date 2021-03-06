package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.LoggerFactory;

import com.chien.dao.CourseDAO;

public class CoursePanel extends JPanel{
	private static final long serialVersionUID = 916497425927871753L;

	private JList<String> list;
	private JButton create,update,delete;
	private JTextField course_name;
	
	private Vector<String> course_data;
	private int course_id;
	
	private CourseDAO db_course;
	
	public CoursePanel(){
		super(new BorderLayout());
		db_course=new CourseDAO();
		course_data=new Vector<>();
		
		JScrollPane sp=new JScrollPane();
		list=new JList<>();
		sp.setViewportView(list);
		loadData();
		add(sp,BorderLayout.WEST);
		
		JPanel p=new JPanel(new VFlowLayout());
		course_name=new JTextField(20);
		p.add(course_name);
		Box box=Box.createHorizontalBox();
		create=new JButton("添加");
		box.add(create);
		box.add(Box.createHorizontalStrut(6));
		update=new JButton("保存");
		box.add(update);
		box.add(Box.createHorizontalStrut(6));
		delete=new JButton("删除");
		box.add(delete);
		p.add(box);
		add(p,BorderLayout.CENTER);
		
		list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO 自动生成的方法存根
				if(e.getValueIsAdjusting()){
					course_id=db_course.getId(list.getSelectedValue());
					course_name.setText(list.getSelectedValue().trim());
				}
			}
		});
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				course_data.add(" ");
				list.setListData(course_data);
				list.setSelectedIndex(course_data.size()-1);
				course_name.requestFocus();
				course_id=0;
			}
		});
		update.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(course_name.getText().equals("")){
					JOptionPane.showMessageDialog(null,"请输入课程名称");
				}else{
					db_course.insert(course_id,course_name.getText());
					course_name.setText("");
					course_id=0;
					JOptionPane.showMessageDialog(null,"保存成功");
					loadData();
				}
			}
		});
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(course_id>0){
					if(JOptionPane.showConfirmDialog(null,"你是否要删除"+list.getSelectedValue()+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
						db_course.delete(course_id);
						loadData();
					}
				}else{
					course_data.remove(list.getSelectedIndex());
					list.setListData(course_data);
				}
			}
		});
	}
	
	private void loadData(){
		try{
			ResultSet cursor=db_course.getAll();
			course_data.clear();
			while(cursor.next()){
				course_data.add(cursor.getString(2));
			}
			list.setListData(course_data);
		}catch(SQLException e){
			LoggerFactory.getLogger(CoursePanel.class).error(e.toString());
		}
	}
}