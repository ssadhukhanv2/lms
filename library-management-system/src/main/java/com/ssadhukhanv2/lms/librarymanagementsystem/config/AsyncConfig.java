package com.ssadhukhanv2.lms.librarymanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfig {
    // https://medium.com/@semotpan/spring-application-events-7ab5390db6dd
    // By-default processing of spring application events are handled synchronously
    // This means that publisher thread would be blocked until all listener finish processing the events
    // To process events asynchronously, i.e. non blocking we annotate
    // configuration class with @EnableAsync
    // annotate handler methods of the listener with @Async
    // we also need to expose a custom executor to the spring context

    @Value("${api.event.corePoolSize:5}")
    private int corePoolSize;
    @Value("${api.event.maxPoolSize:5}")
    private int maxPoolSize;
    @Value("${api.event.queueCapacity:50}")
    private int queueCapacity;
    @Value("${api.event.threadNamePrefix:AsyncEvents-}")
    private String threadNamePrefix;

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setThreadNamePrefix(threadNamePrefix);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
