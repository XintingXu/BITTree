package cn.xuxinting.bitree;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xuxinting.CheckCodeMake.GetMessageCheck;
import cn.xuxinting.CheckRetContentCheck.GetMessageJsonCheck;
import cn.xuxinting.InfoShow.GetUserID;
import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.Message.MessageToSQLite;
import cn.xuxinting.NetWork.NetDeal;

public class MessageShow extends ActionBarActivity {

    SimpleAdapter adapter = null;

    Intent intent = null;
    String Path = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);

        intent = getIntent();
        Path = intent.getStringExtra("Path");

        GetMessage getMessage = new GetMessage(Path);
        getMessage.getMessage();

        ListView Message = (ListView) findViewById(R.id.MessageList);

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            GetMessageInfo getMessageInfo = new GetMessageInfo(Path, i);
            JSONObject jsonObject = getMessageInfo.getInfo();
            if (jsonObject.getString("messageid").length() != 0) {
                map.put("ItemTitle", jsonObject.getString("messagesendername"));
                map.put("ItemText", jsonObject.getString("messagecontent"));
                map.put("MessageID", jsonObject.getString("messageid"));
                map.put("MessageApprove", jsonObject.getInteger("messageapprove"));
                map.put("MessageComment", jsonObject.getInteger("messagecomment"));
                data.add(map);
            } else {
            }
        }

        String[] from = new String[]{"ItemTitle", "ItemText", "MessageID", "MessageApprove"};
        int[] to = new int[]{R.id.ItemTitle, R.id.ItemText, R.id.MessageID, R.id.ApproveNum};

        adapter = new SimpleAdapter(this, data, R.layout.list_item, from, to);
        Message.setAdapter(adapter);
        Message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map map = (Map)parent.getItemAtPosition(position);
                String MessageID = (String)map.get("MessageID");
                Intent intent = new Intent(MessageShow.this, Comment.class);
                intent.putExtra("Path", Path);
                intent.putExtra("MessageID",MessageID);
                startActivity(intent);
            }
        });
    }
}


class GetMessageInfo{
    String MessageID = null;
    int n;
    String MaxMessageID = null;
    String Path = null;

    public GetMessageInfo(String Path,int n){
        this.n = n;
        this.Path = Path;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        Cursor cursor = db.rawQuery("select max(messageid) as max from message",null);
        cursor.moveToNext();
        MaxMessageID = cursor.getString(0);
        Long id = Long.parseLong(MaxMessageID) - n;
        MessageID = String.valueOf(id);
        cursor.close();
        db.close();
    }

    public JSONObject getInfo(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        Cursor cursor = db.rawQuery("select count(messageid) as cont from message " +
                "where messageid = '" + MessageID + "';", null);
        cursor.moveToNext();
        int cont = cursor.getInt(0);
        cursor.close();
        if(cont != 0){
            JSONObject jsonObject = new JSONObject();
            Cursor cursor2 = db.rawQuery("select messagesendername,messagecontent,messageid,messageapprove,messagecomment from message " +
                    "where messageid = '" + MessageID + "';", null);
            cursor2.moveToNext();
            jsonObject.put("messageid", cursor2.getString(2));
            jsonObject.put("messagesendername", cursor2.getString(0));
            jsonObject.put("messagecontent",cursor2.getString(1));
            jsonObject.put("messageapprove",cursor2.getInt(3));
            jsonObject.put("messagecomment", cursor2.getInt(4));
            cursor2.close();
            db.close();
            return jsonObject;
        }else{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("messagesendername","");
            jsonObject.put("messagecontent","");
            jsonObject.put("messageid","");
            jsonObject.put("messageapprove", 0);
            jsonObject.put("messagecomment", 0);
            db.close();
            return jsonObject;
        }
    }
}

class GetMessage{
    int Num;
    String FuncCode = "GetMessage";
    String UserID = null;
    String MaxMessaged = null;
    String ContentCheck = null;
    String Path = null;
    JSONObject jsonObject = new JSONObject();
    JSONObject Get = null;

    public GetMessage(String Path){
        this.Path = Path;
        GetUserID getUserID = new GetUserID(Path);
        UserID = getUserID.getUserID();
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
    }

    void getMax(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        Cursor cursor = db.rawQuery("select count(messageid) as cont from message",null);
        cursor.moveToNext();
        MaxMessaged = cursor.getString(0);
        if(MaxMessaged.equals("0")){
            MaxMessaged = "5000000000";
        }else{
            cursor = db.rawQuery("select max(messageid) as max from message",null);
            cursor.moveToNext();
            MaxMessaged = cursor.getString(0);
            cursor.close();
        }
        cursor.close();
        db.close();
    }

    void GetFromServer(){
        GetMessageCheck getMessageCheck = new GetMessageCheck(Num,UserID,MaxMessaged);
        ContentCheck = getMessageCheck.GetContentCheck();
        jsonObject.put("Num", Num);
        jsonObject.put("FuncCode",FuncCode);
        jsonObject.put("UserID",UserID);
        jsonObject.put("MaxMessaged", MaxMessaged);
        jsonObject.put("ContentCheck", ContentCheck);

        NetDeal netDeal = new NetDeal(jsonObject);
        Get = netDeal.getJSON();
        GetMessageJsonCheck getMessageJsonCheck = new GetMessageJsonCheck(Get);
        if(!getMessageJsonCheck.Check()){
            Get = new JSONObject();
            Get.put("ReturnNum",0);
        }
    }

    void InsertToSQLite(){
        int n = Get.getInteger("ReturnNum");
        JSONObject Message = Get.getJSONObject("Message");
        if(n != 0) {
            for (int i = 0; i <= n; i++) {
                JSONObject temp = Message.getJSONObject("Message" + String.valueOf(i));
                if (temp.getString("MessageID").length() == 0) {
                    ;
                } else {
                    String MessageSenderID = temp.getString("MessageSenderID");
                    String MessageSenderName = temp.getString("MessageSenderName");
                    String MessageSenderSex = temp.getString("MessageSenderSex");
                    String MessageContent = temp.getString("MessageContent");
                    int MessageApproveNum = temp.getInteger("MessageApproveNum");
                    int MessageCommentNum = temp.getInteger("MessageCommentNum");
                    MessageToSQLite messageToSQLite = new MessageToSQLite(Path, temp.getString("MessageID"), MessageSenderID,
                            MessageSenderName, MessageSenderSex, MessageContent, MessageApproveNum,
                            MessageCommentNum);
                    messageToSQLite.DoInsert();
                }
            }
        }
    }

    public void getMessage(){
        getMax();
        GetFromServer();
        InsertToSQLite();
    }

    public JSONObject getJSON(){
        return Get;
    }

    public void SetMaxMessaged(String MaxMessaged){
        this.MaxMessaged = MaxMessaged;
    }

    public void getSetMseeage(){
        GetFromServer();
        InsertToSQLite();
    }
}