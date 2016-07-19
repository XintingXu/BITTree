package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sina.sae.util.SaeUserInfo;



/*
 * 版本v1.0
 * 完成时间：2015年12月10日
 * 功能：查找指定的UserID是否存在
 * */

public class UserIDSelect {
	String sql = null;
	String UserID = null;
	
	/*
	 * 类的构造函数及UserID赋值函数
	 * */
	public UserIDSelect(){
		UserID = null;
	}
	public UserIDSelect(String in){
		UserID = in;
	}
	public void UserNameSet(String in){
		UserID = in;
	}
	
	/*
	 * 查找函数，查找数据库中是否存在相同的UserID，存在则返回true，否则返回false
	 * */
	public boolean UserID(){
		sql = "select count(userid) as count from UserInfo where userid = '" + UserID.toString() + "';";
		int count = 1;
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
			count = resultSet.getInt("count");
			con.close();
		}catch(Exception e){
			
		}
		if(count == 0){
			return false;
		}
		else{
			return true;
		}
	}
}