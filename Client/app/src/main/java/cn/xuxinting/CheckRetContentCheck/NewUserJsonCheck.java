package cn.xuxinting.CheckRetContentCheck;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewUserJsonCheck extends Thread{
    int Num;
    int ReturnCode;
    String UserID = null;

    String contentCheck = null;
    String sourceString = null;
    JSONObject json = null;

    public NewUserJsonCheck(JSONObject json){
        this.json = json;
    }

    void getContentCheck(){
        contentCheck = json.getString("ContentCheck");
    }

    void getSourceString(){
        Num = json.getIntValue("Num");
        ReturnCode = json.getInteger("ReturnCode");
        UserID = json.getString("UserID");
        sourceString = String.valueOf(Num) + String.valueOf(ReturnCode) + UserID;
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
