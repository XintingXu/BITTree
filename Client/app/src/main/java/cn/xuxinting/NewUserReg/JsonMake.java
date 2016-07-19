package cn.xuxinting.NewUserReg;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.PassGenerate.PasswordGenerate;

public class JsonMake {
    int Num;
    String FuncCode = "NewUser";
    String PhoneIMEI = null;
    String UserName = null;
    String UserPass = null;
    String UserSex = null;
    int UserPhoneBond;
    String ContentCheck = null;

    public JsonMake(String PhoneIMEI,String UserName,
                    String UserPass,String UserSex,int UserPhoneBond){
        this.PhoneIMEI = PhoneIMEI;
        this.UserName = UserName;
        this.UserPass = UserPass;
        this.UserSex = UserSex;
        this.UserPhoneBond = UserPhoneBond;
    }

    void makeCheck(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        String source = String.valueOf(Num) + PhoneIMEI + UserName + UserPass + UserSex + String.valueOf(UserPhoneBond);
        PasswordGenerate passwordGenerate = new PasswordGenerate(source);
        ContentCheck = passwordGenerate.GetPassword();
    }

    public JSONObject getJSON(){
        makeCheck();
        JSONObject ret = new JSONObject();
        ret.put("Num",Num);
        ret.put("FuncCode",FuncCode);
        ret.put("PhoneIMEI",PhoneIMEI);
        ret.put("UserName",UserName);
        ret.put("UserPass",UserPass);
        ret.put("UserSex",UserSex);
        ret.put("UserPhoneBond",UserPhoneBond);
        ret.put("ContentCheck",ContentCheck);
        return ret;
    }
}
