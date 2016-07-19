package cn.xuxinting.Message;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

public class MessageToSQLite {
    String MessageID = null;
    String MessageSenderID = null;
    String MessageSenderName = null;
    String MessageSenderSex = null;
    String MessageContent = null;
    int MessageApproveNum = 0;
    int MessageCommentNum = 0;
    String Path = null;

    public MessageToSQLite(String Path,String MessageID,String MessageSenderID,String MessageSenderName,String MessageSenderSex,
                           String MessageContent,int MessageApproveNum,int MessageCommentNum){
        this.MessageID = MessageID;
        this.MessageSenderID = MessageSenderID;
        this.MessageSenderName = MessageSenderName;
        this.MessageSenderSex = MessageSenderSex;
        this.MessageContent = MessageContent;
        this.MessageApproveNum = MessageApproveNum;
        this.MessageCommentNum = MessageCommentNum;
        this.Path = Path;
    }

    void ToSQLite(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        ContentValues contentValues = new ContentValues();
        String []arg = new String[1];
        arg[0] = MessageID;
        Cursor cursor = db.rawQuery("select count(*) as count from message where messageid = ?;",arg);
        cursor.moveToNext();

        /*
        * table message(
        * messageid char(10),
        * messagepostdatetime datetime,
        * messagesenderid char(10),
        * messagesendername varchar(20),
        * messagecontent text,
        * messageapprove int,
        * messagecomment int,
        * messageapproved int)
        * */
        if(cursor.getInt(0) != 0){
            contentValues.put("messagepostdatetime",date);
            contentValues.put("messagesenderid",MessageSenderID);
            contentValues.put("messagesendername",MessageSenderName);
            contentValues.put("messagecontent",MessageContent);
            contentValues.put("messageapprove",MessageApproveNum);
            contentValues.put("messagecomment",MessageCommentNum);
            contentValues.put("messageapproved",0);
            db.update("message",contentValues,"messageid = ?",arg);
            db.close();
        }else{
            contentValues.put("messageid",MessageID);
            contentValues.put("messagepostdatetime",date);
            contentValues.put("messagesenderid",MessageSenderID);
            contentValues.put("messagesendername",MessageSenderName);
            contentValues.put("messagecontent",MessageContent);
            contentValues.put("messageapprove",MessageApproveNum);
            contentValues.put("messagecomment",MessageCommentNum);
            contentValues.put("messageapproved",0);
            db.insert("message",null,contentValues);
            db.close();
        }
    }

    public void DoInsert(){
        ToSQLite();
    }
}
