package cn.xuxinting.JsonCheck;

import net.sf.json.JSONObject;

public class UserLogJsonCheck extends Thread{
	JSONObject Source = null;
	String checkString = null;
	String targetString = null;
	public UserLogJsonCheck(JSONObject json){
		this.Source = json;
	}
	
	public boolean Check(){
		GetCheckString();
		GetTargetString();
		String2MD5 md5 = new String2MD5(checkString);
		if(targetString.equals(md5.getMD5())){
			return true;
		}
		else{
			return false;
		}
	}
	
	void GetCheckString(){
		int Num = Source.getInt("Num");
		String UserPhoneIMEI = Source.getString("UserPhoneIMEI");
		checkString = String.valueOf(Num) + UserPhoneIMEI;
	}
	
	void GetTargetString(){
		targetString = Source.getString("ContentCheck");
	}
	
	public String getCheckCode(){
		GetCheckString();
		GetTargetString();
		String2MD5 md5 = new String2MD5(checkString);
		return md5.getMD5();
	}
}