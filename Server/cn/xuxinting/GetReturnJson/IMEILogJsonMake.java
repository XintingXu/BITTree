package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetUserIMEILog;

public class IMEILogJsonMake extends Thread{
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String UserID = null;
	String UserName = null;
	String contentCheck = null;
	
	public IMEILogJsonMake(int returnCode,String UserID,String UserName){
		this.ReturnCode = returnCode;
		this.UserID = UserID;
		this.UserName = UserName;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetUserIMEILog retUserIMEILog = new RetUserIMEILog(Num, ReturnCode, UserID, UserName);
		contentCheck = retUserIMEILog.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("UserID", UserID);
		object.put("UserName", UserName);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}