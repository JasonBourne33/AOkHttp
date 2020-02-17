package com.dn_alan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dn_alan.myapplication.net.Call;
import com.dn_alan.myapplication.net.Callback;
import com.dn_alan.myapplication.net.DNHttpClient;
import com.dn_alan.myapplication.net.Request;
import com.dn_alan.myapplication.net.RequestBody;
import com.dn_alan.myapplication.net.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    DNHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new DNHttpClient();
    }

    public void get(View view) {
        Request request = new Request.Builder()
                .url("http://www.kuaidi100.com/query?type=yuantong&postid=11111111111")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "get响应体: " + response.getBody());

            }
        });
    }

    public void post(View view) {
        RequestBody body = new RequestBody()
                .add("city", "长沙")
                .add("key", "13cb58f5884f9749287abbead9c658f2");
        Request request = new Request.Builder().url("http://restapi.amap" +
                ".com/v3/weather/weatherInfo").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"post响应体: " + response.getBody());
            }
        });
    }
}
