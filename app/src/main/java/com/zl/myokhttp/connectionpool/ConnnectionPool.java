package com.zl.myokhttp.connectionpool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 连接池
 */
public class ConnnectionPool {

    //容器（双端队列）  保存连接对象
    private static Deque<HttpConnection> httpConnectionDeque = null;
    //清理的标记
    private boolean cleanRunnableFlag;

    //检测机制  每隔一分钟  就去检查 连接池里面是否有可用的（连接对象），如果不可用，就移除
    private long keepAlive;//最大允许闲置时间

    //检测机制  开启一个线程  专门检查 连接池里的（连接对象）清理连接池里的对象
    private Runnable cleanRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                //获取下一次检测时间
                long nextCheckCleanTime = clean(System.currentTimeMillis());
                if (-1 == nextCheckCleanTime) {
                    return;//while(true) 结束
                }
                if (nextCheckCleanTime > 0) {
                    //等待一段时间再去检查是否需要清理
                    try {
                        ConnnectionPool.this.wait(nextCheckCleanTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    //清理那些对象的函数
    private long clean(long currentTimeMills) {
        long recordSave = -1;//下一次的检查时间
        synchronized (this) {

            //遍历容器操作
            Iterator<HttpConnection> iterator = httpConnectionDeque.iterator();
            while (iterator.hasNext()) {
                HttpConnection httpConnection = iterator.next();
                //闲置时间的计算
                long idleTime = currentTimeMills - httpConnection.hasUserTime;
                if (idleTime > keepAlive) {
                    //移除
                    iterator.remove();

                    //关闭Socket
                    httpConnection.closeSocket();

                    continue;
                }
                //得到最长闲置时间
                if (recordSave < idleTime) {
                    recordSave = idleTime;
                }

            }
            if (recordSave >= 0) {
                return (keepAlive - recordSave);
            }
        }
        return recordSave;
    }

    //线程池
    private Executor threadPoolExecutor = new ThreadPoolExecutor(0,//核心线程数
            Integer.MAX_VALUE,//线程池最大值
            60, TimeUnit.SECONDS,//时间单位
            new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("threadPoolExecutorRun");
                    return thread;
                }
            });

    public ConnnectionPool() {
        this(1, TimeUnit.MINUTES);
    }

    public ConnnectionPool(long keepAlive, TimeUnit timeUnit) {
        this.keepAlive = timeUnit.toMillis(keepAlive);
        httpConnectionDeque = new ArrayDeque<>();
    }

    /**
     * Todo 添加连接对象
     */
    public synchronized void putConnection(HttpConnection httpConnection) {
        //一旦添加的时候  就去检查  连接池里  是否需要清理
        if (!cleanRunnableFlag) {
            cleanRunnableFlag = true;

            //开始清理
            threadPoolExecutor.execute(cleanRunnable);

        }
        httpConnectionDeque.add(httpConnection);
    }

    /**
     * todo 获取连接对象
     */

    public HttpConnection getConnection(String host, int port) {
        Iterator<HttpConnection> iterator = httpConnectionDeque.iterator();
        while (iterator.hasNext()) {//遍历容器里的连接对象
            HttpConnection httpConnection = iterator.next();
            if (httpConnection.isConnectionAction(host, port)) {

                //移除连接对象
                iterator.remove();

                //找到相同的socket  可以复用了
                return httpConnection;
            }

        }
        return null;
    }


}
