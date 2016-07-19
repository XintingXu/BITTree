package cn.xuxinting.NetWork;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

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

public class NetDeal{
    JSONObject post = null;
    JSONObject get = null;

    public NetDeal(JSONObject Post){
        this.post = Post;
    }

    public JSONObject getJSON(){
        NetGet();
        return get;
    }

    void NetGet(){
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Callable c1 = new NetChange(post);
        Future f1 = pool.submit(c1);
        try {
            Object obj = f1.get();
            String jsonString = obj.toString();
            if(jsonString.length() == 0){
                get = new JSONObject();
                get.put("ReturnCode",0);
            }else {
                get = JSONObject.parseObject(jsonString);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
}

class  NetChange implements Callable{
    JSONObject post = null;
    String Return = null;

    public NetChange(JSONObject json){
        this.post = json;
    }

    void Network() {
        String httpUrl = "http://bitree.xuxinting.cn/Bitbitree";
        HttpPost httpRequest = new HttpPost(httpUrl);
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = null;

        NameValuePair info = new BasicNameValuePair("bitree", post.toString());
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(info);
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8"));
        } catch (UnsupportedEncodingException e) {

        }

        try {
            httpResponse = httpclient.execute(httpRequest);
        } catch (IOException e) {

        }

        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                StringBuffer sb = new StringBuffer();
                HttpEntity entity = httpResponse.getEntity();
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is,"UTF-8"));
                String data = "";
                while ((data = br.readLine()) != null) {
                    sb.append(data);
                }
                Return = sb.toString();
            } catch (IOException e){
                e.printStackTrace();
            }
        }else{
            Return = " ";
        }
    }

    @Override
    public JSONObject call(){
        Network();
        JSONObject GetJson = new JSONObject();
        try {
            GetJson = (JSONObject) JSONObject.parse(Return);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return GetJson;
    }
}