package cn.xuxinting.NetCheck;

import java.io.IOException;


public class NetCheck {
    public boolean Check(){
        try {
            String ip = "http://bitree.xuxinting.cn";// 测试到服务器域名
            Process p = Runtime.getRuntime().exec("ping -c 4 -w 80 " + ip);
            int status = p.waitFor();
            if (status <= 2) {
                //result = "successful~";
                return true;
            } else {
                //result = "failed~ cannot reach the IP address";
                return false;
            }
        } catch (IOException e) {
            //result = "failed~ IOException";
            return false;
        } catch (InterruptedException e) {
            //result = "failed~ InterruptedException";
            return false;
        }
    }
}
