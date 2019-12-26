package com.zl.myokhttp.okhttp;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Dispatcher2 {
    private int maxRequests = 64;//同时访问任务  最大限制64个
    private int maxRequestsPerHost = 5;//同时访问同一个服务器域名  最大限制5个

    private Deque<RealCall2.AsyncCall2> runningAsyncCalls = new ArrayDeque<>();//存储运行的队列
    private Deque<RealCall2.AsyncCall2> readyAsyncCalls = new ArrayDeque<>();//存储等待的队列

    public void enqueue(RealCall2.AsyncCall2 call) {
        //同时运行的队列数 必须小于配置的64  同时访问同一个服务器域名  不能超过5个
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(call) < maxRequestsPerHost) {
            runningAsyncCalls.add(call);
            executorService().execute(call);
        } else {
            readyAsyncCalls.add(call);
        }
    }

    public ExecutorService executorService() {
        ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("自定义线程");
                        thread.setDaemon(false);//不是守护线程
                        return thread;
                    }
                });
        return executorService;
    }

    /**
     * 判断AsyncCall2中的Host，在运行中的队列中，计数，然后返回
     *
     * @param call
     * @return
     */
    private int runningCallsForHost(RealCall2.AsyncCall2 call) {
        int count = 0;
        if (runningAsyncCalls.isEmpty()) {
            return 0;
        }
        SocketRequestServer srs = new SocketRequestServer();

        //遍历运行队列里面的所有任务，取出任务  host == call.host + 1
        for (RealCall2.AsyncCall2 runningAsyncCall : runningAsyncCalls) {
            if (srs.getHost(runningAsyncCall.getRequest2()).equals(call.getRequest2())) {
                count++;
            }
        }
        return count;
    }

    public void finished(RealCall2.AsyncCall2 call2) {
        //当前运行的任务回收
        runningAsyncCalls.remove(call2);
        //等待队列是否有任务  如果有任务要执行
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        //把等待队列中的任务  给  移动运行队列
        for (RealCall2.AsyncCall2 readyAsyncCall : readyAsyncCalls) {
            readyAsyncCalls.remove(readyAsyncCall);//
            runningAsyncCalls.add(readyAsyncCall);//将删除的等待队列加入到运行队列

            //开始执行任务
            executorService().execute(readyAsyncCall);

        }
    }
}
