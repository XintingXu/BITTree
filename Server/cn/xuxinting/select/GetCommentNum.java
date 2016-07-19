package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sina.sae.util.SaeUserInfo;


public class GetCommentNum {
	String MessageID = null;
	int ReturnNum;
	
	public GetCommentNum(String MessageID){
		this.MessageID = MessageID;
	}
	
	public int getNum(){
		DBget();
		return ReturnNum;
	}
	
	void DBget(){
		String sql = null;
		Connection con = null;
		
		sql = "select count(*) as count from comment_" + MessageID + ";";
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			con=DriverManager.getConnection(URL,Username,Password);	
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			rs.next();
			ReturnNum = rs.getInt("count");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ReturnNum = 0;
		}
	}
}