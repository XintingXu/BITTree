package cn.xuxinting.JsonCheck;

import net.sf.json.JSONObject;

public class UserNameChangeJsonCheck extends Thread{
	JSONObject Source = null;
	String checkString = null;
	String targetString = null;
	public UserNameChangeJsonCheck(JSONObject json){
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
		String UserID = Source.getString("UserID");
		String UserTargetName = Source.getString("UserTargetName");
		checkString = String.valueOf(Num) + UserID + UserTargetName;
	}
	
	void GetTargetString(){
		targetString = Source.getString("ContentCheck");
	}
}