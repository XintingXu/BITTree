package cn.xuxinting.CheckRetContentCheck;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class RetIMEILogJsonCheck extends Thread{
    int Num;
    int ReturnCode;
    String UserName = null;
    String UserID = null;

    String contentCheck = null;
    String sourceString = null;
    JSONObject json = null;

    public RetIMEILogJsonCheck(JSONObject json){
        this.json = json;
    }

    void getContentCheck(){
        contentCheck = json.getString("ContentCheck");
    }

    void getSourceString(){
        Num = json.getIntValue("Num");
        ReturnCode = json.getInteger("ReturnCode");
        UserID = json.getString("UserID");
        UserName = json.getString("UserName");
        sourceString = String.valueOf(Num) + String.valueOf(ReturnCode) + UserID + UserName;
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
