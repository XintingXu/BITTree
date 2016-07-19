package cn.xuxinting.bitree;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import cn.xuxinting.CheckCodeMake.NewMessageCheck;
import cn.xuxinting.CheckRetContentCheck.NewMessageJsonCheck;
import cn.xuxinting.CheckRetContentCheck.NewUserJsonCheck;
import cn.xuxinting.InfoShow.GetUserID;
import cn.xuxinting.InfoShow.GetUserName;
import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.Message.MessageToSQLite;
import cn.xuxinting.NetWork.NetDeal;

public class MessageCreate extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_create);

        Intent intent = getIntent();
        final String Path = intent.getStringExtra("Path");

        Button InputOK = (Button)findViewById(R.id.InputOK);
        InputOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView Input = (TextView)findViewById(R.id.MessageInput);
                String MessageInput = Input.getText().toString();
                MessageInput = MessageInput.replaceAll(";","").replaceAll("&", "&").replaceAll("<","<").replaceAll(">",">").replaceAll("'","")
                        .replaceAll("--", " ").replaceAll("/", "").replaceAll("%","");
                Input.setText(MessageInput);
                if(MessageInput.length() <= 10){
                    TextView alert = (TextView)findViewById(R.id.MessageShow);
                    alert.setText(" ");
                    alert.setText("消息太短啦，我拒绝发布");
                }else{
                    NewMessageJSONMake newMessageJSONMake = new NewMessageJSONMake(Path,MessageInput);
                    JSONObject post = newMessageJSONMake.getJSON();
                    NetDeal netDeal = new NetDeal(post);
                    post = netDeal.getJSON();
                    NewMessageJsonCheck newMessageJsonCheck = new NewMessageJsonCheck(post);
                    if(newMessageJsonCheck.Check()){
                        if(post.getInteger("ReturnCode").equals(1)){
                            Toast.makeText(getApplicationContext(),
                                    "树洞消息发布成功", Toast.LENGTH_LONG)
                                    .show();
                            GetUserID getUserID = new GetUserID(Path);
                            GetUserName getUserName = new GetUserName(Path);
                            MessageToSQLite messageToSQLite = new MessageToSQLite(Path,post.getString("MessageID"),
                                    getUserID.getUserID(),getUserName.getUserName(),"无",MessageInput,0,0);
                            messageToSQLite.DoInsert();
                            Input.setText(" ");
                        }else{
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

class NewMessageJSONMake{
    int Num;
    String FuncCode = "NewMessage";
    String SenderID = null;
    String Message = null;
    String ContentCheck = null;
    JSONObject jsonObject = new JSONObject();
    String Path;

    public NewMessageJSONMake(String Path,String Message){
        this.Path = Path;
        this.Message = Message;
        GetUserID getUserID = new GetUserID(Path);
        SenderID = getUserID.getUserID();
    }

    void make(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        NewMessageCheck newMessageCheck = new NewMessageCheck(Num,SenderID,Message);
        ContentCheck = newMessageCheck.GetContentCheck();
        jsonObject.put("Num",Num);
        jsonObject.put("FuncCode",FuncCode);
        jsonObject.put("SenderID",SenderID);
        jsonObject.put("Message",Message);
        jsonObject.put("ContentCheck",ContentCheck);
    }

    public JSONObject getJSON(){
        make();
        return jsonObject;
    }
}