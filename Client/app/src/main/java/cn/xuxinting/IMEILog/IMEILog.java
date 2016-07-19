package cn.xuxinting.IMEILog;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import cn.xuxinting.CheckCodeMake.IMEILogCheck;
import cn.xuxinting.JsonMake.MakeNum;
import cn.xuxinting.NetWork.NetDeal;

public class IMEILog implements Callable {
    String FuncCode = "IMEILog";
    String UserPhoneIMEI = null;
    String ContentCheck = null;
    JSONObject Json = new JSONObject();
    JSONObject RetJSON = new JSONObject();
    int ReturnCode;
    int Num;

    public IMEILog(String UserPhoneIMEI){
        this.UserPhoneIMEI = UserPhoneIMEI;
    }

    void makeJson(){
        MakeNum makeNum = new MakeNum();
        Num = makeNum.getNum();
        IMEILogCheck imeiLogCheck = new IMEILogCheck(Num,UserPhoneIMEI);
        ContentCheck = imeiLogCheck.GetContentCheck();
        Json.put("Num",Num);
        Json.put("FuncCode",FuncCode);
        Json.put("UserPhoneIMEI",UserPhoneIMEI);
        Json.put("ContentCheck",ContentCheck);
    }

    void getJson(){
        makeJson();
        NetDeal netDeal = new NetDeal(Json);
        RetJSON = netDeal.getJSON();
    }
    public JSONObject call(){
        getJson();
        return RetJSON;
    }
}
