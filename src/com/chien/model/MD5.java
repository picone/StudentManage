package com.chien.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	/**
	 * @param str 要计算的字符串
	 * @return MD5计算结果
	 * @throws NoSuchAlgorithmException
	 */
	public String md5(String str) throws NoSuchAlgorithmException{
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		byte[] btInput=str.getBytes();
		MessageDigest mdInst=MessageDigest.getInstance("MD5");
		mdInst.update(btInput);
		byte[] md=mdInst.digest();
		int j=md.length;
        char s[]=new char[j*2];
        int k = 0;
        for(int i = 0;i< j;i++){
            byte byte0 =md[i];
            s[k++]=hexDigits[byte0>>>4&0xf];
            s[k++]=hexDigits[byte0&0xf];
        }
        return new String(s);
	}
}