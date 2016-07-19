package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class IMEILogCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String UserPhoneIMEI  = null;

    public IMEILogCheck(int Num,String UserPhoneIMEI ){
        this.Num = Num;
        this.UserPhoneIMEI  = UserPhoneIMEI ;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + UserPhoneIMEI;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}