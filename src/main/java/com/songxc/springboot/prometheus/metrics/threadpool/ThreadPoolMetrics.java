package com.songxc.springboot.prometheus.metrics.threadpool;

import io.prometheus.client.Gauge;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolMetrics {

    /**
     * 线程池
     */
    private ThreadPoolExecutor executor;

    /**
     * 线程池名称
     */
    private String name;

    /**
     * 核心线程数
     */
    private Gauge CORE_POOL_SIZE_GAUGE;

    /**
     * 最大线程数
     */
    private Gauge MAX_POOL_SIZE_GAUGE;

    /**
     * 活跃线程数
     */
    private Gauge ACTIVE_THREAD_COUNT_GAUGE;

    /**
     * 当前线程池大小
     */
    private Gauge CURRENT_POOL_SIZE_GAUGE;

    /**
     * 最高线程数
     */
    private Gauge LARGEST_POOL_SIZE_GAUGE;

    /**
     * 队列任务数
     */
    private Gauge QUEUE_SIZE_GAUGE;

    public ThreadPoolMetrics(ThreadPoolExecutor executor, String name) {
        this.executor = executor;
        this.name = name;
        initGauge();
    }

    private void initGauge() {
        CORE_POOL_SIZE_GAUGE = Gauge.build()
                .labelNames("name")
                .name("core_pool_size")
                .help("core_pool_size")
                .register();
        MAX_POOL_SIZE_GAUGE = Gauge.build()
                .labelNames("name")
                .name("max_pool_size")
                .help("max_pool_size")
                .register();
        ACTIVE_THREAD_COUNT_GAUGE = Gauge.build()
                .labelNames("name")
                .name("active_thread_count")
                .help("active_thread_count")
                .register();
        CURRENT_POOL_SIZE_GAUGE = Gauge.build()
                .labelNames("name")
                .name("current_pool_size")
                .help("current_pool_size")
                .register();
        LARGEST_POOL_SIZE_GAUGE = Gauge.build()
                .labelNames("name")
                .name("largest_pool_size")
                .help("largest_pool_size")
                .register();
        QUEUE_SIZE_GAUGE = Gauge.build()
                .labelNames("name")
                .name("queue_size")
                .help("queue_size")
                .register();
    }

    void metrics() {
        CORE_POOL_SIZE_GAUGE.labels(name).set(executor.getCorePoolSize());
        MAX_POOL_SIZE_GAUGE.labels(name).set(executor.getMaximumPoolSize());
        ACTIVE_THREAD_COUNT_GAUGE.labels(name).set(executor.getActiveCount());
        CURRENT_POOL_SIZE_GAUGE.labels(name).set(executor.getPoolSize());
        LARGEST_POOL_SIZE_GAUGE.labels(name).set(executor.getLargestPoolSize());
        QUEUE_SIZE_GAUGE.labels(name).set(executor.getQueue().size());
    }
}
