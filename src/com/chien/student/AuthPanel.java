package com.chien.student;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.slf4j.LoggerFactory;

import com.chien.dao.UserDAO;

public class AuthPanel extends JPanel{
	private static final long serialVersionUID = -8841874939566067724L;

	private JTable table;
	private JButton create,delete,save;
	private DefaultTableModel dtm;
	private TableColumnModel tcm;
	
	private UserDAO db_user;
	
	public AuthPanel(){
		super(new BorderLayout());
		db_user=new UserDAO();
		table=new JTable();
		create=new JButton("添加");
		delete=new JButton("删除");
		save=new JButton("保存");
		//添加表格
		JScrollPane sp=new JScrollPane();
		sp.setViewportView(table);
		add(sp,BorderLayout.WEST);
		//添加右侧创建,删除,保存按钮
		JPanel p=new JPanel(new VFlowLayout());
		p.add(create);
		p.add(delete);
		p.add(save);
		add(p,BorderLayout.EAST);
		
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				dtm.addRow(new Object[]{"","",false,false,false,false});
				table.changeSelection(table.getRowCount()-1, 0, false, false);
			}
		});
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int row=table.getSelectedRow();
				if(row>=0&&JOptionPane.showConfirmDialog(null,"你确定要删除用户"+table.getValueAt(row,1)+"吗?","警告",JOptionPane.YES_NO_OPTION)==JOptionPane.OK_OPTION){
					try{
						db_user.delete((String)table.getValueAt(row,0));
						dtm.removeRow(table.getSelectedRow());
						JOptionPane.showMessageDialog(null,"删除成功");
					}catch(SQLException ex){
						LoggerFactory.getLogger(AuthPanel.class).error(e.toString());
						JOptionPane.showMessageDialog(null,"删除失败");
					}
				}
			}
		});
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int auth=0,row=table.getSelectedRow();
				String id=(String)table.getValueAt(row,0),name=(String)table.getValueAt(row,1);
				//计算权限值
				if((boolean)table.getValueAt(row,2))auth+=1;
				if((boolean)table.getValueAt(row,3))auth+=2;
				if((boolean)table.getValueAt(row,4))auth+=4;
				if((boolean)table.getValueAt(row,5))auth+=8;
				if(table.getValueAt(row,6)!=null&&(boolean)table.getValueAt(row,6))auth+=16;
				try{
					if(name.equals("")){
						JOptionPane.showMessageDialog(null,"请输入名字");
					}else{
						if(id.equals("")){
							String password=JOptionPane.showInputDialog(null,"请输入密码");
							db_user.insert((String)table.getValueAt(row,1),password,auth);
						}else{
							db_user.update(id,(String)table.getValueAt(row,1),auth);
						}
						JOptionPane.showMessageDialog(null,"保存成功");
					}
				}catch(SQLException | NoSuchAlgorithmException ex){
					LoggerFactory.getLogger(AuthPanel.class).error(e.toString());
				}
			}
		});
		//加载数据
		loadData();
		//设置2到6列是Checkbox类型的Editor和Renderer
		for(int i=2;i<=6;i++){
			tcm.getColumn(i).setCellEditor(new DefaultCellEditor(new JCheckBox()));
			tcm.getColumn(i).setCellRenderer(new CheckBoxRenderer());
		}
		tcm.getColumn(0).setMaxWidth(18);
	}
	
	private void loadData(){
		try{
			ResultSet cursor=db_user.getAll();
			dtm=new DefaultTableModel(new String[]{"ID","用户名","成绩管理","学生管理","班级管理","课程管理","权限管理"},0);
			while(cursor.next()){
				Object temp[]=new Object[7];
				int auth=cursor.getInt(4); 
				temp[0]=cursor.getString(1);
				temp[1]=cursor.getString(2);
				temp[2]=(auth&1)==1;
				temp[3]=(auth&2)==2;
				temp[4]=(auth&4)==4;
				temp[5]=(auth&8)==8;
				temp[6]=(auth&16)==16;
				dtm.addRow(temp);
			}
			table.setModel(dtm);
			tcm=table.getColumnModel();
		}catch(SQLException e){
			LoggerFactory.getLogger(AuthPanel.class).error(e.toString());
		}
	}
}