package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sina.sae.util.SaeUserInfo;

public class GetUserIDbyIMEI {
	String UserID = null;
	String UserPhoneIMEI = null;
	String sql = null;
	Connection con = null;
	Statement st = null;
	
	public GetUserIDbyIMEI(String PhoneIMEI){
		this.UserPhoneIMEI = PhoneIMEI;
	}
	
	public String GetUserID(){
		sql = "select userid from UserInfo where userphoneimei = '" + UserPhoneIMEI + "';";
		
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			Connection con=DriverManager.getConnection(URL,Username,Password);	
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet resultSet = (ResultSet) pstmt.executeQuery();
			resultSet.next();
			UserID = resultSet.getString("userid");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			UserID = "";
		}
		return UserID;
	}
}