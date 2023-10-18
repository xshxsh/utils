package com.example.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 **/
@Configuration
public class ThreadPoolConfiguration {

    private static final int MAX_THREAD_POOL_SIZE = 6;
    private static final int DEFAULT_THREAD_POOL_SIZE = 2;
    private static int GENERATE_CODE_THREAD_POOL_SIZE = DEFAULT_THREAD_POOL_SIZE;

    static {
        int cores = Runtime.getRuntime().availableProcessors();
        if (cores > MAX_THREAD_POOL_SIZE) {
            GENERATE_CODE_THREAD_POOL_SIZE = MAX_THREAD_POOL_SIZE;
        }
    }

    @Bean(name = "codeGeneratorExecutor", destroyMethod = "shutdown")
    public ThreadPoolExecutor codeGeneratorExecutor() {
        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNameFormat("codeGeneratorThread-");
        ThreadFactory build = threadFactoryBuilder.build();
        return new ThreadPoolExecutor(GENERATE_CODE_THREAD_POOL_SIZE, GENERATE_CODE_THREAD_POOL_SIZE, 30L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(), build);
    }


}
