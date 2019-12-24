package com.zl.myokhttp.okhttp;

import java.util.HashMap;
import java.util.Map;

public class Request2 {

    public static final String GET = "get";
    public static final String POST = "post";

    private String url;
    private String requestMethod = GET;
    private Map<String, String> mHeaderList = new HashMap<>();

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public Map<String, String> getmHeaderList() {
        return mHeaderList;
    }

    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url = builder.url;
    }

    public final static class Builder {

        private String url;
        private String requestMethod = GET;
        private Map<String, String> mHeaderList = new HashMap<>();

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            this.requestMethod = GET;
            return this;
        }

        public Builder post() {
            this.requestMethod = POST;
            return this;
        }

        public Builder addRequestHeader(String key, String value) {
            mHeaderList.put(key, value);
            return this;
        }

        public Request2 build() {
            return new Request2(this);
        }
    }

}
