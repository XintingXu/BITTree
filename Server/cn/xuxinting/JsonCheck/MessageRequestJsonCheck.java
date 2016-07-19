package cn.xuxinting.JsonCheck;

import net.sf.json.JSONObject;

public class MessageRequestJsonCheck extends Thread{
	JSONObject Source = null;
	String checkString = null;
	String targetString = null;
	
	public MessageRequestJsonCheck(JSONObject json){
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
		String MaxMessaged = Source.getString("MaxMessaged");
		checkString = String.valueOf(Num) + UserID + MaxMessaged;
	}
	
	void GetTargetString(){
		targetString = Source.getString("ContentCheck");
	}
}