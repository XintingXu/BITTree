package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetGetMessage extends Thread{
	String sourceString = null;
	String checkString = null;
	int Num = 0;
	int ReturnNum = 0;
	
	public RetGetMessage(int Num,int ReturnNum){
		this.Num = Num;
		this.ReturnNum = ReturnNum;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + String.valueOf(ReturnNum);
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}