package com.zl.myokhttp.connectionpool;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * 连接对象  对Socket的封装
 */
public class HttpConnection {

    private static final String TAG = HttpConnection.class.getSimpleName();

    Socket socket;
    long hasUserTime;//连接对象 最后使用的时间

    public HttpConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过端口和域名判断是否相同
     * @param host
     * @param port
     * @return
     */
    public boolean isConnectionAction(String host, int port) {
        if (socket == null) {
            return false;
        }
        if (socket.getPort() == port && socket.getInetAddress().getHostName().equals(host)) {
            return true;
        }


        return false;
    }

    //关闭socket
    public void closeSocket(){
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,"closeSocket:IOException e:"+e.getMessage());
            }
        }
    }
}
