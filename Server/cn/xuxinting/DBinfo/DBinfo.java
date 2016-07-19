package cn.xuxinting.DBinfo;
import com.sina.sae.util.SaeUserInfo;

public class DBinfo {
	String username=SaeUserInfo.getAccessKey();
	String password=SaeUserInfo.getSecretKey();
	String ConnectUserDB = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
	String ConnectMessageDB = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
	String ConnectInformDB = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
	String ConnectCommentDB = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_bitbitree";
	
	public String getConnectUserDB() {
		return ConnectUserDB;
	}
	public String getConnectMessageDB() {
		return ConnectMessageDB;
	}
	public String getConnectInformDB() {
		return ConnectInformDB;
	}
	public String getConnectCommentDB() {
		return ConnectCommentDB;
	}
	
	public String getName(){
		return username;
	}
	
	public String getPass(){
		return password;
	}
}