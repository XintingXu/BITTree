package cn.xuxinting.select;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sina.sae.util.SaeUserInfo;


/*
 * 版本v1.0
 * 完成日期：2015年12月10日
 * 作者：徐欣廷
 * 功能：查询数据库中有无重复的UserName项，不处理SQLException
 * */

public class UserNameSelect {
	String sql = null;
	Connection con = null;
	Statement st = null;
	String UserName = null;
	
	/*
	 * 类的初始化函数，以及变量赋值函数；
	 * */
	public UserNameSelect(){
		UserName = null;
	}
	public UserNameSelect(String in){
		UserName = in;
	}
	public void UserNameSet(String in){
		UserName = in;
	}
	
	/*
	 * 检查函数，检查数据库中的相同UserName个数，不处理异常。
	 * 如果数据库中没有相同的，返回true，如果存在重复的，返回 false
	 * */
	public boolean UserNameCheck(){
		sql = "select count(username) as count from UserInfo where username = '" + UserName.toString() + "';";
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
			result = resultSet.getString("count");
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		if(result.equals("0")){
			return true;
		}
		else{
			return false;
		}
	}
}