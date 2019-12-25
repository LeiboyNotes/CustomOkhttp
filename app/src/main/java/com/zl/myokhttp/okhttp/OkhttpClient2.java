package com.zl.myokhttp.okhttp;

public class OkhttpClient2 {

    private Dispatcher2 dispatcher;
    private boolean isCanceled;
    int recount ;//

    public OkhttpClient2() {
        this(new Builder());
    }

    public OkhttpClient2(Builder builder) {
        dispatcher = builder.dispatcher;
        isCanceled = builder.isCanceled;
        recount = builder.recount;
    }

    public int getRecount(){
        return recount;
    }

    public boolean getCanceled() {
        return isCanceled;
    }

    public final static class Builder {
        private Dispatcher2 dispatcher;
        private boolean isCanceled;

        int recount = 3;//重试次数

        public Builder() {
            dispatcher = new Dispatcher2();
        }

        public Builder dispatcher() {
            this.dispatcher = dispatcher;
            return this;
        }

        //用户取消请求  为true
        public Builder canceled() {
            isCanceled = true;
            return this;
        }
        public Builder setReCount(int recount) {
            this.recount = recount;
            return this;
        }

        public OkhttpClient2 build() {
            return new OkhttpClient2(this);
        }
    }

    public Call2 newCall(Request2 request2) {
        return new RealCall2(this, request2);
    }

    public Dispatcher2 dispatcher() {
        return dispatcher;
    }
}
