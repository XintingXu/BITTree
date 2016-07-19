package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class GetMessageCheck extends Thread{
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String UserID = null;
    String MaxMessaged = null;

    public GetMessageCheck(int Num,String UserID,String MaxMessaged){
        this.Num = Num;
        this.MaxMessaged = MaxMessaged;
        this.UserID = UserID;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + UserID + MaxMessaged;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}
