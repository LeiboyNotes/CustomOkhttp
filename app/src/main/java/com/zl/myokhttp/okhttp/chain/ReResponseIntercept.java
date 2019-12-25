package com.zl.myokhttp.okhttp.chain;

import android.util.Log;

import com.zl.myokhttp.okhttp.OkhttpClient2;
import com.zl.myokhttp.okhttp.RealCall2;
import com.zl.myokhttp.okhttp.Response2;

import java.io.IOException;

/**
 * 重试拦截器
 */
public class ReResponseIntercept implements Interceptor2 {
    @Override
    public Response2 doNext(Chain2 chain2) throws IOException {
        Log.d("ReResponseIntercept","我是重试拦截器  执行了");
        IOException ioException = null;
        ChainManager chainManager = (ChainManager) chain2;
        RealCall2 realCall2 = chainManager.getCall();
        OkhttpClient2 client2 = realCall2.getOkhttpClient2();
        if (client2.getRecount() != 0) {
            for (int i = 0; i < client2.getRecount(); i++) {
                try {
                    Log.d("ReResponseIntercept","我是重试拦截器  我要放回response2");
                    Response2 response = chain2.getResponse(chainManager.gerRequest());
                    return response;
                } catch (IOException e) {
                    ioException = e;
                }

            }
        }
        return null;
    }
}
