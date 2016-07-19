package cn.xuxinting.bitree;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import cn.xuxinting.IMEILog.UserLogbyIMEI;
import cn.xuxinting.NetCheck.NetCheck;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CreatePath();
        DbCreateTable();


        Context con = getApplicationContext();
        final String Path = con.getFilesDir().getPath();

        AsyncTask net = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                if(NetTest()) {
                    NetCheck netCheck = new NetCheck();
                    if(netCheck.Check()) {
                        File file = new File(Path + "/BITree.sqlite");
                        if(file.exists()) {

                        } else {
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),
                                "貌似服务器离家出走了...", Toast.LENGTH_LONG)
                                .show();
                    }
                }
                return null;
            }
        };
        net.execute();


        ImageButton ReadMessage = (ImageButton)findViewById(R.id.ReadMessage);
        ImageButton NewMessage = (ImageButton)findViewById(R.id.NewMessage);
        ImageButton UserConfig = (ImageButton)findViewById(R.id.UserConfig);
        ImageButton LogIn = (ImageButton)findViewById(R.id.LogIn);

        ReadMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MessageShow.class);
                intent.putExtra("Path", Path);
                if(UserLogedCheck(Path) && NetTest()) {
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "亲，请先注册再用^_^，找不到注册页面，点下方", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        NewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageCreate.class);
                intent.putExtra("Path", Path);
                if (UserLogedCheck(Path) && NetTest()) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "亲，请先注册再用^_^，找不到注册页面，点下方", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        UserConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserConfig.class);
                intent.putExtra("Path", Path);
                if(UserLogedCheck(Path) && NetTest()) {
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "亲，请先注册再用^_^，找不到注册页面，点下方", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserLogDeal();

                if(!UserLogedCheck(Path) && NetTest()){
                    Intent intent = new Intent(MainActivity.this,UserRegister.class);
                    intent.putExtra("Path",Path);
                    startActivity(intent);
                }else if(UserLogedCheck(Path)){
                    Intent intent = new Intent(MainActivity.this, cn.xuxinting.bitree.UserConfig.class);
                    intent.putExtra("Path",Path);
                    startActivity(intent);
                }
            }
        });
    }


    boolean UserLogedCheck(String Path){
        boolean Loged = false;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
        Cursor cursor = db.rawQuery("select count(userid) as c from userinfo;",null);
        cursor.moveToNext();
        if(cursor.getString(0).equals("0")){
            Loged = false;
        }
        else {
            Loged = true;
        }
        cursor.close();
        db.close();
        return  Loged;
    }

    /*
    * 判断SQlite中是否存在表userinfo
    * 返回true代表存在，false代表不存在
    * */
    final boolean DbUserinfoCheck(){
        boolean result = true;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            Context con = getApplicationContext();
            String Path = con.getFilesDir().getPath();
            db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
            String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='userinfo';";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count > 0){
                    result = true;
                }
                else{
                    result = false;
                }
            }else {
                result = false;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

    /*
    * 判断SQLite中是否存在message表
    * 返回true代表存在，false代表不存在
    * */
    final boolean DbMessageCheck(){
        boolean result = true;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            Context con = getApplicationContext();
            String Path = con.getFilesDir().getPath();
            db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
            String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='message' ;";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count > 0){
                    result = true;
                }
                else{
                    result = false;
                }
            }else {
                result = false;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }


    void  CreatePath(){
        Context con = getApplicationContext();
        String Path = con.getFilesDir().getPath();
        File file = new File(Path + "/BITree.sqlite");
        if(!file.exists()){
            if(file.mkdirs()){
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    finish();
                }
            }
            else {
                finish();
            }
        }
        else{
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
            db.close();
        }
    }
    /*
    * 执行创建空表任务
    * */
    void DbCreateTable(){
        Context con = getApplicationContext();
        String Path = con.getFilesDir().getPath();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
        if(!DbUserinfoCheck()) {
            db.execSQL("create table userinfo(userid char(10),username varchar(20),lastLogtime datetime);");
        }
        if(!DbMessageCheck()) {
            db.execSQL("create table message(messageid char(10),messagepostdatetime datetime,messagesenderid char(10)," +
                    "messagesendername varchar(20),messagecontent text,messageapprove int,messagecomment int,messageapproved int);");
        }
        db.close();
    }

    /*
    * User第一次登陆判断处理
    * */
    void UserLogDeal(){
        Context con = getApplicationContext();
        String Path = con.getFilesDir().getPath();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree",null);
        String UserCount = "select count(*) as c from userinfo;";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(UserCount, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count == 0){
                    TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
                    String re = tm.getDeviceId();
                    UserLogbyIMEI userLogbyIMEI = new UserLogbyIMEI(re);
                    JSONObject jsonObject = userLogbyIMEI.GetResult();

                    if(jsonObject.getInteger("ReturnCode").equals(2)) {
                        Intent intent = new Intent(MainActivity.this, UserRegister.class);
                        intent.putExtra("Path", Path);
                        startActivity(intent);
                    }else{
                        db = SQLiteDatabase.openOrCreateDatabase(Path + "/BITree", null);
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date = sDateFormat.format(new java.util.Date());
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("userid",jsonObject.getString("UserID"));
                        contentValues.put("username",jsonObject.getString("UserName"));
                        contentValues.put("lastLogtime",date);
                        db.insert("userinfo", null, contentValues);
                        Toast.makeText(getApplicationContext(),
                                "欢迎回来," + jsonObject.getString("UserName"), Toast.LENGTH_LONG)
                                .show();
                    }
                }
                else{

                }
            }
            cursor.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        db.close();
    }

    /*
    * 网络状态检测
    * 如果能连通，返回true，否则返回false
    * */
    boolean NetTest(){
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),
                    "亲，网络连了么？", Toast.LENGTH_LONG)
                    .show();
            return false;
        }
    }
}