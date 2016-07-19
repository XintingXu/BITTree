package cn.xuxinting.insert;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sina.sae.util.SaeUserInfo;

import cn.xuxinting.select.*;



/*
 * 版本v1.0
 * 功能：插入一条新的用户信息
 * 作者：徐欣廷
 * 完成日期：2015年12月10日
 * */


public class UserInsert {
	String UserID = "";
	String UserPhoneIMEI = "";
	String UserPassword = "";
	short UserPhoneBond = 1;
	String UserName = "";
	String UserSex = "";
	String UserMajor = "";
	int UserAge = 0;
	String UserQQ = "";
	short UserQQVisible = 0;
	String UserMail = "";
	short UserMailVisible = 0;
	String UserFirstLog = "";

	String sql;
	
	/*
	 * 设置用户信息中的用户IMEI
	 * 返回值为boolean型，无异常需要处理
	 * */
	public boolean SetUserPhoneIMEI(String in){
		if(in.length()!=15){
			return false;
		}
		else{
			String CheckUserPhoneIMEI = "^[1-9]{1}[0-9]{14}$";
			if(in.matches(CheckUserPhoneIMEI)){
				UserPhoneIMEI = in;
				return true;
			}
			else
				return false;
		}
	}
	
	/*
	 * 设定要写入的UserName信息
	 * 返回值boolean，返回true代表数据校验正确，返回为false，代表数据重复，需要更改
	 * 不处理异常，需要调用时处理SQLException
	 * */
	public boolean SetUserName(String in) throws SQLException{
		UserNameSelect userNameSelect = new UserNameSelect(in);
		if(userNameSelect.UserNameCheck()){
			UserName = in;
			return true;
		}
		else
			return false;
	}
	
	/*
	 * 设定安全密码，密码为32位MD5摘要码
	 * 返回值为boolean型，返回true代表结果正确，返回false代表数据计算出现错误
	 * */
	public boolean SetUserPassword(String in){
		if(in.length() == 32){
			UserPassword = in;
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * 根据用户设定，选择是否ID与用户手机IMEI绑定
	 * 函数输入为short型整数，值为0或1
	 * */
	public void SetUserPhoneBond(short in){
		UserPhoneBond = in;
	}
	
	
	/*
	 * 设定用户性别
	 * 返回值为boolean型，返回true代表数据没有问题，返回false代表数据出现错误
	 * */
	public boolean SetUserSex(String in){
		if(in.length() != 0){
			UserSex = in;
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * 设定用户ID信息，根据数据库中已经存在的编码，将当前用户的ID设置为最大值+1
	 * 返回值为boolean型，true代表处理成功，false代表处理失败
	 * 未处理SQLException异常，需要调用时处理。
	 * */
	private boolean SetUserID(){
		MaxUserIDSelect maxUserIDSelect = new MaxUserIDSelect();
		UserID = maxUserIDSelect.MaxUserID();
		Long ID = null;
		ID = Long.parseLong(UserID);
		ID = ID + 1;
		UserID = ID.toString();
		if(UserID.length() == 10){
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 设定SQL语句
	 * */
	public boolean SqlSet() throws SQLException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserFirstLog = format.format(new Date());

		if(SetUserID()){
			sql = "insert into UserInfo values('"+UserID.toString()+"','"+UserPhoneIMEI.toString()
			+"','"+UserPassword.toString()+"','"+UserPhoneBond+"','"+UserName.toString()+"','"+UserSex.toString()+"','"
			+UserMajor.toString()+"','"+UserAge+"','"+UserQQ.toString()+"','"+UserQQVisible+"','"
			+UserMail.toString()+"','"+UserMailVisible+"','"+UserFirstLog.toString()+"');";
			
			return true;
		}
		else{
			return false;
		}
	}
	
	/*
	 * 执行数据库写入
	 * 处理SQLException异常
	 * */
	public void Insert(){
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getUserID(){
		return UserID;
	}
}