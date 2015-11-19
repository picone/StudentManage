package com.chien.student;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class NumberLimitedDoc extends PlainDocument{
	private static final long serialVersionUID = 8480890596144010565L;
	
	private int limit;
	
	/**
	 * @param int limit 限制的位数
	 */
	public NumberLimitedDoc(int limit){
		super();
		this.limit=limit;
	}
	
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO 自动生成的方法存根
		if(str!=null&&getLength()+str.length()<=limit){
			char[] buffer=str.toCharArray();
			int cpos=0;
			for(char val:buffer){
				if(val>='0'&&val<=9)buffer[cpos++]=val;
			}
			super.insertString(offs, str, a);
		}
	}
}