package com.zl.myokhttp.okhttp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class SocketRequestServer {

    private final String K = " ";
    private final String VERSION = "HTTP/1.1";
    private final String GRGN = "\r\n";

    /**
     * todo 通过Request 对象 寻找到域名Host
     *
     * @param request2
     * @return
     */
    public String getHost(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            return url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPort(Request2 request2) {
        try {
            URL url = new URL(request2.getUrl());
            int port = url.getPort();
            return port == -1 ? url.getDefaultPort() : port;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     *  请求首行
     *  POST /hello/index.jsp HTTP/1.1
     *  请求头信息
     *  Host: localhost
     *  User-Agent: Mozilla/5.0 (Windows NT 5.1; rv:5.0) Gecko/20100101 Firefox/5.0
     *  Accept: text/html,application/xhtml+xml,application/xml;q=0.9,
     *  Content-Type: application/x-www-form-urlencoded
     *  Content-Length: 14
     *  这里是空行
     *  POST有请求正文
     *  username=hello
     *
     *
     * @param request2
     * @return
     */

    public String getRequestHeaderAll(Request2 request2) {
        //得到请求方式
        URL url = null;
        try {
            url = new URL(request2.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String file = url.getFile();
        // todo 拼接 请求行  GET /search?hl=zh-CN&source=hp&q=domety&aq=f&oq= HTTP/1.1
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request2.getRequestMethod())
                .append(K)
                .append(file)
                .append(K)
                .append(VERSION)
                .append(GRGN);

        //todo 拼接请求头
        if (!request2.getmHeaderList().isEmpty()) {
            Map<String, String> map = request2.getmHeaderList();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append(":")
                        .append(entry.getValue())
                        .append(GRGN);
            }
            //拼接空行
            stringBuffer.append(GRGN);
        }
        //todo 拼接请求体
        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            stringBuffer.append(request2.getRequestBody2().getBody()).append(GRGN);
        }

        return stringBuffer.toString();
    }


}
