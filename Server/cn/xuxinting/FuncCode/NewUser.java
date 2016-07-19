package cn.xuxinting.FuncCode;

import java.sql.SQLException;

import cn.xuxinting.GetReturnJson.NewUserJsonMake;
import cn.xuxinting.insert.UserInsert;
import net.sf.json.JSONObject;

public class NewUser {
	JSONObject source = new JSONObject();
	String UserPhoneIMEI = null;
	String UserName = null;
	String UserPass = null;
	String UserSex = null;
	int UserPhonrBond;
	
	int ReturnCode;
	String UserID = null;
	
	public NewUser(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		UserPhoneIMEI = source.getString("PhoneIMEI");
		UserName = source.getString("UserName");
		UserPass = source.getString("UserPass");
		UserSex = source.getString("UserSex");
		UserPhonrBond = source.getInt("UserPhoneBond");
	}
	
	boolean DBinsert(){
		getInfo();
		UserInsert userInsert = new UserInsert();
		try {
			if(userInsert.SetUserName(UserName)){
				if(userInsert.SetUserPhoneIMEI(UserPhoneIMEI)){
					if(userInsert.SetUserPassword(UserPass) &&  
							userInsert.SetUserSex(UserSex)){
						userInsert.SetUserPhoneBond((short)UserPhonrBond);
						if(userInsert.SqlSet()){
							userInsert.Insert();
							UserID = userInsert.getUserID();
							ReturnCode = 1;
							return true;
						}
						else{
							ReturnCode = 4;
							return false;
						}
					}
					else{
						ReturnCode = 4;
						return false;
					}
				}else{
					ReturnCode = 3;
					return false;
				}
			}else{
				ReturnCode = 2;
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public JSONObject getJSON(){
		DBinsert();
		NewUserJsonMake newUserJsonMake = new NewUserJsonMake(ReturnCode, UserID);
		return newUserJsonMake.getJson();
	}
}