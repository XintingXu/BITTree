package cn.xuxinting.CheckCodeMake;


import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewUserCheck extends Thread{
    int Num;
    String PhoneIMEI = null;
    String UserName = null;
    String UserPass = null;
    String UserSex = null;
    int UserPhoneBond;
    String sourceString = null;
    String ContentCheck = null;

    public NewUserCheck(int Num,String PhoneIMEI,String UserName,String UserPass,
                        String UserSex,int UserPhoneBond){
        this.Num = Num;
        this.PhoneIMEI = PhoneIMEI;
        this.UserName = UserName;
        this.UserPass = UserPass;
        this.UserSex = UserSex;
        this.UserPhoneBond = UserPhoneBond;
    }

    public String GetContentCheck(){
        getSourceString();
        getTarget();
        return ContentCheck;
    }

    void getSourceString(){
        sourceString = String.valueOf(Num) + PhoneIMEI + UserName + UserPass
                +UserSex + String.valueOf(UserPhoneBond);
    }
    void getTarget(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(sourceString);
        ContentCheck = passwordGenerate.GetPassword();
    }
}
