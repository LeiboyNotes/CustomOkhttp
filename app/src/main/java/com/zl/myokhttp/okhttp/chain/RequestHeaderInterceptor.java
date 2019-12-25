package com.zl.myokhttp.okhttp.chain;

import com.zl.myokhttp.okhttp.Request2;
import com.zl.myokhttp.okhttp.RequestBody2;
import com.zl.myokhttp.okhttp.Response2;
import com.zl.myokhttp.okhttp.SocketRequestServer;

import java.io.IOException;
import java.util.Map;

/**
 * 请求头拦截器处理
 */
public class RequestHeaderInterceptor implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {

        ChainManager chainManager = (ChainManager) chain2;
        Request2 request2 = chainManager.gerRequest();
        Map<String, String> mHeaderList = request2.getmHeaderList();
        mHeaderList.put("Host", new SocketRequestServer().getHost(chainManager.gerRequest()));
        if ("POST".equalsIgnoreCase(request2.getRequestMethod())) {
            //请求体 type
            mHeaderList.put("Content-Length", request2.getRequestBody2().getBody());
            mHeaderList.put("Content-Type", RequestBody2.TYPE);
        }
        return chain2.getResponse(request2);//执行下一个拦截器
    }
}
