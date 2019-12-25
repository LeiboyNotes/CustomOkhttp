package com.zl.myokhttp.okhttp.chain;

import com.zl.myokhttp.okhttp.Response2;

import java.io.IOException;

public interface Interceptor2 {

    Response2 doNext(Chain2 chain2)throws IOException;
}
