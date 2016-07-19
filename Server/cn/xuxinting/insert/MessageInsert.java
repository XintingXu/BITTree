package cn.xuxinting.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sina.sae.util.SaeUserInfo;

import cn.xuxinting.select.MaxMessageIDSelect;

public class MessageInsert {
	String MessageSenderID = null;
	String MessageContent = null;
	String MessageID = null;
	int MessageVisible = 1;
	String MessagePostTime = null;
	int MessageApprove = 0;
	int MessageCommentNumber = 0;
	
	public MessageInsert (String SenderID,String Content) {
		this.MessageSenderID = SenderID;
		this.MessageContent = Content;
		setMessageID();
		SetPostTime();
	}
	
	public MessageInsert() {
		// TODO Auto-generated constructor stub
		setMessageID();
		SetPostTime();
	}
	
	public boolean SetMesageSenderID(String SenderID) {
		if(SenderID.length() == 10){
			this.MessageSenderID = SenderID;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean SetMessageContent(String Content) {
		if(Content.length() <= 255 && Content.length() > 0){
			this.MessageContent = Content;
			return true;
		}
		else{
			return false;
		}
	}
	
	boolean setMessageID(){
		MaxMessageIDSelect maxMessageIDSelect = new MaxMessageIDSelect();
		MessageID = maxMessageIDSelect.getMaxMessageID();
		Long id = Long.parseLong(MessageID);
		id = id + 1;
		MessageID = id.toString();
		if(MessageID.length() == 10){
			return true;
		}
		else{
			return false;
		}
	}
	
	void SetPostTime(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MessagePostTime = format.format(new Date());
	}
	
	public boolean doMessageInsert() {
		setMessageID();
		SetPostTime();
		String sql = null;
		Connection con = null;
		sql = "insert into MessageInfo(messageid,messagevisible,messagepostdatetime,"
				+ "messagesenderid,messagecontent,messageapprove,messagecommentnumber)"
				+ " values('" + MessageID + "','" + String.valueOf(MessageVisible) + "','" + MessagePostTime
				+ "','" + MessageSenderID + "','" + MessageContent + "','" + String.valueOf(MessageApprove) + "','"
				+ String.valueOf(MessageCommentNumber) + "');";
		
		String URL="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			con=DriverManager.getConnection(URL,Username,Password);	
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.execute();
			con.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public String getMessageID(){
		return MessageID;
	}
}
