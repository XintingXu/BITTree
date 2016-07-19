package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetNewUserReg;

public class NewMessageJsonMake extends Thread{
JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String MessageID = null;
	String contentCheck = null;
	
	public NewMessageJsonMake(int returnCode,String messageID){
		this.ReturnCode = returnCode;
		this.MessageID = messageID;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetNewUserReg retNewUserReg = new RetNewUserReg(Num, ReturnCode, MessageID);
		contentCheck = retNewUserReg.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("MessageID", MessageID);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}