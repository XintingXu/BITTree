package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetNewUserReg extends Thread{
	String sourceString = null;
	String checkString = null;
	int Num = 0;
	int returnCode = 0;
	String UserID = null;
	
	public RetNewUserReg(int Num,int returnCode,String UserID){
		this.Num = Num;
		this.returnCode = returnCode;
		this.UserID = UserID;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + returnCode + UserID;
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}
