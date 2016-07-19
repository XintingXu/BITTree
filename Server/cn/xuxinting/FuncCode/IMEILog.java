package cn.xuxinting.FuncCode;

import cn.xuxinting.GetReturnJson.IMEILogJsonMake;
import cn.xuxinting.select.GetUserInfobyIMEI;
import net.sf.json.JSONObject;

public class IMEILog {
	JSONObject source = null;
	String UserPhoneIMEI = null;
	String UserID = null;
	String UserName = null;
	JSONObject ret = new JSONObject();
	int ReturnCode;
	
	public IMEILog(JSONObject json){
		this.source = json;
	}
	
	void getInfo(){
		this.UserPhoneIMEI = source.getString("UserPhoneIMEI");
	}
	
	public JSONObject getJSON(){
		getInfo();
		GetUserInfobyIMEI getUserInfobyIMEI = new GetUserInfobyIMEI(UserPhoneIMEI);
		ret = getUserInfobyIMEI.GetUserInfo();
		IMEILogJsonMake imeiLogJsonMake = new IMEILogJsonMake(ret.getInt("ReturnCode"), ret.getString("UserID"), ret.getString("UserName"));
		return imeiLogJsonMake.getJson();
	}
}