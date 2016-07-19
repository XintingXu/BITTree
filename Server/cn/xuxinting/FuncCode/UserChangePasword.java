package cn.xuxinting.FuncCode;

import net.sf.json.JSONObject;
import cn.xuxinting.GetReturnJson.ChangeUserPassJsonMake;
import cn.xuxinting.Update.UpdateUserPassword;

public class UserChangePasword {
	JSONObject source = null;
	String UserID = null;
	String UserTargetPassword = null;
	int ReturnCode;
	
	public UserChangePasword(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		UserID = source.getString("UserID");
		UserTargetPassword = source.getString("UserTargetPassword");
	}
	
	void Insert(){
		UpdateUserPassword updateUserPassword = new UpdateUserPassword(UserID, UserTargetPassword);
		if(updateUserPassword.Update()){
			ReturnCode = 1;
		}
		else{
			ReturnCode = 2;
		}
	}
	
	public JSONObject getJSON(){
		getInfo();
		Insert();
		ChangeUserPassJsonMake chage = new ChangeUserPassJsonMake(ReturnCode);
		return chage.getJson();
	}
}