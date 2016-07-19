package cn.xuxinting.CheckMake;

import cn.xuxinting.JsonCheck.String2MD5;

public class RetNewComment extends Thread{
	String sourceString = null;
	String checkString = null;
	int commentID = 0;
	int Num = 0;
	int returnCode = 0;
	
	public RetNewComment(int Num,int returnCode,int commentID){
		this.Num = Num;
		this.returnCode = returnCode;
		this.commentID = commentID;
	}
	
	public String getCheckCode(){
		GetSourceString();
		GetCheckString();
		return checkString;
	}
	
	void GetSourceString(){
		sourceString = String.valueOf(Num) + String.valueOf(returnCode) + String.valueOf(commentID);
	}
	void GetCheckString(){
		String2MD5 string = new String2MD5(sourceString);
		checkString = string.getMD5();
	}
}
