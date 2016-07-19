package cn.xuxinting.bitree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.impl.io.ContentLengthInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.xuxinting.CheckCodeMake.NewApproveCheck;
import cn.xuxinting.CheckCodeMake.NewCommentCheck;
import cn.xuxinting.InfoShow.GetUserID;
import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.NetWork.NetDeal;
import cn.xuxinting.PassGenerate.PasswordGenerate;

public class Comment extends ActionBarActivity {

    Intent intent = null;
    String Path = null;
    String MessageID = null;
    Button NewComment = null;
    ImageButton Approve = null;
    TextView CommentInput = null;
    TextView ApproveNum = null;
    ListView comment = null;

    TextView MessageSenderName = null;
    TextView MessageContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        intent = getIntent();
        Path = intent.getStringExtra("Path");
        MessageID = intent.getStringExtra("MessageID");
        NewComment = (Button)findViewById(R.id.NewComment);
        Approve = (ImageButton)findViewById(R.id.MeaasgeApprove);
        comment = (ListView)findViewById(R.id.CommentList);

        CommentInput = (TextView)findViewById(R.id.editText);
        ApproveNum = (TextView)findViewById(R.id.ApproveNum);

        MessageSenderName = (TextView)findViewById(R.id.SenderName);
        MessageContent = (TextView)findViewById(R.id.MessageContent);

        getComment geCom = new getComment(Path,MessageID);
        geCom.SQLDBdeal();

        MessageShow();
        CommentShow();

        NewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CommentContent = CommentInput.getText().toString();
                if(CommentContent.length() != 0 && CommentContent.length() < 255) {
                    newComment comment = new newComment(Path, MessageID, CommentContent);
                    if (comment.Comment()) {
                        Toast.makeText(getApplicationContext(),
                                "评论成功", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "服务器好像偷懒了", Toast.LENGTH_LONG)
                                .show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "检查后再试", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newApprove approve = new newApprove(Path,MessageID);
                if(approve.getApprove()){
                    Toast.makeText(getApplicationContext(),
                            "点赞", Toast.LENGTH_LONG)
                            .show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "服务器好像偷懒了", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    void CommentShow(){
        String[] from = new String[] {"Name","Content","Date"};
        int[] to = new int[] {R.id.CommentSender, R.id.CommentContent ,R.id.CommentDate};
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
        String sql = "select commentername,commentcontent,commentdate from comment_" + MessageID + ";";
        Cursor cursor = db.rawQuery(sql,null);
        int count = cursor.getCount();
        for(int i = 0 ; i < count ; i ++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Name",cursor.getString(0));
            map.put("Content",cursor.getString(1));
            map.put("Date",cursor.getString(2));
            data.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.comment_list, from, to);
        comment.setAdapter(simpleAdapter);
    }

    void MessageShow(){
        String MessageSender = null;
        String MessageContent = null;

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
        String sql = "select messagesendername,messagecontent from message where messageid = '" + MessageID + "';";
        Cursor cursor = db.rawQuery(sql,null);
        MessageSender = cursor.getString(0);
        MessageContent = cursor.getString(1);
        cursor.close();
        db.close();

        MessageSenderName.setText(MessageSender);
        this.MessageContent.setText(MessageContent);
    }
}

class getComment{
    String Path = null;
    int Num;
    String MessageID = null;
    String FuncCode = "GetComment";
    JSONObject post = new JSONObject();
    JSONObject get = null;

    public getComment(String Path,String MessageID){
        this.Path = Path;
        this.MessageID = MessageID;
    }

    void makeJSON(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        post.put("Num",Num);
        post.put("FuncCode",FuncCode);
        post.put("MessageID",MessageID);
        String source = String.valueOf(Num) + MessageID;
        PasswordGenerate passwordGenerate = new PasswordGenerate(source);
        post.put("ContentCheck",passwordGenerate.GetPassword());
    }

    void UpandDown(){
        makeJSON();
        NetDeal netDeal = new NetDeal(post);
        get = netDeal.getJSON();
    }

    public void SQLDBdeal(){
        UpandDown();
        if(get.getInteger("ReturnNum").equals(0)){
            ;
        }else{
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
            String sql = "select count(*) as c from Sqlite_master where type ='table' and name = 'comment_" + MessageID + "';";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToNext();
            int c = cursor.getInt(0);
            cursor.close();
            db.close();
            if(c == 0){
                sql = "create table comment_" + MessageID + "(commentid int,commentername varchar(30)," +
                        "commenterid char(10),commenttarget int,commentcontent varchar(255)," +
                        "commentdate datetime);";
                db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
                db.execSQL(sql);
                db.close();
            }else{
                ;
            }
            Update(get.getInteger("ReturnNum"),get.getJSONObject("Comment"));
        }
    }

    void Update(int num,JSONObject Comment){
        int CommentID;
        String CommenterName = null;
        String CommenterID = null;
        int CommentTarget;
        String CommentContent = null;
        String CommentDate = null;
        JSONObject temp = null;

        for(int i = 0 ; i < num ; i ++){
            temp = Comment.getJSONObject("comment_" + String.valueOf(i));
            CommentID = temp.getInteger("CommentID");
            CommenterName = temp.getString("CommenterName");
            CommenterID = temp.getString("CommenterID");
            CommentTarget = temp.getInteger("CommentTarget");
            CommentContent = temp.getString("CommentContent");
            CommentDate = temp.getString("CommentDate");

            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
            String sql = "select count(*) as c from comment_" + MessageID + " where commentid = '" + CommentID + "';";
            Cursor cursor = db.rawQuery(sql,null);
            cursor.moveToNext();
            int c = cursor.getInt(0);
            cursor.close();
            db.close();

            if(c == 0){
                ContentValues contentValues = new ContentValues();
                contentValues.put("commentid",CommentID);
                contentValues.put("commentername",CommenterName);
                contentValues.put("commenterid",CommenterID);
                contentValues.put("commenttarget",CommentTarget);
                contentValues.put("commentcontent",CommentContent);
                contentValues.put("commentdate",CommentDate);
                db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
                db.insert("comment_" + MessageID,null,contentValues);
            }else{

            }
        }
    }
}

class newComment{
    String Path;
    int Num;
    String FuncCode = "NewComment";
    String MessageID = null;
    String CommenterID = null;
    int CommentTarget;
    String CommentContent = null;
    String ContentCheck = null;

    JSONObject get = null;

    public newComment(String Path,String MessageID,String CommentContent){
        this.Path = Path;
        this.MessageID = MessageID;
        this.CommentContent = CommentContent;
        MakeNum makeNum = new MakeNum();
        this.Num = makeNum.getNum();
        GetUserID getUserID = new GetUserID(Path);
        CommenterID = getUserID.getUserID();
        CommentTarget = 0;
        NewCommentCheck newCommentCheck = new NewCommentCheck(Num,MessageID,CommenterID
        ,String.valueOf(CommentTarget),CommentContent);
        ContentCheck = newCommentCheck.GetContentCheck();
    }

    void UpandDown(){
        JSONObject post = new JSONObject();
        post.put("Num",Num);
        post.put("FuncCode",FuncCode);
        post.put("MessageID",MessageID);
        post.put("CommenterID",CommenterID);
        post.put("CommentTarget",CommentTarget);
        post.put("CommentContent",CommentContent);
        post.put("ContentCheck",ContentCheck);

        NetDeal netDeal = new NetDeal(post);
        get = netDeal.getJSON();
    }

    public boolean Comment(){
        UpandDown();
        if(get.getInteger("ReturnCode").equals(1)){
            return true;
        }else{
            return false;
        }
    }
}

class newApprove{
    String Path = null;
    int Num;
    String FuncCode = "MessageApprove";
    String MessageID = null;
    String UserID = null;
    String ContentCheck = null;
    JSONObject post = new JSONObject();

    public newApprove(String Path,String MessageID){
        this.Path = Path;
        this.MessageID = MessageID;
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        GetUserID getUserID = new GetUserID(Path);
        this.UserID = getUserID.getUserID();
        NewApproveCheck newApproveCheck = new NewApproveCheck(Num,this.MessageID,UserID);
        ContentCheck = newApproveCheck.GetContentCheck();
    }

    void makeJSON(){
        post.put("Num",Num);
        post.put("FuncCode",FuncCode);
        post.put("MessageID", MessageID);
        post.put("UserID", UserID);
        post.put("ContentCheck", ContentCheck);
    }

    void Approve(){
        makeJSON();
        NetDeal netDeal = new NetDeal(post);
        post = netDeal.getJSON();
    }

    public boolean getApprove(){
        Approve();
        if(post.getInteger("ReturnCode").equals(1)){
            return true;
        }else {
            return false;
        }
    }
}