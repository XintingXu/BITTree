package cn.xuxinting.InfoShow;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GetUserID {
    String Path = null;
    String UserID = null;
    public  GetUserID(String path){
        this.Path = path;
    }
    public String getUserID(){
        if(get()) {
            return UserID;
        }
        else{
            return "";
        }
    }
    boolean get(){
        String [] column = new String[1];
        column[0] = "userid";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        Cursor cursor = db.query("userinfo", column, null, null, null, null, null);
        cursor.moveToNext();
        UserID = cursor.getString(0);
        if(UserID.length() == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
