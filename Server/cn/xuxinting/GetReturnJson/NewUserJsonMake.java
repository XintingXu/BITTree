package cn.xuxinting.GetReturnJson;

import cn.xuxinting.CheckMake.RetNewUserReg;
import net.sf.json.JSONObject;

public class NewUserJsonMake extends Thread{
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String UserID = null;
	String contentCheck = null;
	
	public NewUserJsonMake(int returnCode,String userID){
		this.ReturnCode = returnCode;
		this.UserID = userID;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetNewUserReg retNewUserReg = new RetNewUserReg(Num, ReturnCode, UserID);
		contentCheck = retNewUserReg.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("UserID", UserID);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}