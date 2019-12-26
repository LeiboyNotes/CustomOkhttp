package com.zl.myokhttp.connectionpool;

import android.nfc.Tag;
import android.util.Log;

public class UseConnectionPool {

    private static final String TAG = UseConnectionPool.class.getSimpleName();

    public void useConnectionPool(ConnnectionPool connnectionPool, String host, int port) {
        //开始模拟  请求连接器动作
        //首先从连接池里获取是否有连接池对象
        HttpConnection httpConnection = connnectionPool.getConnection(host, port);
        if (httpConnection == null) {
            httpConnection = new HttpConnection(host, port);
            Log.e(TAG, "连接池里没有连接对象   需要实例化一个对象...");
        }else {
            Log.e(TAG,"复用池  里面有一个连接对象");
        }
    }
}
