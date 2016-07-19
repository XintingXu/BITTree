package cn.xuxinting.JsonCheck;

import cn.xuxinting.MD5.MD5;

public class String2MD5 {
	String Source = null;
	String Attach = "658HJGBJfcc^%^";
	
	public String2MD5(String source) {
		// TODO Auto-generated constructor stub
		this.Source = source;
	}
	
	public String getMD5(){
		Source = Calcu();
		Source = Calcu();
		return Source;
	}
	
	String Calcu(){
		 MD5 md5 = new MD5(Source);
	     Source = md5.Encode();
	     Source = Attach + Source + Attach;
	     MD5 md51 = new MD5(Source);
	     Source = md51.Encode();
	     return Source;
    }
}
