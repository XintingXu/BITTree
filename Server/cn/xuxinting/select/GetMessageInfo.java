package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sina.sae.util.SaeUserInfo;

import net.sf.json.JSONObject;

public class GetMessageInfo {
	String MessageID = null;
	String MessageSenderID = null;
	String MessageSenderName = null;
	String MessageSenderSex = null;
	String MessageContent = null;
	int MessageApproveNum;
	int MessageCommentNum;
	
	JSONObject object = new JSONObject();
	
	public GetMessageInfo(String MessageID){
		this.MessageID = MessageID;
	}
	
	void getMessageDB(){
		String sql = null;
		Connection con = null;
		sql = "select messagesenderid,messagecontent,messageapprove,messagecommentnumber"
				+ " from MessageInfo where messageid = '" + MessageID + "';";
		
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
			MessageSenderID = rs.getString("messagesenderid");
			MessageContent = rs.getString("messagecontent");
			MessageApproveNum = rs.getInt("messageapprove");
			MessageCommentNum = rs.getInt("messagecommentnumber");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	void getSenderDB(){
		String sql = null;
		Connection con = null;
		sql = "select username,usersex from UserInfo where userid = '" + MessageSenderID + "';";
		
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
			MessageSenderName = rs.getString("username");
			MessageSenderSex = rs.getString("usersex");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public JSONObject getMessage(){
		getMessageDB();
		if(MessageSenderID.length() == 10){
			getSenderDB();
		}
		object.put("MessageID", MessageID);
		object.put("MessageSenderID", MessageSenderID);
		object.put("MessageSenderName", MessageSenderName);
		object.put("MessageSenderSex", MessageSenderSex);
		object.put("MessageContent", MessageContent);
		object.put("MessageApproveNum", MessageApproveNum);
		object.put("MessageCommentNum", MessageCommentNum);
		return object;
	}
}