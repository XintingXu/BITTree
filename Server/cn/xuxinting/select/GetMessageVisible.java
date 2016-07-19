package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.sina.sae.util.SaeUserInfo;


public class GetMessageVisible {
	String MessageID = null;
	int MessageVisible;
	
	
	public GetMessageVisible(String MessageID){
		this.MessageID = MessageID;
	}
	
	void getMessageVisible(){
		String sql = null;
		sql = "select count(messagevisible) as visible from MessageInfo where messageid = '" + MessageID + "';";
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
			MessageVisible = resultSet.getInt("visible");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public boolean CheckVisible(){
		getMessageVisible();
		if(MessageVisible == 1){
			return true;
		}
		else{
			return false;
		}
	}
}