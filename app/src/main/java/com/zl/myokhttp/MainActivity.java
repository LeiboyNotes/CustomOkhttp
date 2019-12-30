package com.zl.myokhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zl.myokhttp.connectionpool.ConnectionPool;
import com.zl.myokhttp.connectionpool.UseConnectionPool;
import com.zl.myokhttp.okhttp.Call2;
import com.zl.myokhttp.okhttp.Callback2;
import com.zl.myokhttp.okhttp.OkhttpClient2;
import com.zl.myokhttp.okhttp.Request2;
import com.zl.myokhttp.okhttp.RequestBody2;
import com.zl.myokhttp.okhttp.Response2;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

//    private final String PATH = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100";
//    private final String PATH = "http://www.weather.com.cn/data/cityinfo/101190408.html";
//    private final String PATH = "http://product.weather.com.cn/alarm/stationalarm.php";
    private final String PATH = "http://wthrcdn.etouch.cn/WeatherApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void userOkhttp(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url(PATH).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("OKHTTP请求失败...");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("OKHTTP请求成功...result:"+response.body().toString());
            }
        });
    }

    public void userMyOkhttp(View view) {
        OkhttpClient2 okhttpClient2 = new OkhttpClient2.Builder().build();
        RequestBody2 requestBody2 = new RequestBody2();
        requestBody2.addBody("citykey","101010100");
//        requestBody2.addBody("count","-1");
//        requestBody2.addBody("_","1381894097508");
        Request2 request2 = new Request2.Builder().post(requestBody2).url(PATH).build();
//        Request2 request2 = new Request2.Builder().post(requestBody2).url(PATH).build();
        Call2 call2 = okhttpClient2.newCall(request2);

        //执行异步
        call2.enqueue(new Callback2() {
            @Override
            public void onFailure(Call2 call, IOException e) {
                System.out.println("自定义OKHTTP请求失败...");
            }

            @Override
            public void onResponse(Call2 call, Response2 response) throws IOException {
                System.out.println("自定义OKHTTP请求成功...result:"+response.getBody()+",请求结果码:"+response.getStatus());
                Log.e("mainactivity","自定义OKHTTP请求成功...result:"+response.getBody()+",请求结果码:"+response.getStatus());
            }
        });
    }

    public void connectionPoolRequest(View view) {
        final ConnectionPool connectionPool = new ConnectionPool();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UseConnectionPool useConnectionPool = new UseConnectionPool();
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com",80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com",80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com",80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com",80);
                useConnectionPool.useConnectionPool(connectionPool,"restapi.amap.com",80);
            }
        }).start();
    }
}
