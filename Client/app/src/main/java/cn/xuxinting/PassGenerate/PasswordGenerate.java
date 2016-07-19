package cn.xuxinting.PassGenerate;

import cn.xuxinting.MD5.MD5;

public class PasswordGenerate extends Thread{
    String PassSource = "";
    String Attach = "658HJGBJfcc^%^";
    public PasswordGenerate(String pass){
        this.PassSource = pass;
    }
    public String GetPassword(){
        PassSource = Calcu();
        PassSource = Calcu();
        return PassSource;
    }
    String Calcu(){
        MD5 md5 = new MD5(PassSource);
        PassSource = md5.Encode();
        PassSource = Attach + PassSource + Attach;
        MD5 md51 = new MD5(PassSource);
        PassSource = md51.Encode();
        return PassSource;
    }
}