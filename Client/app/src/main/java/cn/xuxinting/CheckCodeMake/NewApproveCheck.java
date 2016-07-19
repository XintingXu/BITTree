package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewApproveCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String UserID = null;
    String MessageID = null;

    public NewApproveCheck(int Num,String MessageID,String UserID){
        this.Num = Num;
        this.UserID = UserID;
        this.MessageID = MessageID;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + MessageID + UserID;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}