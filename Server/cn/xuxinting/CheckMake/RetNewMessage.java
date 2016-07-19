package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetNewMessage extends Thread{
	String sourceString = null;
	String checkString = null;
	int Num = 0;
	int returnCode = 0;
	String MessageID = null;
	
	public RetNewMessage(int Num,int returnCode,String MessageID){
		this.Num = Num;
		this.returnCode = returnCode;
		this.MessageID = MessageID;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + returnCode + MessageID;
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}
