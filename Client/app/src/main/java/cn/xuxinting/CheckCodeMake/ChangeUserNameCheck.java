package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class ChangeUserNameCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String UserID = null;
    String UserTargetName = null;

    public ChangeUserNameCheck(int Num,String UserID,String UserTargetName){
        this.Num = Num;
        this.UserID = UserID;
        this.UserTargetName = UserTargetName;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + UserID + UserTargetName;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}
