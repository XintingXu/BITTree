package cn.xuxinting.UserInfoChange;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ChangeName {
    String targetName = null;
    String Path = null;
    public  ChangeName(String name){
        this.targetName = name;
    }
    public void change(){
        LocalChange();
    }

    void LocalChange(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("username",targetName);
        db.update("userinfo",contentValues,null,null);
    }
}
