package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

public class MaxCommentIDSelect {
	/*
	 * 默认MessageID已经经过验证
	 * */
	String MessageID = null;
	String CommentID = null;
	
	public MaxCommentIDSelect() {
		// TODO Auto-generated constructor stub
	}
	public MaxCommentIDSelect(String MessageID){
		this.MessageID = MessageID;
	}
	public void SetMessageID(String MessageID) {
		this.MessageID = MessageID;
	}
	
	public String getMaxCommentID(){
		CommentID = String.valueOf(0);
		try {
			DBget();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			CommentID = String.valueOf(0);
		}
		return CommentID;
	}
	
	void DBget() throws SQLException{
		String sql = null;
		Connection con = null;
		
		sql = "select max(commentid) as num from comment_" + MessageID + ";";
		int ID = 0;
		
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			con=DriverManager.getConnection(URL,Username,Password);	
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet resultSet = (ResultSet) pstmt.executeQuery();
			
			resultSet.next();
			String result = resultSet.getString("num");
			ID = Integer.parseInt(result);
			if(ID == 0){
				ID = 0;
				CommentID = String.valueOf(ID);
			}
			else{
				CommentID = String.valueOf(ID);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			CommentID = String.valueOf(0);
		}
		con.close();
	}
}
