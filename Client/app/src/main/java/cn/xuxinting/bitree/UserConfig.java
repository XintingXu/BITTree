package cn.xuxinting.bitree;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.*;

import cn.xuxinting.CheckCodeMake.ChangeUserNameCheck;
import cn.xuxinting.CheckCodeMake.ChangeUserPassCheck;
import cn.xuxinting.CheckRetContentCheck.ChangeUserNameJsonCheck;
import cn.xuxinting.InfoShow.GetUserID;
import cn.xuxinting.InfoShow.GetUserName;
import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.NetWork.NetDeal;
import cn.xuxinting.PassGenerate.PasswordGenerate;
import cn.xuxinting.UserInfoChange.ChangeName;


public class UserConfig extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);
        TextView UserID = (TextView)findViewById(R.id.UserIDShow);
        final EditText UserName = (EditText)findViewById(R.id.UserNameChange);
        final EditText UserPassword = (EditText)findViewById(R.id.UserPasswordChange);
        Button InputSure = (Button)findViewById(R.id.InputSure);

        Intent intent = getIntent();
        final String Path = intent.getStringExtra("Path");

        GetUserID getUserID = new GetUserID(Path);
        UserID.setText(getUserID.getUserID());

        GetUserName getUserName = new GetUserName(Path);
        UserName.setText(getUserName.getUserName());

        InputSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView UserInputError = (TextView)findViewById(R.id.NameInputError);
                UserInputError.setText(" ");

                String UserNameInput = UserName.getText().toString();
                String UserPasswordInput = UserPassword.getText().toString();

                if(UserNameInput.length() != 0){
                    UserNameInput = UserNameInput.replaceAll(";","").replaceAll("&", "&").replaceAll("<","<").replaceAll(">",">").replaceAll("'","")
                            .replaceAll("--", " ").replaceAll("/", "").replaceAll("%","");
                    UserName.setText(UserNameInput);
                    ChangeNameJsonMake changeNameJsonMake = new ChangeNameJsonMake(Path,UserNameInput);
                    JSONObject post = changeNameJsonMake.getJSON();
                    NetDeal netDeal = new NetDeal(post);
                    post = netDeal.getJSON();
                    ChangeUserNameJsonCheck changeUserNameJsonCheck = new ChangeUserNameJsonCheck(post);
                    if(changeUserNameJsonCheck.Check()) {
                        if (post.getInteger("ReturnCode").equals(1)) {
                            Toast.makeText(getApplicationContext(),
                                    "昵称修改成功", Toast.LENGTH_LONG)
                                    .show();
                            ChangeName changeName = new ChangeName(UserNameInput);
                            changeName.change();
                        } else if (post.getInteger("ReturnCode").equals(3)) {
                            UserInputError = (TextView) findViewById(R.id.NameInputError);
                            UserInputError.setText("名字已重复，请修改");
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "服务器离家出走了。。。", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "服务器被外星人劫持了。。。", Toast.LENGTH_LONG)
                                .show();
                    }
                }
                if(UserPasswordInput.length() != 0){
                    PasswordGenerate passwordGenerate = new PasswordGenerate(UserPasswordInput);
                    ChangePassJSONMake changePassJSONMake = new ChangePassJSONMake(Path,passwordGenerate.GetPassword());
                    JSONObject post = changePassJSONMake.getJSON();
                    NetDeal netDeal = new NetDeal(post);
                    post = netDeal.getJSON();
                    cn.xuxinting.CheckRetContentCheck.ChangeUserPassCheck changeUserPassCheck =
                            new cn.xuxinting.CheckRetContentCheck.ChangeUserPassCheck(post);
                    if(changeUserPassCheck.Check()) {
                        if (post.getInteger("ReturnCode").equals(1)) {
                            Toast.makeText(getApplicationContext(),
                                    "密码修改成功", Toast.LENGTH_LONG)
                                    .show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "服务器离家出走了。。。", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "服务器被外星人劫持了。。。", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        });
    }
}

class ChangeNameJsonMake{
    String UserID = null;
    String UserTargetName = null;
    JSONObject Target = new JSONObject();
    String Path = null;
    int Num;
    String CheckCode = null;
    String FuncCode = "UserChangeName";

    public ChangeNameJsonMake(String Path,String targetName){
        this.UserTargetName = targetName;
        this.Path = Path;
        GetUserID getUserID = new GetUserID(Path);
        UserID = getUserID.getUserID();
    }

    public JSONObject getJSON(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        ChangeUserNameCheck changeUserNameCheck = new ChangeUserNameCheck(Num,UserID,UserTargetName);
        CheckCode = changeUserNameCheck.GetContentCheck();
        Target.put("Num",Num);
        Target.put("FuncCode",FuncCode);
        Target.put("UserID",UserID);
        Target.put("UserTargetName",UserTargetName);
        Target.put("ContentCheck", CheckCode);
        return Target;
    }
}

class ChangePassJSONMake{
    int Num;
    String FuncCode = "UserChangePasword";
    String UserID = null;
    String UserTargetPassword = null;
    String ContentCheck = null;
    String Path = null;
    JSONObject targrt = new JSONObject();

    public ChangePassJSONMake(String Path,String userTargetPassword){
        this.Path = Path;
        this.UserTargetPassword = userTargetPassword;
        GetUserID getUserID = new GetUserID(Path);
        UserID = getUserID.getUserID();
    }

    void make(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        ChangeUserPassCheck changeUserPassCheck = new ChangeUserPassCheck(Num,UserID,UserTargetPassword);
        ContentCheck = changeUserPassCheck.GetContentCheck();
        targrt.put("Num",Num);
        targrt.put("FuncCode",FuncCode);
        targrt.put("UserID", UserID);
        targrt.put("UserTargetPassword",UserTargetPassword);
        targrt.put("ContentCheck",ContentCheck);
    }

    public JSONObject getJSON(){
        make();
        return targrt;
    }
}