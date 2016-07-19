package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewMessageCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String SenderID = null;
    String Message = null;

    public NewMessageCheck(int Num,String SenderID,String Message){
        this.Num = Num;
        this.SenderID = SenderID;
        this.Message = Message;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + SenderID + Message;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}
