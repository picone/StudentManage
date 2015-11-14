package com.chien.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainMenuBar extends JMenuBar{
	private static final long serialVersionUID = -5591067543500678011L;

	public MainMenuBar(){
		super();
		JMenu menu=new JMenu("文件(F)");
		menu.setMnemonic('F');
		JMenuItem item=new JMenuItem("注销");
		menu.add(item);
		item=new JMenuItem("退出(E)",'E');
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
		item.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);
			}
		});
		menu.add(item);
		add(menu);
		menu=new JMenu("关于(A)");
		menu.addMenuListener(new MenuListener(){
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO 自动生成的方法存根
				JOptionPane.showMessageDialog(null,"Code BY 小B.");
			}
			@Override
			public void menuDeselected(MenuEvent e){}
			@Override
			public void menuCanceled(MenuEvent e){}
		});
		add(menu);
	}
}