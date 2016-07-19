package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.GetCommentJsonMake;
import cn.xuxinting.select.GetCommentNum;
import cn.xuxinting.select.GetSingleComment;
import net.sf.json.JSONObject;

public class GetComment {
	JSONObject source = null;
	int ReturnNum;
	String MessageID = null;
	JSONObject Comment = new JSONObject();
	
	public GetComment(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		this.MessageID = source.getString("MessageID");
	}
	
	void getNum(){
		getInfo();
		GetCommentNum getCommentNum = new GetCommentNum(MessageID);
		ReturnNum = getCommentNum.getNum();
	}
	
	void getComment(){
		getNum();
		int n = 1;
		for(n = 1 ; n <= ReturnNum ; n ++){
			GetSingleComment getSingleComment = new GetSingleComment(String.valueOf(n), MessageID);
			Comment.put("Comment" + String.valueOf(n - 1),getSingleComment.getJson());
		}
	}
	
	public JSONObject getJSON(){
		getComment();
		GetCommentJsonMake getCommentJsonMake = new GetCommentJsonMake(ReturnNum, Comment);
		return getCommentJsonMake.getJson();
	}
}