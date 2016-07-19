package cn.xuxinting.CheckRetContentCheck;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewMessageJsonCheck extends Thread{
    int Num;
    int ReturnCode;
    String MessageID = null;

    String contentCheck = null;
    String sourceString = null;
    JSONObject json = null;

    public NewMessageJsonCheck(JSONObject json){
        this.json = json;
    }

    void getContentCheck(){
        contentCheck = json.getString("ContentCheck");
    }

    void getSourceString(){
        Num = json.getIntValue("Num");
        ReturnCode = json.getInteger("ReturnCode");
        MessageID = json.getString("MessageID");
        sourceString = String.valueOf(Num) + String.valueOf(ReturnCode) + MessageID;
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