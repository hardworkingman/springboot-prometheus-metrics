package com.songxc.springboot.prometheus.metrics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "metrics")
public class MetricsProperties {

    /**
     * 线程池定时打点时间 ms
     */
    private int threadPoolInterval = 5000;

    public int getThreadPoolInterval() {
        return threadPoolInterval;
    }

    public void setThreadPoolInterval(int threadPoolInterval) {
        this.threadPoolInterval = threadPoolInterval;
    }
}
