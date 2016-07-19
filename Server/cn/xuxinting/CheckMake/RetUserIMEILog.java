package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetUserIMEILog extends Thread{
	String sourceString = null;
	String checkString = null;
	int Num = 0;
	int returnCode = 0;
	String UserID = null;
	String UserName = null;
	
	public RetUserIMEILog(int Num,int returnCode,String UserID,String UserName){
		this.Num = Num;
		this.returnCode = returnCode;
		this.UserID = UserID;
		this.UserName = UserName;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + returnCode + UserID + UserName;
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}
