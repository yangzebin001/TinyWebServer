package com.yzb;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description handle Connector Thread Pool
 * @Date 2021/1/27 下午12:07
 * @Creater BeckoninGshy
 */
public class ConnectorThreadPool {
    private static ThreadPoolExecutor tpe = new ThreadPoolExecutor(20,100,60,TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));
    public static void run(Runnable runnable){
        tpe.execute(runnable);
    }
}
