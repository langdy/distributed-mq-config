package com.yjl.distributed.mq.config.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 同步线程池
 * 
 * @author zhaoyc
 * @version 创建时间：2018年2月2日 下午10:43:24
 */
public class ThreadPoolUtils {
    /**
     * 线程池
     */
    private static ExecutorService threadPool;

    static {
        // 配置线程池
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadFactory namedThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("eva-pool-%d").build();
        threadPool = new ThreadPoolExecutor(availableProcessors, availableProcessors * 10, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(availableProcessors * 100),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取同步线程池
     * 
     * @return
     */
    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
