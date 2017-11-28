package com.yjl.distributed.mq.config.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 统一线程池配置
 * 
 * @author zhaoyc@1109
 * @version 创建时间：2017年10月23日 下午7:13:58
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());// 线程的最少数量
		executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 5);// 线程的最大数量
		executor.setQueueCapacity(Runtime.getRuntime().availableProcessors() * 50);// 缓冲队列 大小
		executor.setThreadNamePrefix("distributed-mq-config-executor-");// 线程名字前缀
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
