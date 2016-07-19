package cn.xuxinting.NewUserReg;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

import cn.xuxinting.MD5.*;
import cn.xuxinting.PassGenerate.PasswordGenerate;

public class NewUserReg {
    String UserName = null;
    String UserPassword = null;
    String UserSex = null;
    int UserPhoneBond = 0;
    String UserID = null;
    String Path = null;
    String UserPhoneIMEI = null;

    /*
    * 构造函数
    * */
    public NewUserReg(String Path,String Name,String Pass,int Bond,String Sex,String phone){
        this.UserName = StringReplace(Name);
        this.UserPassword = StringReplace(Pass);
        this.UserPhoneBond = Bond % 2;
        this.UserSex = Sex;
        this.Path = Path;
        this.UserPhoneIMEI = phone;
    }

    /*
    * 替代危险字符
    * */
    String StringReplace(String str){
        str=str.replaceAll(";","").replaceAll("&", "&").replaceAll("<","<").replaceAll(">",">").replaceAll("'","")
                .replaceAll("--", " ").replaceAll("/", "").replaceAll("%","");
        return str;
    }

    /*
    * 生成存储密码
    * */
    void PassGene(){
        PasswordGenerate passwordGenerate = new PasswordGenerate(UserPassword);
        UserPassword = passwordGenerate.GetPassword();
    }

    /*
    *调用servlet，实现
    * */
    public Boolean UserInsert(){
        PassGene();
        if(true){
            UserID = "1000000001";
            InsertIntoDB();
            return true;
        }
        else{
            return false;
        }
    }

    /*
    * 用户信息写入SQLite
    * */
    void InsertIntoDB(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",UserID);
        contentValues.put("username",UserName);
        contentValues.put("lastLogtime",date);
        db.insert("userinfo", null, contentValues);
    }
}
