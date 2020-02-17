package com.dn_alan.myapplication.net.chain;

import android.util.Log;

import com.dn_alan.myapplication.net.DNHttpClient;
import com.dn_alan.myapplication.net.HttpConnection;
import com.dn_alan.myapplication.net.HttpUrl;
import com.dn_alan.myapplication.net.Request;
import com.dn_alan.myapplication.net.Response;
import java.io.IOException;

public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain chain) throws IOException {
        Log.e("interceprot","连接拦截器....");
        Request request = chain.call.request();
        DNHttpClient client = chain.call.client();
        HttpUrl url = request.url();
        String host = url.getHost();
        int port = url.getPort();
        HttpConnection connection = client.connectionPool().get(host, port);
        if (null == connection) {
            connection = new HttpConnection();
        } else {
            Log.e("call", "使用连接池......");
        }
        connection.setRequest(request);
        try {
            //下一个拦截器
            Response response = chain.proceed(connection);
            if (response.isKeepAlive()) {
                //保持连接
                client.connectionPool().put(connection);
            }
            return response;
        } catch (IOException e) {
            throw e;
        }
    }
}
