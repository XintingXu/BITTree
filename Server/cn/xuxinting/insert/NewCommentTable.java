package cn.xuxinting.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.sina.sae.util.SaeUserInfo;


public class NewCommentTable {
	String MessageID = null;
	String sql = null;
	
	public NewCommentTable(String MessageID){
		this.MessageID = MessageID;
	}
	
	void makeSQL(){
		sql = "create table comment_" + MessageID + "(commentid int,"
				+ "commentvisible tinyint,commentposter char(10),"
				+ "commenttarget int,commentdatetime datetime,"
				+ "commentcontent varchar(255)) DEFAULT CHARSET=utf8;";
	}
	
	public boolean createTable(){
		makeSQL();
		String URL="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			Connection con=DriverManager.getConnection(URL,Username,Password);	
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.execute();
			con.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}