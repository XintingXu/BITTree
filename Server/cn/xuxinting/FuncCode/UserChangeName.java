package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.ChangeUserNameJsonMake;
import cn.xuxinting.Update.UpdateUserName;
import cn.xuxinting.select.UserNameSelect;
import net.sf.json.JSONObject;

public class UserChangeName {
	JSONObject source = null;
	String UserID = null;
	String UserTargetName = null;
	int ReturnCode;
	
	public UserChangeName(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		UserID = source.getString("UserID");
		UserTargetName = source.getString("UserTargetName");
	}
	
	void nameCheck(){
		UserNameSelect userNameSelect = new UserNameSelect();
		userNameSelect.UserNameSet(UserTargetName);

			if(!userNameSelect.UserNameCheck()){
				ReturnCode = 3;
			}
			else{
				ReturnCode = 1;
			}

	}
	
	void Insert(){
		UpdateUserName updateUserName = new UpdateUserName(UserID, UserTargetName);
		if(updateUserName.Update()){
			ReturnCode = 1;
		}
		else{
			ReturnCode = 2;
		}
	}
	
	public JSONObject getJSON(){
		getInfo();
		nameCheck();
		if(ReturnCode == 1){
			Insert();
		}
		ChangeUserNameJsonMake chage = new ChangeUserNameJsonMake(ReturnCode);
		return chage.getJson();
	}
}