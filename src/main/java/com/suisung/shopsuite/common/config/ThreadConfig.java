package com.suisung.shopsuite.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(20, 200,
                10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000000),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }

}
