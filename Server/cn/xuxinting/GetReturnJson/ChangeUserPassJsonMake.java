package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetUserChangePass;;

public class ChangeUserPassJsonMake extends Thread{
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String contentCheck = null;
	
	public ChangeUserPassJsonMake(int returnCode){
		this.ReturnCode = returnCode;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetUserChangePass retUserChangePass = new RetUserChangePass(Num, ReturnCode);
		contentCheck = retUserChangePass.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}