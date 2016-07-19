package cn.xuxinting.JsonCheck;

import net.sf.json.JSONObject;

public class NewCommentJsonCheck extends Thread{
	JSONObject Source = null;
	String checkString = null;
	String targetString = null;
	public NewCommentJsonCheck(JSONObject json){
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
		String MessageID = Source.getString("MessageID");
		String CommenterID = Source.getString("CommenterID");
		String CommentTarget = Source.getString("CommentTarget");
		String CommentContent = Source.getString("CommentContent");
		checkString = String.valueOf(Num) + MessageID + CommenterID + CommentTarget + CommentContent;
	}
	
	void GetTargetString(){
		targetString = Source.getString("ContentCheck");
	}
}