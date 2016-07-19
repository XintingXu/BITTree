package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sina.sae.util.SaeUserInfo;

import net.sf.json.JSONObject;

public class GetUserInfobyIMEI {
	String UserID = null;
	String UserName = null;
	String UserPhoneIMEI = null;
	String sql = null;
	Connection con = null;
	Statement st = null;
	
	public GetUserInfobyIMEI(String PhoneIMEI){
		this.UserPhoneIMEI = PhoneIMEI;
	}
	
	public JSONObject GetUserInfo(){
		int ReturnCode;
		JSONObject ret = new JSONObject();
		
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			Connection con=DriverManager.getConnection(URL,Username,Password);
			sql = "select count(*) as count from UserInfo where userphoneimei = '" + UserPhoneIMEI + "';";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet resultSet = (ResultSet) pstmt.executeQuery();
			
			resultSet.next();
			ReturnCode = resultSet.getInt("count");
			if(ReturnCode == 1){
				try{
					Class.forName(Driver).newInstance();
					Connection conn=DriverManager.getConnection(URL,Username,Password);	
					sql = "select userid,username from UserInfo where userphoneimei = '" + UserPhoneIMEI + "';";
					PreparedStatement pstmtt = conn.prepareStatement(sql);
					ResultSet resultSett = (ResultSet) pstmtt.executeQuery();
					resultSett.next();
					UserID = resultSett.getString("userid");
					UserName = resultSett.getString("username");
					conn.close();
				}catch(Exception e){
					
				}
			}
			else{
				ReturnCode = 2;
			}
			con.close();
		} catch (Exception e){
			// TODO Auto-generated catch block
			ReturnCode = 2;
		}
		ret.put("ReturnCode", ReturnCode);
		ret.put("UserID", UserID);
		ret.put("UserName", UserName);
		return ret;
	}
}
