package com.zl.myokhttp.okhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//请求体对象
public class RequestBody2 {

    //表单提交 Type
    public static final String TYPE = "application/x-www-form-urlencoded";
    private final String ENC = "utf-8";
    //请求体集合
    Map<String, String> bodys = new HashMap<>();

    /**
     * 添加请求体信息
     *
     * @param key
     * @param value
     */
    public void addBody(String key, String value) {
        try {
            bodys.put(URLEncoder.encode(key, ENC), URLEncoder.encode(value, ENC));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String getBody() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : bodys.entrySet()) {
            stringBuffer.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}
