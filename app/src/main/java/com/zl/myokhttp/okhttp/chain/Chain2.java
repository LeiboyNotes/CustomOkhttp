package com.zl.myokhttp.okhttp.chain;

import com.zl.myokhttp.okhttp.Request2;
import com.zl.myokhttp.okhttp.Response2;

import java.io.IOException;

public interface Chain2 {

    Request2 gerRequest();

    Response2 getResponse(Request2 request2) throws IOException;
}
