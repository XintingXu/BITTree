package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sina.sae.util.SaeUserInfo;

/*
 * 版本v1.0
 * 完成日期：2015年12月10日
 * 作者：徐欣廷
 * 功能：查询当前最大UserID情况，可以查询管理员ID和普通用户ID
 * 返回值：查询到的最大ID，类型为String
 * */
public class MaxUserIDSelect {
	String sql = null;
	
	/*
	 * 功能：查询最大普通用户ID，不处理SQLException异常
	 * 返回String类型的ID
	 * */
	public String MaxUserID(){
		sql = "select max(userid) as max from UserInfo where userid like '1%';";
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
	
	/*
	 * 功能：查询最大管理用户ID，不处理SQLException异常
	 * 返回String类型的ID
	 * */
	public String MaxManagerID() throws SQLException{
		sql = "select max(userid) as max from UserInfo where userid like '2%';";
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