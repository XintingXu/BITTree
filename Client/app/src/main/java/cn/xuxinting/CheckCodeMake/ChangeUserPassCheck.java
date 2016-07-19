package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class ChangeUserPassCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String UserID = null;
    String UserTargetPassword = null;

    public ChangeUserPassCheck(int Num,String UserID,String UserTargetPassword){
        this.Num = Num;
        this.UserID = UserID;
        this.UserTargetPassword = UserTargetPassword;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + UserID + UserTargetPassword;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}