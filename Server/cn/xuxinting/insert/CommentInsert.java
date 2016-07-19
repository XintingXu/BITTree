package cn.xuxinting.insert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sina.sae.util.SaeUserInfo;

import net.sf.json.JSONObject;
import cn.xuxinting.select.MaxCommentIDSelect;

public class CommentInsert {
	String MessageID = null;
	int CommentID;
	String CommenterID = null;
	int CommentTarget;
	String CommentContent = null;
	
	public CommentInsert(String MessageID,String CommenterID,int CommentTarget,String CommentContent){
		this.MessageID = MessageID;
		this.CommenterID = CommenterID;
		this.CommentTarget = CommentTarget;
		this.CommentContent = CommentContent;
		
		MaxCommentIDSelect maxCommentIDSelect = new MaxCommentIDSelect(this.MessageID);
		CommentID = Integer.parseInt(maxCommentIDSelect.getMaxCommentID());
		CommentID = CommentID + 1;
	}
	
	boolean DBinsert(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String PostTime = format.format(new Date());
		String sql = "insert into comment_" + MessageID + "(commentid,commentposter,commenttarget,commentdatetime,commentcontent)"
				+ " values('" + String.valueOf(CommentID) + "','"
				+ CommenterID + "','" + String.valueOf(CommentTarget) + "','"
				+ PostTime + "','" + CommentContent + "');";
		
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
	
	public JSONObject Insert(){
		JSONObject ret = new JSONObject();
		if(DBinsert()){
			ret.put("ReturnCode", 1);
			ret.put("CommentID", CommentID);
		}
		else{
			ret.put("ReturnCode", 2);
			ret.put("CommentID", 0);
		}
		return ret;
	}
}