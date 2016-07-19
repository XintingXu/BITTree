package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetUserChangePass extends Thread{
	String sourceString = null;
	String checkString = null;
	int Num = 0;
	int returnCode = 0;
	
	public RetUserChangePass(int Num,int returnCode){
		this.Num = Num;
		this.returnCode = returnCode;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + returnCode;
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}
