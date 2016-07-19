package cn.xuxinting.GetReturnJson;

import net.sf.json.JSONObject;
import cn.xuxinting.CheckMake.RetNewComment;

public class RetNewCommentJsonMake extends Thread{
	JSONObject object = new JSONObject();
	
	int Num;
	int ReturnCode;
	int CommentID;
	String contentCheck = null;
	
	public RetNewCommentJsonMake(int returnCode,int commentID){
		this.CommentID = commentID;
		this.ReturnCode = returnCode;
	}
	
	public JSONObject getJson(){
		MakeNum makeNum = new MakeNum();
		Num = makeNum.getNum();
		RetNewComment retNewComment = new RetNewComment(Num, ReturnCode,CommentID);
		contentCheck = retNewComment.getCheckCode();
		object.put("Num", Num);
		object.put("ReturnCode", ReturnCode);
		object.put("ContentCheck", contentCheck);
		return object;
	}
}
