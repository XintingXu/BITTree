package cn.xuxinting.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 * 功能：求一个字符串的MD5信息
 * 用法：
 * new md5(String);
 * String encode = md5.Encode();
 * */
public class MD5 extends Thread{
	String Source = null;
	public MD5(String in){
		this.Source = in;
	}
	public String Encode(){
        MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(Source.getBytes());
	        // 获得密文
	        byte[] md = mdInst.digest();
	        // 把密文转换成十六进制的字符串形式
	        StringBuffer hexString = new StringBuffer();
	        // 字节数组转换为 十六进制 数
	        for (int i = 0; i < md.length; i++) {
	            String shaHex = Integer.toHexString(md[i] & 0xFF);
	            if (shaHex.length() < 2) {
	                hexString.append(0);
	            }
	            hexString.append(shaHex);
	        }
	        return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
            return "";
		}
    }
}