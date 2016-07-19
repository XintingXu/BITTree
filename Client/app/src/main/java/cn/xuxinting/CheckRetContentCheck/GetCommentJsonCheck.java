package cn.xuxinting.CheckRetContentCheck;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.PassGenerate.PasswordGenerate;

public class GetCommentJsonCheck {
    int Num;
    int ReturnNum;

    String contentCheck = null;
    String sourceString = null;
    JSONObject json = null;

    public GetCommentJsonCheck(JSONObject json){
        this.json = json;
    }

    void getContentCheck(){
        contentCheck = json.getString("ContentCheck");
    }

    void getSourceString(){
        Num = json.getIntValue("Num");
        ReturnNum = json.getInteger("ReturnNum");
        sourceString = String.valueOf(Num) + String.valueOf(ReturnNum);
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
