package com.zl.myokhttp.okhttp;

import java.util.HashMap;
import java.util.Map;

public class Request2 {

    public static final String GET = "get";
    public static final String POST = "post";

    private String url;
    private String requestMethod = GET;
    private Map<String, String> mHeaderList = new HashMap<>();
    private RequestBody2 requestBody2;

    public String getUrl() {
        return url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public RequestBody2 getRequestBody2(){
        return requestBody2;
    }
    public Map<String, String> getmHeaderList() {
        return mHeaderList;
    }

    public Request2() {
        this(new Builder());
    }

    public Request2(Builder builder) {
        this.url = builder.url;
        this.mHeaderList = builder.mHeaderList;
        this.requestMethod = builder.requestMethod;
        this.requestBody2 = builder.requestBody2;
    }

    public final static class Builder {

        private String url;
        private String requestMethod = GET;
        private Map<String, String> mHeaderList = new HashMap<>();
        private RequestBody2 requestBody2;
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            this.requestMethod = GET;
            return this;
        }

        public Builder post(RequestBody2 requestBody2) {
            this.requestMethod = POST;
            this.requestBody2 = requestBody2;
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
