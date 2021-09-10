package com.songxc.springboot.prometheus.metrics.threadpool;

import io.prometheus.client.Gauge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadPoolMetricsMonitor implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolMetricsMonitor.class);

    /**
     * thread pool bean name set
     */
    private static final Set<String> EXECUTOR_NAME_SET = new HashSet<>();

    /**
     * thread pool bean map
     */
    private Map<String, ThreadPoolExecutor> executorMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        EXECUTOR_NAME_SET.forEach(beanName -> {
            Object bean = applicationContext.getBean(beanName);
            addBean(beanName, bean);
        });
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("thread pool metrics were initialized");
        }
    }

    /**
     * add bean to set
     * @param beanName bean name
     * @param o bean
     */
    private void addBean(String beanName, Object o) {
        if (o instanceof ThreadPoolTaskExecutor) {
            executorMap.put(beanName, ((ThreadPoolTaskExecutor) o).getThreadPoolExecutor());
        } else if (o instanceof ThreadPoolExecutor) {
            executorMap.put(beanName, (ThreadPoolExecutor) o);
        }
    }

    public void metrics() {
        executorMap.forEach(this::metrics);
    }

    /**
     * static add bean name to set
     * @param beanName bean name
     */
    public static void addBeanName(String beanName) {
        EXECUTOR_NAME_SET.add(beanName);
    }

    /**
     * 核心线程数
     */
    private static final Gauge CORE_POOL_SIZE_GAUGE = Gauge.build()
            .labelNames("name")
            .name("core_pool_size")
            .help("core_pool_size")
            .register();

    /**
     * 最大线程数
     */
    private static final Gauge MAX_POOL_SIZE_GAUGE = Gauge.build()
            .labelNames("name")
            .name("max_pool_size")
            .help("max_pool_size")
            .register();

    /**
     * 活跃线程数
     */
    private static final Gauge ACTIVE_THREAD_COUNT_GAUGE = Gauge.build()
            .labelNames("name")
            .name("active_thread_count")
            .help("active_thread_count")
            .register();

    /**
     * 当前线程池大小
     */
    private static final Gauge CURRENT_POOL_SIZE_GAUGE = Gauge.build()
            .labelNames("name")
            .name("current_pool_size")
            .help("current_pool_size")
            .register();

    /**
     * 最高线程数
     */
    private static final Gauge LARGEST_POOL_SIZE_GAUGE  = Gauge.build()
            .labelNames("name")
            .name("largest_pool_size")
            .help("largest_pool_size")
            .register();

    /**
     * 队列任务数
     */
    private static final Gauge QUEUE_SIZE_GAUGE = Gauge.build()
            .labelNames("name")
            .name("queue_size")
            .help("queue_size")
            .register();

    private void metrics(String name, ThreadPoolExecutor executor) {
        CORE_POOL_SIZE_GAUGE.labels(name).set(executor.getCorePoolSize());
        MAX_POOL_SIZE_GAUGE.labels(name).set(executor.getMaximumPoolSize());
        ACTIVE_THREAD_COUNT_GAUGE.labels(name).set(executor.getActiveCount());
        CURRENT_POOL_SIZE_GAUGE.labels(name).set(executor.getPoolSize());
        LARGEST_POOL_SIZE_GAUGE.labels(name).set(executor.getLargestPoolSize());
        QUEUE_SIZE_GAUGE.labels(name).set(executor.getQueue().size());
    }
}
