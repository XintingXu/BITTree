package cn.xuxinting.bitree;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.xuxinting.NewUserReg.NewUserReg;

public class UserRegister extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Button ButtonReg = (Button)findViewById(R.id.InputSure);

        ButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserName = null;
                String UserPass = null;
                String UserSex = null;
                String UserPhoneIMEI = null;
                int UserPhoneBond = 0;
                String Path = null;
                Intent intent = getIntent();
                Path = intent.getStringExtra("Path");

                MessageClear();
                UserName = UserNameGet();
                UserPass = UserPasswordGet();
                UserSex = UserSexGet();
                UserPhoneBond = UserBondGet();
                UserPhoneIMEI = UserPhoneIMEIGet();

                if(UserInputCheck(UserName,UserPass)){
                    if(DoCreate(Path,UserName,UserPass,UserSex,UserPhoneBond,UserPhoneIMEI) == 0){
                        finish();
                    }
                    else{

                    }
                }
                else{
                    TextView MessageShow = (TextView)findViewById(R.id.MessageShow);
                    MessageShow.setText("请更正后再试");
                }

            }
        });
    }

    boolean UserInputCheck(String UserName,String UserPass){
        boolean ret = true;
        TextView UserNameInputError = (TextView)findViewById(R.id.InputNameError);
        TextView UserPasswordInputError = (TextView)findViewById(R.id.InputPasswordError);
        TextView UserSexInputError = (TextView)findViewById(R.id.InputSexError);
        RadioButton UserSexMan = (RadioButton)findViewById(R.id.InputSexMan);
        RadioButton UserSexFemale = (RadioButton)findViewById(R.id.InputSexFemale);
        RadioButton UserSexNone = (RadioButton)findViewById(R.id.InputSexNone);

        if(UserName.length() >= 20){
            String UserNameInputAlert = "名字过长，请限制在20个字符";
            UserNameInputError.setText(UserNameInputAlert);
            ret = false;
        }
        if(UserName.length() == 0){
            String UserNameInputAlert = "请输入一个昵称";
            UserNameInputError.setText(UserNameInputAlert);
            ret = false;
        }
        if(UserPass.length() == 0){
            String UserPasswordAlert = "为了方便找回用户，请输入密码";
            UserPasswordInputError.setText(UserPasswordAlert);
            ret = false;
        }
        if(!(UserSexMan.isChecked() || UserSexFemale.isChecked() || UserSexNone.isChecked())){
            String UserSexAlert = "请选择性别信息";
            UserSexInputError.setText(UserSexAlert);
            ret = false;
        }
        return ret;
    }

    String UserNameGet(){
        TextView UserNameInput = (TextView)findViewById(R.id.InputName);
        String UserName = UserNameInput.getText().toString();
        return UserName;
    }
    String UserPasswordGet(){
        TextView UserPasswordInput = (TextView)findViewById(R.id.InputPassword);
        String UserPass = UserPasswordInput.getText().toString();
        return UserPass;
    }
    String UserSexGet(){
        String UserSex;
        RadioButton UserSexMan = (RadioButton)findViewById(R.id.InputSexMan);
        RadioButton UserSexFemale = (RadioButton)findViewById(R.id.InputSexFemale);
        RadioButton UserSexNone = (RadioButton)findViewById(R.id.InputSexNone);
        if(UserSexMan.isChecked()){
            UserSex = "男";
        }
        else if(UserSexFemale.isChecked()){
            UserSex = "女";
        }
        else if(UserSexNone.isChecked()){
            UserSex = "无";
        }
        else{
            UserSex = "无";
        }
        return UserSex;
    }
    int UserBondGet(){
        int UserPhoneBond;
        CheckBox UserPhoneBondInput = (CheckBox)findViewById(R.id.InputBondPhone);
        if(UserPhoneBondInput.isChecked()){
            UserPhoneBond = 1;
        }
        else{
            UserPhoneBond = 0;
        }
        return UserPhoneBond;
    }
    String UserPhoneIMEIGet(){
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String re = tm.getDeviceId();
        return re;
    }

    int DoCreate(String path,String UserName,String UserPass,String UserSex,int UserPhoneBond,String UserPhone){
        TextView MessageShow = (TextView)findViewById(R.id.MessageShow);
        NewUserReg newUser = new NewUserReg(path,UserName,UserPass,UserPhoneBond,UserSex,UserPhone);
        if(newUser.UserInsert()){
            Toast.makeText(getApplicationContext(),
                    "创建成功", Toast.LENGTH_LONG)
                    .show();
        }
        else{
            MessageShow.setText("创建失败");
        }

        return 0;
    }

    void MessageClear(){
        TextView UserNameInputError = (TextView)findViewById(R.id.InputNameError);
        TextView UserPasswordInputError = (TextView)findViewById(R.id.InputPasswordError);
        TextView UserSexInputError = (TextView)findViewById(R.id.InputSexError);
        TextView MessageShow = (TextView)findViewById(R.id.MessageShow);

        MessageShow.setText(" ");
        UserNameInputError.setText(" ");
        UserPasswordInputError.setText(" ");
        UserSexInputError.setText(" ");
    }
}
