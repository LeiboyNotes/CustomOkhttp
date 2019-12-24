package com.zl.myokhttp.okhttp;

import java.io.IOException;

public class RealCall2 implements Call2 {


    private OkhttpClient2 okhttpClient2;
    private Request2 request2;

    public RealCall2(OkhttpClient2 okhttpClient2, Request2 request2) {
        this.okhttpClient2 = okhttpClient2;
        this.request2 = request2;
    }

    private boolean executed;


    @Override
    public void enqueue(Callback2 responseCallback) {

        //不能重复执行 enqueue
        synchronized (this) {
            if (executed) {
                executed = true;
                throw new IllegalStateException("Already Executed");

            }
        }
        okhttpClient2.dispatcher().enqueue(new AsyncCall2(responseCallback));
    }

    final class AsyncCall2 implements Runnable {

        private Callback2 callback2;


        public Request2 getRequest2() {
            return RealCall2.this.request2;
        }

        public AsyncCall2(Callback2 callback2) {
            this.callback2 = callback2;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                Response2 response = getResponseWithInterceptorChain();
                //如果用户取消请求  回调用户
                if (okhttpClient2.getCanceled()) {
                    signalledCallback = true;
                    callback2.onFailure(RealCall2.this, new IOException("用户Canceled"));
                } else {
                    signalledCallback = true;
                    callback2.onResponse(RealCall2.this, response);
                }
            } catch (IOException e) {
                //责任划分
                if (signalledCallback) {
                    System.out.println("用户在使用过程中出错了...");
                } else {
                    callback2.onFailure(RealCall2.this, new IOException("OKHTTP getResponseWithInterceptorChain()错误  e:" + e.toString()));
                }
            } finally {
                //回收处理
                okhttpClient2.dispatcher().finished(this);
            }

        }

        private Response2 getResponseWithInterceptorChain() {
            Response2 response2  = new Response2();
            response2.setBody("流程走通....");
            return response2;
        }
    }


}
