package com.chien.student;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer{
	private static final long serialVersionUID = 9093994985998191568L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO 自动生成的方法存根
		if(value instanceof Boolean){
			setSelected((boolean)value);
			return this;
		}else{
			return null;
		}
	}

}