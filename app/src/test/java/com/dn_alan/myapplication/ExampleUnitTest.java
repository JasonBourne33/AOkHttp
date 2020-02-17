package com.dn_alan.myapplication;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        String url = "http://www.baidu.com";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()  //默认为GET请求，可以不写
                .build();
        final Call call = client.newCall(request);
        try {
            call.execute();  //同步
        } catch (IOException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback() {   //异步
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


//        ================拦截器==================
//        OkHttpClient client1 = new OkHttpClient().newBuilder().addInterceptor().build();  //应用拦截器
//        OkHttpClient client2 = new OkHttpClient().newBuilder().addNetworkInterceptor().build(); //网络拦截器


        Socket socket = null;
        try {
            // socket = SSLSocketFactory.getDefault().createSocket("wwww.baidu.com", 443);
            socket = new Socket("www.weather.com.cn", 80);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(("GET /weather/101250101.shtml HTTP/1.1\r\n" +
                    "Host: www.weather.com.cn\r\n\r\n").getBytes());
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = socket.getInputStream().read(bytes)) != -1) {
                String s = new String(bytes, 0, len);
                System.out.println(s);
            }

           /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String result = null;
            while ((result = bufferedReader.readLine()) != null ) {
                System.out.println(result);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(1111);
    }
}