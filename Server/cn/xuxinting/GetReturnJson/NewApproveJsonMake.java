package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetNewApprove;

public class NewApproveJsonMake {
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	String contentCheck = null;
	
	public NewApproveJsonMake(int returnCode){
		this.ReturnCode = returnCode;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetNewApprove retNewComment = new RetNewApprove(Num, ReturnCode);
		contentCheck = retNewComment.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}