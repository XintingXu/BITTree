package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.NewApproveJsonMake;
import cn.xuxinting.Update.UpdateMessageApprove;
import net.sf.json.JSONObject;

public class MessageApprove {
	JSONObject source = null;
	String MessageID = null;
	int ReturnCode;
	
	public MessageApprove(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		this.MessageID = source.getString("MessageID");
	}
	
	void Dbdo(){
		getInfo();
		UpdateMessageApprove upa = new UpdateMessageApprove(MessageID);
		if(upa.Update()){
			ReturnCode = 1;
		}else{
			ReturnCode = 2;
		}
	}
	
	public JSONObject getJSON(){
		Dbdo();
		NewApproveJsonMake newApproveJsonMake = new NewApproveJsonMake(ReturnCode);
		return newApproveJsonMake.getJson();
	}
}