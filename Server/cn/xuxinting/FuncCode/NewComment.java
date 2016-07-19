package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.RetNewCommentJsonMake;
import cn.xuxinting.Update.UpdateMessageCommentMumber;
import cn.xuxinting.insert.CommentInsert;
import net.sf.json.JSONObject;

public class NewComment {
	JSONObject source = null;
	JSONObject ret =  new JSONObject();
	String MessageID = null;
	int CommentID;
	int CommentTarget;
	String CommenterID = null;
	String CommentContent = null;
	
	public NewComment(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		MessageID = source.getString("MessageID");
		CommenterID = source.getString("CommenterID");
		CommentTarget = source.getInt("CommentTarget");
		CommentContent = source.getString("CommentContent");
	}
	
	void Insert(){
		getInfo();
		CommentInsert commentInsert = new CommentInsert(MessageID, CommenterID, CommentTarget, CommentContent);
		ret = commentInsert.Insert();
		if(ret.getInt("ReturnCode") == 1){
			UpdateMessageCommentMumber upmcn = new UpdateMessageCommentMumber(MessageID);
			upmcn.Update();
		}
	}
	
	public JSONObject getJSON(){
		Insert();
		RetNewCommentJsonMake retNewCommentJsonMake = new RetNewCommentJsonMake(ret.getInt("ReturnCode"), ret.getInt("CommentID"));
		return retNewCommentJsonMake.getJson();
	} 
}