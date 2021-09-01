package com.songxc.springboot.prometheus.metrics.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadPoolMetricsHolder implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolMetricsHolder.class);

    private List<ThreadPoolMetrics> metrics = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, ThreadPoolExecutor> executorMap = applicationContext.getBeansOfType(ThreadPoolExecutor.class);
        if (!executorMap.isEmpty()) {
            executorMap.forEach((key, executor) -> {
                ThreadPoolMetrics gaugeMetrics = new ThreadPoolMetrics(executor, key);
                metrics.add(gaugeMetrics);
            });
        }
        Map<String, ThreadPoolTaskExecutor> taskExecutorMap = applicationContext.getBeansOfType(ThreadPoolTaskExecutor.class);
        if (!taskExecutorMap.isEmpty()) {
            taskExecutorMap.forEach((key, executor) -> {
                ThreadPoolMetrics gaugeMetrics = new ThreadPoolMetrics(executor.getThreadPoolExecutor(), key);
                metrics.add(gaugeMetrics);
            });
        }
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("thread pool metrics were initialized");
        }
    }

    public void metrics() {
        metrics.forEach(ThreadPoolMetrics::metrics);
    }
}
