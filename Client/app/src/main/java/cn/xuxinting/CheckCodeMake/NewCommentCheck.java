package cn.xuxinting.CheckCodeMake;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewCommentCheck {
    String ContentCheck = null;
    String sourceString = null;

    int Num;
    String CommenterID = null;
    String MessageID = null;
    String CommentTarget = null;
    String CommentContent = null;

    public NewCommentCheck(int Num,String MessageID,String CommenterID,String CommentTarget,String CommentContent){
        this.Num = Num;
        this.MessageID = MessageID;
        this.CommenterID = CommenterID;
        this.CommentTarget = CommentTarget;
        this.CommentContent = CommentContent;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + MessageID + CommenterID + CommentTarget + CommentContent;
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}