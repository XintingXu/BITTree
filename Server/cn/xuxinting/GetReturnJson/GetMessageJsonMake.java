package cn.xuxinting.GetReturnJson;

import cn.xuxinting.CheckMake.RetGetMessage;
import net.sf.json.JSONObject;

public class GetMessageJsonMake {
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnNum;
	JSONObject Message = null;
	String ContentCheck = null;
	public GetMessageJsonMake(int ReturnNum,JSONObject Message){
		this.ReturnNum = ReturnNum;
		this.Message = Message;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetGetMessage retGetMessage = new RetGetMessage(Num, ReturnNum);
		ContentCheck = retGetMessage.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnNum", ReturnNum);
		object.put("Message", Message);
		object.put("ContentCheck", ContentCheck);
		return object;
	}
}