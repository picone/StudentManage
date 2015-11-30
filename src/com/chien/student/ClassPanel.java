package com.chien.student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.LoggerFactory;

import com.chien.dao.AcademyDAO;
import com.chien.dao.ClassDAO;
import com.chien.dao.MajorDAO;

public class ClassPanel extends JPanel{
	private static final long serialVersionUID = -4640672445288095256L;

	private JList<String> academy_list,major_list,class_list;
	private ListMenu list_menu;
	
	private Vector<String> academy_data,major_data,class_data;
	private int selected_list=0;
	
	private AcademyDAO db_academy;
	private MajorDAO db_major;
	private ClassDAO db_class;
	
	public ClassPanel(){
		super(new GridLayout(1,3,8,8));
		db_academy=new AcademyDAO();
		db_major=new MajorDAO();
		db_class=new ClassDAO();
		academy_data=new Vector<>();
		major_data=new Vector<>();
		class_data=new Vector<>();
		
		academy_list=new JList<>();
		major_list=new JList<>();
		class_list=new JList<>();
		list_menu=new ListMenu();
		//添加学院,专业,班级的List
		add(academy_list);
		add(major_list);
		add(class_list);
		loadData(1);
		ListClick l=new ListClick();
		academy_list.addMouseListener(l);
		academy_list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO 自动生成的方法存根
				loadData(2);
			}
		});
		major_list.addMouseListener(l);
		major_list.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO 自动生成的方法存根
				loadData(3);
			}
		});
		class_list.addMouseListener(l);
	}
	
	private void loadData(int type){
		try{
			ResultSet cursor;
			switch(type){
			case 1:
				cursor=db_academy.getAll();
				academy_data.clear();
				while(cursor.next()){
					academy_data.add(cursor.getString(2));
				}
				academy_list.setListData(academy_data);
				break;
			case 2:
				cursor=db_major.getAll(academy_list.getSelectedValue());
				major_data.clear();
				while(cursor.next()){
					major_data.add(cursor.getString(1));
				}
				major_list.setListData(major_data);
				break;
			case 3:
				cursor=db_class.getAll(major_list.getSelectedValue());
				class_data.clear();
				while(cursor.next()){
					class_data.add(cursor.getString(1));
				}
				class_list.setListData(class_data);
				break;
			}
		}catch(SQLException e){
			LoggerFactory.getLogger(ClassPanel.class).error(e.toString());
		}
	}
	
	private class ListMenu extends JPopupMenu{
		private static final long serialVersionUID = -8246137194728129855L;

		public ListMenu(){
			super();
			JMenuItem item;
			item=new JMenuItem("添加");
			item.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					String name=JOptionPane.showInputDialog(null,"请输入名字");
					if(name==null)return;
					if(name.equals("")){
						JOptionPane.showMessageDialog(null,"名字不能为空");
					}else{
						try{
							switch(selected_list){
							case 1:
								db_academy.insert(name);
								break;
							case 2:
								if(academy_list.isSelectionEmpty()){
									JOptionPane.showMessageDialog(null,"请选择学院");
								}else{
									db_major.insert(db_academy.getId(academy_list.getSelectedValue()),name);
								}
								break;
							case 3:
								if(major_list.isSelectionEmpty()){
									JOptionPane.showMessageDialog(null,"请选择专业");
								}else{
									db_class.insert(db_major.getId(major_list.getSelectedValue()),name);
								}
								break;
							}
							loadData(selected_list);
						}catch(SQLException ex){
							LoggerFactory.getLogger(ClassPanel.class).error(ex.toString());
						}
					}
				}
			});
			add(item);
			item=new JMenuItem("删除");
			item.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					try{
						switch(selected_list){
						case 1:
							if(!academy_list.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null,"你是否要删除"+academy_list.getSelectedValue()+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
								db_academy.delete(academy_list.getSelectedValue());
							}
							break;
						case 2:
							if(!major_list.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null,"你是否要删除"+major_list.getSelectedValue()+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
								db_major.delete(major_list.getSelectedValue());
							}
							break;
						case 3:
							if(!class_list.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null,"你是否要删除"+class_list.getSelectedValue()+"?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
								db_class.delete(class_list.getSelectedValue());
							}
							break;
						}
						loadData(selected_list);
					}catch(SQLException ex){
						LoggerFactory.getLogger(ClassPanel.class).error(ex.toString());
					}
				}
			});
			add(item);
			item=new JMenuItem("修改");
			item.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					String name,before;
					try{
						switch(selected_list){
						case 1:
							before=academy_list.getSelectedValue();
							if(!before.equals("")){
								name=JOptionPane.showInputDialog(null,"请输入名字",academy_list.getSelectedValue());
								if(name!=null&&!name.equals("")){
									db_academy.update(before,name);
								}
							}
							break;
						case 2:
							before=major_list.getSelectedValue();
							if(!before.equals("")){
								name=JOptionPane.showInputDialog(null,"请输入名字",major_list.getSelectedValue());
								if(name!=null&&!name.equals("")){
									db_major.update(before,name);
								}
							}
							break;
						case 3:
							before=class_list.getSelectedValue();
							if(!before.equals("")){
								name=JOptionPane.showInputDialog(null,"请输入名字",class_list.getSelectedValue());
								if(name!=null&&!name.equals("")){
									db_class.update(before,name);
								}
							}
							break;
						}
						loadData(selected_list);
					}catch(SQLException ex){
						LoggerFactory.getLogger(ClassPanel.class).error(ex.toString());
					}
				}
			});
			add(item);
		}
	}
	
	private class ListClick extends MouseAdapter{
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自动生成的方法存根
			super.mousePressed(e);
			JList<?> list=(JList<?>)e.getSource();
			 if (e.isPopupTrigger()){
				 list_menu.show(e.getComponent(),e.getX(), e.getY());
			 }
			 if(list.equals(academy_list)){
				 selected_list=1;
			 }else if(list.equals(major_list)){
				 selected_list=2;
			 }else{
				 selected_list=3;
			 }
		}
	}
}