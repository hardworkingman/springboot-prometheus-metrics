package com.songxc.springboot.prometheus.metrics.threadpool;

import com.songxc.springboot.prometheus.metrics.config.MetricsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ThreadPoolMetricsScheduler implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolMetricsScheduler.class);

    private final MetricsProperties metricsProperties;

    private final ThreadPoolMetricsMonitor threadPoolMetricsMonitor;

    public ThreadPoolMetricsScheduler(MetricsProperties metricsProperties, ThreadPoolMetricsMonitor threadPoolMetricsMonitor) {
        this.metricsProperties = metricsProperties;
        this.threadPoolMetricsMonitor = threadPoolMetricsMonitor;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedRateTask(this::metrics, metricsProperties.getThreadPoolInterval());
    }

    private void metrics() {
        try {
            threadPoolMetricsMonitor.metrics();
        } catch (Exception e) {
            LOGGER.error("MetricsTaskDecorator metrics error");
        }
    }
}
