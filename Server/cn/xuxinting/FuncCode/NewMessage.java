package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.NewMessageJsonMake;
import cn.xuxinting.insert.MessageInsert;
import cn.xuxinting.insert.NewCommentTable;
import net.sf.json.JSONObject;

public class NewMessage {
	JSONObject object = null;
	String SenderID = null;
	String MessageContent = null;
	String MessageID = null;
	int ReturnCode;
	
	public NewMessage(JSONObject obj){
		this.object = obj;
	}
	
	void getInfo(){
		SenderID = object.getString("SenderID");
		MessageContent = object.getString("Message");
	}
	
	boolean insert(){
		getInfo();
		MessageInsert messageInsert = new MessageInsert(SenderID,MessageContent);
		if(messageInsert.doMessageInsert()){
			MessageID = messageInsert.getMessageID();
			NewCommentTable newCommentTable = new NewCommentTable(MessageID);
			if(newCommentTable.createTable()){
				ReturnCode = 1;
				return true;
			}else{
				ReturnCode = 2;
				return false;
			}
		}else {
			ReturnCode = 3;
			return false;
		}
	}
	
	public JSONObject getJSON(){
		insert();
		NewMessageJsonMake newMessageJsonMake = new NewMessageJsonMake(ReturnCode,MessageID);
		JSONObject ret = newMessageJsonMake.getJson();
		return ret;
	}
}