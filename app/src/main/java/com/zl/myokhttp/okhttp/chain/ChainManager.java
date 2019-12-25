package com.zl.myokhttp.okhttp.chain;

import com.zl.myokhttp.okhttp.RealCall2;
import com.zl.myokhttp.okhttp.Request2;
import com.zl.myokhttp.okhttp.Response2;

import java.io.IOException;
import java.util.List;

/**
 * 责任节点任务管理器
 */
public class ChainManager implements Chain2 {
    private final List<Interceptor2> interceptors;

    private int index;
    private final Request2 request;
    private final RealCall2 call;

    public ChainManager(List<Interceptor2> interceptors, int index, Request2 request, RealCall2 call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
        this.call = call;
    }

    public List<Interceptor2> getInterceptors() {
        return interceptors;
    }

    public int getIndex() {
        return index;
    }

    public RealCall2 getCall() {
        return call;
    }

    @Override
    public Request2 gerRequest() {
        return request;
    }

    @Override
    public Response2 getResponse(Request2 request2) throws IOException {
        //判断 index++ 计数器
        if (index >= interceptors.size()) throw new AssertionError();

        if (interceptors.isEmpty()) {
            throw new IOException("interceptors is empty");
        }


        //取出第一个拦截器
        Interceptor2 interceptor2 = interceptors.get(index);
        ChainManager chainManager = new ChainManager(interceptors, index + 1, request2, call);
        Response2 response2 = interceptor2.doNext(chainManager);
        return response2;
    }
}
