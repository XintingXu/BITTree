package cn.xuxinting.GetReturnJson;

import cn.xuxinting.CheckMake.RetChangeUserName;
import net.sf.json.JSONObject;

public class ChangeUserNameJsonMake extends Thread{
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String contentCheck = null;
	
	public ChangeUserNameJsonMake(int returnCode){
		this.ReturnCode = returnCode;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetChangeUserName retChangeUserName = new RetChangeUserName(Num, ReturnCode);
		contentCheck = retChangeUserName.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}
