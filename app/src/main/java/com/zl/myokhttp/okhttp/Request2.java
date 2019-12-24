package com.zl.myokhttp.okhttp;

public class Request2 {

    private String url;

    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url = builder.url;
    }

    public final static class Builder {

        private String url;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Request2 build() {
            return new Request2(this);
        }
    }

}
