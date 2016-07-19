package cn.xuxinting.JsonCheck;

import net.sf.json.JSONObject;

public class NewUserJsonCheck extends Thread{
	JSONObject Source = null;
	String checkString = null;
	String targetString = null;
	public NewUserJsonCheck(JSONObject json){
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
		String PhoneIMEI = Source.getString("PhoneIMEI");
		String UserName = Source.getString("UserName");
		String UserPass = Source.getString("UserPass");
		String UserSex = Source.getString("UserSex");
		int UserPhoneBon = Source.getInt("UserPhoneBond");
		checkString = String.valueOf(Num) + PhoneIMEI + UserName + UserPass + UserSex + String.valueOf(UserPhoneBon);
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
