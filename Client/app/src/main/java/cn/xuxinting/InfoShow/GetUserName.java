package cn.xuxinting.InfoShow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GetUserName {
    String Path = null;
    String UserName = null;
    public  GetUserName(String path){
        this.Path = path;
    }
    public String getUserName(){
        if(get()) {
            return UserName;
        }
        else{
            return "";
        }
    }
    boolean get(){
        String [] column = new String[1];
        column[0] = "username";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        Cursor cursor = db.query("userinfo", column, null, null, null, null, null);
        cursor.moveToNext();
        UserName = cursor.getString(0);
        if(UserName.length() == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
