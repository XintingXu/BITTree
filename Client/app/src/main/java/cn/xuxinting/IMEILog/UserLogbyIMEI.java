package cn.xuxinting.IMEILog;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.xuxinting.CheckRetContentCheck.RetIMEILogJsonCheck;

public class UserLogbyIMEI {
    String UserPhoneIMEI = null;
    JSONObject Ret = new JSONObject();

    public UserLogbyIMEI(String UserPhoneIMEI){
        this.UserPhoneIMEI = UserPhoneIMEI;
    }

    void GetServerJSON(){
        ExecutorService pool = Executors.newFixedThreadPool(1);
        IMEILog imeiLog = new IMEILog(UserPhoneIMEI);
        Future future = pool.submit(imeiLog);
        try {
            Ret = (JSONObject)future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    boolean JSONCheck(){
        RetIMEILogJsonCheck retIMEILogJsonCheck = new RetIMEILogJsonCheck(Ret);
        if(retIMEILogJsonCheck.Check()){
            return true;
        }
        else{
            return false;
        }
    }

    public JSONObject GetResult(){
        GetServerJSON();
        if(JSONCheck()){
            return Ret;
        }
        else{
            JSONObject newjson = new JSONObject();
            newjson.put("ReturnCode",2);
            return newjson;
        }
    }
}
