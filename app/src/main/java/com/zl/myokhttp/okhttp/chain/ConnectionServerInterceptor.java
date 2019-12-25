package com.zl.myokhttp.okhttp.chain;

import android.util.Log;

import com.zl.myokhttp.okhttp.Request2;
import com.zl.myokhttp.okhttp.Response2;
import com.zl.myokhttp.okhttp.SocketRequestServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConnectionServerInterceptor implements Interceptor2 {

    private final String TAG = ConnectionServerInterceptor.class.getSimpleName();

    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {

        SocketRequestServer srs = new SocketRequestServer();
        Request2 request2 = chain2.gerRequest();
        Socket socket = new Socket(srs.getHost(request2), srs.getPort(request2));
        //todo 请求
        OutputStream os = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
        String requestAll = srs.getRequestHeaderAll(request2);
        Log.d(TAG, "requestAll:" + requestAll);
        bufferedWriter.write(requestAll);
        bufferedWriter.flush();

        //todo 响应
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        String readerLine = null;
//                        while (true) {
//                            try {
//                                if ((readerLine = bufferedReader.readLine()) != null) {
//                                    Log.d(TAG, "服务器的响应:" + readerLine);
//                                } else {
//                                    return;
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//        ).start();

        Response2 response2 = new Response2();
//        response2.setBody("流程走通");

        //取出响应码
        String readerLine = bufferedReader.readLine();//读取第一行
        String[] strings = readerLine.split(" ");
        response2.setStatus(Integer.parseInt(strings[1]));
        //取出响应体  空行区分
        String readLine = null;
        try {
            while ((readLine = bufferedReader.readLine()) != null) {
                if ("".equals(readLine)) {
                    response2.setBody(bufferedReader.readLine());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response2;
    }
}
