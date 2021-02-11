package com.yzb.common;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description handle Connector Thread Pool
 * @Date 2021/1/27 下午12:07
 * @Creater BeckoninGshy
 */
public class CommonThreadPool {
    private static ThreadPoolExecutor tpe = new ThreadPoolExecutor(20,100,60,TimeUnit.SECONDS,new LinkedBlockingDeque<>());
    public static void run(Runnable runnable){
        tpe.execute(runnable);
    }

    public static void shutdown() {
        tpe.shutdown();
        while(!tpe.isShutdown());
    }
}
