package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.sina.sae.util.SaeUserInfo;

import net.sf.json.JSONObject;

public class GetSingleComment {
	String MessageID = null;;
	JSONObject Comment = new JSONObject();
	String CommentID = null;
	String CommenterName = null;
	String CommenterID = null;
	int CommentTarget;
	String CommentContent = null;
	String CommentDate = null;
	
	int ReturnNum;
	
	public GetSingleComment(String CommentID,String MessageID){
		this.CommentID = CommentID;
		this.MessageID = MessageID;
	}
	
	void getMessageDB(){
		String sql = null;
		Connection con = null;
		sql = "select commentid,commentposter,commenttarget,commentdatetime,commentcontent"
				+ " from comment_" + MessageID +" where commentid = '" + CommentID + "';";
		
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
			CommentID = resultSet.getString("commentid");
			CommenterID = resultSet.getString("commentposter");
			CommentTarget = resultSet.getInt("commenttarget");
			CommentDate = resultSet.getString("commentdatetime");
			CommentContent = resultSet.getString("commentcontent");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	void getSenderDB(){
		getMessageDB();
		String sql = null;
		
		String URL="jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_bitbitree";
		String Username=SaeUserInfo.getAccessKey();
		String Password=SaeUserInfo.getSecretKey();
		String Driver="com.mysql.jdbc.Driver";
		try{
			Class.forName(Driver).newInstance();
			Connection con=DriverManager.getConnection(URL,Username,Password);	
			

			sql = "select username from UserInfo where userid = '" + CommenterID + "';";
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet resultSet = (ResultSet) pstmt.executeQuery();
			resultSet.next();
			CommenterName = resultSet.getString("username");
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public JSONObject getJson(){
		getSenderDB();
		Comment.put("CommentID", CommentID);
		Comment.put("CommenterName", CommenterName);
		Comment.put("CommenterID", CommenterID);
		Comment.put("CommentTarget", CommentTarget);
		Comment.put("CommentContent", CommentContent);
		Comment.put("CommentDate", CommentDate);
		return Comment;
	}
}