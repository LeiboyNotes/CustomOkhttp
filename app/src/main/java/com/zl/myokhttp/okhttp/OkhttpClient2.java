package com.zl.myokhttp.okhttp;

public class OkhttpClient2 {


    public OkhttpClient2() {
        this(new Builder());
    }

    public OkhttpClient2(Builder builder) {
    }

    public final static class Builder {
        public OkhttpClient2 build() {
            return new OkhttpClient2(this);
        }
    }

    public Call2 newCall(Request2 request2) {
        return null;
    }
}
