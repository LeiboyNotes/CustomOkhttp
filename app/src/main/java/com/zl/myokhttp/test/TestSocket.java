package com.zl.myokhttp.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;

import javax.net.ssl.SSLSocketFactory;

public class TestSocket {

    public static void main(String[] args) {

        System.out.println("请输入网址，然后回车");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String inputPath = br.readLine();
            URL url = new URL("https://" + inputPath);
            String host = url.getHost();//主机域名
            Socket socket = null;
            int port = 0;
            if ("HTTP".equalsIgnoreCase(url.getProtocol())) {
                port = 80;
                socket = new Socket(host, port);
            } else if ("HTTPs".equalsIgnoreCase(url.getProtocol())) {
                port = 443;
                socket = SSLSocketFactory.getDefault().createSocket(host, port);
            }
            if (socket == null) {
                System.out.println("error");
                return;
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("GET / HTTP/1.1\r\n");
            bw.write("Host: " + host + "\r\n\r\n");
            bw.flush();

            //读取响应数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String readLine = null;
                if ((readLine = bufferedReader.readLine()) != null) {
                    System.out.println("响应的数据:" + readLine);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
