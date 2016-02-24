package com.pyystone.apkintentjump.http;

import org.apache.http.client.HttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class HttpManager {
    public static void httpGet(String str,HttpCallBack callBack) {
        URL url = null;
        try {
            url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            callBack.HttpResult(connection.getResponseCode(),getStringFromInputStream(inputStream));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer,0,len);
        }
        is.close();
        String state = os.toString();
        os.close();
        return state;
    }

    public interface HttpCallBack{
        void HttpResult(int resultCode,String response);
    }
}
