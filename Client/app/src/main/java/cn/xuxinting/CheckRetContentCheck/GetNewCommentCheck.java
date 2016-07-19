package cn.xuxinting.CheckRetContentCheck;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class GetNewCommentCheck extends Thread{
    int Num;
    int ReturnCode;
    int CommentID;

    String contentCheck = null;
    String sourceString = null;
    JSONObject json = null;

    public GetNewCommentCheck(JSONObject json){
        this.json = json;
    }

    void getContentCheck(){
        contentCheck = json.getString("ContentCheck");
    }

    void getSourceString(){
        Num = json.getIntValue("Num");
        ReturnCode = json.getInteger("ReturnCode");
        CommentID = json.getInteger("CommentID");
        sourceString = String.valueOf(Num) + String.valueOf(ReturnCode) + String.valueOf(CommentID);
    }

    public boolean Check(){
        getSourceString();
        getContentCheck();
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        if(contentCheck.equals(passwordGenerate.GetPassword())){
            return true;
        }else {
            return false;
        }
    }
}