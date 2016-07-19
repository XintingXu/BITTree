package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetGetMessage;

public class GetCommentJsonMake {
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnNum;
	JSONObject Comment = null;
	String ContentCheck = null;
	public GetCommentJsonMake(int ReturnNum,JSONObject Comment){
		this.ReturnNum = ReturnNum;
		this.Comment = Comment;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetGetMessage retGetMessage = new RetGetMessage(Num, ReturnNum);
		ContentCheck = retGetMessage.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnNum", ReturnNum);
		object.put("Comment", Comment);
		object.put("ContentCheck", ContentCheck);
		return object;
	}
}