package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.GetMessageJsonMake;
import cn.xuxinting.select.GetMessageInfo;
import cn.xuxinting.select.GetMessageVisible;
import cn.xuxinting.select.MaxMessageIDSelect;
import net.sf.json.JSONObject;

public class GetMessage {
	JSONObject source = null;
	String MaxMessaged = null;
	int ReturnNum;
	Long MaxMessageD; 
	JSONObject returnObject = new JSONObject();
	JSONObject Message = new JSONObject();
	
	public GetMessage(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		MaxMessaged = source.getString("MaxMessaged");
		MaxMessageD = Long.parseLong(MaxMessaged);
	}
	
	void getMessage(){
		getInfo();
		MaxMessageIDSelect maxID= new MaxMessageIDSelect();

			Long MaxNow = Long.parseLong(maxID.getMaxMessageID());
			Long tempLong = MaxNow - MaxMessageD;
			ReturnNum = tempLong.intValue();
			if(ReturnNum >= 10){
				ReturnNum = 10;
			}else{
				;
			}
			
		if(ReturnNum == 0){
			;
		}
		else{
			int Decount = 0;
			int Bit = 0;
			MaxMessageIDSelect maxMessageIDSelect = new MaxMessageIDSelect();
			Long maxLong;
			maxLong = Long.parseLong(maxMessageIDSelect.getMaxMessageID());
			if(maxLong > MaxMessageD){
				for(int i = 0;i <= ReturnNum;i ++){
					if(MaxMessageD + i > maxLong){
						break;
					}
					GetMessageVisible getVisible = new GetMessageVisible(String.valueOf(MaxMessageD + i));
					if(getVisible.CheckVisible()){
						GetMessageInfo getMessageInfo = new GetMessageInfo(String.valueOf(MaxMessageD + i));
						JSONObject tempJsonObject = getMessageInfo.getMessage();
						Message.put("Message" + String.valueOf(Bit), tempJsonObject);
						Bit ++;
					}
					else{
						Decount ++;
					}
				}
				ReturnNum = ReturnNum - Decount;
			}else{
				
			}
		}
	}
	
	public JSONObject getJSON(){
		getMessage();
		GetMessageJsonMake getMessage = new GetMessageJsonMake(ReturnNum, Message);
		return getMessage.getJson();
	}
}