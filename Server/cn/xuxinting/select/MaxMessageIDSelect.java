package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sina.sae.util.SaeUserInfo;

public class MaxMessageIDSelect {
	String sql = null;
	
	public String getMaxMessageID() {
		sql = "select max(messageid) as max from MessageInfo where messageid like '5%';";
		String result = null;
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
			result = resultSet.getString("max");
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
